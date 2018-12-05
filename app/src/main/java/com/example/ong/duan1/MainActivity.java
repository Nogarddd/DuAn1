package com.example.ong.duan1;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ong.duan1.Fragment.AccountFragment;
import com.example.ong.duan1.Fragment.FollowingFragment;
import com.example.ong.duan1.Fragment.HomeFragment;
import com.example.ong.duan1.Fragment.NearMeFragment;
import com.example.ong.duan1.Fragment.SavedFragment;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    FragmentManager fManager;
    SpaceNavigationView spaceNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HomeFragment homeFragment=new HomeFragment();
        fManager = getSupportFragmentManager();
        fManager.beginTransaction()
                .add(R.id.frameLayout,homeFragment)
                .commit();

        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Home", R.drawable.ic_home));
        spaceNavigationView.addSpaceItem(new SpaceItem("Following", R.drawable.ic_star_unfollow_gray));
        spaceNavigationView.addSpaceItem(new SpaceItem("Saved", R.drawable.ic_heart));
        spaceNavigationView.addSpaceItem(new SpaceItem("Account", R.drawable.ic_person));
        spaceNavigationView.showIconOnly();
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                NearMeFragment mapFragment=new NearMeFragment();
                fManager.beginTransaction()
                        .replace(R.id.frameLayout, mapFragment)
                        .commit();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemName.equals("Home")){
                    HomeFragment homeFragment=new HomeFragment();
                    fManager.beginTransaction()
                            .replace(R.id.frameLayout,homeFragment)
                            .commit();
                }
                else if (itemName.equals("Following")){
                    FollowingFragment followingFragment=new FollowingFragment();
                    fManager.beginTransaction()
                            .replace(R.id.frameLayout,followingFragment)
                            .commit();
                }
                else if (itemName.equals("Saved")){
                    SavedFragment savedFragment=new SavedFragment();
                    fManager.beginTransaction()
                            .replace(R.id.frameLayout, savedFragment)
                            .commit();
                }
                else {
                    AccountFragment accountFragment=new AccountFragment();
                    fManager.beginTransaction()
                            .replace(R.id.frameLayout, accountFragment)
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
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_noti){
            Intent i=new Intent(MainActivity.this, NotificationActivity.class);
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
