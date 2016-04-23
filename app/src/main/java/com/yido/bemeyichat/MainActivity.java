package com.yido.bemeyichat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.yido.bemeyichat.model.chat;
import com.yido.bemeyichat.model.itemChatModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    private static Firebase mRefChat = new Firebase("https://bemeyichat.firebaseio.com/chats");

    private long id;
    private String username;

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.btnSend)
    Button mSend;

    @Bind(R.id.txtToSend)
    EditText mToSend;

    private List<chat> messages;

    private void initialize()
    {
        messages=new ArrayList<>();

        LinearLayoutManager lm = new LinearLayoutManager(this);


        mRecyclerView.setLayoutManager(lm);

        mRecyclerView.setAdapter(new EasyRecyclerAdapter<chat>(
                this,
                itemChatModel.class,
                messages));

        mRefChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                messages.clear();

                if(dataSnapshot.getChildrenCount()>0)
                {
                    for(DataSnapshot data:dataSnapshot.getChildren())
                    {
                        chat Message = data.getValue(chat.class);
                        messages.add(Message);
                    }

                    mRecyclerView.getAdapter().notifyDataSetChanged();

                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.scrollToPosition(mRecyclerView.getAdapter().getItemCount()-1);
                        }
                    });

                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = mToSend.getText().toString();
                chat message = new chat();
                message.setUsername(username);
                message.setId(id);
                message.setText(text);
                mRefChat.push().setValue(message);
                mToSend.setText("");
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        id = getIntent().getLongExtra("id",0);
        username = getIntent().getStringExtra("username");

        initialize();

    }








}
