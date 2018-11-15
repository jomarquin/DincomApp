package com.martinez.johan.dincomapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.martinez.johan.dincomapp.Adapters.TermAdapter;
import com.martinez.johan.dincomapp.Entities.Term;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchTermActivity extends AppCompatActivity {

    Button searchTerm;
    EditText termSearch;
    RecyclerView recyclerListTerms;
    IComunicaFragments interfaceComunicaFragments;
    private static final String TAG = "MyActivity";

    ArrayList<Term> ListTerms;
    ArrayList<Term> ListTerms1;
    ListtermsFragment searchWordFragment;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseRef = ref.child("Terminos");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_term);

        termSearch=findViewById(R.id.editTextBusqueda);
        ListTerms=new ArrayList<>();
        ListTerms1=new ArrayList<>();
        searchWordFragment = new ListtermsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, searchWordFragment).commit();

        /**recyclerListTerms =findViewById(R.id.id_recycler_terms);
        recyclerListTerms.setLayoutManager(new LinearLayoutManager(this));**/

        searchTerm=findViewById(R.id.btnBuscar);
        searchTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = termSearch.getText().toString();
                if (!TextUtils.isEmpty(search)){
                    boolean searchState=false;
                    Iterator<Term> it = ListTerms.iterator();
                    String wordSearch = termSearch.getText().toString().toLowerCase();
                    Log.i(TAG, "palabra ingresada: "+wordSearch);
                    int i=0;
                    while (it.hasNext()){
                        Term tmr = it.next();
                        String wordTerm=tmr.getT_Name().toLowerCase();
                        Log.i(TAG, wordTerm+" prueba1");
                        boolean findText = wordTerm.contains(wordSearch);
                        if (findText){
                            searchState=true;
                            ListTerms1.add(tmr);
                            Term prueba=ListTerms1.get(i);
                            i++;
                            Log.i(TAG, "prueba lista 2: "+prueba.getT_Name());
                        }
                        Log.i(TAG, tmr.getT_Name()+" prueba2");
                    }
                    if (searchState){
                        TermAdapter adapter =  new TermAdapter(ListTerms1);
                        recyclerListTerms.setAdapter(adapter);

                        adapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //evento para llamar DetailtermFragment
                                interfaceComunicaFragments.sendTerm
                                        (ListTerms1.get(recyclerListTerms.getChildAdapterPosition(view)));
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), "No hay resultados con los términos de búsqueda",
                                Toast.LENGTH_SHORT).show();
                        termSearch.setText("");
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Ingrese una palabra para iniciar la búsqueda",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    /**
     * Acá obtengo la lista de términos de la base de datos firebase para realizar la búsqueda
     */
    @Override
    protected void onStart() {
        super.onStart();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Term term;
                //clearing the previous artist list
                ListTerms.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    term=new Term();

                    term.setT_Name(postSnapshot.child("nombre").getValue().toString());
                    term.setT_Definition(postSnapshot.child("significado").getValue().toString());

                    ListTerms.add(term);
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No se pudo obtener información ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
