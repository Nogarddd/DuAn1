package com.example.ong.duan1;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {
    ImageView ivAvatar;
    EditText edFullName, edEmail, edPhone;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    Button btnConfirm;
    String email;
    Spinner spAccType, spGender;
    ImageView ivChangeAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ivAvatar=findViewById(R.id.ivUserAvatar);
        edFullName=findViewById(R.id.edFullName);
        edEmail=findViewById(R.id.edEmail);
        edPhone=findViewById(R.id.edPhone);
        btnConfirm=findViewById(R.id.btnConfirm);
        spAccType=findViewById(R.id.spAccType);
        spGender=findViewById(R.id.spGender);
        ivChangeAvatar=findViewById(R.id.ivChangeAvatar);

        mAuth=FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();

        email = getIntent().getStringExtra("email");
        edEmail.setText(email);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avatarUrl;
                String accType=spAccType.getSelectedItem().toString();
                String fullName= edFullName.getText().toString();
                String email=edEmail.getText().toString();
                String phone=edPhone.getText().toString();
                String gender=spGender.getSelectedItem().toString();

                Map mMap=new HashMap();
                mMap.put("accType", accType);
                mMap.put("fullName", fullName);
                mMap.put("email", email);
                mMap.put("phone", phone);
                mMap.put("gender", gender);

                String user_id= user.getUid();
                databaseReference=FirebaseDatabase.getInstance().getReference();
                DatabaseReference currentUserReference=databaseReference.child("User").child(user_id);
                currentUserReference.setValue(mMap);
                Intent i=new Intent(UpdateProfileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        ivChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseGallerOrCamera();
            }
        });
    }
    private void chooseGallerOrCamera() {
        final CharSequence[] items = { "Take Photo", "Choose from Gallery",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);
        builder.setTitle("Change Avatar");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "MyImage.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 0);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            1);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    ivAvatar.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    ivAvatar.setImageURI(selectedImage);
                }
                break;
        }
    }

}
