package com.preid.firebasetester;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class SignUpActivity extends AppCompatActivity {
    private Firebase mFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseRef = new Firebase(MainActivity.FIREBASE_URL);
    }

    public void onConfirmButtonClick(View view) {
        EditText userEditText = (EditText) findViewById(R.id.signup_email_edit_text);
        EditText passEditText = (EditText) findViewById(R.id.signup_password_edit_text);

        String username = userEditText.getText().toString().trim();
        String password = passEditText.getText().toString().trim();

        mFirebaseRef.createUser(username, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                AlertDialog dialog = new AlertDialog.Builder(SignUpActivity.this)
                        .setMessage(firebaseError.getMessage())
                        .setPositiveButton("OK", null)
                        .create();
                dialog.show();
            }
        });
    }
}
