package com.example.tring0.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tring0.R;
import com.example.tring0.firebase.FireStoreService;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "LoginActivity_TAG";
    private Context context=this;

    private EditText edit_Email;
    private EditText edit_Password;

    private Button btn_Login;
    private Button btn_EmailSignUp;
    private SignInButton btn_GoogleLogin;

    private static final int RC_SIGN_IN=1000;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setGoogleLogin();

        edit_Email=findViewById(R.id.LoginActivity_Edit_Email);
        edit_Password=findViewById(R.id.LoginActivity_Edit_Password);

        btn_Login=findViewById(R.id.LoginActivity_Btn_Login);
        btn_Login.setOnClickListener(this);

        btn_EmailSignUp=findViewById(R.id.LoginActivity_Btn_SignUp);
        btn_EmailSignUp.setOnClickListener(this);

        btn_GoogleLogin=findViewById(R.id.LoginActivity_Btn_GoogleLogin);
        btn_GoogleLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.LoginActivity_Btn_Login:
                break;
            case R.id.LoginActivity_Btn_SignUp:
                startActivity(new Intent(context,SignUpActivity.class));
                break;
            case R.id.LoginActivity_Btn_FindPassword:
                break;
            case R.id.LoginActivity_Btn_GoogleLogin:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;
            default:
        }

    }

    private void setGoogleLogin()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try
            {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

                FireStoreService.Auth.googleLogin(context, account.getIdToken());
            }
            catch (ApiException e)
            {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
}