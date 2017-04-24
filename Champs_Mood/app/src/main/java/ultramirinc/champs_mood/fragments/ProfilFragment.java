package ultramirinc.champs_mood.fragments;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ultramirinc.champs_mood.R;

/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class ProfilFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, View.OnTouchListener, LocationListener {

    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private String mLatitudeText;
    private String mLongitudeText;
    private View view;
    private ImageButton mGpsUpdate;
    private LocationRequest mLocationRequest;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);


        }else{
            view = inflater.inflate(R.layout.fragment_profil, container, false);
        }

        SupportMapFragment mMap = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mGpsUpdate = (ImageButton) view.findViewById(R.id.update_gps);
        mGpsUpdate.setEnabled(false);//TODO make it class field

        Log.d("debug", "after inflater");
        mGpsUpdate.setOnClickListener(this);

        createGoogleMapClient(); // <- moi

        mMap.getMapAsync(this);

        TextView name = (TextView)view.findViewById(R.id.profil_text);

        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        Switch mSwitch = (Switch) view.findViewById(R.id.share_location);
        mSwitch.setChecked(false);

        RadioButton button1 = (RadioButton) view.findViewById(R.id.radioButton1);
        RadioButton button2 = (RadioButton) view.findViewById(R.id.radioButton2);
        RadioButton button3 = (RadioButton) view.findViewById(R.id.radioButton3);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);

        //TODO change this for databse
        mSwitch.setChecked(false);


        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    button1.setEnabled(false);
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                }else{
                    button1.setEnabled(true);
                    button2.setEnabled(true);
                    button3.setEnabled(true);
                }
            }
        });

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        return view;
    }

    public void onStart() {
        super.onStart();
        if(mGoogleApiClient == null)
            createGoogleMapClient();

        if(mGoogleApiClient.isConnected()){
            createGoogleMapClient(); // <- moi
            startLocationUpdates();
        }
    }

    public void onStop() {
        super.onStop();
        stopLocationUpdates();

    }

    @Override
    public void onResume(){
        super.onResume();
        if(!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();

        }else{
            startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        createRequestLocation();
        startLocationUpdates();
        mGpsUpdate.setEnabled(true);
    }

    private void createGoogleMapClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void createRequestLocation() {
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

        mMap.addMarker(new MarkerOptions().position(champlain).title("Champlain College"));

        CameraPosition pos = CameraPosition.builder().target(champlain).bearing(-103).build(); //rotate map
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));


        mMap.setMinZoomPreference((float)17.3);
        mMap.setMaxZoomPreference(20);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
    }

    private boolean checkPermission() {

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COARSE_LOCATION );

            ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    MY_PERMISSION_ACCESS_FINE_LOCATION );
            return false;
        }

        return true;
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void startLocationUpdates() {

        if(checkPermission() && mGoogleApiClient != null) { //debug
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }else{
            Toast.makeText(getActivity(), "something went wrong in start", Toast.LENGTH_SHORT).show();
        }
    }

    protected void stopLocationUpdates() {
        if(checkPermission() && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient,  this);
        }else{
            //Toast.makeText(getActivity(), "something went wrong in stop", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onClick(View v) {
        //TODO do this
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        //Do Nothing
    }
}
