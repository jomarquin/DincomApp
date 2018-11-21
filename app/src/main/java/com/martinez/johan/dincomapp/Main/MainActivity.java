package com.martinez.johan.dincomapp.Main;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.martinez.johan.dincomapp.Conexion.ConexionSQLiteHelper;
import com.martinez.johan.dincomapp.Entities.Tutorial;
import com.martinez.johan.dincomapp.Entities.Term;
import com.martinez.johan.dincomapp.Conexion.IComunicaFragments;
import com.martinez.johan.dincomapp.R;
import com.martinez.johan.dincomapp.Search.SearchTutorialFragment;
import com.martinez.johan.dincomapp.Search.SearchWordFragment;
import com.martinez.johan.dincomapp.Search.SearchingFragment;
import com.martinez.johan.dincomapp.Search.SearchingTutorialFragment;
import com.martinez.johan.dincomapp.Terms.DetailtermFragment;
import com.martinez.johan.dincomapp.Terms.ListtermsFragment;
import com.martinez.johan.dincomapp.Tutorials.DetailtutorialFragment;
import com.martinez.johan.dincomapp.Tutorials.ListtutorialsFragment;
import com.martinez.johan.dincomapp.Tutorials.YoutubeActivity;
import com.martinez.johan.dincomapp.Utilities.Utilities;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AboutFragment.OnFragmentInteractionListener,
        SearchWordFragment.OnFragmentInteractionListener, ListtermsFragment.OnFragmentInteractionListener,
        ListtutorialsFragment.OnFragmentInteractionListener, MenuFragment.OnFragmentInteractionListener,
        IComunicaFragments, DetailtutorialFragment.OnFragmentInteractionListener,
        SearchingFragment.OnFragmentInteractionListener, DetailtermFragment.OnFragmentInteractionListener,
        SearchingTutorialFragment.OnFragmentInteractionListener, SearchTutorialFragment.OnFragmentInteractionListener{

    Fragment menuFragment;
    DetailtermFragment detailtermFragment;
    ArrayList<Term> listTerm;
    MenuItem item;
    int id=0;
    int rowsFire=0;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseRef = ref.child("Terminos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Bienvenido"); //cambia el titulo de la Toolbar
        //getSupportActionBar().hide(); //oculta la Toolbar
        //getSupportActionBar().show(); //muestra la Toolbar
        item=findViewById(R.id.action_search);
        //item.setIcon(R.mipmap.ic_rojo_bar);

        listTerm =new ArrayList<>();

        createLocalDB();
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
     * Este método se encarga de llenar la tabla 'terminos' de la base de datos local
     * con la información de los términos y palabras
     */
    private void createLocalDB() {

        //Se verifica si hay conexión a internet
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (cantRows()==0 && networkInfo != null && networkInfo.isConnected()){
            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Term term;
                    //clearing the previous artist list
                    listTerm.clear();
                    //iterating through all the nodes
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        term=new Term();

                        term.setT_Name(postSnapshot.child("nombre").getValue().toString());
                        term.setT_Definition(postSnapshot.child("significado").getValue().toString());

                        listTerm.add(term);
                    }
                    ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(getApplicationContext(), "bd_diccionario", null, 1 );
                    SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
                    db.beginTransaction();
                    Iterator<Term> it = listTerm.iterator();
                    //Log.i("Create DataBase", "palabra ingresada: ");
                    while (it.hasNext()){
                        Term tmr = it.next();
                        id++;
                        ContentValues values = new ContentValues();
                        values.put(Utilities.FIELD_ID, id);
                        values.put(Utilities.FIELD_T_NAME, tmr.getT_Name());
                        values.put(Utilities.FIELD_T_DEFINITION, tmr.getT_Definition());
                        values.put(Utilities.FIELD_T_LINKTUTORIAL, "");
                        db.insert(Utilities.TABLE_TERMS, null, values);
                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "No se pudo crear la base de datos local",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }else if(cantRows()!=0 && networkInfo != null && networkInfo.isConnected()){
            this.deleteDatabase("bd_diccionario");
            databaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Term term;
                    //clearing the previous artist list
                    listTerm.clear();
                    //iterating through all the nodes
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        term=new Term();

                        term.setT_Name(postSnapshot.child("nombre").getValue().toString());
                        term.setT_Definition(postSnapshot.child("significado").getValue().toString());

                        listTerm.add(term);
                    }
                    ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(getApplicationContext(), "bd_diccionario", null, 1 );
                    SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
                    db.beginTransaction();
                    Iterator<Term> it = listTerm.iterator();
                    //Log.i("Create DataBase", "palabra ingresada: ");
                    while (it.hasNext()){
                        Term tmr = it.next();
                        id++;
                        ContentValues values = new ContentValues();
                        values.put(Utilities.FIELD_ID, id);
                        values.put(Utilities.FIELD_T_NAME, tmr.getT_Name());
                        values.put(Utilities.FIELD_T_DEFINITION, tmr.getT_Definition());
                        values.put(Utilities.FIELD_T_LINKTUTORIAL, "");
                        db.insert(Utilities.TABLE_TERMS, null, values);
                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "No se pudo crear la base de datos local",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }//End createLocalDB()

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
            if(Utilities.SEARCH_TYPE.equals("terms")){
                getSupportActionBar().setTitle("Buscar Términos");
                Fragment miFragment = new SearchWordFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment)
                        .addToBackStack(null).commit();
            }else if (Utilities.SEARCH_TYPE.equals("tutorials")){
                getSupportActionBar().setTitle("Buscar Tutoriales");
                Fragment miFragment = new SearchTutorialFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment)
                        .addToBackStack(null).commit();
            }else{
                Toast.makeText(this, "Seleccione la lista de términos o tutoriales para realizar una búsqueda.",
                        Toast.LENGTH_LONG).show();
            }
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
        }  else if (id == R.id.nav_closeSession) {
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
        Utilities.ID_VIDEO=tutorial.getLinkVideo();

        Intent intent=new Intent(this,YoutubeActivity.class);
        startActivity(intent);
    }
}
