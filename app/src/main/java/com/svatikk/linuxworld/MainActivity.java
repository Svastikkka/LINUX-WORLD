package com.svatikk.linuxworld;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();
    // Movies json url
    private static final String url = "https://badshahsharmaforever.000webhostapp.com/latestHapping.json";
    private ProgressDialog pDialog;
    private ProgressBar progressBar;
    private List<Data> dataList = new ArrayList<Data>();
    private ListView listView;
    private ListAdapterForLatestHappining adapter;
    private RequestQueue requestQueue;
    SwipeRefreshLayout swipeRefreshLayout;

    // we use the the boolenan in the opening & closining activity remember see fab animation
    boolean isOpen = false;


    Animation fabopen,fabclosed;
    FloatingActionButton fab1,fab2,fab3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("NewsFeed");
        setSupportActionBar(toolbar);


        //Add animation to opening the layout
        fabopen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fb_open);
        //Add animation to closing the layout
        fabclosed = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fb_close);





        fab1 = (FloatingActionButton) findViewById(R.id.faacebook);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri =Uri.parse("https://www.facebook.com/LinuxWorld.India/?ref=br_rs");
                Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        fab2 = (FloatingActionButton) findViewById(R.id.youtube);
        fab2.setBackgroundResource(R.color.svatikk3);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/channel/UCkuD8jyHRrQR0-Oop0K5skw"));
                startActivity(intent);
            }
        });
        fab3 = (FloatingActionButton) findViewById(R.id.fab);
        fab3.setBackgroundResource(R.color.svatikk2);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

                    fab3.setImageResource(R.drawable.ic_add_24dp);
                    fab2.startAnimation(fabclosed);
                    fab1.startAnimation(fabclosed);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    isOpen = false;

                } else {


                    fab3.setImageResource(R.drawable.ic_subtract_24dp);

                    fab2.startAnimation(fabopen);
                    fab1.startAnimation(fabopen);

                    fab1.setClickable(true);
                    fab2.setClickable(true);


                    isOpen = true;

                }


            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);








        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeView);
        swipeRefreshLayout.setColorSchemeResources(R.color.svatikk1, R.color.svatikk2,R.color.svatikk3);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                //Add your own code to refresh

                dataList.clear();//
                adapter.notifyDataSetChanged();
                getDataFromUrl();
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.VISIBLE);
            }

            private void getDataFromUrl() {

                //2nd Volley call active when refresh is needed



                // Creating volley request obj
                final JsonArrayRequest movieReq = new JsonArrayRequest(url,
                        new com.android.volley.Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d(TAG, response.toString());
                                progressBar.setVisibility(View.GONE);

                                // Parsing json
                                for (int i = 0; i < response.length(); i++) {
                                    try {

                                        JSONObject obj = response.getJSONObject(i);
                                        Data data = new Data();
                                        data.setTitle(obj.getString("title"));
                                        data.setThumbnailUrl(obj.getString("image"));









                                        // adding data to data array
                                        dataList.add(data);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                // notifying list adapter about data changes
                                // so that it renders the list view with updated data
                                adapter.notifyDataSetChanged();
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(), "Please swipe down to refresh", Toast.LENGTH_SHORT).show();


                        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024*10);
                        Network network =new BasicNetwork(new HurlStack());



                        requestQueue = new RequestQueue(cache,network);
                        requestQueue.start();











                    }
                });

                // Adding request to request queue
                MySingletoneForAll.getInstance().addToRequestQueue(movieReq);


















            }

        });







        final Animation move_up_in_listView = AnimationUtils.loadAnimation(getApplication(), R.anim.move_up);




        listView = (ListView) findViewById(R.id.list);
        adapter = new ListAdapterForLatestHappining(this, dataList);
        listView.setAdapter(adapter);
        listView.setAnimation(move_up_in_listView);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);




        // Creating volley request obj
        final JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        progressBar.setVisibility(View.GONE);
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Data data = new Data();
                                data.setTitle(obj.getString("title"));
                                data.setThumbnailUrl(obj.getString("image"));









                                // adding data to data array
                                dataList.add(data);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());


                progressBar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), "Please swipe down to refresh", Toast.LENGTH_SHORT).show();


                Cache cache = new DiskBasedCache(getCacheDir(),1024*1024*10);
                Network network =new BasicNetwork(new HurlStack());



                requestQueue = new RequestQueue(cache,network);
                requestQueue.start();











            }
        });

        // Adding request to request queue
        MySingletoneForAll.getInstance().addToRequestQueue(movieReq);

























    }







    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }












    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.introduction) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(),introduction.class);
            startActivity(intent);
        } else if (id == R.id.jietlocation) {
            Intent intent , chooser;
            Uri gmmIntentUri = Uri.parse("geo:26.866316,75.786021?z=10&q=linuxworld");
            intent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
             // Make the Intent explicit by setting the Google Maps package
            intent.setPackage("com.google.android.apps.maps");
            chooser = Intent.createChooser(intent,"Launch Maps");
            startActivity(chooser);
        }  else if (id == R.id.contact) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(),ContactNumber.class);
            startActivity(intent);
        } else if (id == R.id.holiday) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(),holidays.class);
            startActivity(intent);


        } else if (id == R.id.studentcell) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(),StudentCell.class);
            startActivity(intent);

        } else if (id == R.id.share) {

            ApplicationInfo applicationInfo = getApplicationContext().getApplicationInfo();
            String apkPath = applicationInfo.sourceDir;

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/vnd.android.package-archive");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkPath)));
            startActivity(Intent.createChooser(intent,"Share using"));



        } else if (id == R.id.feedback) {

            //Need a email client so user email me.....

            try {
                Uri uri =Uri.parse("market://details?id="+getPackageName());
                Intent  intent =new Intent(Intent.ACTION_VIEW , uri);
                startActivity(intent);

            }catch (ActivityNotFoundException e){
                Uri uri =Uri.parse("http://play.google.com/store/apps/details?id"+getPackageName());
                Intent intent =new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        } else if (id == R.id.contact_us) {
            // Handle the camera action
            Intent app1 = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("mailto:support@svatikk.com"));
            startActivity(app1);
        }  else if (id == R.id.about_us) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(),about_us.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
