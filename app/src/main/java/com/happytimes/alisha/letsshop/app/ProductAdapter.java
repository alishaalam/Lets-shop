package com.happytimes.alisha.letsshop.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.happytimes.alisha.letsshop.R;
import com.happytimes.alisha.letsshop.helper.VolleySingleton;
import com.happytimes.alisha.letsshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alishaalam on 7/8/16.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> mProductsList = new ArrayList<>();
    ImageLoader mImageLoader;
    Context mContext;
    private static RecyclerViewClickListener mItemListener;

    public ProductAdapter() {
    }

    public ProductAdapter(List<Product> productsList) {
        this.mProductsList = productsList;
    }

    public ProductAdapter(List<Product> productsList, Context mContext, RecyclerViewClickListener itemListener) {
        this.mProductsList = productsList;
        this.mContext = mContext;
        this.mItemListener = itemListener;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.product_list_content, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        final Product product = mProductsList.get(position);
        if (product != null) {
            if (mImageLoader == null)
                mImageLoader = VolleySingleton.getInstance(mContext.getApplicationContext()).getImageLoader();

            holder.getProductTitle().setText(product.getProductName());
            holder.getProductPrice().setText(product.getPrice());
            Picasso.with(mContext)
                    .load(product.getProductImage())
                    .fit().centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.getImageURL());
        }
    }

    @Override
    public int getItemCount() {
        return mProductsList.size();
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View vView;
        public ImageView vImageURL;
        public TextView vProductTitle;
        public TextView vProductPrice;
        //public IProductViewHolderClicks vListener;


        //public ProductViewHolder(View view, IProductViewHolderClicks listener) {
        public ProductViewHolder(View view) {//, IProductViewHolderClicks listener) {
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
            mItemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }

}
