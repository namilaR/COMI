package com.example.namilaradith.comi_beta;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Namila Radith on 2016-03-10.
 */



public class TabActivityFragment extends Fragment {

    ListView lv;
    Context context;

    ArrayList prgmName;
    public static String [] prgmNameList = {"one","two","tree","four"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tab_activity_fragment, container, false);
    }
}
