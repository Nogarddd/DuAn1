package com.example.quocphu.getdealsapplication;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quocphu.getdealsapplication.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyProfileActivity extends AppCompatActivity {
    ImageView ivAvatar;
    EditText edFullName, edEmail, edPhone, edBirthday;
    FirebaseAuth mAuth;
    DatabaseReference node_user;
    FirebaseUser user;
    StorageReference mStorage;
    Button btnConfirm;
    Spinner spAccType, spGender;
    ImageView ivChangeAvatar;
    String[] accType = {"Client", "Store Manager"};
    String[] gender = {"Male", "Female"};
    StorageReference imageRef;
    String user_id;
    Button btnDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ivAvatar=findViewById(R.id.ivAvatar);
        edFullName=findViewById(R.id.edFullName);
        edEmail=findViewById(R.id.edEmail);
        edPhone=findViewById(R.id.edPhone);
        edBirthday=findViewById(R.id.edBirthday);
        btnConfirm=findViewById(R.id.btnConfirm);
        spAccType=findViewById(R.id.spAccType);
        spGender=findViewById(R.id.spGender);
        ivChangeAvatar=findViewById(R.id.ivChangeAvatar);
        btnDatePicker=findViewById(R.id.btnDatePicker);

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
        mStorage=FirebaseStorage.getInstance().getReference();

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
                String birthday=edBirthday.getText().toString();

                user_id = user.getUid();
                node_user = FirebaseDatabase.getInstance().getReference("User").child(user_id);

                uploadImage();
                User u = new User(fullName, email, phone, gender, accType, birthday);
                node_user.setValue(u);

                if(accType.equals("Client")){
                    Intent i=new Intent(MyProfileActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else{
                    Intent i=new Intent(MyProfileActivity.this, StoreMainActivity.class);
                    startActivity(i);
                }

            }
        });
        ivChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooser();
            }
        });
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(MyProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            String date = year+"-"+(month+1)+"-"+dayOfMonth;
                            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                            Date d = simpleDate.parse(date);
                            edBirthday.setText(simpleDate.format(d));
                        }catch (Exception e){

                        }

                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

    }

    private void showChooser() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(MyProfileActivity.this);
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
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Toast.makeText(MyProfileActivity.this, "Avatar uploaded", Toast.LENGTH_SHORT).show();
                    String avatarUrl = downloadUri.toString(); //YOU WILL GET THE DOWNLOAD URL HERE !!!!
                    Map<String,Object> updateUser = new HashMap<>();
                    updateUser.put("avatarUrl", avatarUrl);
                    node_user.updateChildren(updateUser);
                }

            }
        });
    }

}