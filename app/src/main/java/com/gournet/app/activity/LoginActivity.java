package com.gournet.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.gournet.app.model.TokenObj;
import com.gournet.app.model.UserPass;
import com.gournet.app.other.SessionManager;
import com.gournet.app.rest.ApiClient;
import com.gournet.app.rest.ApiEndpointInterface;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;



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
                EnableDisable(false);
                ApiClient.service.create(ApiEndpointInterface.loginService.class).doLogin(new UserPass(mUsernameView.getText().toString(), mPasswordView.getText().toString()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((result) -> {
                            ApiClient.generateWToken(result.getAccess().getToken());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user", result.getUser());
                            startActivity(intent);
                            EnableDisable(true);
            });
            }
        });

        mSignInButton.performClick();
    }

    public void EnableDisable(boolean isEnable) {
        mProgressBar.setVisibility(isEnable ? View.INVISIBLE : View.VISIBLE);
        mUsernameView.setEnabled(isEnable);
        mPasswordView.setEnabled(isEnable);
        mSignInButton.setEnabled(isEnable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}







 // poziv u SignIn button  new PerformLogin(LoginActivity.this).execute();
//class PerformLogin extends AsyncTask<Object, Void, Token> {
//    LoginActivity context;
//    SessionManager session;
//    PerformLogin(LoginActivity context) {
//        this.context = context;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        context.EnableDisable(false);
//    }
//
//    @Override
//    protected Token doInBackground(Object... params) {
//        Retrofit retrofit = ApiClient.service;
//
//        ApiEndpointInterface.loginService service = retrofit.create(ApiEndpointInterface.loginService.class);
//
//        Token token = null;
//
//        try {
//            token = service.doLogin(
//                    new UserPass(context.mUsernameView.getText().toString(),
//                            context.mPasswordView.getText().toString())
//            ).execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ApiClient.generateWToken(token.getAccess().getToken());
//         //  session=new SessionManager(context);
//      //  session.saveTokenToPreference(context,"GournetPref","token",token);
//
//        //session.sav
//        //   session.createLoginSession(token.getRefresh(),token.getAccess(),token.getUser().getUsername(),token.getUser().getFullName(),token.getUser().getLatitude(),token.getUser().getLongitute());
//        return token;
//    }
//
//    @Override
//    protected void onPostExecute( Token o) {
//
//       Intent intent = new Intent(context, MainActivity.class);
//       intent.putExtra("user", o.getUser());
//       context.startActivity(intent);
//        context.EnableDisable(true);
//    }
//}

