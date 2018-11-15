package com.martinez.johan.dincomapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.martinez.johan.dincomapp.Entities.Tutorial;
import com.martinez.johan.dincomapp.Entities.Term;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AboutFragment.OnFragmentInteractionListener, SearchWordFragment.OnFragmentInteractionListener,
        ComidasFragment.OnFragmentInteractionListener, ListtermsFragment.OnFragmentInteractionListener,
        ListtutorialsFragment.OnFragmentInteractionListener, PreferencesFragment.OnFragmentInteractionListener,
        MenuFragment.OnFragmentInteractionListener, BebidasFragment.OnFragmentInteractionListener,
        IComunicaFragments, DetailtutorialFragment.OnFragmentInteractionListener, SearchingFragment.OnFragmentInteractionListener,
        EditprofileFragment.OnFragmentInteractionListener, DetailtermFragment.OnFragmentInteractionListener{

    Fragment menuFragment;
    ListtermsFragment listtermsFragment;
    DetailtermFragment detailtermFragment;
    DetailtutorialFragment detailTutorialFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        menuFragment = new MenuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, menuFragment)
                .addToBackStack(null).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Fragment miFragment = new SearchWordFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment)
                    .addToBackStack(null).commit();
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment miFragment=null;
        boolean fragmentSelected=false;

        if (id == R.id.nav_list_terms) {
            miFragment = new ListtermsFragment();
            fragmentSelected = true;
        } else if (id == R.id.nav_list_tutorials) {
            miFragment = new ListtutorialsFragment();
            fragmentSelected = true;
        } else if (id == R.id.nav_search) {
            miFragment = new SearchWordFragment();
            fragmentSelected = true;
            /**Intent intent = new Intent(MainActivity.this, SearchTermActivity.class);
            startActivity(intent);**/
        } else if (id == R.id.nav_configurations) {
            miFragment = new PreferencesFragment();
            fragmentSelected = true;
        } else if (id == R.id.nav_closeSession) {
            finish();
        } else if (id == R.id.nav_about) {
            miFragment=new AboutFragment();
            fragmentSelected=true;
        } else if (id == R.id.nav_menu) {
            miFragment=new MenuFragment();
            fragmentSelected=true;
        }
            if (fragmentSelected==true){
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment)
                        .addToBackStack(null).commit();
            }
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void sendTerm(Term term) {
        detailtermFragment = new DetailtermFragment();
        Bundle bundleSend = new Bundle();
        bundleSend.putSerializable("objeto", term);
        detailtermFragment.setArguments(bundleSend);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, detailtermFragment).addToBackStack(null).commit();
    }

    @Override
    public void sendTutorial(Tutorial tutorial) {
        detailTutorialFragment = new DetailtutorialFragment();
        Bundle bundleSend = new Bundle();
        bundleSend.putSerializable("objeto", tutorial);
        detailTutorialFragment.setArguments(bundleSend);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, detailTutorialFragment).addToBackStack(null).commit();
    }
}
