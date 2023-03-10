package ddwu.mobile.finalproject.ma01_20200989.view.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import ddwu.mobile.finalproject.ma01_20200989.R;
import ddwu.mobile.finalproject.ma01_20200989.model.domain.dto.HospitalDto;
import ddwu.mobile.finalproject.ma01_20200989.model.service.parser.HospitalXmlParser;
import ddwu.mobile.finalproject.ma01_20200989.view.adapter.HospitalAdapter;

public class HospitalActivity extends AppCompatActivity {
    private final int REQ_PERMISSION_CODE = 100;
    private final String CURRENT_TITLE = "?????? ??????";
    private final String TAG = "HospitalActivity";

    private GoogleMap googleMapObject;
    private Location lastLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentMarker;
    private ListView listView;
    private List<HospitalDto> hospitals;
    private HospitalAdapter hospitalAdapter;
    String apiAddress;
    private HospitalXmlParser hospitalXmlParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        checkPermission();

        fusedLocationProviderClient.requestLocationUpdates(
                getLocationRequest(),
                locationCallback,
                Looper.getMainLooper()
        );

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapReadyCallback);

        apiAddress = getResources().getString(R.string.hospital_url);
        hospitalXmlParser = new HospitalXmlParser();

        if (!isOnline()) {
            Toast.makeText(HospitalActivity.this, "??????????????? ?????????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            return;
        }

        new NetworkAsyncTask().execute(apiAddress);

        hospitals = new ArrayList<>();
    }

    OnMapReadyCallback mapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMapObject = googleMap;

            checkPermission();
            googleMapObject.setMyLocationEnabled(true);

        }
    };

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                lastLocation = location;
                LatLng currentLocation = new LatLng(latitude, longitude);

                googleMapObject.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(currentLocation);
                markerOptions.title(CURRENT_TITLE);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                currentMarker = googleMapObject.addMarker(markerOptions);
                currentMarker.showInfoWindow();
            }
        }
    };

    //----------------------------------------------------------------------------------------------------------

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "???????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "???????????? ?????????", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            // ????????? ?????? ?????? ????????? ??????
            Toast.makeText(this,"Permissions Granted", Toast.LENGTH_SHORT).show();
        } else {
            // ?????? ??????
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQ_PERMISSION_CODE);
        }
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void getLastLocation() {
        checkPermission();

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            lastLocation = location;
                        } else {
                            Toast.makeText(HospitalActivity.this, "No location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        fusedLocationProviderClient.getLastLocation().addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Unknown");
                    }
                }
        );

    }

    //-----------------------------------------------------------------------------------------------------------

    class NetworkAsyncTask extends AsyncTask<String, Void, String> {

        final static String NETWORK_ERR_MSG = "Server Error!";
        public final static String TAG = "NetworkAsyncTask";
        ProgressDialog progressDlg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDlg = ProgressDialog.show(HospitalActivity.this, "Wait", "Downloading...");     // ???????????? ??????????????? ??????
        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0];
            String result = downloadContents(address);

            if (result == null) {
                cancel(true);
                return NETWORK_ERR_MSG;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG, result);
            progressDlg.dismiss();
            hospitals.clear();

            hospitals = hospitalXmlParser.parse(result);
            Log.i(TAG, hospitals.toString());

            if (hospitals == null) {
                Toast.makeText(HospitalActivity.this, "????????? ????????????.", Toast.LENGTH_SHORT).show();
            } else if (!hospitals.isEmpty()) {
                hospitalAdapter = new HospitalAdapter(HospitalActivity.this, R.layout.custom_hospital_adapter, hospitals);
                listView = (ListView) findViewById(R.id.hospitalListView);
                listView.setAdapter(hospitalAdapter);
            }
        }


        @Override
        protected void onCancelled(String msg) {
            super.onCancelled();
            progressDlg.dismiss();
            Toast.makeText(HospitalActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    protected String downloadContents(String address) {
        HttpURLConnection conn = null;
        InputStream stream = null;
        String result = null;

        try {
            URL url = new URL(address);
            conn = (HttpURLConnection)url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToString(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }

        return result;
    }

    protected Bitmap downloadImage(String address) {
        HttpURLConnection conn = null;
        InputStream stream = null;
        Bitmap result = null;

        try {
            URL url = new URL(address);
            conn = (HttpURLConnection)url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToBitmap(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }

        return result;
    }

    private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {
        conn.setReadTimeout(3000);
        conn.setConnectTimeout(3000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + conn.getResponseCode());
        }

        return conn.getInputStream();
    }

    protected String readStreamToString(InputStream stream){
        StringBuilder result = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String readLine = bufferedReader.readLine();

            while (readLine != null) {
                result.append(readLine + "\n");
                readLine = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    protected Bitmap readStreamToBitmap(InputStream stream) {
        return BitmapFactory.decodeStream(stream);
    }

}