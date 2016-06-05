package com.example.mehmood.splashy;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.Snackbar;
import android.text.method.ScrollingMovementMethod;
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

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    android.support.v4.app.FragmentManager aMap;
    GoogleMap map;
    DrawerLayout drawer;
    private static final int PLACE_PICKER_REQUEST = 1;
    Place place=null;
    private int hotel_id;
    private int hotel_detail;
    TextView recoText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myMapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recoText= (TextView) findViewById(R.id.reco);
        recoText.setMovementMethod(new ScrollingMovementMethod());



        if (recoText != null) {
            recoText.setVisibility(View.INVISIBLE);
        }
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();

        aMap = getSupportFragmentManager();
        if (!myMapFragment.isAdded()) {
            aMap.beginTransaction().add(R.id.map, myMapFragment).commit();
        }
        else {
            aMap.beginTransaction().show(myMapFragment).commit();
        }


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




        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);



        myMapFragment.getMapAsync(this);

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
            drawer.openDrawer(GravityCompat.END);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);
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

        if (imageView != null) {
            imageView.setVisibility(View.INVISIBLE);
        }

        if (recoText != null) {
            recoText.setVisibility(View.INVISIBLE);
        }

        int id = item.getItemId();

        if (myMapFragment.isAdded())
            aMap.beginTransaction().hide(myMapFragment).commit();


        if (id == R.id.nav_gallery) {

            if (!myMapFragment.isAdded())
                aMap.beginTransaction().add(R.id.map, myMapFragment).commit();

            else
                aMap.beginTransaction().show(myMapFragment).commit();

        } else if (id == R.id.nav_slideshow) {



        } else if (id == R.id.nav_recommendations) {
            //imageView.setVisibility(View.VISIBLE);
            recoText.setVisibility(View.VISIBLE);

            //imageView.setImageResource(R.drawable.coming_soon);

        } else if (id == R.id.nav_planning) {
            imageView.setVisibility(View.VISIBLE);

            imageView.setImageResource(R.drawable.coming_soon);

        } else if (id == R.id.nav_manage) {
            imageView.setVisibility(View.VISIBLE);

            imageView.setImageResource(R.drawable.tourister_poster);

        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        myMapFragment.getMapAsync(this);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode ==  RESULT_OK) {
                place = PlacePicker.getPlace(data, this);
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

        map=googleMap;
      //  googleMap.clear();
        for(int i=0;i<arrayList.size();i++){
            Marker marker=googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(arrayList.get(i).get("lat")), Double.parseDouble(arrayList.get(i).get("lng"))))
                    .title(arrayList.get(i).get("name"))
                    .snippet(arrayList.get(i).get("item_id"))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_tag)));

            Log.v("christmas" + "=" + i, arrayList.get(i).get("lat"));



        }

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                                               @Override public boolean onMarkerClick(Marker marker) {

                                                   marker.showInfoWindow();
                                                   Log.e("i am clicked", String.valueOf(marker.getSnippet()));
                                                   hotel_id= Integer.parseInt(marker.getSnippet());
                                                   recoText.setText("       -::- RECOMMENDED HOTELS -::- \n\n");
                                                    recommend_hotel();
                                                   //use snippet to get itemid and send get request contentfilter


                                                   return true;
                                               }

                                           }
        );

    if(check) {
            //Intent tag = new Intent(MainActivity.this, TagPOI.class);
           // startActivity(tag);

            my_pop();



/*
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lt.latitude, lt.longitude))
                        .title((String) ch)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_green_round)));

*/
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lt, 15));
            googleMap.animateCamera(CameraUpdateFactory.zoomIn());
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

            check=false;



 /*            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    View view = getLayoutInflater().inflate(R.layout.info_tag, null);
                    Log.e("i am clicked",marker.getId());
                    Log.v("i am clicked","verbose");
                    Log.i("i am clicked",marker.getId());
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
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(lt.latitude, lt.longitude))
                        .title((String) ch)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_tag)));
                tag_write();
                pw.dismiss();
            }
        });
    }

    public void tag_write(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://tourister.space/hotelTag",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        Log.e("result", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();


                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> data = new HashMap<String, String>();
                //Map<String,Map> dat=new HashMap<String,Map>();
                data.put("name", (String) place.getName());
                data.put("taggedby", "A");
                data.put("addr", (String) place.getAddress());
                data.put("type","hotel");
                data.put("lat", String.valueOf(place.getLatLng().latitude));
                data.put("lng", String.valueOf(place.getLatLng().longitude));
                data.put("city","");
                data.put("country","");
                data.put("region","");
                data.put("stars", String.valueOf(place.getRating()));
                data.put("total","");
                data.put("room_type","");
                data.put("features","");




                //dat.put("data",data);
                Log.v("result", String.valueOf(data));
                return data;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void recommend_hotel(){

        JsonArrayRequest jsonRequest = null;
        jsonRequest = new JsonArrayRequest(Request.Method.GET, "http://tourister.space/contentFilter/"+hotel_id,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Log.e("Respsne is=", String.valueOf(response.get(String.valueOf(0))));
                Log.e("Respsne222 is=", String.valueOf(response));
                for(int i=0;i<response.length();i++){
                    try {
                        Log.e("Resp="+i, String.valueOf(response.get(i)));
                        hotel_detail= Integer.parseInt((String) response.get(i)) ;
                        for(int j=0;j<arrayList.size();j++){
                            if(hotel_detail == Integer.parseInt(arrayList.get(j).get("item_id"))){
                                recoText.append("Hotel ID = "+arrayList.get(j).get("item_id")+"\n"+arrayList.get(j).get("name")+"\n"+arrayList.get(j).get("addr")+"\n\n");
                            }
                        }
                        hotels_detail();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);
    }

    public void hotels_detail(){

        StringRequest  jsonRequest = null;
        jsonRequest = new StringRequest (Request.Method.GET, "http://tourister.space/hotelDetails/"+hotel_detail,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e("Respsne is=", String.valueOf(response.get(String.valueOf(0))));
                Log.e("Respsne999 is=", String.valueOf(response));



              //  recoText.append(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("Resp=lll", String.valueOf(error));
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                URL url = new URL("http://tourister.space/markerXML");

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
                    hm.put("item_id",nElement.getAttribute("item_id"));
                    hm.put("addr",nElement.getAttribute("addr"));
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
