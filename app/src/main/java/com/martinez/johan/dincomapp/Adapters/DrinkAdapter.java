package com.martinez.johan.dincomapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.martinez.johan.dincomapp.Entities.Drink;

import java.util.ArrayList;


import com.martinez.johan.dincomapp.R;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinksViewHolder> implements  View.OnClickListener {

    ArrayList<Drink> listDrinks;
    private View.OnClickListener listener;

    public DrinkAdapter(ArrayList<Drink> listDrinks) {
        this.listDrinks = listDrinks;
    }

    @Override
    public DrinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.item_list_drinks, null, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);
        return new DrinksViewHolder(view);
    }



    @Override
    public void onBindViewHolder(DrinksViewHolder holder, int position) {

        holder.txtNombre.setText(listDrinks.get(position).getdName());
        holder.txtPrecio.setText(listDrinks.get(position).getdPrice());//aca tenia el error del recyclerview
        holder.foto.setImageResource(listDrinks.get(position).getdImage());

    }

    @Override
    public int getItemCount() {
        return listDrinks.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class DrinksViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtPrecio;
        ImageView foto;

        public DrinksViewHolder(View itemView) {
            super(itemView);
            txtNombre=itemView.findViewById(R.id.id_name_listDrink);
            txtPrecio=itemView.findViewById(R.id.id_price_listDrink);
            foto=itemView.findViewById(R.id.id_photo_listDrink);
        }
    }
}
