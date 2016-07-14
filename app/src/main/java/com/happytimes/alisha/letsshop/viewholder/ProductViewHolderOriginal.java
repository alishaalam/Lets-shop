package com.happytimes.alisha.letsshop.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.happytimes.alisha.letsshop.R;

/**
 * Created by alishaalam on 7/8/16.
 */
public class ProductViewHolderOriginal extends RecyclerView.ViewHolder implements View.OnClickListener {

    public View vView;
    public ImageView vImageURL;
    public TextView vProductTitle;
    public TextView vProductPrice;
    //public IProductViewHolderClicks vListener;


    //public ProductViewHolder(View view, IProductViewHolderClicks listener) {
    public ProductViewHolderOriginal(View view) {//, IProductViewHolderClicks listener) {
        super(view);
        vView = view;
        vProductTitle = (TextView) view.findViewById(R.id.product_title);
        vProductPrice = (TextView) view.findViewById(R.id.product_price);
        vImageURL = (ImageView) view.findViewById(R.id.product_image_url);
        //vListener = listener;
        view.setOnClickListener(this);
    }


    public TextView getProductTitle() {
        return vProductTitle;
    }

    public ImageView getImageURL() {
        return vImageURL;
    }

    public TextView getProductPrice() {
        return vProductPrice;
    }

    @Override
    public void onClick(View v) {
        //vListener.onItemClick(v);
    }

}
