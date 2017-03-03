package com.aji.crudvolley.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aji.crudvolley.MainActivity;
import com.aji.crudvolley.R;
import com.aji.crudvolley.helper.helper;
import com.aji.crudvolley.holder.HapeHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AJISETYA
 */
public class AdapterList extends RecyclerView.Adapter<HapeHolder> {

    private ArrayList<HashMap<String, String>> list_data;
    Context context;

    public AdapterList(MainActivity mainActivity, ArrayList<HashMap<String, String>> list_data) {
        this.list_data = list_data;
        this.context = mainActivity;
    }

    @Override
    public HapeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        return new HapeHolder(view);
    }

    @Override
    public void onBindViewHolder(HapeHolder holder, int position) {
        holder.txthape.setText(list_data.get(position).get("merk"));
        Glide.with(context).load(helper.main_url + "img/" + list_data.get(position).get("gambar"))
                .crossFade().placeholder(R.mipmap.ic_launcher).into(holder.imghape);
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }
}
