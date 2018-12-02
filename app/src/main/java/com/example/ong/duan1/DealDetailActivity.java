package com.example.ong.duan1;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ong.duan1.Model.Deal;

public class DealDetailActivity extends AppCompatActivity {
    TextView tvStoreName, tvFollower, tvTitle, tvPercentSale, tvNewPrice, tvOldPrice, tvSaved;
    TextView toolbar_title;
    android.support.v7.widget.Toolbar toolbar;
    Deal deal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_detail);
        tvStoreName=findViewById(R.id.tvStoreName);
        tvFollower=findViewById(R.id.tvFollower);
        tvTitle=findViewById(R.id.tvTitle);
        tvPercentSale=findViewById(R.id.tvPercentSale);
        tvNewPrice=findViewById(R.id.tvNewPrice);
        tvOldPrice=findViewById(R.id.tvOldPrice);
        tvSaved=findViewById(R.id.tvSaved);
        toolbar=findViewById(R.id.toolbar);
        toolbar_title=findViewById(R.id.toolbar_title);
        toolbar_title.setText("Deal Detail");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        deal=(Deal) getIntent().getSerializableExtra("data");
        tvStoreName.setText(deal.getStoreName());
        tvFollower.setText(deal.getFollower()+"");
        tvTitle.setText(deal.getTitle());
        tvPercentSale.setText(deal.getPercentSale()+"");
        tvNewPrice.setText(deal.getNewPrice()+"");
        tvOldPrice.setText(deal.getOldPrice()+"");
        tvSaved.setText(deal.getSaved()+"");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }
}
