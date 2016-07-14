package com.happytimes.alisha.letsshop.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.happytimes.alisha.letsshop.R;
import com.happytimes.alisha.letsshop.model.Product;
import com.happytimes.alisha.letsshop.model.StoreProducts;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Product detail screen.
 * This fragment is either contained in a {@link ProductListActivity}
 * in two-pane mode (on tablets) or a {@link ProductDetailActivity}
 * on handsets.
 */
public class ProductDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_PAGER_ID = "item_id";
    private static final String TAG = ProductDetailFragment.class.getSimpleName();

    /**
     * The content this fragment is presenting.
     */
    private Product mProduct;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductDetailFragment() {
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mProduct = StoreProducts.PRODUCT_MAP.get(getArguments().get(ARG_ITEM_ID));

            *//*Activity activity = this.getActivity();
            TextView textView = (TextView) activity.findViewById(R.id.product_name);
            textView.setText(mProduct.getProductName());
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mProduct.getProductName());
            }*//*
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_detail_fragment, container, false);

        Toolbar appBarLayout = (Toolbar) rootView.findViewById(R.id.toolbar_detail);

        /*if (appBarLayout != null) {
            appBarLayout.setTitle(mProduct.getProductName());
        }*/

        // Show the  content as text in a TextView.
        if (mProduct != null) {
          ((TextView) rootView.findViewById(R.id.product_title)).setText(mProduct.longDescription);
            Log.d(TAG, ((TextView)rootView).getText().toString());
        }

        Bundle args = getArguments();
        if(args != null) {
            int position = args.getInt(ARG_ITEM_PAGER_ID);
            /*((TextView) rootView.findViewById(R.id.product_title)).append(
                    Integer.toString(args.getInt(ARG_ITEM_PAGER_ID)));*/
            mProduct = StoreProducts.PRODUCT_LIST.get(position);
            TextView product_title = (TextView) rootView.findViewById(R.id.product_title);
            product_title.setText(mProduct.getProductName());
            if (appBarLayout != null) {
                appBarLayout.setTitle(getActivity().getTitle());
            }

            WebView wvShortDescription = (WebView) rootView.findViewById(R.id.product_short_desc);
            wvShortDescription.loadDataWithBaseURL(null, mProduct.getShortDescription(), "text/html", "utf-8", null);

            RatingBar rbProductRating = (RatingBar) rootView.findViewById(R.id.product_review_rating);
            rbProductRating.setRating(Float.parseFloat(mProduct.getReviewRating()));

            TextView tvProductReviewCount = (TextView) rootView.findViewById(R.id.product_review_count);
            tvProductReviewCount.setText("( " + mProduct.getReviewCount() + " reviews )");

            ImageView imgProductImage = (ImageView) rootView.findViewById(R.id.product_image);
            Picasso.with(getContext())
                    .load(mProduct.getProductImage())
                    .fit().centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imgProductImage);

            TextView tvProductPrice = (TextView) rootView.findViewById(R.id.product_price);
            tvProductPrice.append(mProduct.getPrice());



            WebView wvLongDescription = (WebView) rootView.findViewById(R.id.product_long_desc);
            wvLongDescription.loadDataWithBaseURL(null, mProduct.getLongDescription(), "text/html", "utf-8", null);

        }

        return rootView;
    }
}
