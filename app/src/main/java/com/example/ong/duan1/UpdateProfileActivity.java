package com.example.ong.duan1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {
    ImageView ivAvatar;
    EditText edFullName, edEmail, edPhone;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    StorageReference mStorage;
    Button btnConfirm;
    Spinner spAccType, spGender;
    ImageView ivChangeAvatar;
    String[] accType = {"User", "Merchant"};
    String[] gender = {"Male", "Female"};
    StorageReference imageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ivAvatar=findViewById(R.id.ivAvatar);
        edFullName=findViewById(R.id.edFullName);
        edEmail=findViewById(R.id.edEmail);
        edPhone=findViewById(R.id.edPhone);
        btnConfirm=findViewById(R.id.btnConfirm);
        spAccType=findViewById(R.id.spAccType);
        spGender=findViewById(R.id.spGender);
        ivChangeAvatar=findViewById(R.id.ivChangeAvatar);

        ArrayAdapter<String> spAccTypeAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item, accType);
        spAccTypeAdapter.setDropDownViewResource(R.layout.spinner_item);
        spAccType.setAdapter(spAccTypeAdapter);

        ArrayAdapter<String> spGenderAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item, gender);
        spAccTypeAdapter.setDropDownViewResource(R.layout.spinner_item);
        spGender.setAdapter(spGenderAdapter);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        String facebookUserId="";
        // find the Facebook profile and get the user's id
        for(UserInfo profile : user.getProviderData()) {
            // check if the provider id matches "facebook.com"
            if(FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                facebookUserId = profile.getUid();
                String photoUrl = "https://graph.facebook.com/" + facebookUserId + "/picture?height=500";
                Glide.with(this)
                        .load(photoUrl)
                        .into(ivAvatar);
            }
            else{
                if (user.getPhotoUrl() != null){
                    Glide.with(this)
                            .load(user.getPhotoUrl())
                            .into(ivAvatar);
                }
            }
        }

        if (user.getDisplayName() != null){
            edFullName.setText(user.getDisplayName());
        }
        if (user.getEmail() != null){
            edEmail.setText(user.getEmail());
        }
        if (user.getPhoneNumber() != null){
            edPhone.setText(user.getPhoneNumber());
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accType=spAccType.getSelectedItem().toString();
                String fullName= edFullName.getText().toString();
                String email=edEmail.getText().toString();
                String phone=edPhone.getText().toString();
                String gender=spGender.getSelectedItem().toString();

                uploadImage();

                Map mMap=new HashMap();
                mMap.put("avatarUrl", imageRef.getPath());
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
                showChooser();
            }
        });

        mStorage=FirebaseStorage.getInstance().getReference();

    }

    private void showChooser() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(UpdateProfileActivity.this);
        myAlertDialog.setTitle("Upload Option");
        myAlertDialog.setMessage("How do you want to set your picture?");
        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent i = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.setType("image/*");
                        startActivityForResult(
                                i, 0);

                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent i = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i, 1);
                    }
                });
        myAlertDialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    ivAvatar.setImageURI(selectedImage);
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ivAvatar.setImageBitmap(bitmap);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
    }

    private void uploadImage(){
        Calendar calendar = Calendar.getInstance();
        imageRef = mStorage.child("image" + calendar.getTimeInMillis()+".png");

        // Get the data from an ImageView as bytes
        ivAvatar.setDrawingCacheEnabled(true);
        ivAvatar.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) ivAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }

}