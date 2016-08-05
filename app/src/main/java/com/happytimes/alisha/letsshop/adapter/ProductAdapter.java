package com.happytimes.alisha.letsshop.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.happytimes.alisha.letsshop.R;
import com.happytimes.alisha.letsshop.app.RecyclerViewClickListener;
import com.happytimes.alisha.letsshop.helper.VolleySingleton;
import com.happytimes.alisha.letsshop.model.Product;
import com.happytimes.alisha.letsshop.viewholder.ProgressViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alishaalam on 7/8/16.
 */
public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> mProductsList = new ArrayList<>();
    ImageLoader mImageLoader;
    Context mContext;

    private static RecyclerViewClickListener mItemListener;

    // The minimum amount of items to have below your current scroll position before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public static final int VIEW_PROG = 0; //Represents end of the list
    public static final int PRODUCT = 1;

    public ProductAdapter() {
    }


    public ProductAdapter(List<Product> productsList, RecyclerView recyclerView, RecyclerViewClickListener itemListener) {
        this.mProductsList = productsList;
        this.mItemListener = itemListener;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {

            final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = gridLayoutManager.getItemCount();
                    lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    /**
     * Returns the view type of the item at position for view recycling**/
    @Override
    public int getItemViewType(int position) {
        if(mProductsList.get(position) == null) {
            return VIEW_PROG;
        } else if (mProductsList.get(position) instanceof Product) {
            return PRODUCT;

        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_PROG:
                View v0 = inflater.inflate(R.layout.progressbar_item, parent, false);
                viewHolder = new ProgressViewHolder(v0);
                break;
            default:
                View v1 = inflater.inflate(R.layout.product_list_content, parent, false);
                viewHolder = new ProductAdapter.ProductViewHolder(v1);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case VIEW_PROG:
                ((ProgressViewHolder) viewHolder).progressBar.setIndeterminate(true);
                break;
            case PRODUCT:
                ProductViewHolder placeVH = (ProductViewHolder) viewHolder;
                configureProductViewHolder(placeVH, position);
                break;
        }
    }

    private void configureProductViewHolder(ProductViewHolder productViewHolder, int position) {
        final Product product = mProductsList.get(position);
        if (product != null) {
            if (mImageLoader == null)
                mImageLoader = VolleySingleton.getInstance(mContext.getApplicationContext()).getImageLoader();

            productViewHolder.getProductTitle().setText(product.getProductName());
            productViewHolder.getProductPrice().setText(product.getPrice());
            Picasso.with(mContext)
                    .load(product.getProductImage())
                    .fit()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(productViewHolder.getImageURL());
        }
    }

    @Override
    public int getItemCount() {
        return mProductsList.size();
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View vView;
        public ImageView vImageURL;
        public TextView vProductTitle;
        public TextView vProductPrice;

        public ProductViewHolder(View view) {
            super(view);
            vView = view;
            vProductTitle = (TextView) view.findViewById(R.id.product_title);
            vProductPrice = (TextView) view.findViewById(R.id.product_price);
            vImageURL = (ImageView) view.findViewById(R.id.product_image_url);
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
            mItemListener.recyclerViewListClicked(v, this.getAdapterPosition());
        }
    }
}
