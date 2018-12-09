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

import com.example.quocphu.getdealsapplication.model.Deal;
import com.example.quocphu.getdealsapplication.model.Post;
import com.example.quocphu.getdealsapplication.model.Store;
import com.example.quocphu.getdealsapplication.model.User;
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

public class AddDealActivity extends AppCompatActivity {
    EditText edTitle, edOldPrice, edNewPrice, edDateStart, edDateEnd, edQuantity, edCode;
    Button btn_post, btnDatePicker1, btnDatePicker2;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    String key_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deal);
        edTitle=findViewById(R.id.edTitle);
        edOldPrice=findViewById(R.id.edOldPrice);
        edNewPrice=findViewById(R.id.edNewPrice);
        edDateStart=findViewById(R.id.edDateStart);
        edDateEnd=findViewById(R.id.edDateEnd);
        edQuantity=findViewById(R.id.edQuantity);
        edCode=findViewById(R.id.edCode);
        btn_post=findViewById(R.id.btn_post);
        btnDatePicker1=findViewById(R.id.btnDatePicker1);
        btnDatePicker2=findViewById(R.id.btnDatePicker2);
        final Store s= new Store("FPT", "123 NKKN", 123);

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title=edTitle.getText().toString();
                final String oldPrice=edOldPrice.getText().toString();
                final String newPrice=edNewPrice.getText().toString();
                final String dateStart=edDateStart.getText().toString();
                final String dateEnd=edDateEnd.getText().toString();
                final String quantity=edQuantity.getText().toString();
                final String code=edCode.getText().toString();


                database=FirebaseDatabase.getInstance();
                mAuth=FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                DatabaseReference node_store = database.getReference("Store").child(user.getUid());

                node_store.setValue(s);

                node_store.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        key_store = dataSnapshot.getKey();
                        DatabaseReference node_deal = database.getReference("Deal").child(key_store);
                        Deal d = new Deal(title, oldPrice, newPrice, dateStart, dateEnd, quantity, code);
                        node_deal.push().setValue(d);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        btnDatePicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDealActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            String date = year+"-"+(month+1)+"-"+dayOfMonth;
                            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                            Date d = simpleDate.parse(date);
                            edDateStart.setText(simpleDate.format(d));
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
        btnDatePicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDealActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        try {
                            String date = year+"-"+(month+1)+"-"+dayOfMonth;
                            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
                            Date d = simpleDate.parse(date);
                            edDateEnd.setText(simpleDate.format(d));
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


}
