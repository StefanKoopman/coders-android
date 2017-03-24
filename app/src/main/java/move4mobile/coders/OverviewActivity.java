package move4mobile.coders;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import move4mobile.coders.fragments.FragmentProfile;
import move4mobile.coders.fragments.FragmentRepos;
import move4mobile.coders.fragments.FragmentStats;
import move4mobile.coders.fragments.FragmentUsers;

public class OverviewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar = toolbar;


        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_overview,new FragmentRepos()).commitAllowingStateLoss();
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
        getMenuInflater().inflate(R.menu.overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_stats) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction().replace(R.id.content_overview,new FragmentStats()).commitAllowingStateLoss();
            mToolbar.setTitle("Stats");

        } else if (id == R.id.nav_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_overview,new FragmentProfile()).commitAllowingStateLoss();
            mToolbar.setTitle("Profile");

        } else if (id == R.id.nav_users) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_overview,new FragmentUsers()).commitAllowingStateLoss();
            mToolbar.setTitle("Users");

        } else if (id == R.id.nav_repos) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_overview,new FragmentRepos()).commitAllowingStateLoss();
            mToolbar.setTitle("Repos");

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent login = new Intent(OverviewActivity.this,LoginActivity.class);
            startActivity(login);
            OverviewActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
