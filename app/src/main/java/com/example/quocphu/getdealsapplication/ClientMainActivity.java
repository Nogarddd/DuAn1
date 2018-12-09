package com.example.quocphu.getdealsapplication;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.quocphu.getdealsapplication.fragment.AccountFragment;
import com.example.quocphu.getdealsapplication.fragment.FollowingFragment;
import com.example.quocphu.getdealsapplication.fragment.HomeFragment;
import com.example.quocphu.getdealsapplication.fragment.MapFragment;
import com.example.quocphu.getdealsapplication.fragment.SavedFragment;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class ClientMainActivity extends AppCompatActivity {
    Toolbar toolbar;
    FragmentManager fManager;
    SpaceNavigationView spaceNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HomeFragment homeFragment=new HomeFragment();
        fManager = getSupportFragmentManager();
        fManager.beginTransaction()
                .add(R.id.flContent,homeFragment)
                .commit();

        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Home", R.drawable.ic_home));
        spaceNavigationView.addSpaceItem(new SpaceItem("Following", R.drawable.ic_star));
        spaceNavigationView.addSpaceItem(new SpaceItem("Saved", R.drawable.ic_heart));
        spaceNavigationView.addSpaceItem(new SpaceItem("Account", R.drawable.ic_person));
        spaceNavigationView.showIconOnly();
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                MapFragment mapFragment=new MapFragment();
                fManager.beginTransaction()
                        .replace(R.id.flContent, mapFragment)
                        .commit();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemName.equals("Home")){
                    HomeFragment homeFragment=new HomeFragment();
                    fManager.beginTransaction()
                            .replace(R.id.flContent,homeFragment)
                            .commit();
                }
                else if (itemName.equals("Following")){
                    FollowingFragment followingFragment=new FollowingFragment();
                    fManager.beginTransaction()
                            .replace(R.id.flContent,followingFragment)
                            .commit();
                }
                else if (itemName.equals("Saved")){
                    SavedFragment savedFragment=new SavedFragment();
                    fManager.beginTransaction()
                            .replace(R.id.flContent, savedFragment)
                            .commit();
                }
                else {
                    AccountFragment accountFragment=new AccountFragment();
                    fManager.beginTransaction()
                            .replace(R.id.flContent, accountFragment)
                            .commit();
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_client, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_noti){
            Intent i=new Intent(ClientMainActivity.this, NotificationActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }

}