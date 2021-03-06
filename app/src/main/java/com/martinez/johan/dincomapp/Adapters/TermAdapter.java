package com.martinez.johan.dincomapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.martinez.johan.dincomapp.Entities.Term;
import com.martinez.johan.dincomapp.R;

import java.util.ArrayList;




public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermsViewHolder> implements  View.OnClickListener{

    ArrayList<Term> listTerms;
    private View.OnClickListener listener;

    public TermAdapter(ArrayList<Term> listTerms) {
        this.listTerms = listTerms;
    }

    @Override
    public TermsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.item_list_terms, null, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);
        return new TermsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TermsViewHolder holder, int position) {
        holder.txtName.setText(listTerms.get(position).getT_Name());

    }

    @Override
    public int getItemCount() {
        return listTerms.size();
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

    public class TermsViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;

        public TermsViewHolder(View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.id_name_listTerms);
        }
    }
}
