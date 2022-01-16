package com.example.tring0.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.tring0.R;

public class SignUpActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getIntent();
    }
}