package com.martinez.johan.dincomapp.Terms;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListtermsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListtermsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListtermsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerListTerms;
    ArrayList<Term> listTerm;
    ConexionSQLiteHelper conn;
    Activity activity;
    IComunicaFragments interfaceComunicaFragments;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseRef = ref.child("Terminos");

    public ListtermsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListtermsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListtermsFragment newInstance(String param1, String param2) {
        ListtermsFragment fragment = new ListtermsFragment();
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
        View view=inflater.inflate(R.layout.fragment_listterms, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "bd_diccionario", null, 1);
        Utilities.SEARCH_TYPE="terms"; //Para saber que fragment de busqueda cargar
        listTerm =new ArrayList<>();
        recyclerListTerms =view.findViewById(R.id.id_recycler_terms);
        recyclerListTerms.setLayoutManager(new LinearLayoutManager(getContext()));

        //Se verifica si hay conexión a internet
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            consultListTerms();
        } else {
            consultListTermsLocal();
            Toast.makeText(getContext(), "Base de datos local", Toast.LENGTH_SHORT).show();
            TermAdapter adapter =  new TermAdapter(listTerm);
            recyclerListTerms.setAdapter(adapter);

            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //evento para llamar DetailtermFragment
                    interfaceComunicaFragments.sendTerm
                            (listTerm.get(recyclerListTerms.getChildAdapterPosition(view)));
                }
            });
        }
        return view;
    }

    private void consultListTerms() {

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
                TermAdapter adapter =  new TermAdapter(listTerm);
                recyclerListTerms.setAdapter(adapter);

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //evento para llamar DetailtermFragment
                        interfaceComunicaFragments.sendTerm
                                (listTerm.get(recyclerListTerms.getChildAdapterPosition(view)));
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No se pudo obtener información ", Toast.LENGTH_SHORT).show();
            }
        });
    }//End consultListTerms()

    private void consultListTermsLocal() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Term term;

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilities.TABLE_TERMS, null);

        while (cursor.moveToNext()){
            term =new Term();
            term.setT_Name(cursor.getString(1));
            term.setT_Definition(cursor.getString(2));

            listTerm.add(term);
        }

    }//End consultListTermsLocal()


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
