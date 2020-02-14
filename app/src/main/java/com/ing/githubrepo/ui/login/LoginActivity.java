package com.ing.githubrepo.ui.login;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ing.githubrepo.MainActivity;
import com.ing.githubrepo.R;
import com.ing.githubrepo.base.BaseActivity;

/**
 * Created by karamans on 13.02.2020.
 */

public class LoginActivity extends BaseActivity {

    private EditText usernameEditText;
    private Button loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        usernameEditText = findViewById(R.id.username);
        loginButton = findViewById(R.id.login);

        usernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    initLogin();
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLogin();
            }
        });
    }

    private void initLogin() {
        if (isUserNameValid(usernameEditText.getText().toString())) {
            login(usernameEditText.getText().toString());
        }

    }

    private boolean isUserNameValid(String username) {
        return username == null ? false : !username.trim().isEmpty();
    }


    private void login(String username) {
        changeScreen(MainActivity.newIntent(username, this));
    }

}
