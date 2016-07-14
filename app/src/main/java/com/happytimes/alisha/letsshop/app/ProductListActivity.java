package com.happytimes.alisha.letsshop.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.happytimes.alisha.letsshop.R;
import com.happytimes.alisha.letsshop.helper.JacksonRequest;
import com.happytimes.alisha.letsshop.helper.VolleySingleton;
import com.happytimes.alisha.letsshop.model.Product;
import com.happytimes.alisha.letsshop.model.StoreProducts;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Products. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ProductDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ProductListActivity extends AppCompatActivity implements  RecyclerViewClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    public static boolean mTwoPane;
    List<Product> productList = new ArrayList<>();
    RecyclerView productsRecyclerView;
    ProductAdapter productAdapter;

    private static final String TAG = ProductListActivity.class.getSimpleName();
    public static final String PRODUCT_URL = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/walmartproducts";
    public static final String API_KEY = "/add26e4a-c43e-4ce2-87e8-ebcb41cc6b61";
    public static final String PAGE_NUMBER = "/1";
    public static final String PAGE_SIZE = "/30";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        productsRecyclerView = (RecyclerView) findViewById(R.id.product_list);
        assert productsRecyclerView != null;
        setupRecyclerView(productsRecyclerView);

        if (findViewById(R.id.product_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        fetchProductsFromUrl(PRODUCT_URL + API_KEY + PAGE_NUMBER + PAGE_SIZE);
    }

    private void setupRecyclerView(@NonNull RecyclerView productsRecyclerView) {

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        productsRecyclerView.setLayoutManager(llm);

        productsRecyclerView.setAdapter(new ProductAdapter());
        productsRecyclerView.setHasFixedSize(true);
    }

    private void fetchProductsFromUrl(String url) {
        JacksonRequest<StoreProducts> jacksonRequest = new JacksonRequest<>
                (Request.Method.GET, url, null, StoreProducts.class, new Response.Listener<StoreProducts>() {
                    @Override
                    public void onResponse(StoreProducts response) {
                        Log.d(TAG, response.toString());
                        parseResponseDetails(response);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // Adding a request (in this example, called jacksonRequest) to the RequestQueue.
        VolleySingleton.getInstance(this).addToRequestQueue(jacksonRequest, TAG);
    }

    private void parseResponseDetails(StoreProducts response) {
        productList = response.getProductsList();
        addProductsToMap(productList);
        displayProductList(productList);
    }

    private void addProductsToMap(List<Product> productList) {
        for(Product p : productList) {
            StoreProducts.PRODUCT_LIST.add(p);
            StoreProducts.PRODUCT_MAP.put(p.getProductId(), p);
        }
    }

    private void displayProductList(List<Product> productList) {
        for(Product p : productList) {
            Log.d(TAG, p.toString());
        }

        productAdapter = new ProductAdapter(productList, this, this);
        productsRecyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Product product = productList.get(position);
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ProductDetailFragment.ARG_ITEM_ID, product.getProductId());
            ProductDetailFragment fragment = new ProductDetailFragment();
            fragment.setArguments(arguments);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.product_detail_container, fragment)
                    .commit();
        } else {
            Context context = v.getContext();
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra(ProductDetailFragment.ARG_ITEM_ID, product.getProductId());
            context.startActivity(intent);
        }

    }

    /*public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            //holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.vView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ProductDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        ProductDetailFragment fragment = new ProductDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.product_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ProductDetailActivity.class);
                        intent.putExtra(ProductDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View vView;
            public final ImageView mIdView;
            public final TextView mContentView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                vView = view;
                mIdView = (ImageView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }*/
}
