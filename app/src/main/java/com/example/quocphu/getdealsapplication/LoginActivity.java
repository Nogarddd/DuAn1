package com.example.quocphu.getdealsapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quocphu.getdealsapplication.model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;
    private LoginManager loginManager;
    final static int RC_SIGN_IN=999;
    Button btnFbLogin;
    Button btnGoogleLogin;
    DatabaseReference node_user;

    /*private List<User> list_user = new ArrayList<>();
    FirebaseAuth.AuthStateListener mAuthListner;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnFbLogin=findViewById(R.id.btnFbLogin);
        btnGoogleLogin=findViewById(R.id.btnGoogleLogin);

        mAuth = FirebaseAuth.getInstance();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

        btnFbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager=LoginManager.getInstance();
                loginManager.logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
                loginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                    }
                });
            }
        });



    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            if (isNew){
                                Intent i=new Intent(LoginActivity.this, MyProfileActivity.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                xulysaukhidangnhap();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            if (isNew){
                                Intent i=new Intent(LoginActivity.this, MyProfileActivity.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                xulysaukhidangnhap();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
            }
        }

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void xulysaukhidangnhap(){
        FirebaseUser user=mAuth.getCurrentUser();

        String user_id= user.getUid();
        node_user=FirebaseDatabase.getInstance().getReference("User").child(user_id);
        node_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                String accType=currentUser.getAccType();
                if (accType.equals("Client")){
                    Intent i=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else{
                    Intent i=new Intent(LoginActivity.this, StoreMainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





    /* //Hàm xử lí xác thực google với Firbase
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            if(isNew){
                                startActivity(new Intent(LoginActivity.this,ProfileUserActivity.class));
                            }else {
                                checkUserClient();
                                for (int i = 0; i < list_user.size(); i++) {
                                    if (user.getUid().equals(list_user.get(i).getId_user()) && list_user.get(i).getTypeAccount().equals("client")) {
                                        Toast.makeText(LoginActivity.this, "Client", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                        break;
                                    } else if (user.getUid().equals(list_user.get(i).getId_user()) && list_user.get(i).getTypeAccount().equals("null")) {
                                        Toast.makeText(LoginActivity.this, "User store not accept by Admin", Toast.LENGTH_SHORT).show();
                                        break;
                                    } else if (user.getUid().equals(list_user.get(i).getId_user()) && list_user.get(i).getTypeAccount().equals("store")) {
                                        Toast.makeText(LoginActivity.this, "Store", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this,StoreMainActivity.class));
                                        break;

                                    }
                                }
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    //Xử lí hàm xác thực facebook với Firebase
    private void handleFacebookAccessToken(final AccessToken token) {
        Log.d("FacebookToken", "handleFacebookAccessToken:" + token.getToken());
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            if (token.getUserId().equals("1612894812190330")){
                                startActivity(new Intent(LoginActivity.this,AdminMainActivity.class));
                            }
                            else if (isNew){
                                startActivity(new Intent(LoginActivity.this,ProfileUserActivity.class));
                            }
                            else { checkUserClient();
                            for (int i = 0; i < list_user.size(); i++) {
                                if (user.getUid().equals(list_user.get(i).getId_user()) && list_user.get(i).getTypeAccount().equals("client")) {
                                    Toast.makeText(LoginActivity.this, "Client", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    break;
                                } else if (user.getUid().equals(list_user.get(i).getId_user()) && list_user.get(i).getTypeAccount().equals("null")) {
                                    Toast.makeText(LoginActivity.this, "User store not accept by Admin", Toast.LENGTH_SHORT).show();
                                    break;
                                } else if (user.getUid().equals(list_user.get(i).getId_user()) && list_user.get(i).getTypeAccount().equals("store")) {
                                    Toast.makeText(LoginActivity.this, "Store", Toast.LENGTH_SHORT).show();
                                    break;

                                }
                            }}



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FacebookLoginFails", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    //    FirebaseAuth.getInstance().signOut(); Đăng xuất account
    public void xulysaukhilogin(LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),new GraphRequest.GraphJSONObjectCallback()
        {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                Log.v("LoginActivity", response.toString());
                // Application code
                try {
                    String name = object.getString("name");
//                    Toast.makeText(LoginActivity.this, "Welcome back! Admin "+name, Toast.LENGTH_SHORT).show();
                    Log.d("iduser",object.getString("id"));
                } catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "loi"+ e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email ");
        request.setParameters(parameters);request.executeAsync();

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Failed",connectionResult + "");
    }
    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            // lưu lại người đã đăng nhập vào show lên
            GoogleSignInAccount act=result.getSignInAccount();
            // Lấy thư viện PICASSO về để load hình
//            Picasso.with(this).load(act.getPhotoUrl()).into(iv);
        }
        else {

        }
    }
    private void checkUserClient(){
        Query queryClient = FirebaseDatabase.getInstance().getReference("user");
        queryClient.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list_user.clear();
                for(DataSnapshot itemUser : dataSnapshot.getChildren()){
                    User user = itemUser.getValue(User.class);
                    list_user.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

}