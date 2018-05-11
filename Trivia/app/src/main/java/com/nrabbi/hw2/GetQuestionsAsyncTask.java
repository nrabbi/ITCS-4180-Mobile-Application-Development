// ITCS 4180 : Homework 2
// GetQuestionsAsyncTask.java
// Nazmul Rabbi, Dyrell Cole

package com.nrabbi.hw2;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class GetQuestionsAsyncTask extends AsyncTask<String, Void, ArrayList<Question>>
{
    private IData activity;

    GetQuestionsAsyncTask(IData activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.setTriviaReady(false);
    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {
        BufferedReader _BufferedReader = null;

        try
        {
            URL url = new URL(params[0]);
            HttpURLConnection _HttpURLConnection = (HttpURLConnection) url.openConnection();
            _HttpURLConnection.setRequestMethod("GET");
            _HttpURLConnection.connect();

            int statusCode = _HttpURLConnection.getResponseCode();

            if(statusCode == HttpURLConnection.HTTP_OK) {
                _BufferedReader = new BufferedReader(new InputStreamReader(_HttpURLConnection.getInputStream()));
                StringBuilder _StringBuilder = new StringBuilder();
                String currLine = _BufferedReader.readLine();

                while(currLine != null) {
                    _StringBuilder.append(currLine);
                    currLine = _BufferedReader.readLine();
                }

                return parseQuestions(_StringBuilder.toString());
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        finally {
            if(_BufferedReader != null) {
                try {
                    _BufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  null;
    }

    private static ArrayList<Question> parseQuestions(String jsonString) throws JSONException {
        ArrayList<Question> result = new ArrayList<Question>();

        JSONObject root = new JSONObject(jsonString);
        JSONArray questionsJson = root.getJSONArray("questions");

        for(int i = 0; i < questionsJson.length(); i++) {
            JSONObject currQuestionJSON = questionsJson.getJSONObject(i);
            Question currQuestion = new Question();

            currQuestion.setId(currQuestionJSON.getInt("id"));
            currQuestion.setText(currQuestionJSON.getString("text"));
            currQuestion.setAnswer(currQuestionJSON.getJSONObject("choices").getInt("answer") - 1);

            if(currQuestionJSON.has("image")) {
                currQuestion.setImage(currQuestionJSON.getString("image"));
            }

            JSONArray currQuestionChoicesJSON = currQuestionJSON.getJSONObject("choices").getJSONArray("choice");
            String[] currQuestionChoices = new String[currQuestionChoicesJSON.length()];

            for(int j = 0; j < currQuestionChoices.length; j++) {
                currQuestionChoices[j] = currQuestionChoicesJSON.getString(j);
            }

            currQuestion.setChoices(currQuestionChoices);
            result.add(currQuestion);
        }

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
        activity.setTriviaReady(true);

        activity.setQuestions(questions);
    }

    interface IData {
        void setTriviaReady(boolean ready);
        void setQuestions(ArrayList<Question> questions);
    }
}
