package com.aji.crudvolley.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aji.crudvolley.R;

/**
 * Created by AJISETYA
 */
public class HapeHolder extends RecyclerView.ViewHolder {
    public TextView txthape;
    public ImageView imghape;

    public HapeHolder(View itemView) {
        super(itemView);

        txthape = (TextView)itemView.findViewById(R.id.txthape);
        imghape = (ImageView)itemView.findViewById(R.id.imghp);
    }
}
