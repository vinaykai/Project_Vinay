package com.vkai.project;

/**
 * Created by ganesh4517 on 09-04-2016.
 */
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;



import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;



public class WalkingRoute extends FragmentActivity implements LocationListener {

    GoogleMap mGoogleMap;
    ArrayList<LatLng> mMarkerPoints;
    double mLatitude = 0;
    double mLongitude = 0;
    String starttime = "";
    String stoptime = "";
    String distanceCovered = "";
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking_route);

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Initializing
            mMarkerPoints = new ArrayList<LatLng>();

            // Getting reference to SupportMapFragment of the activity_main
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting Map for the SupportMapFragment
            mGoogleMap = fm.getMap();

            // Enable MyLocation Button in the Map
            mGoogleMap.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location From GPS
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                onLocationChanged(location);
            }

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(provider, 20000, 0, this);

            // Setting onclick event listener for the map
            mGoogleMap.setOnMapClickListener(new OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {

                    // Already map contain destination location
                    if (mMarkerPoints.size() > 1) {

                        FragmentManager fm = getSupportFragmentManager();
                        mMarkerPoints.clear();
                        mGoogleMap.clear();
                        LatLng startPoint = new LatLng(mLatitude, mLongitude);

                        // draw the marker at the current position
                        drawMarker(startPoint);
                    }

                    // draws the marker at the currently touched location
                    drawMarker(point);

                    // Checks, whether start and end locations are captured
                    if (mMarkerPoints.size() >= 2) {
                        LatLng origin = mMarkerPoints.get(0);
                        LatLng dest = mMarkerPoints.get(1);

                        // Getting URL to the Google Directions API
                        String url = getDirectionsUrl(origin, dest);

                        DownloadTask downloadTask = new DownloadTask();

                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }
                }
            });
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        LatLng from = new LatLng(origin.latitude,origin.longitude);
        LatLng to = new LatLng(dest.latitude,dest.longitude);

        //Calculating the distance in meters
        Double distance = SphericalUtil.computeDistanceBetween(from, to);

        distanceCovered =  String.valueOf(distance);

        //Displaying the distance
        Toast.makeText(this, String.valueOf(distance + " Meters"), Toast.LENGTH_SHORT).show();

        // Sensor enabled
        String sensor = "sensor=false";

        //mode = walking
        String mode = "mode=walking";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        String parameter = str_origin + "&" + str_dest;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    public void GetRoute(View v) throws IOException {
      /*  EditText Source = (EditText) findViewById(R.id.text_Source);

        EditText Destination = (EditText) findViewById(R.id.text_Dest);


        String LongLatSource = getLatLng(Source.getText().toString());

        String LongLatDest = getLatLng(Destination.getText().toString());


        String[] latlong =  LongLatSource.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        LatLng LongLatiSource = new LatLng(latitude,longitude);

        String[] latlong1 =  LongLatDest.split(",");
        double latitude1 = Double.parseDouble(latlong1[0]);
        double longitude1 = Double.parseDouble(latlong1[1]);
        LatLng LongLatiDest = new LatLng(latitude1,longitude1);



        String url = getDirectionsUrl(LongLatiSource, LongLatiDest);



        DownloadTask downloadTask = new DownloadTask();

         //Start downloading json data from Google Directions API
              downloadTask.execute(url);*/


    }



    public  String getJson(String url) {

        InputStream is = null;
        String result = "";
        String line = null;
        HttpURLConnection urlConnection = null;


        // HTTP
        try {
            URL url1 = new URL(url);
            urlConnection = (HttpURLConnection)url1.openConnection();
            urlConnection.connect();
            is = urlConnection.getInputStream();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(),
                    Toast.LENGTH_LONG).show();

        }

        // Read response to string

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();

        } catch (Exception e) {
            return null;
        }
        return result;
    }

   /* public String getRouteUrl(String Address){
        String locationPoint = "";
        String locationAddress = Address.replaceAll(" ", "+");
        String str = "http://maps.googleapis.com/maps/api/geocode/json?address="
                + locationAddress + "&sensor=false";
        str = "http://maps.googleapis.com/maps/api/geocode/json?address=lake%20meadows%20Chicago&sensor=false";
        //str = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=lake%20meadows&destinations=San+Francisco|Victoria+BC&mode=walking&language=fr-FR&key=AIzaSyCFCGAuBF5bqMci80_79ALpOF9H-q6TvTU";

        String ss="";



            //ss = getJson(str);



        *//*JSONObject json;
        try {

            String lat, lon;
            json = new JSONObject(ss);

            lat = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
            lon = json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
            locationPoint = (Double.parseDouble(lat)+","+Double.parseDouble(lon));

        } catch (Exception e) {
            e.printStackTrace();
        }*//*
        return locationPoint;
    }*/


    public void GetStartTime(View v)
    {

        // starttime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Date date = new Date();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("hh:mm:ss");
        starttime = String.valueOf(sdf.format(date));


    }

    public void GetStopTime(View v) throws ParseException {
        //stoptime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        // d1, d2 are dates
        Date date = new Date();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("hh:mm:ss");
        stoptime = String.valueOf(sdf.format(date));
        Date dt = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = format.parse(starttime);
        Date date2 = format.parse(stoptime);
        double difference = date2.getTime() - date1.getTime();





        /*long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;*/
        double diffHours = difference / (60 * 60 * 1000) % 24;
        // double roundOff = Math.round(diffHours * 100.0) / 100.0;
        /*long diffDays = diff / (24 * 60 * 60 * 1000);*/


        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        //get current date time with Date()
        Date dat = new Date();



        Bundle bundle = getIntent().getExtras();
        user_id = bundle.getInt("id1");

        //Following code needs to be uncomemnted after integration.

        /*TableUserProfile DB = new TableUserProfile(this);
        DB.open();
        Cursor cur = DB.selectOneItem(user_id);

        if (cur !=null)
        {
            cur.moveToNext();
            Double BMR = cur.getString(6);
        }


        Double caloriesBurned = (BMR / 24) * 7 * diffHours;



        TableRunHistory db = new TableRunHistory(this);
        db.open();
        db.insertData(user_id,dateFormat.format(dat),starttime,stoptime,caloriesBurned);*/


    }



    public String getLatLng(String address) throws IOException {
        Geocoder gc = new Geocoder(this);
        List<Address> list= gc.getFromLocationName(address, 1);
        Address add = list.get(0);
        double lat = add.getLatitude();
        double lng = add.getLongitude();

        Toast.makeText(getApplicationContext(), String.valueOf(lat)+","+String.valueOf(lng),
                Toast.LENGTH_LONG).show();

        return String.valueOf(lat)+","+String.valueOf(lng);
    }


    /**
     * A method to download json data from url
     */
    public String downloadUrl(String strUrl) {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while dnwld", e.toString());
        } finally {

            urlConnection.disconnect();
        }
        return data;
    }

    /**
     * A class to download data from Google Directions URL
     */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Directions in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            mGoogleMap.addPolyline(lineOptions);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.activity_walking_route, menu);
        return true;
    }

    private void drawMarker(LatLng point) {
        mMarkerPoints.add(point);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        // Setting the position of the marker
        options.position(point);

        /**
         * For the start location, the color of marker is GREEN and
         * for the end location, the color of marker is RED.
         */
        if (mMarkerPoints.size() == 1) {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else if (mMarkerPoints.size() == 2) {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        }

        // Add new marker to the Google Map Android API V2
        mGoogleMap.addMarker(options);
    }

    @Override
    public void onLocationChanged(Location location) {
        // Draw the marker, if destination location is not set
        if (mMarkerPoints.size() < 2) {

            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
            LatLng point = new LatLng(mLatitude, mLongitude);

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(point));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

            drawMarker(point);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

}
