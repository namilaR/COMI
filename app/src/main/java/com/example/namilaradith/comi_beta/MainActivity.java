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

import com.android.volley.Request;
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

    public static final String DATA_URL = "http://192.168.8.100:80/comi_server/?method=get_shared_intrest";

    public static final String DATA_URL_ACTIVITIES = "http://192.168.8.100:80/comi_server/?method=get_shared_activities";

    public static final String TAG_IMAGE_URL = "image";
    public static final String TAG_NAME = "name";
    public static final String TAG_ID = "id";
    public static final String TAG_CAPTION = "caption";

    private GridView gridView;

    private ArrayList<String> images;
    private ArrayList<String> names;
    private ArrayList<String> ids;
    private ArrayList<String> captions;


    private void getData(){

        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...","Fetching data...",false,false);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        showGrid(response);

                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(jsonArrayRequest);
    }

    private void getDataActivities(){

        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...","Fetching data...",false,false);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL_ACTIVITIES,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        showGrid(response);

                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void showGrid(JSONArray jsonArray){


        for(int i = 0; i<jsonArray.length(); i++){

            JSONObject obj = null;
            try {

                obj = jsonArray.getJSONObject(i);

                images.add(obj.getString(TAG_IMAGE_URL));
                names.add(obj.getString(TAG_NAME));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        GridViewAdapter gridViewAdapter = new GridViewAdapter(MainActivity.this,images,names,ids,captions);

        gridView.setAdapter(gridViewAdapter);
    }

///////ONCREATE//////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Intrests").setIcon(icons[0]));
        tabLayout.addTab(tabLayout.newTab().setText("Activities").setIcon(icons[1]));
        tabLayout.addTab(tabLayout.newTab().setText("Joined").setIcon(icons[2]));
        tabLayout.addTab(tabLayout.newTab().setText("My Intrests").setIcon(icons[3]));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                String selectedTab = tab.getText().toString();

                        if(selectedTab.equals("Intrests")) {
                            gridView = (GridView) findViewById(R.id.gridView);

                            images = new ArrayList<>();
                            names = new ArrayList<>();


                            getData();

                            viewPager.setCurrentItem(tab.getPosition());

                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View v,
                                                        int position, long id) {

                                    StringBuilder sb = new StringBuilder();
                                    sb.append("");
                                    sb.append(images.get(position));
                                    String strI = sb.toString();

                                    Intent goToNextActivity = new Intent(MainActivity.this, SelectedIntrestActivity.class);
                                    goToNextActivity.putExtra("url", strI);
                                    startActivity(goToNextActivity);
                                }
                            });
                        }
                        if(selectedTab.equals("Activities")) {
                            gridView = (GridView) findViewById(R.id.gridView2);

                            images = new ArrayList<>();
                            names = new ArrayList<>();

                            getDataActivities();

                            viewPager.setCurrentItem(tab.getPosition());

                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View v,
                                                        int position, long id) {

                                    StringBuilder sb = new StringBuilder();
                                    sb.append("");
                                    sb.append(images.get(position));
                                    String strI = sb.toString();

                                    Intent goToNextActivity = new Intent(MainActivity.this, SelectedIntrestActivity.class);
                                    goToNextActivity.putExtra("url", strI);
                                    startActivity(goToNextActivity);
                                }
                            });

                        }

                if(selectedTab.equals("Joined")) {

                }

                if(selectedTab.equals("My Intrests")) {

                }

            }
///////////////////////////////////////////////////////////////////////////////////////////////////////////


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
