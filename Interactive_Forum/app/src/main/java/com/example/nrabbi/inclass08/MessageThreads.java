// Nazmul Rabbi & Dyrell Cole
// ITCS 4180 : In Class Assignment 8
// MessageThreads.java
// Group 20

package com.example.nrabbi.inclass08;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageThreads extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    TokenInfo tokenInfo;
    ListView list;
    TextView tokenUsername;
    EditText newThread;
    ThreadMessageResponse threadList;
    CustomAdapter customAdapter;
    ThreadMessageResponse thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_threads);
        list = (ListView)findViewById(R.id.listView);
        tokenUsername = (TextView)findViewById(R.id.tvUserName);
        threadList = (ThreadMessageResponse) getIntent().getSerializableExtra("LIST");
        newThread = (EditText) findViewById(R.id.newThreadText);
        Intent intent = getIntent();
        tokenInfo = (TokenInfo) intent.getExtras().getSerializable("TOKEN");
        tokenUsername.setText(tokenInfo.user_fname + " " + tokenInfo.user_lname);
        getThreadMessages();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MessageThreads.this, Chatroom.class);
                intent.putExtra("LIST", threadList);
                intent.putExtra("MESSAGE", threadList.threads.get(i));
                intent.putExtra("TOKEN", tokenInfo);
                startActivity(intent);
            }
        });

        findViewById(R.id.logoff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tokenInfo.token = "";
                Intent intent1 = new Intent(MessageThreads.this, MainActivity.class);
                startActivity(intent1);
            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!newThread.getText().toString().matches("")){
                    addThread(newThread.getText().toString());
                }
            }
        });
    }

    class CustomAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<ThreadMessage> mDataSource;

        public CustomAdapter(Context context, ArrayList<ThreadMessage> items) {
            mContext = context;
            mDataSource = items;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.thread, parent, false);
            TextView title = convertView.findViewById(R.id.message);
            TextView author = convertView.findViewById(R.id.author);
            ImageView delete = convertView.findViewById(R.id.deleteThisThread);
            final ThreadMessage message = mDataSource.get(position);

            if(Integer.parseInt(message.user_id) == tokenInfo.user_id){
                delete.setImageResource(R.drawable.ic_delete);
            }

            delete.setVisibility(View.VISIBLE);
            delete.setEnabled(true);
            title.setText(message.title);
            author.setText(message.user_fname);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Integer.parseInt(message.user_id) == tokenInfo.user_id)
                    deleteThread(message.id, position);
                }
            });

            return convertView;
        }
    }

    public void addThread(String message){
        RequestBody formBody = new FormBody.Builder()
                .add("title", message)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread/add")
                .header("Authorization", "BEARER " + tokenInfo.token)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("demo", "onFailure: " + "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    String test = response.body().string();
                    thread = gson.fromJson(test, ThreadMessageResponse.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            newThread.setText("");
                        }
                    });

                    getThreadMessages();
                    Log.d("demo", "onResponse: " + thread.toString());

                } else{
                    Log.d("demo", "onResponse Error: " + response.body().string());
                }
            }
        });
    }

    public void getThreadMessages(){
        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread")
                .header("Authorization", "BEARER " + tokenInfo.token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("demo", "onFailure: " + "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    threadList = gson.fromJson(response.body().string(), ThreadMessageResponse.class);
                    Log.d("demo", "onResponse: " + threadList.threads.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            customAdapter = new CustomAdapter(getApplicationContext(), threadList.threads);
                            list.setAdapter(customAdapter);
                            customAdapter.notifyDataSetChanged();
                        }
                    });

                } else{
                    Log.d("demo", "onResponse Error: " + response.body().string());
                }
            }
        });
    }

    public void deleteThread(String id, int i){
        final int index = i;

        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread/delete/" + id)
                .header("Authorization", "BEARER " + tokenInfo.token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("demo", "onFailure: " + "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){

                    threadList.threads.remove(index);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            customAdapter = new CustomAdapter(getApplicationContext(), threadList.threads);
                            list.setAdapter(customAdapter);
                            customAdapter.notifyDataSetChanged();
                        }
                    });

                } else{
                    Log.d("demo", "onResponse Error: " + response.body().string());
                }
            }
        });
    }
}
