package com.example.namilaradith.comi_beta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import Appclasses.FireErroDialogFragment;
import com.example.namilaradith.comi_beta.GridViewAdapter;

public class MainActivity extends AppCompatActivity {
    private int[] icons = {R.drawable.ic_activity,R.drawable.ic_calander,R.drawable.ic_groups,R.drawable.ic_messages};

    ///////////////////////////////////////////////////////////////////


    //Web api url
    public static final String DATA_URL = "http://192.168.8.101:80/comi_server/?method=get_shared_intrest";

    //Tag values to read from json
    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_NAME = "name";
    public static final String TAG_ID = "id";

    //GridView Object
    private GridView gridView;

    //ArrayList for Storing image urls and titles
    private ArrayList<String> images;
    private ArrayList<String> names;
    private ArrayList<String> ids;


    private void getData(){
        //Showing a progress dialog while our app fetches the data from url
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...","Fetching data...",false,false);

        //Creating a json array request to get the json from our api
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing the progressdialog on response
                        loading.dismiss();

//                        String s = response.toString();
//                        final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Please wait...", s, false, false);



//
//                        //Displaying our grid
                        showGrid(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding our request to the queue
        requestQueue.add(jsonArrayRequest);
    }


    private void showGrid(JSONArray jsonArray){

        //Looping through all the elements of json array
        for(int i = 0; i<jsonArray.length(); i++){
            //Creating a json object of the current index
            JSONObject obj = null;
            try {

                //getting json object from current index
                obj = jsonArray.getJSONObject(i);

                //getting image url and title from json object
                images.add(obj.getString(TAG_IMAGE_URL));
                names.add(obj.getString(TAG_NAME));
                ids.add(obj.getString(TAG_ID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Creating GridViewAdapter Object
        GridViewAdapter gridViewAdapter = new GridViewAdapter(MainActivity.this,images,names,ids);


        //Adding adapter to gridview
        gridView.setAdapter(gridViewAdapter);
    }






    /////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Intrests").setIcon(icons[0]));
        tabLayout.addTab(tabLayout.newTab().setText("Activities").setIcon(icons[1]));
        tabLayout.addTab(tabLayout.newTab().setText("Groups").setIcon(icons[2]));
        tabLayout.addTab(tabLayout.newTab().setText("Messages").setIcon(icons[3]));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                gridView = (GridView) findViewById(R.id.gridView);

                images = new ArrayList<>();
                names = new ArrayList<>();

                //Calling the getData method
                getData();

                viewPager.setCurrentItem(tab.getPosition());

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {

                        StringBuilder sb = new StringBuilder();
                        sb.append("");
                        sb.append(images.get(position));
                        String strI = sb.toString();

//                        FireErroDialogFragment Fr = new FireErroDialogFragment("SADAD");
//                        final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Please wait...",strI,false,false);


                        Intent goToNextActivity = new Intent(MainActivity.this, SelectedIntrestActivity.class);
                        goToNextActivity.putExtra("url",strI);
                        startActivity(goToNextActivity);
                    }
                });

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });



    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }

        switch (id) {
            case R.id.action_interests:
                Intent goToNextActivity = new Intent(this, PostIntrestActivity.class);
                startActivity(goToNextActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void show_post_new_intrest(View view){

        Intent goToNextActivity = new Intent(this, PostIntrestActivity.class);
        startActivity(goToNextActivity);
    }

}
