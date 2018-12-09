package com.example.quocphu.getdealsapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.quocphu.getdealsapplication.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {
    Animation anim;
    ImageView iv_logo;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    xetAccountType();
                }
            },2500);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i=new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            },2500);
        }

        iv_logo=findViewById(R.id.iv_logo);
        anim= AnimationUtils.loadAnimation(this, R.anim.anim);
        iv_logo.setAnimation(anim);
    }
    public void xetAccountType(){
        FirebaseUser user=mAuth.getCurrentUser();
        String user_id = user.getUid();
        DatabaseReference node_user = FirebaseDatabase.getInstance().getReference("User").child(user_id);
        node_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                String accType=currentUser.getAccType();
                if (accType.equals("Client")){
                    Intent i=new Intent(SplashScreenActivity.this, ClientMainActivity.class);
                    startActivity(i);
                }
                else{
                    Intent i=new Intent(SplashScreenActivity.this, StoreManagerMainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
