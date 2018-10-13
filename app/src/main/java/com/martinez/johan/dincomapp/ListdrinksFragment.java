package com.martinez.johan.dincomapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.martinez.johan.dincomapp.Adapters.DrinkAdapter;
import com.martinez.johan.dincomapp.Entities.Drink;
import com.martinez.johan.dincomapp.Utilities.Utilities;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListdrinksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListdrinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListdrinksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerListDrinks;
    ArrayList<Drink> listDrink;
    ConexionSQLiteHelper conn;
    Activity activity;
    IComunicaFragments interfaceComunicaFragments;

    public ListdrinksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListdrinksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListdrinksFragment newInstance(String param1, String param2) {
        ListdrinksFragment fragment = new ListdrinksFragment();
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
        View view=inflater.inflate(R.layout.fragment_listdrinks, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "bd_maxipollo", null, 1);
        listDrink=new ArrayList<>();
        recyclerListDrinks=view.findViewById(R.id.id_recycler_drinks);
        recyclerListDrinks.setLayoutManager(new LinearLayoutManager(getContext()));

        //fillDrinksList();
        consultListDrinks();

        DrinkAdapter adapter =  new DrinkAdapter(listDrink);
        recyclerListDrinks.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //evento para llamar al Fragment
                interfaceComunicaFragments.sendDrink
                        (listDrink.get(recyclerListDrinks.getChildAdapterPosition(view)));
            }
        });

        return view;
    }

    private void consultListDrinks() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Drink drink;

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilities.TABLE_DRINKS, null);

        String name;
        while (cursor.moveToNext()){
            drink=new Drink();
            drink.setdName(cursor.getString(0));
            drink.setdPrice("Precio: $"+cursor.getString(1));
            //drink.setdImage(R.drawable.bebidas1);

            name=cursor.getString(0);
            if (name.equals("Jugo Natural")){
                drink.setdImage(R.drawable.jugo_natural);
            }else if(name.equals("Limonada de Coco")){
                drink.setdImage(R.drawable.limonada_coco);
            }else if(name.equals("Granizado")){
                drink.setdImage(R.drawable.granizado);
            }else if(name.equals("Michelada")){
                drink.setdImage(R.drawable.michelada);
            }else if(name.equals("Gaseosa")){
                drink.setdImage(R.drawable.gaseosa);
            }else if(name.equals("Cocteles")){
                drink.setdImage(R.drawable.bebidas1);
            }else if(name.equals("Cerveza")){
                drink.setdImage(R.drawable.cerveza);
            }else {
                drink.setdImage(R.drawable.bebidas1);
            }

            listDrink.add(drink);

        }
    }

    /**private void fillDrinksList() {
        listDrink.add(new Drink("Pollo Asado",  "Precio: 15000",  R.mipmap.pollo_asado));
        listDrink.add(new Drink("Pollo Asado",  "Precio: 15000",  R.mipmap.pollo_asado));
        listDrink.add(new Drink("Pollo Asado",  "Precio: 15000",  R.mipmap.pollo_asado));
        listDrink.add(new Drink("Pollo Asado",  "Precio: 15000",  R.mipmap.pollo_asado));
        listDrink.add(new Drink("Pollo Asado",  "Precio: 15000",  R.mipmap.pollo_asado));
        listDrink.add(new Drink("Pollo Asado",  "Precio: 15000",  R.mipmap.pollo_asado));
        listDrink.add(new Drink("Pollo Asado",  "Precio: 15000",  R.mipmap.pollo_asado));
    }*/

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
