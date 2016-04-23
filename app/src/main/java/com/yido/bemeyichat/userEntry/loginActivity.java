package com.yido.bemeyichat.userEntry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.yido.bemeyichat.App;
import com.yido.bemeyichat.MainActivity;
import com.yido.bemeyichat.R;
import com.yido.bemeyichat.model.user;

import butterknife.Bind;
import butterknife.ButterKnife;

public class loginActivity extends AppCompatActivity {

    private static Firebase mRefUsers = new Firebase("https://bemeyichat.firebaseio.com/users");

    @Bind(R.id.btnRegister)
    Button btnRegister;

    @Bind(R.id.btnLogin)
    Button btnLogin;

    @Bind(R.id.txtUsername)
    EditText txtUsername;

    @Bind(R.id.txtPassword)
    EditText txtPassword;

    private void initialize() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(), signUpActivity.class);
                startActivity(registerIntent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    private void Login() {

        final String username = txtUsername.getText().toString();
        final String password = txtPassword.getText().toString();

        mRefUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean canLogin = false;
                user LoggedInUser = null;

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    user User = data.getValue(user.class);

                    if (User.getUsername().equals(username) && User.getPassword().equals(password)) {
                        canLogin = true;
                        LoggedInUser = User;
                        break;
                    }
                }

                if (canLogin) {

                    App.userID = LoggedInUser.getId();
                    App.username = LoggedInUser.getUsername();
                    Toast.makeText(getApplicationContext(), "Login successful.", Toast.LENGTH_SHORT).show();
                    Intent chatActivityStartIntent = new Intent(getApplicationContext(), MainActivity.class);
                    chatActivityStartIntent.putExtra("id", LoggedInUser.getId());
                    chatActivityStartIntent.putExtra("username", LoggedInUser.getUsername());
                    startActivity(chatActivityStartIntent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initialize();
    }
}
