package com.martinez.johan.dincomapp;


import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import com.martinez.johan.dincomapp.Entities.Drink;
import com.martinez.johan.dincomapp.Entities.Term;
import com.martinez.johan.dincomapp.Utilities.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AboutFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener,
        ComidasFragment.OnFragmentInteractionListener, ListtermsFragment.OnFragmentInteractionListener,
        ListdrinksFragment.OnFragmentInteractionListener, PreferencesFragment.OnFragmentInteractionListener,
        MenuFragment.OnFragmentInteractionListener, BebidasFragment.OnFragmentInteractionListener,
        IComunicaFragments, DetaildrinkFragment.OnFragmentInteractionListener,
        EditprofileFragment.OnFragmentInteractionListener, DetailtermFragment.OnFragmentInteractionListener{

    Fragment menuFragment;
    ListtermsFragment listtermsFragment;
    DetailtermFragment detailtermFragment;
    DetaildrinkFragment detaildrinkFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

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

    /**
     * Este método se encarga de llenar la tabla 'terminos' con la información de los términos y palabras
     */
    private void init(){
        if (cantRows()==0){
            String[] text= readFileTerms();
            ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_diccionario", null, 1 );
            SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
            db.beginTransaction();

            for (int i=0;i<text.length;i++){
                String[] line = text[i].split(";");
                ContentValues values = new ContentValues();
                values.put(Utilities.FIELD_ID, line[0]);
                values.put(Utilities.FIELD_T_NAME, line[1]);
                values.put(Utilities.FIELD_T_DEFINITION, line[2]);
                values.put(Utilities.FIELD_T_LINKTUTORIAL, line[3]);
                db.insert(Utilities.TABLE_TERMS, null, values);
            }
            Toast.makeText(getApplicationContext(), "Registros insertados: "+text.length, Toast.LENGTH_LONG).show();
            db.setTransactionSuccessful();
            db.endTransaction();
        }else{
            Toast.makeText(getApplicationContext(), "Base de datos actualizada!!", Toast.LENGTH_LONG).show();
        }
    }//Fin Init

    /**
     * Este método se encarga de contar cuantos registros hay en la tabla 'terminos' para determinar si se debe cargar
     * la información de términos y palabras
     * @return retorna un long con la cantidad de registros que tiene la tabla
     */
    private long cantRows(){
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_diccionario", null, 1 );
        SQLiteDatabase db = conexionSQLiteHelper.getReadableDatabase();
        long cn = DatabaseUtils.queryNumEntries(db,Utilities.TABLE_TERMS);
        db.close();
        return cn;
    }

    /**
     * Este método lee el archivo donde esta la información a cargar en la tabla 'terminos'
     * @return Retorna un arreglo string con la información de los términos o palabras
     */
    private String[] readFileTerms(){
        InputStream inputStream = getResources().openRawResource(R.raw.terms);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try {
            int i = inputStream.read();
            while (i != -1){
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");
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

        Fragment miFragment=null;
        boolean fragmentSelected=false;

        if (id == R.id.nav_list_terms) {
            miFragment = new ListtermsFragment();
            fragmentSelected = true;
        } else if (id == R.id.nav_list_tutorials) {
            miFragment = new ListdrinksFragment();
            fragmentSelected = true;
        } else if (id == R.id.nav_search) {
            miFragment = new ProfileFragment();
            fragmentSelected = true;
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
    public void sendDrink(Drink drink) {
        detaildrinkFragment = new DetaildrinkFragment();
        Bundle bundleSend = new Bundle();
        bundleSend.putSerializable("objeto", drink);
        detaildrinkFragment.setArguments(bundleSend);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, detaildrinkFragment).addToBackStack(null).commit();
    }
}
