package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.Observable;
import java.util.Observer;

import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.User;

/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class ProfilFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, Observer,
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
    private Context context;

    public ProfilFragment(){
        context = getContext();
    }


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
        mMap.getMapAsync(this);

        EditText editText = (EditText) view.findViewById(R.id.editMoodText);
        editText.clearFocus();


        mGpsUpdate = (ImageButton) view.findViewById(R.id.update_gps);
        mGpsUpdate.setEnabled(false);//TODO make it class field


        mGpsUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempMood = view.findViewById(R.id.editMoodText).toString();
                Toast t = Toast.makeText(context, "Mood cannot be empty!", Toast.LENGTH_SHORT);
                if(tempMood != null && tempMood != "")
                    setMood(tempMood);
            }
        });


        ImageButton mMoodUpdate = (ImageButton) view.findViewById(R.id.enterMood);
        mMoodUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempMood = view.findViewById(R.id.editMoodText).toString();
                Toast t = Toast.makeText(context, "Mood cannot be empty!", Toast.LENGTH_SHORT);
                if(tempMood != null && tempMood != "")
                    setMood(tempMood);
            }
        });

        createGoogleMapClient(); // <- moi



        TextView name = (TextView)view.findViewById(R.id.profil_text);

        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        Switch mSwitch = (Switch) view.findViewById(R.id.share_location);
        mSwitch.setChecked(false);

        RadioGroup mRadioGroup = (RadioGroup) view.findViewById(R.id.floor_group);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.radioButton1){
                    Log.d("Database debug:", "Floor changed to: 1");
                    UserManager.getInstance().getCurrentUser().setFloor(1);
                    UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());
                }else if(checkedId == R.id.radioButton2){
                    Log.d("Database debug:", "Floor changed to: 2");
                    UserManager.getInstance().getCurrentUser().setFloor(2);
                    UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());
                }else if(checkedId == R.id.radioButton3){
                    Log.d("Database debug:", "Floor changed to: 3");
                    UserManager.getInstance().getCurrentUser().setFloor(3);
                    UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());
                }
            }
        });
        RadioButton button1 = (RadioButton) view.findViewById(R.id.radioButton1);
        RadioButton button2 = (RadioButton) view.findViewById(R.id.radioButton2);
        RadioButton button3 = (RadioButton) view.findViewById(R.id.radioButton3);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);

        ImageButton mMoodButton = (ImageButton) view.findViewById(R.id.enterMood);
        mMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editMoodInput = (EditText) getView().findViewById(R.id.editMoodText);
                String enteredMood = editMoodInput.getText().toString();
                setMood(enteredMood);
            }
        });


        mSwitch.setChecked(false);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked){

                    UserManager.getInstance().getCurrentUser().setLocationShared(false);
                    button1.setEnabled(false);
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                }else{

                    UserManager.getInstance().getCurrentUser().setLocationShared(true);
                    button1.setEnabled(true);
                    button2.setEnabled(true);
                    button3.setEnabled(true);
                }
                UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());

                //HandleFloorStates(isChecked);

            }
        });

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        loadProfile();

        return view;
    }

    @Override
    public void update(Observable o, Object arg) {

        SetUserAndPaintProfile((User) arg);
    }

    private void setMood(String mood) { //TODO change this
        UserManager.getInstance().getCurrentUser().setMood(mood);

        UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());
        Toast.makeText(getActivity(), "Mood updated!", Toast.LENGTH_SHORT).show();
    }

    private void setBreakText(String breakText) {
        UserManager.getInstance().getCurrentUser().setBreakText(breakText);
        UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());
        Toast.makeText(getActivity(), "Break text updated!", Toast.LENGTH_SHORT).show();
    }

    private void loadProfile() {
        UserManager.getInstance().deleteObservers();
        UserManager.getInstance().addObserver(this);
        UserManager.getInstance().getUserInformations();
    }
    //Maybe not necessary
    /*
    private void HandleFloorStates(boolean isChecked) {
        User currentUser = UserManager.getInstance().getCurrentUser();

        if (currentUser == null)
            return;

        RadioButton floor1 = (RadioButton) view.findViewById(R.id.radioButton1);
        RadioButton floor2 = (RadioButton) view.findViewById(R.id.radioButton2);
        RadioButton floor3 = (RadioButton) view.findViewById(R.id.radioButton3);



        if (isChecked) {
            currentUser.setShareFloor(true);
            if (floor1.isChecked()){
                currentUser.setFloorLevel(1);
            }
            if (floor2.isChecked()){
                currentUser.setFloorLevel(2);
            }
            if (floor3.isChecked()){
                currentUser.setFloorLevel(3);
            }
        }
        else {
            currentUser.setShareFloor(false);
            floor1.setEnabled(false);
            floor2.setEnabled(false);
            floor3.setEnabled(false);
        }
        //save changes
        UserManager.getInstance().editUserInformations(currentUser);
    }
    */

    public void SetUserAndPaintProfile(User u) {

        TextView profileName = (TextView) view.findViewById(R.id.profil_text);
        profileName.setText("Hello " +  UserManager.getInstance().getCurrentUser().getName());
        EditText editMood = (EditText) view.findViewById(R.id.editMoodText);
        editMood.setText( UserManager.getInstance().getCurrentUser().getMood());

        Switch mSwitch = (Switch) view.findViewById(R.id.share_location);
        RadioGroup mRadioGroup = (RadioGroup) view.findViewById(R.id.floor_group);
        RadioButton button1 = (RadioButton) view.findViewById(R.id.radioButton1);
        RadioButton button2 = (RadioButton) view.findViewById(R.id.radioButton2);
        RadioButton button3 = (RadioButton) view.findViewById(R.id.radioButton3);

        int tempFloor = u.getFloor();
        switch (tempFloor){
            case 1: button1.setChecked(true); break;
            case 2: button2.setChecked(true); break;
            case 3: button3.setChecked(true); break;
        }

        if(u.isLocationShared()) {
            mSwitch.setChecked(true);

        }
        else
            mSwitch.setChecked(false);

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
        /*if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    MY_PERMISSION_ACCESS_FINE_LOCATION );
        }*/
        return false;
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
        } else {
            //Toast.makeText(getActivity(), "something went wrong in start", Toast.LENGTH_SHORT).show();
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

    public boolean checkConnection(){
        Toast t;
        if(false) {// TODO
            t = Toast.makeText(getContext(), "No internet Connection", Toast.LENGTH_LONG);
            t.show();
            return false;
        }else
            return true;
    }
}
