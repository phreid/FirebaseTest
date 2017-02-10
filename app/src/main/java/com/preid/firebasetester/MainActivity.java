package com.preid.firebasetester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

public class MainActivity extends AppCompatActivity {
    static final String FIREBASE_URL = "https://crackling-torch-7756.firebaseio.com";
    private Firebase mFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase(FIREBASE_URL);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    public void onLoginButtonClick(View view) {
        EditText emailEditText = (EditText) findViewById(R.id.email_edit_text);
        EditText passEditText = (EditText) findViewById(R.id.password_edit_text);

        String email = emailEditText.getText().toString().trim();
        String pass = passEditText.getText().toString().trim();

        mFirebaseRef.authWithPassword(email, pass, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Intent intent = new Intent(MainActivity.this, TodoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {

            }
        });
    }

    public void onSignUpButtonClick(View view) {
        Intent intent =  new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
