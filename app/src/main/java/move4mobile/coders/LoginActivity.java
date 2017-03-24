package move4mobile.coders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.auth.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import move4mobile.coders.models.AccessTokenObject;
import move4mobile.coders.models.AuthDing;
import move4mobile.coders.models.GithubAPIUser;
import move4mobile.coders.models.TokenRequestObject;
import move4mobile.coders.network.NetworkUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by stefankoopman on 18/03/17.
 */

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "LoginActivity";

    @BindView(R.id.webview)
    WebView mWebView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    String userid = user.getUid();
                    Log.d(TAG,userid);

                    for(UserInfo info: user.getProviderData()){
                        if(info.getProviderId() != null && info.getProviderId().equals("github.com")){
                            String userId = info.getUid();

                            NetworkUtil.getUserController().getUser("https://api.github.com/user/" + userId).enqueue(new Callback<GithubAPIUser>() {
                                @Override
                                public void onResponse(Call<GithubAPIUser> call, Response<GithubAPIUser> response) {
                                    Log.d(TAG,response.body().getLogin());
                                    AppController.id = response.body().getLogin();
                                    overridePendingTransition(0,0);
                                    Intent main = new Intent(LoginActivity.this,OverviewActivity.class);
                                    startActivity(main);
                                    LoginActivity.this.finish();

                                }

                                @Override
                                public void onFailure(Call<GithubAPIUser> call, Throwable t) {

                                }
                            });

                        }
                    }


                }else{
                    // not signed in
                    goFixAuth();
                }
            }
        };


    }

    private void goFixAuth(){
        AuthDing authDing = new AuthDing();
        authDing.setClient_id(getString(R.string.client_id));


        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowContentAccess(true);

        String url = "https://github.com/login/oauth/authorize?client_id=" + getString(R.string.client_id);

        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG,url);
                if(url.contains("m4m-code-heroes")){
                    String code = url.split("=")[1];
                    getTokenWithCode(code);
                }
            }
        });
    }

    private void getTokenWithCode(String code){
        TokenRequestObject tokenRequestObject = new TokenRequestObject();
        tokenRequestObject.setClient_id(getString(R.string.client_id));
        tokenRequestObject.setClient_secret(getString(R.string.client_secret));
        tokenRequestObject.setCode(code);
        tokenRequestObject.setRedirect_ur("https://m4m-code-heroes.firebaseapp.com/__/auth/handler");

        NetworkUtil.getGithubController().getAccesstoken(tokenRequestObject).enqueue(new Callback<AccessTokenObject>() {
            @Override
            public void onResponse(Call<AccessTokenObject> call, Response<AccessTokenObject> response) {
                Log.d(TAG,response.code() + " status");
                validatewithFireBase(response.body().getAccess_token());

            }

            @Override
            public void onFailure(Call<AccessTokenObject> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });


    }

    private void validatewithFireBase(String token){
        AuthCredential credential = GithubAuthProvider.getCredential(token);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}
