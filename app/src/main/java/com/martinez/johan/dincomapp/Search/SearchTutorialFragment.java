package com.martinez.johan.dincomapp.Search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
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
import com.martinez.johan.dincomapp.Entities.Tutorial;
import com.martinez.johan.dincomapp.R;
import com.martinez.johan.dincomapp.Utilities.Utilities;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchTutorialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchTutorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchTutorialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Button searchTut;
    EditText tutSearch;
    private static final String TAG = "MyActivity";
    ArrayList<Tutorial> ListTutorials;
    ArrayList<Tutorial> ListTutorials1;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseRef = ref.child("Tutoriales");

    public SearchTutorialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchTutorialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchTutorialFragment newInstance(String param1, String param2) {
        SearchTutorialFragment fragment = new SearchTutorialFragment();
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
        View view = inflater.inflate(R.layout.fragment_search_tutorial, container, false);

        tutSearch=view.findViewById(R.id.editTextSearchTut);
        ListTutorials=new ArrayList<>();
        ListTutorials1=new ArrayList<>();

        getListTutorials();

        searchTut=view.findViewById(R.id.btnSearchTut);
        searchTut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = tutSearch.getText().toString();
                if (!TextUtils.isEmpty(search)){
                    boolean searchState=false;
                    Iterator<Tutorial> it = ListTutorials.iterator();
                    String tutorialSearch = tutSearch.getText().toString().toLowerCase();
                    Utilities.TUTORIAL_SEARCH = tutorialSearch;
                    Log.i(TAG, "palabra ingresada: "+tutorialSearch);
                    while (it.hasNext()){
                        Tutorial tut = it.next();
                        String wordTerm=tut.getdName().toLowerCase();//aca
                        boolean findText = wordTerm.contains(tutorialSearch);
                        if (findText){
                            searchState=true;
                            ListTutorials1.add(tut);
                        }
                    }
                    if (searchState){
                        tutSearch.setText("");
                        android.support.v4.app.Fragment miFrag = new SearchingTutorialFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_main, miFrag).addToBackStack(null).commit();
                    }else{
                        Toast.makeText(getContext(), "No hay resultados con los términos de búsqueda",
                                Toast.LENGTH_SHORT).show();
                        tutSearch.setText("");
                    }
                }else{
                    Toast.makeText(getContext(), "Ingrese una palabra para iniciar la búsqueda",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void getListTutorials() {

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Tutorial tutorial;
                //clearing the previous artist list
                ListTutorials.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    tutorial=new Tutorial();

                    tutorial.setdName(postSnapshot.child("nombre").getValue().toString());
                    tutorial.setLinkVideo(postSnapshot.child("video").getValue().toString());

                    ListTutorials.add(tutorial);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No se pudo obtener información ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
