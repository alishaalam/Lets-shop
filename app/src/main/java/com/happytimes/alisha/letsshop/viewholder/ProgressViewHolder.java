package com.happytimes.alisha.letsshop.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.happytimes.alisha.letsshop.R;

/**
 * Created by alishaalam on 7/25/16.
 */
public class ProgressViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public ProgressViewHolder(View v) {
        super(v);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
    }
}