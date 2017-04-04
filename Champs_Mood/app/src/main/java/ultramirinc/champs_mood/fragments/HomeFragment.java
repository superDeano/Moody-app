package ultramirinc.champs_mood.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ultramirinc.champs_mood.MapsActivity;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.TabActivity;


/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, LocationListener {

    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private String mLatitudeText;
    private String mLongitudeText;
    private View view;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            Log.d("debug", "inside TRy");
            view = inflater.inflate(R.layout.fragment_homepage, container, false);


        } catch (Exception e) {
            Log.d("debug", "error inside try:"+e.toString());
        } finally {
            SupportMapFragment mMap = (SupportMapFragment) this.getChildFragmentManager()
                    .findFragmentById(R.id.map);
            ImageButton mGpsUpdate = (ImageButton) view.findViewById(R.id.update_gps);
            Log.d("debug", "after inflater");
            mGpsUpdate.setOnClickListener(this);

            createGoogleMapClient(); // <- moi

            mMap.getMapAsync(this);
            return view;
        }
    }


    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        startLocationUpdates();

    }

    public void onStop() {
        super.onStop();
        stopLocationUpdates();
        mGoogleApiClient.disconnect();


    }

    @Override
    public void onResume(){
        super.onResume();
        if(!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();

        startLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            stopLocationUpdates();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        createRequestLocation();
        startLocationUpdates();
    }


    private void createGoogleMapClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void createRequestLocation(){
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(30000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("debug", "in");
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng champlain = new LatLng(45.5164522,-73.52062409999996);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(champlain));//Move camera to Champlain
        mMap.addMarker(new MarkerOptions().position(champlain).title("Champlain College"));

        CameraPosition oldPos = mMap.getCameraPosition();
        CameraPosition pos = CameraPosition.builder(oldPos).bearing(-103).build(); //rotate map
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));

        mMap.setMinZoomPreference((float)17.3);
        mMap.setMaxZoomPreference(20);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);

        //Add marker on my location
        updateLocation();
    }



    public void startLocationUpdates() {
        checkPermission();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COARSE_LOCATION );
            ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    MY_PERMISSION_ACCESS_FINE_LOCATION );
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("Cleint error", "Google client did not connect");

    }

    @Override
    public void onDestroyView() {

        FragmentManager fm = getFragmentManager();

        Fragment xmlFragment = fm.findFragmentById(R.id.map);
        if (xmlFragment != null) {
            fm.beginTransaction().remove(xmlFragment).commit();
        }

        super.onDestroyView();
    }

    public void updateLocation(){
        checkPermission();
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
            mGoogleApiClient);

        if (mLastLocation != null) {

            mLatitudeText = String.valueOf(mLastLocation.getLatitude());
            mLongitudeText = String.valueOf(mLastLocation.getLongitude());

            Log.d("Coordinates",(mLatitudeText + ", " + mLongitudeText));


            LatLng me = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(me).title("me"));

        }else {
            Log.d("debug", "fused not working");

        }
    }

    @Override
    public void onClick(View v) {
        Log.d("debug", "inside Button Listener");
        switch(v.getId()){
            case R.id.update_gps:
                Log.d("debug", "inside good case");
                mMap.clear();
                updateLocation();
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        /*
        mMap.clear();
        mLastLocation = location;
        LatLng me = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(me).title("me"));
        */
    }
}
