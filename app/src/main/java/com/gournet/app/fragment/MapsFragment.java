package com.gournet.app.fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gournet.app.R;
import com.gournet.app.model.Marker;
import com.gournet.app.model.User;
import com.gournet.app.other.PermissionUtils;
import com.gournet.app.other.SessionManager;


public class MapsFragment extends Fragment implements OnMapReadyCallback,ActivityCompat.OnRequestPermissionsResultCallback {


    SessionManager session;
    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;


    GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private com.google.android.gms.maps.model.Marker marker;

    private Marker[] markers = null;

    public MapsFragment() {
    }

    private boolean isAdding = false;
    public void populateMarkers() {
        if (isAdding || mMap == null || markers == null) return;
        isAdding = true;
        for (Marker marker : markers)
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(marker.getLocation().getLatitude(), marker.getLocation().getLongitude()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .title(marker.getTypeDisplay() + " \"" + marker.getName() + "\"")
            );
        markers = null;
        isAdding = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps_fragment, container, false);
        //inflater.inflate(R.layout.activity_maps_fragment, container, false);
        // Inflate the layout for this fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());


        // Add a marker in Sydney and move the camera
          Bundle extras = getActivity().getIntent().getExtras();
          User user = (User) extras.getSerializable("user");

        LatLng home = new LatLng(user.location.getLatitude(),user.location.getLongitude());
        marker = mMap.addMarker(new MarkerOptions().position(home));
        populateMarkers();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 15));

        enableMyLocation();
    }



    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            //mMap.setMyLocationEnabled(true);
            LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(android.location.Location location) {
                    if (location != null) {
                        LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                        marker.setPosition(pos);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(pos));
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }


    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getChildFragmentManager(), "dialog");
    }

    public void setMarkers(Marker[] markers) {
        this.markers = markers;
    }
}









//onCreateView call  new GetHome(MapsFragment.this).execute();

//class GetHome extends AsyncTask<Object, Void, Marker[]> {
//    private MapsFragment context;
//
//    GetHome(MapsFragment context) {
//        this.context = context;
//    }
//
//
//    @Override
//    protected Marker[] doInBackground(Object... objects) {
//
//        Retrofit retrofit = ApiClient.service;
//
//        ApiEndpointInterface.homeService service = retrofit.create(ApiEndpointInterface.homeService.class);
//
//        ResponseBody body=null;
//
//        try {
//            body = service.getHome().execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        JsonParser parser = new JsonParser();
//        if (body != null) {
//            JsonArray array = null;
//            try {
//                array = parser.parse(body.string()).getAsJsonArray();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Marker[] markers = new Gson().fromJson(array.get(1).getAsJsonObject().getAsJsonArray("results"), Marker[].class);
//
//            return markers;
//        }
//
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Marker[] markers) {
//        for (Marker marker : markers) {
//            context.mMap.addMarker(new MarkerOptions()
//                    .position(new LatLng(marker.getLocation().getLatitude(), marker.getLocation().getLongitude()))
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
//                    .title(marker.getTypeDisplay()+" \""+marker.getName()+"\"")
//            );
//        }
//    }
//}