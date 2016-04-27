package com.example.mehmood.splashy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    SupportMapFragment myMapFragment;
    private LatLng lt;
    private CharSequence ch;
    private boolean check = false;
    private Button sub;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>();
    HashMap<String, String> hm ;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private static final int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myMapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
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
        navigationView.setItemIconTintList(null);



        myMapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("WrongViewCast")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final ImageView imageView = (ImageView) findViewById(R.id.imageView2);

        android.support.v4.app.FragmentManager aMap = getSupportFragmentManager();
        if (imageView != null) {
            imageView.setVisibility(View.INVISIBLE);
        }

        int id = item.getItemId();

        if (myMapFragment.isAdded())
            aMap.beginTransaction().hide(myMapFragment).commit();


        if (id == R.id.nav_gallery) {

    /*        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
*/
            if (!myMapFragment.isAdded())
                aMap.beginTransaction().add(R.id.map, myMapFragment).commit();

            else
                aMap.beginTransaction().show(myMapFragment).commit();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_recommendations) {
            imageView.setVisibility(View.VISIBLE);

            imageView.setImageResource(R.drawable.coming_soon);

        } else if (id == R.id.nav_planning) {
            imageView.setVisibility(View.VISIBLE);

            imageView.setImageResource(R.drawable.coming_soon);

        } else if (id == R.id.nav_manage) {
            imageView.setVisibility(View.VISIBLE);

            imageView.setImageResource(R.drawable.tourister_poster);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode ==  RESULT_OK) {
                final Place place = PlacePicker.getPlace(data, this);
                final CharSequence name = place.getName();
                lt = place.getLatLng();
                ch = place.getName();
                check = true;
                myMapFragment.getMapAsync(this);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        for(int i=0;i<arrayList.size();i++){
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(arrayList.get(i).get("lat")), Double.parseDouble(arrayList.get(i).get("lng"))))
                    .title(arrayList.get(i).get("name"))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_green_round)));

            Log.v("christmas"+"="+i,arrayList.get(i).get("lat"));
        }




    if(check) {
            //Intent tag = new Intent(MainActivity.this, TagPOI.class);
            //startActivity(tag);
            my_pop();


                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lt.latitude, lt.longitude))
                        .title((String) ch)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_green_round)));


// Move the camera instantly to Sydney with a zoom of 15.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lt, 15));

// Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());

// Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

            //final String s=getIntent().getStringExtra("type");
            //final int s2= Integer.parseInt(getIntent().getStringExtra("rate"));

/*
            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    View view = getLayoutInflater().inflate(R.layout.info_tag, null);
                    TextView lat = (TextView) view.findViewById(R.id.textView3);
                    TextView lng = (TextView) view.findViewById(R.id.textView4);
                    TextView snip = (TextView) view.findViewById(R.id.snippet);

                    LatLng ll = marker.getPosition();
                    lat.setText("longitude :" + ll.longitude);
                    lng.setText("latitude :" + ch);
                    snip.setText(marker.getSnippet());
                    return view;
                }
            });
*/

        }
  /*      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
*/
    }
    public void my_pop(){
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.pop_up, null, false);
        final PopupWindow pw = new PopupWindow(popupView, 700,1100,true);
        pw.showAtLocation(this.findViewById(R.id.imgs), Gravity.CENTER, 0, 0);
        sub=(Button)popupView.findViewById(R.id.b_submit);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();
            }
        });
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                URL url = new URL("http://webassignment3.site88.net/markerXML.xml");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream in =new BufferedInputStream(conn.getInputStream());

                String lati = null;
                String longi= null;
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                Document doc = builder.parse(in);//issue here
                NodeList nodes = doc.getElementsByTagName("marker");

                for (int i = 0; i < nodes.getLength(); i++) {

                    hm= new HashMap<String, String>();
                    Node nNode=nodes.item(i);
                    Element nElement=(Element)nNode;
                    lati = nElement.getAttribute("lat");
                    longi = nElement.getAttribute("lng");
                    resp= String.valueOf(lati);
                    hm.put("lat",lati);
                    hm.put("lng",longi);
                    hm.put("name",nElement.getAttribute("name"));
                    arrayList.add(i,hm);
                    Log.v("name",nElement.getAttribute("name"));

                }


            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return resp;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation

            //e1.setText(arrayList.get(5).values().toString());
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {

            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog

        }

    }


}
