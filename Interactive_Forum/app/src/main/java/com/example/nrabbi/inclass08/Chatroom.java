// Nazmul Rabbi & Dyrell Cole
// ITCS 4180 : In Class Assignment 8
// Chatroom.java
// Group 20

package com.example.nrabbi.inclass08;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import org.ocpsoft.prettytime.PrettyTime;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Chatroom extends AppCompatActivity {
    ListView list;
    TextView title;
    private final OkHttpClient client = new OkHttpClient();
    TokenInfo tokenInfo;
    ThreadMessage message;
    MessagesList newMessage;
    MessagesList mList;
    CustomAdapter customAdapter;
    ImageView home;
    ImageView addMessage;
    EditText messageToSend;
    ImageView delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        list = (ListView)findViewById(R.id.messageList);
        title = (TextView)findViewById(R.id.messageTitle);
        home = (ImageView)findViewById(R.id.homeButton);
        addMessage = (ImageView)findViewById(R.id.sendMessage);
        messageToSend = (EditText)findViewById(R.id.enterMessage);
        Intent intent = getIntent();
        message = (ThreadMessage) intent.getExtras().getSerializable("MESSAGE");
        tokenInfo = (TokenInfo) intent.getExtras().getSerializable("TOKEN");
        title.setText(message.title);
        getMessages();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Chatroom.this, MessageThreads.class);
                intent.putExtra("TOKEN", tokenInfo);
                startActivity(intent);
            }
        });

        addMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!messageToSend.getText().toString().matches("")){
                    addMessage(messageToSend.getText().toString(), message.id);
                }
            }
        });
    }

    public void getMessages(){
        Request request = new Request.Builder()
                .url(("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/messages/" + message.id))
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
                    String test = response.body().string();
                    mList = gson.fromJson(test, MessagesList.class);
                    Log.d("demo", "onResponse MessageList: " + mList.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            customAdapter = new CustomAdapter(getApplicationContext(), mList.messages);
                            list.setAdapter(customAdapter);
                        }
                    });

                } else{
                    Log.d("demo", "onResponse Error: " + response.body().string());
                }
            }
        });
    }

    class CustomAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<com.example.nrabbi.inclass08.Message> mDataSource;

        public CustomAdapter(Context context, ArrayList<com.example.nrabbi.inclass08.Message> items) {
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
            convertView = mInflater.inflate(R.layout.chat_items, parent, false);

            TextView title = convertView.findViewById(R.id.mTitle);
            TextView author = convertView.findViewById(R.id.mAuthor);
            TextView time = convertView.findViewById(R.id.mTimeStamp);
            delete = convertView.findViewById(R.id.deleteMessage);

            final com.example.nrabbi.inclass08.Message message = mDataSource.get(position);

            title.setText(message.message);
            author.setText(message.user_fname + " " + message.user_lname);

            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
            PrettyTime p = new PrettyTime();

            try {
                time.setText(p.format(s.parse(message.created_at)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(message.user_id == tokenInfo.user_id){
                delete.setImageResource(R.drawable.ic_delete);
            }
            else{
                delete.setImageResource(0);
            }

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(message.user_id == tokenInfo.user_id){
                        deleteMessage(String.valueOf(message.id), position);
                    }
                }
            });

            return convertView;
        }
    }

    public void deleteMessage(String id, int i){
        final int index = i;

        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/message/delete/" + id)
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

                    mList.messages.remove(index);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            customAdapter = new CustomAdapter(getApplicationContext(), mList.messages);
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

    public void addMessage(String message, String thread_id){
        RequestBody formBody = new FormBody.Builder()
                .add("message", message)
                .add("thread_id", thread_id)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/message/add")
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
                    newMessage = gson.fromJson(test, MessagesList.class);

                    getMessages();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageToSend.setText("");
                            customAdapter.notifyDataSetChanged();
                        }
                    });

                    Log.d("demo", "onResponse: " + newMessage.toString());


                } else{
                    Log.d("demo", "onResponse Error: " + response.body().string());
                }
            }
        });
    }
}
