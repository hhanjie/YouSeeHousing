package com.example.youseehousing.lib.fragment;


import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends ListPageFragment {

    private ListType type = ListPageFragment.ListType.FAVORITES;

    private int mColumnCount = 1;

    public FavoritesFragment() {
        super();
    }

    @Override
    public ListType getListType() {
        return type;
    }
}