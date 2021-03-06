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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class EditProfileActivity extends AppCompatActivity {
    ImageView ivAvatar;
    EditText edFullName, edEmail, edPhone, edBirthday;
    FirebaseAuth mAuth;
    DatabaseReference node_user;
    FirebaseUser user;
    StorageReference mStorage;
    Button btnConfirm;
    Spinner spGender;
    ImageView ivChangeAvatar;
    String[] gender = {"Male", "Female"};
    StorageReference imageRef;
    String user_id;
    Button btnDatePicker;
    User currentUser;
    boolean photoChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ivAvatar=findViewById(R.id.ivAvatar);
        edFullName=findViewById(R.id.edFullName);
        edEmail=findViewById(R.id.edEmail);
        edPhone=findViewById(R.id.edPhone);
        edBirthday=findViewById(R.id.edBirthday);
        btnConfirm=findViewById(R.id.btnConfirm);
        spGender=findViewById(R.id.spGender);
        ivChangeAvatar=findViewById(R.id.ivChangeAvatar);
        btnDatePicker=findViewById(R.id.btnDatePicker);

        ArrayAdapter<String> spGenderAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item, gender);
        spGender.setAdapter(spGenderAdapter);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        mStorage=FirebaseStorage.getInstance().getReference();
        currentUser = (User) getIntent().getSerializableExtra("user");
        Glide.with(this)
                .load(currentUser.getAvatarUrl())
                .into(ivAvatar);
        edFullName.setText(currentUser.getFullName());
        edEmail.setText(currentUser.getEmail());
        edPhone.setText(currentUser.getPhone());
        edBirthday.setText(currentUser.getBirthday());
        String gender=currentUser.getGender();
        if (gender.equals("Male")){
            spGender.setSelection(0);
        }
        else{
            spGender.setSelection(1);
        }
        photoChanged=false;

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName= edFullName.getText().toString();
                String email=edEmail.getText().toString();
                String phone=edPhone.getText().toString();
                String gender=spGender.getSelectedItem().toString();
                String birthday=edBirthday.getText().toString();

                user_id = user.getUid();
                node_user = FirebaseDatabase.getInstance().getReference("User").child(user_id);

                if (photoChanged){
                    uploadImage();
                    User u = new User(fullName, email, phone, gender, currentUser.getAccType(), birthday);
                    node_user.setValue(u);
                    finish();
                }
                else{
                    User u = new User(fullName, email, phone, gender, currentUser.getAccType(), birthday, currentUser.getAvatarUrl());
                    node_user.setValue(u);
                    finish();
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(EditProfileActivity.this);
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
                    photoChanged=true;
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ivAvatar.setImageBitmap(bitmap);
                    photoChanged=true;
                }
                break;
        }
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
                    Toast.makeText(EditProfileActivity.this, "Avatar uploaded", Toast.LENGTH_SHORT).show();
                    String avatarUrl = downloadUri.toString(); //YOU WILL GET THE DOWNLOAD URL HERE !!!!
                    Map<String,Object> updateUser = new HashMap<>();
                    updateUser.put("avatarUrl", avatarUrl);
                    node_user.updateChildren(updateUser);
                }

            }
        });
    }

}