package com.muk.helpinghand;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.muk.helpinghand.activity.LogInActivity;
import com.muk.helpinghand.activity.ViewInfoActivity;
import com.muk.helpinghand.data.HomelessInfo;
import com.muk.helpinghand.fragment.AboutFragment;
import com.muk.helpinghand.fragment.ChangePwdFragment;
import com.muk.helpinghand.fragment.HandUpFragment;
import com.muk.helpinghand.fragment.HandWrittenFragment;
import com.muk.helpinghand.fragment.HandyFeedFragment;
import com.muk.helpinghand.fragment.MainFragment;
import com.muk.helpinghand.fragment.TakeHandFragment;
import com.muk.helpinghand.fragment.UserPostFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //Set navigate
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //First page will navigate to the main fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();

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
        // Inflate the menu
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();

        //Navigate to different fragment
        if (id == R.id.nav_helping_hand) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();

        } else if (id == R.id.nav_hand_up) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new HandUpFragment()).commit();

        } else if (id == R.id.nav_take_hand) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new TakeHandFragment()).commit();

        } else if (id == R.id.nav_handy_feed) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new HandyFeedFragment()).commit();
        } else if (id == R.id.nav_hand_written) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new HandWrittenFragment()).commit();

        } else if (id == R.id.nav_usr_post) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new UserPostFragment()).commit();

        } else if (id == R.id.nav_send) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ChangePwdFragment()).commit();

        } else if (id == R.id.nav_about) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AboutFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Set setting on app bar on the right
        if (id == R.id.action_settings) {
            //When item select, pop up warning dialog
            alertDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void alertDialog(){
        AlertDialog builder = new AlertDialog.Builder(MainActivity.this).create();
        builder.setTitle("Hand Up");
        builder.setMessage("Would you like to log out?");
        builder.setButton(AlertDialog.BUTTON1, "No Thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setButton(AlertDialog.BUTTON2, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //when user select yes, log out and navigate to log in activity
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
            }
        });
        builder.show();

    }
}
