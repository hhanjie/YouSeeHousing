package com.example.youseehousing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.youseehousing.dummy.DummyContent.DummyItem;

public class MainHousingListing extends FragmentActivity implements ItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

    }

    public void onListFragmentInteraction(DummyItem item) {

    }
}
