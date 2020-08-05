package com.example.bookticket.UserSide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookticket.R;
import com.google.android.material.navigation.NavigationView;

public class HomeUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout drawer;
    ImageView menu;
    Button signout;
    TextView name;
    SharedPreferences sharedPreferences;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);
//        Toolbar toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        menu=findViewById(R.id.menu);
        sharedPreferences=getSharedPreferences("UserData",MODE_PRIVATE);
        signout=(Button)findViewById(R.id.signout);

        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        String s=sharedPreferences.getString("name",null);
        View header = navigationView.getHeaderView(0);
        name=header.findViewById(R.id.namenav);
        name.setText(s);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                editor.apply();
                startActivity(new Intent(HomeUser.this, Signin.class));
                finish();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                    new HomeFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen((GravityCompat.START))){
            drawer.closeDrawer(GravityCompat.START);
        }else{

            androidx.fragment.app.FragmentManager fm=getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                Log.i("MainActivity", "popping backstack");
                fm.popBackStack();
            } else {
                Log.i("MainActivity", "nothing on backstack, calling super");
                if(i==0){
                    Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                    i++;
                }else{

                    super.onBackPressed();

                }
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.viewrecord:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new ViewRecordBookingFragment()).addToBackStack("HomeFragment").commit();
                drawer.closeDrawer(GravityCompat.START);

                break;
            case R.id.feedback:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new SubFeedbackFragment()).addToBackStack("HomeFragment").commit();
                        drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.searchtrack:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new TrackFragment()).addToBackStack("HomeFragment").commit();
                drawer.closeDrawer(GravityCompat.START);

                break;
            case R.id.updatepassword:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new ChangePassFragment()).addToBackStack("HomeFragment").commit();
                drawer.closeDrawer(GravityCompat.START);

                break;
                case R.id.contactus:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmmentcontainer,
                        new ContactFragment()).addToBackStack("HomeFragment").commit();
                drawer.closeDrawer(GravityCompat.START);

                break;
            case R.id.signout:
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                editor.apply();
                startActivity(new Intent(HomeUser.this, Signin.class));
                finish();

                drawer.closeDrawer(GravityCompat.START);
                break;

        }
        return true;
    }


}
