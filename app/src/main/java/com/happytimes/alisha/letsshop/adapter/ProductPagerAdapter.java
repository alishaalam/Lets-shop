package com.happytimes.alisha.letsshop.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.happytimes.alisha.letsshop.app.ProductDetailFragment;
import com.happytimes.alisha.letsshop.model.StoreProducts;

/**
 * Created by alishaalam on 7/10/16.
 */
public class ProductPagerAdapter extends FragmentStatePagerAdapter {



    public ProductPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ProductDetailFragment.ARG_ITEM_PAGER_ID, i);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getCount() {
        return StoreProducts.PRODUCT_MAP.size();
    }
}
