package com.example.quocphu.getdealsapplication;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.quocphu.getdealsapplication.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditPostActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private EditText et_title,et_content,et_date_start,et_date_end,et_quantity;
    private Button btn_edit,btn_pickdate_start,btn_pickdate_end;
    private TextView tv_code;
    private Spinner sp_type;
    private ArrayList<String> list_type = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference node_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        toolbar = findViewById(R.id.toolbar_neweditpost);
        et_title = findViewById(R.id.et_title_editpost);
        et_content = findViewById(R.id.et_content_editpost);
        et_date_start = findViewById(R.id.et_datestart_editpost);
        et_date_end = findViewById(R.id.et_dateend_editpost);
        et_quantity = findViewById(R.id.et_quantity_editpost);
        btn_edit = findViewById(R.id.btn_editpost);
        tv_code = findViewById(R.id.tv_codedeal);
        btn_pickdate_start = findViewById(R.id.btn_pickday_start_edit);
        btn_pickdate_end = findViewById(R.id.btn_pickday_end_edit);
        sp_type = findViewById(R.id.sp_type_editpost);
        database = FirebaseDatabase.getInstance();

        //Node post
        final String key_post = getIntent().getExtras().getString("key_post");
        final String key_store = getIntent().getExtras().getString("key_store");
        node_post = database.getReference("posts").child(key_store).child(key_post);
        node_post.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for(DataSnapshot itemPost:dataSnapshot.getChildren()){
                    et_title.setText(dataSnapshot.getValue(Post.class).getTittle());
                    et_content.setText(dataSnapshot.getValue(Post.class).getContentPost());
                    et_date_start.setText(dataSnapshot.getValue(Post.class).getTimeStart());
                    et_date_end.setText(dataSnapshot.getValue(Post.class).getTimeEnd());
                    et_quantity.setText(dataSnapshot.getValue(Post.class).getQuantity());
                    tv_code.setText(dataSnapshot.getValue(Post.class).getCodeDeal());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Thêm thể loại vào spinner
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
        sp_type.setAdapter(adapter_type);
        //Sự kiện nút chọn ngày
        btn_pickdate_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditPostActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            String date = year+"-"+(month+1)+"-"+dayOfMonth;
                            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                            Date d = simpleDate.parse(date);
                            et_date_start.setText(simpleDate.format(d));
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
        btn_pickdate_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditPostActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            String date = year+"-"+(month+1)+"-"+dayOfMonth;
                            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                            Date d = simpleDate.parse(date);
                            et_date_end.setText(simpleDate.format(d));
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
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString();
                String content = et_content.getText().toString();
                String dateStart = et_date_start.getText().toString();
                String dateEnd = et_date_end.getText().toString();
                String quantity = et_quantity.getText().toString();
                String type = sp_type.getSelectedItem().toString();
                Map<String,Object> valuePost = new HashMap<>();
                valuePost.put("tittle",title);
                valuePost.put("contentPost",content);
                valuePost.put("timeStart",dateStart);
                valuePost.put("timeEnd",dateEnd);
                valuePost.put("quantity",quantity);
                node_post.updateChildren(valuePost);
                finish();


            }
        });

    }
}
