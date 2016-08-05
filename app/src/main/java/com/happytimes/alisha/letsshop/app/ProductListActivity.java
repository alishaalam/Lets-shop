package com.happytimes.alisha.letsshop.app;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.happytimes.alisha.letsshop.R;
import com.happytimes.alisha.letsshop.adapter.ProductAdapter;
import com.happytimes.alisha.letsshop.helper.JacksonRequest;
import com.happytimes.alisha.letsshop.helper.VolleySingleton;
import com.happytimes.alisha.letsshop.model.Product;
import com.happytimes.alisha.letsshop.model.StoreProducts;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements RecyclerViewClickListener {


    List<Product> productList = new ArrayList<>();
    RecyclerView productsRecyclerView;
    ProductAdapter productAdapter;

    private static final String TAG = ProductListActivity.class.getSimpleName();
    public static final String PRODUCT_URL = "https://walmartlabs-test.appspot.com/_ah/api/walmart/v1/walmartproducts";
    public static final String API_KEY = "/add26e4a-c43e-4ce2-87e8-ebcb41cc6b61";
    public static final String DELIMITER = "/";
    public static final int PAGE_SIZE = 30;

    public static int PAGE_NUMBER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        productsRecyclerView = (RecyclerView) findViewById(R.id.product_list);
        setupRecyclerView(productsRecyclerView);

        fetchProductsFromUrl(PRODUCT_URL + API_KEY + DELIMITER + String.valueOf(PAGE_NUMBER) + DELIMITER + String.valueOf(PAGE_SIZE));
    }

    private void setupRecyclerView(RecyclerView productsRecyclerView) {

        if (isTablet(this)) {
            GridLayoutManager glm = new GridLayoutManager(this, 2);
            glm.setOrientation(GridLayoutManager.VERTICAL);
            productsRecyclerView.setLayoutManager(glm);
        } else {
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            productsRecyclerView.setLayoutManager(llm);
        }

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
        List<Product> responseList = response.getProductsList();
        productList.addAll(responseList);
        addProductsToMap(responseList);
        displayProductList(responseList);
    }

    private void addProductsToMap(List<Product> list) {
        for (Product p : list) {
            StoreProducts.PRODUCT_LIST.add(p);
            StoreProducts.PRODUCT_MAP.put(p.getProductId(), p);
        }
    }

    private void displayProductList(List<Product> list) {
        for (Product p : list) {
            Log.d(TAG, p.getProductName() + p.getPrice());
        }

        productAdapter = new ProductAdapter(productList, productsRecyclerView, this);
        productsRecyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();

        productAdapter.setOnLoadMoreListener(new ProductAdapter.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {

                productList.add(null);
                productAdapter.notifyItemInserted(productList.size() - 1);
                productList.remove(productList.size() - 1);
                productAdapter.notifyItemRemoved(productList.size());
                if (productList.size() < 2 * PAGE_SIZE * PAGE_NUMBER) {
                    PAGE_NUMBER++;
                    fetchProductsFromUrl(PRODUCT_URL + API_KEY + DELIMITER + String.valueOf(PAGE_NUMBER) + DELIMITER + String.valueOf(PAGE_SIZE));
                    productAdapter.notifyDataSetChanged();
                }
                productAdapter.setLoaded();
            }
        });
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Product product = productList.get(position);
        Context context = v.getContext();
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(ProductDetailFragment.ARG_ITEM_ID, product.getProductId());
        context.startActivity(intent);

    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
