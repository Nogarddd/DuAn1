package com.example.ong.duan1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class SignUpActivity extends AppCompatActivity {
    EditText edEmail, edPassword, edRePassword;
    Button btnSignUp;
    Button btnLogin;
    FirebaseAuth mAuth;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edEmail=findViewById(R.id.edEmail);
        edPassword=findViewById(R.id.edPassword);
        edRePassword=findViewById(R.id.edRePassword);
        btnSignUp=findViewById(R.id.btnSignUp);
        btnLogin=findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=edEmail.getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please type an email", Toast.LENGTH_SHORT).show();
                }
                else{
                    password=edPassword.getText().toString();
                    if (password.isEmpty()){
                        Toast.makeText(SignUpActivity.this, "Please type a password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String rePassword=edRePassword.getText().toString();
                        if (password.equals(rePassword)){
                            SignUpWithEmail();
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void SignUpWithEmail(){
        final String email=edEmail.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(SignUpActivity.this, UpdateProfileActivity.class);
                            startActivity(i);
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthUserCollisionException e){
                                Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(SignUpActivity.this, "Weak password", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                Toast.makeText(SignUpActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                            } catch(Exception e) {
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
