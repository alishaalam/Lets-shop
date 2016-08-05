package com.happytimes.alisha.letsshop.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.happytimes.alisha.letsshop.R;
import com.happytimes.alisha.letsshop.adapter.ProductPagerAdapter;

public class ProductDetailActivity extends FragmentActivity {

    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    ProductPagerAdapter mProductPagerAdapter;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_product_detail);
        setContentView(R.layout.activity_pager);

       /* if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(ProductDetailFragment.ARG_ITEM,
                    getIntent().getParcelableExtra(ProductDetailFragment.ARG_ITEM));
            ProductDetailFragment fragment = new ProductDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pager, fragment)
                    .commit();
        }
*/
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mProductPagerAdapter =
                new ProductPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mProductPagerAdapter);
        int position = getIntent().getIntExtra(ProductDetailFragment.ARG_ITEM_PAGER_ID, 0);
        mViewPager.setCurrentItem(position);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            navigateUpTo(new Intent(this, ProductListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
