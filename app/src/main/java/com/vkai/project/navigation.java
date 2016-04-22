package com.vkai.project;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {


    SupportMapFragment supportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String str = getIntent().getStringExtra("email");

        supportMapFragment = supportMapFragment.newInstance();

        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView t = (TextView)findViewById(R.id.textView);
        //t is the textview for username@gmail.com, the next line which is commented is not working
        //t.setText(str);
        navigationView.setNavigationItemSelectedListener(this);

        /*FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
        */

        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentManager fm1 = getSupportFragmentManager();

        if(supportMapFragment.isAdded())
            fm1.beginTransaction().hide(supportMapFragment).commit();

        if (id == R.id.nav_startrun)
        {
           Intent intent = new Intent(navigation.this,WalkingRoute.class);
            startActivity(intent);
            /*if(!supportMapFragment.isAdded())
                fm1.beginTransaction().add(R.id.map, supportMapFragment).commit();
            else
                fm1.beginTransaction().show(supportMapFragment).commit();
*/

        } else if (id == R.id.nav_nearby) {

            Intent intent = new Intent(navigation.this , NearbyPlacesActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_runhistory) {

            Intent intent = new Intent(navigation.this , RunHistoryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);



        } else if (id == R.id.nav_stat) {
            Intent intent = new Intent(navigation.this , StatisticsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else if (id == R.id.nav_exercisevideos) {

            Intent intent = new Intent(navigation.this , VideoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);



        } else if (id == R.id.nav_foodsuggestion) {

            Intent intent = new Intent(navigation.this , FoodSuggestionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);



        }
        else if (id == R.id.nav_userprofile) {

            Bundle bundle = getIntent().getExtras();
            int user_id = bundle.getInt("id1");
            String email = bundle.getString("email");
            Bundle bundle1 = new Bundle();
            bundle1.putInt("id1", user_id);
            Toast.makeText(getApplicationContext(), "id is" + user_id, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(navigation.this, UserProfileActivity.class);
            intent.putExtras(bundle1);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
            else if (id == R.id.nav_share) {




        } else if (id == R.id.nav_send) {



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
