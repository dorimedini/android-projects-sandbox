package com.example.dori.sandbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Main login screen activity.
 *
 * Should be the first screen the user sees.
 *
 * TODO: Test Facebook login with more than one user on the phone
 * TODO: Implement Google login as well
 * FIXME: On login success the login button in 'LOGOUT' state is visible before transition to main activity
 */
public class LoginActivity extends AppCompatActivity {

    private static final Logger log = LoggerFactory.getLogger(MainActivity.class);
    public static final String LoginActivityTag = "LOGIN_ACTIVITY";
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log.info("In LoginActivity.onCreate()");
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                try {
                    AccessToken token = loginResult.getAccessToken();
                    Log.e(LoginActivityTag, "SUCCESS! Got login result '" +
                            loginResult.toString() +
                            "' with access token '" +
                            token.toString() + "'");
                    loginOK();
                } catch (Exception e) {
                    Log.e(LoginActivityTag, e.toString());
                }
            }
            @Override
            public void onCancel() {
                try {
                    Log.e(LoginActivityTag, "Cancelled login");
                    loginFailed();
                } catch (Exception e) {
                    Log.e(LoginActivityTag, e.toString());
                }
            }
            @Override
            public void onError(FacebookException e) {
                try {
                    Log.e(LoginActivityTag, "Login error");
                    loginFailed();
                } catch (Exception e2) {
                    Log.e(LoginActivityTag, e2.toString());
                }
            }
        });
        if (isLoggedIn()) {
            log.info("Already logged in...");
            loginOK();
        }
    }

    public static boolean isLoggedIn() {
        return (AccessToken.getCurrentAccessToken() != null);
    }

    private void loginFailed() {
        Log.e(LoginActivityTag, "Failed to login!");
        goToMainActivity();
    }
    private void loginOK() {
        Log.e(LoginActivityTag, "LoginActivity in loggedIn()");
        goToMainActivity();
    }
    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        log.info("starting main activity...");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(LoginActivityTag, "Got login activity result");
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
