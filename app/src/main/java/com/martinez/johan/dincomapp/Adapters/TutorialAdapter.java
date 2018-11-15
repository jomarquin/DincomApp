package com.martinez.johan.dincomapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.martinez.johan.dincomapp.Entities.Tutorial;
import com.martinez.johan.dincomapp.R;

import java.util.ArrayList;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.DrinksViewHolder> implements  View.OnClickListener {

    ArrayList<Tutorial> listTutorials;
    private View.OnClickListener listener;
    private Context mContext;

    public TutorialAdapter(ArrayList<Tutorial> listTutorials) {
        this.listTutorials = listTutorials;
    }

    @Override
    public DrinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.item_list_tutorials, null, false);
        RecyclerView.LayoutParams layParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layParams);

        view.setOnClickListener(this);
        return new DrinksViewHolder(view);
    }


    @Override
    public void onBindViewHolder(DrinksViewHolder holder, int position) {

        holder.txtNombre.setText(listTutorials.get(position).getdName());
        Log.i("Tutorial", "link: "+listTutorials.get(position).getLinkVideo());
        //holder.video.loadData(listTutorials.get(position).getLinkVideo(),"text/html", "utf-8");

    }

    @Override
    public int getItemCount() {
        return listTutorials.size();
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

        TextView txtNombre;
        WebView video;

        public DrinksViewHolder(View itemView) {
            super(itemView);
            txtNombre=itemView.findViewById(R.id.id_name_listTutorial);
            //txtVideo=itemView.findViewById(R.id
            video=itemView.findViewById(R.id.id_videoTutorial);
        }
    }
}
