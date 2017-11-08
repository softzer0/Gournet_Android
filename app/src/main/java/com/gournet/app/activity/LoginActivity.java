package com.gournet.app.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.gournet.app.R;
import com.gournet.app.model.Token;
import com.gournet.app.model.UserPass;
import com.gournet.app.rest.ApiClient;
import com.gournet.app.rest.ApiEndpointInterface;

import java.io.IOException;

import retrofit2.Retrofit;


class PerformLogin extends AsyncTask<Object, Void, Token> {
    LoginActivity context;

    PerformLogin(LoginActivity context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        context.EnableDisable(false);
    }

    @Override
    protected Token doInBackground(Object... params) {
        Retrofit retrofit = ApiClient.service;

        ApiEndpointInterface.loginService service = retrofit.create(ApiEndpointInterface.loginService.class);

        Token token = null;

        try {
            token = service.doLogin(
                new UserPass(context.mUsernameView.getText().toString(),
                             context.mPasswordView.getText().toString())
            ).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;
    }

    @Override
    protected void onPostExecute(Token o) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("token", o);
        context.startActivity(intent);
        context.EnableDisable(true);
    }
}

public class LoginActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    AutoCompleteTextView mUsernameView;
    EditText mPasswordView;
    Button mSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressBar = findViewById(R.id.progressBar);
        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
        mSignInButton = findViewById(R.id.sign_in);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            new PerformLogin(LoginActivity.this).execute();
            }
        });
    }

    public void EnableDisable(boolean isEnable) {
        mProgressBar.setVisibility(isEnable ? View.INVISIBLE : View.VISIBLE);
        mUsernameView.setEnabled(isEnable);
        mPasswordView.setEnabled(isEnable);
        mSignInButton.setEnabled(isEnable);
    }
}

