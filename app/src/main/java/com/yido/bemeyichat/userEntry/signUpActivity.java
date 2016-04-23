package com.yido.bemeyichat.userEntry;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.yido.bemeyichat.R;
import com.yido.bemeyichat.base.BaseActivity;
import com.yido.bemeyichat.model.user;

import butterknife.Bind;
import butterknife.ButterKnife;

public class signUpActivity extends AppCompatActivity {

    private static Firebase mRefUsers = new Firebase("https://bemeyichat.firebaseio.com/users");

    @Bind(R.id.btnSignUp)
    Button mBtnSignUp;

    @Bind(R.id.txtPassword)
    EditText mPassText;

    @Bind(R.id.txtPasswordRepeat)
    EditText mPassRepeatText;

    @Bind(R.id.txtUsername)
    EditText mUsernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

    }

    private void SignUp() {
        final String username = mUsernameText.getText().toString();

        mRefUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean canSignUp = true;

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    user User = data.getValue(user.class);

                    if (User.getUsername().equals(username)) {
                        canSignUp = false;
                        break;
                    }

                }

                if (canSignUp) {
                    String pass1 = mPassText.getText().toString();
                    String pass2 = mPassRepeatText.getText().toString();
                    if (pass1.equals(pass2)) {
                        user newUser = new user();
                        newUser.setUsername(username);
                        newUser.setPassword(pass1);
                        newUser.setId(dataSnapshot.getChildrenCount() + 1);

                        mRefUsers.push().setValue(newUser);

                        Toast.makeText(getApplicationContext(), "Üyelik başarılı bir şekilde alındı.", Toast.LENGTH_SHORT).show();

                        Intent loginActivityStartIntent = new Intent(getApplicationContext(), loginActivity.class);
                        loginActivityStartIntent.putExtra("username", username);
                        loginActivityStartIntent.putExtra("password", pass1);
                        startActivity(loginActivityStartIntent);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Girdiğiniz şifreler uyuşmuyor!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Aynı isimde kullanıcı mevcut!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


}
