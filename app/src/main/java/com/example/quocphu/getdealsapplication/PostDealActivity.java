package com.example.quocphu.getdealsapplication;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.quocphu.getdealsapplication.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PostDealActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText et_title,et_content,et_datestart,et_dateend,et_quantity,et_code;
    private Button btn_date_start,btn_date_end,btn_post;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference node_post;
    private String key_user,key_store,key_post;
    private ArrayList<String> list_type = new ArrayList<>();
    private Spinner sp_type_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_deal);
        toolbar = findViewById(R.id.toolbar_newpost);
        et_title = findViewById(R.id.et_title_post);
        et_content = findViewById(R.id.et_content_post);
        et_datestart = findViewById(R.id.et_datestart_post);
        et_dateend = findViewById(R.id.et_dateend_post);
        et_quantity = findViewById(R.id.et_quantity_post);
        et_code = findViewById(R.id.et_codedeal);
        btn_date_start = findViewById(R.id.btn_pickday_start);
        btn_date_end = findViewById(R.id.btn_pickday_end);
        btn_post = findViewById(R.id.btn_post);
        sp_type_post = findViewById(R.id.sp_type_post);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        list_type.add("Electronic Devices");
        list_type.add("Electronic Accesssories");
        list_type.add("TV & Home Appliances");
        list_type.add("Health & Beauty");
        list_type.add("Babies & Toys");
        list_type.add("Home & Lifestyle");
        list_type.add("Women's Fashion");
        list_type.add("Men's Fashion");
        list_type.add("Fashion Accessories");
        list_type.add("Sport & Travel");
        list_type.add("Automotive & Motocycles");
        ArrayAdapter<String> adapter_type = new ArrayAdapter<>(this,R.layout.spinner_item,list_type);
        sp_type_post.setAdapter(adapter_type);

        btn_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(PostDealActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            String date = year+"-"+(month+1)+"-"+dayOfMonth;
                            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                            Date d = simpleDate.parse(date);
                            et_datestart.setText(simpleDate.format(d));
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

        btn_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(PostDealActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            String date = year+"-"+(month+1)+"-"+dayOfMonth;
                            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                            Date d = simpleDate.parse(date);
                            et_dateend.setText(simpleDate.format(d));
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
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = et_title.getText().toString();
                final String content = et_content.getText().toString();
                final String dateStart = et_datestart.getText().toString();
                final String dateEnd = et_dateend.getText().toString();
                final String quantity = et_quantity.getText().toString();
                final String codeDeal = et_code.getText().toString();
                final String typePost = sp_type_post.getSelectedItem().toString();
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm"); //vd: Wed, 4 Jul 2001, 12:08
                final String datePost = df.format(Calendar.getInstance().getTime());

                Query node_user = database.getReference("user").orderByChild("id_user").equalTo(currentUser.getUid());
                node_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot itemUser:dataSnapshot.getChildren()){
                            key_user = itemUser.getKey();
                        }
                        final DatabaseReference node_store = database.getReference("stores").child(key_user);
                        node_store.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot itemStore: dataSnapshot.getChildren()){
                                    key_store = dataSnapshot.getKey();
                                }
                                DatabaseReference node_post = database.getReference("posts").child(key_store);
                                key_post = node_post.push().getKey();
                                Post post = new Post(key_post,title,content,typePost,datePost,dateStart,dateEnd,quantity,codeDeal);
                                node_post.child(key_post).setValue(post); //Thêm dữ liệu vào node post
                                node_store.child("posts").child(key_post).setValue(post); //Thêm dữ liệu posts vào store
                                finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
    //Không xài
     private String random() {  //Tạo chuỗi ngẫu nhiên theo độ dài làm CODE
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(6);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
