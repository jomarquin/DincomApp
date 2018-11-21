package com.martinez.johan.dincomapp.Search;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.martinez.johan.dincomapp.Adapters.TermAdapter;
import com.martinez.johan.dincomapp.Conexion.ConexionSQLiteHelper;
import com.martinez.johan.dincomapp.Entities.Term;
import com.martinez.johan.dincomapp.Conexion.IComunicaFragments;
import com.martinez.johan.dincomapp.R;
import com.martinez.johan.dincomapp.Utilities.Utilities;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchWordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchWordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Button searchTerm;
    EditText termSearch;
    RecyclerView recyclerListTerms;
    IComunicaFragments interfaceComunicaFragments;
    private static final String TAG = "MyActivity";
    ArrayList<Term> ListTerms;
    ArrayList<Term> ListTerms1;

    ConexionSQLiteHelper conn;
    Activity activity;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseRef = ref.child("Terminos");

    public SearchWordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchWordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchWordFragment newInstance(String param1, String param2) {
        SearchWordFragment fragment = new SearchWordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_word, container, false);

        termSearch=view.findViewById(R.id.editTextBusqueda);
        ListTerms=new ArrayList<>();
        ListTerms1=new ArrayList<>();
        conn = new ConexionSQLiteHelper(getContext(), "bd_diccionario", null, 1);

        recyclerListTerms =view.findViewById(R.id.id_recycler_searchWord);
        recyclerListTerms.setLayoutManager(new LinearLayoutManager(getContext()));

        getListTerms();

        searchTerm=view.findViewById(R.id.btnBuscar);
        searchTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = termSearch.getText().toString();
                if (!TextUtils.isEmpty(search)){
                    boolean searchState=false;
                    Iterator<Term> it = ListTerms.iterator();
                    String wordSearch = termSearch.getText().toString().toLowerCase();
                    Utilities.WORD_SEARCH = wordSearch;
                    Log.i(TAG, "palabra ingresada: "+wordSearch);
                    ListTerms1.clear();
                    while (it.hasNext()){
                        Term tmr = it.next();
                        String wordTerm=tmr.getT_Name().toLowerCase();
                        boolean findText = wordTerm.contains(wordSearch);
                        if (findText){
                            searchState=true;
                            ListTerms1.add(tmr);
                        }
                    }
                    if (searchState){
//                        termSearch.setText(""); //Con estas 3 lineas enviaba la busqueda al fragment searchingFragment
//                        Fragment miFrag = new SearchingFragment();
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.content_main, miFrag).addToBackStack(null).commit();
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
                        Toast.makeText(getContext(), "No hay resultados con los términos de búsqueda",
                                Toast.LENGTH_SHORT).show();
                        termSearch.setText("");
                    }
                }else{
                    Toast.makeText(getContext(), "Ingrese una palabra para iniciar la búsqueda",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void getListTerms() {

        //Se verifica si hay conexión a internet
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
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
                    Toast.makeText(getContext(), "No se pudo obtener información ", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            SQLiteDatabase db=conn.getReadableDatabase();

            Term term;

            Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilities.TABLE_TERMS, null);

            while (cursor.moveToNext()){
                term =new Term();
                //term.setId_term(cursor.getInt(0));
                term.setT_Name(cursor.getString(1));
                term.setT_Definition(cursor.getString(2));
                //term.setT_linkTutorial(cursor.getString(3));
                ListTerms.add(term);
            }
        }
    }//End getListTerms()

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){ //Establece comunicacion entre la lista y el detalle
            this.activity = (Activity) context;
            interfaceComunicaFragments = (IComunicaFragments) this.activity;
        }

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
