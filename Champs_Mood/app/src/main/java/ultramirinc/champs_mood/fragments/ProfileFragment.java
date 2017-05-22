package ultramirinc.champs_mood.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import android.Manifest;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.Break;
import ultramirinc.champs_mood.models.User;

/**
 * Created by Etienne Berube on 2017-03-23.
 * This will be the user's profile. He will be able to change his mood, location and floor from this page.
 */

public class ProfileFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, Observer,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, View.OnTouchListener, LocationListener {

    /**Constant for the permission of the Fine Location*/
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;
    /**Contains map for fragment*/
    private GoogleMap mMap;
    /**Contains Google Api Client*/
    private GoogleApiClient mGoogleApiClient;
    /**Contains the last known location of the user*/
    private Location mLastLocation;
    /**A String version of the user's last known latitude*/
    private String mLatitudeText;
    /**A String version of the user's last known longitude*/
    private String mLongitudeText;
    /**Contains the fragments visual layout*/
    private View view;
    /**Contains the button for the GPS update*/
    private ImageButton mGpsUpdate;
    /**Contains the location request in order to get updates form the user's device*/
    private LocationRequest mLocationRequest;
    /**Contains the context from the attached activity*/
    private Context context;
    /**Contains the a builder to change the user's device GPS state*/
    private LocationSettingsRequest.Builder builder;
    /**Contains the result of the permission validity*/
    private PendingResult<LocationSettingsResult> result;
    /**If the map is ready*/
    private boolean mapIsReady = false;

    public ProfileFragment(){
        context = getContext();
    }

    /**Gets called when the fragment gets attached to an activity. This method gets the context.*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            this.context = context;
        }

    }

    /**Inflates the fragment's visual layout.*/
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



        mGpsUpdate = (ImageButton) view.findViewById(R.id.update_gps);
        mGpsUpdate.setEnabled(false);//TODO make it class field


        mGpsUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());
                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(@NonNull LocationSettingsResult result) {
                        final Status status = result.getStatus();
                        final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                        switch (status.getStatusCode()) {
                            case LocationSettingsStatusCodes.SUCCESS:
                                break;
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                try {

                                    status.startResolutionForResult(
                                            getActivity(),
                                            0x1);//Code for REQUEST_CHECK_SETTINGS
                                } catch (IntentSender.SendIntentException e) {
                                    // Ignore the error.
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                                break;
                        }
                    }
                });


                Toast toast;
                if(mSwitch.isChecked()){
                    updateLocation();
                    Log.d("debug location: ", "should update");
                }else {
                    toast = Toast.makeText(getContext(), "Your location is not shared", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

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

                if(enteredMood.length() > 20){
                    Toast.makeText(getContext(), "Your mood is too long, shrink it down!", Toast.LENGTH_LONG).show();
                }
                else if(enteredMood == null || enteredMood.equals("")){
                    setMood("No Mood");
                }
                else{
                    setMood(enteredMood);
                }
            }
        });


        mSwitch.setChecked(false);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked){
                    clearMap();
                    UserManager.getInstance().getCurrentUser().setLocationShared(false);
                    button1.setEnabled(false);
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                }else{
                    if(mapIsReady){
                        printLocation();
                    }
                    UserManager.getInstance().getCurrentUser().setLocationShared(true);
                    button1.setEnabled(true);
                    button2.setEnabled(true);
                    button3.setEnabled(true);
                }
                UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());

            }
        });

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (UserManager.getInstance().getCurrentUser() == null)
            loadProfile();
        else
            setUserAndPaintProfile(UserManager.getInstance().getCurrentUser());

        return view;
    }

    /**Updates the user's information when a field is changed in the database.*/
    @Override
    public void update(Observable o, Object arg) {
        setUserAndPaintProfile((User) arg);
    }

    /**Changes the user's Mood in the database.*/
    private void setMood(String mood) { //TODO change this
        UserManager.getInstance().getCurrentUser().setMood(mood);
        UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());
        Toast.makeText(getActivity(), "Mood updated!", Toast.LENGTH_SHORT).show();
    }

    /**Gets the user's information from the database.*/
    private void loadProfile() {
        UserManager.getInstance().deleteObservers();
        UserManager.getInstance().addObserver(this);
        UserManager.getInstance().getUserInformations();
    }

    /**Changes the layout according to the user's information.*/
    public void setUserAndPaintProfile(User u) {

        TextView profileName = (TextView) view.findViewById(R.id.profil_text);
        profileName.setText("Hello " +  UserManager.getInstance().getCurrentUser().getName());

        EditText editMood = (EditText) view.findViewById(R.id.editMoodText);
        editMood.setText( UserManager.getInstance().getCurrentUser().getMood());

        TextView breakText = (TextView) view.findViewById(R.id.breakText);
        breakText.setText(UserManager.getInstance().getCurrentUser().getBreakText());//TODO try

        Switch mSwitch = (Switch) view.findViewById(R.id.share_location);

        RadioGroup mRadioGroup = (RadioGroup) view.findViewById(R.id.floor_group);

        RadioButton button1 = (RadioButton) view.findViewById(R.id.radioButton1);
        RadioButton button2 = (RadioButton) view.findViewById(R.id.radioButton2);
        RadioButton button3 = (RadioButton) view.findViewById(R.id.radioButton3);

        checkBreakStatus(u);

        int tempFloor = u.getFloor();
        switch (tempFloor){
            case 1: button1.setChecked(true); break;
            case 2: button2.setChecked(true); break;
            case 3: button3.setChecked(true); break;
        }

        if(u.isLocationShared())
            mSwitch.setChecked(true);
        else
            mSwitch.setChecked(false);

    }

    /**Create or connect to a Google Api Client (if needed) and start
     * location updates from the device when the fragment starts. */
    public void onStart() {
        super.onStart();
        if(mGoogleApiClient == null)
            createGoogleMapClient();

        if(mGoogleApiClient.isConnected()){
            createGoogleMapClient(); // <- moi
            startLocationUpdates();
        }
    }

    /**Stops the location updates from the device when the fragment stops.*/
    public void onStop() {
        super.onStop();
        stopLocationUpdates();

    }

    /**Reconnects to the Google Api Client (if needed) and start the location updates from the user's device.*/
    @Override
    public void onResume(){
        super.onResume();
        if(!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();

        }else{
            startLocationUpdates();
        }

    }

    /**Stops the location updates from the user's device and disconnect the Google API client.*/
    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
        mGoogleApiClient.disconnect();
    }

    /**Enables the user to update his or her location and start the location updates from the user's device.*/
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        createRequestLocation();
        startLocationUpdates();
        mGpsUpdate.setEnabled(true);

    }

    /**Creates a Google Map Api Client in order to use Google's APIs.*/
    private void createGoogleMapClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**Creates the request for the location updates.*/
    private void createRequestLocation() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

    }

    /**Sets the map when it is ready to be used (location, zoom, markers).*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("debug", "in");
        mapIsReady = true;
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng champlain = new LatLng(45.5164522,-73.52062409999996);
        //LatLng papa = new LatLng(45.589532, -73.354669);

        //mMap.addMarker(new MarkerOptions().position(champlain).title("Champlain College"));

        CameraPosition pos = CameraPosition.builder().target(champlain).bearing(-103).build(); //rotate map
        //CameraPosition pos = CameraPosition.builder().target(papa).build(); //rotate map

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));

        if(UserManager.getInstance().getCurrentUser().getLastLocation() != null && UserManager.getInstance().getCurrentUser().isLocationShared()) {
            User u = UserManager.getInstance().getCurrentUser();
            LatLng temp = new LatLng(u.getLastLocation().getLat(), u.getLastLocation().getLng());
            Marker tempMarker = mMap.addMarker(new MarkerOptions().position(temp).title("Me"));
            tempMarker.showInfoWindow();
        }

        mMap.setMinZoomPreference((float)17.3);
        mMap.setMaxZoomPreference(20);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
    }

    /**Check if the application has the authorization to use the device's localisation.*/
    private boolean checkPermission() {//This should work
        int permissionCheck = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION); // Here is the problem BITCH ***********************************************************************************************************

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1);

            return false;
        } else {
            return true;
            //Toast.makeText(getActivity(), "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
        }

        /*String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = getContext().checkCallingPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);*/
    }


    /**Stops the location updates if the connection to the Google API Client is lost.*/
    @Override
    public void onConnectionSuspended(int i) {
        stopLocationUpdates();
    }

    /**Handles the event where the connection to the Google API Client fails.*/
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    /**Start the location updates.*/
    public void startLocationUpdates() {

        if(checkPermission() && mGoogleApiClient != null) { //debug
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } else {
            //Toast.makeText(getActivity(), "something went wrong in start", Toast.LENGTH_SHORT).show();
        }
    }
    /**Stop the location updates.*/
    protected void stopLocationUpdates() {
        if(checkPermission() && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient,  this);
        }else{
            //Toast.makeText(getActivity(), "something went wrong in stop", Toast.LENGTH_SHORT).show();
        }
    }

    /**Destroys the map when the fragment stops in order to prevent re-inflation of the view.*/
    @Override
    public void onDestroyView() {

        FragmentManager fm = getFragmentManager();

        Fragment xmlFragment = fm.findFragmentById(R.id.map);
        if (xmlFragment != null) {
            fm.beginTransaction().remove(xmlFragment).commit();
        }

        super.onDestroyView();
    }

    /**prevents the user to click on undesired elements.*/
    @Override
    public void onClick(View v) {
    }

    /**Prevents the user to swipe undesired elements.*/
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    /**Handles the event where the user's location changes.*/
    @Override
    public void onLocationChanged(Location location) {
        //Do Nothing
    }

    /**Update the user's location on the map and in the database.*/
    public void updateLocation() { //TODO add parameter true/false + implement
        if (checkPermission() && mGoogleApiClient.isConnected()) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                mMap.clear();

                mLatitudeText = String.valueOf(mLastLocation.getLatitude());
                mLongitudeText = String.valueOf(mLastLocation.getLongitude());

                Log.d("Coordinates", (mLatitudeText + ", " + mLongitudeText));

                LatLng me = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                UserManager.getInstance().getCurrentUser().getLastLocation().setLat(Double.parseDouble(mLatitudeText));
                UserManager.getInstance().getCurrentUser().getLastLocation().setLng(Double.parseDouble(mLongitudeText));

                UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());
                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(me).title("Me"));

                Toast.makeText(getContext(), "Location updated", Toast.LENGTH_SHORT).show();

            } else {
                Log.d("debug", "fused not working");
            }
        } else {
            //Toast.makeText(getActivity(), "something went wrong in start", Toast.LENGTH_SHORT).show();
        }
    }

    /**Requests a permission to a given aspect to the user.*/
    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{permissionName}, permissionRequestCode);
    }

    /**Handles the result of the the permission request.*/
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }
    /**Clears the map.*/
    public void clearMap(){
        mMap.clear();
    }

    /**Returns the time difference between two dates in milliseconds*/
    public static long getDateDiff(GregorianCalendar date1, GregorianCalendar date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime().getTime() - date1.getTime().getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    /**Gets the user's break status (time before a break, in break or no more breaks)*/
    private void checkBreakStatus(User u) {

        ArrayList<Break> friendBreaks = new ArrayList<>();
        //Loading things from database

        DatabaseReference breaksReference = FirebaseDatabase.getInstance().getReference("breaks");
        Query breakQuery = breaksReference.orderByChild("userId").equalTo(u.getId());

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("debug bt ", "inside checkBreakListener for: "+u.getName() );
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    friendBreaks.add(singleSnapshot.getValue(Break.class));
                }
                ((TextView)view.findViewById(R.id.breakText)).setText(breakCalculator(u, friendBreaks));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Not Handled
            }
        };
        breakQuery.addListenerForSingleValueEvent(postListener);
    }

    /**Calculates the time difference between two breaks.*/
    public String breakCalculator(User u, ArrayList<Break> friendBreaks){
        u.setBreaks(friendBreaks);

        java.util.GregorianCalendar current = new java.util.GregorianCalendar();
        int currentDay = adaptDayOfWeek(current.get(java.util.Calendar.DAY_OF_WEEK));

        long closestBreakMin = 1440; //Why? bcz: Initialise variable at 24 hrs (because 23:59 is the max value possible between now and the closest break in the same day) ps: this var keeps track of the closest break of the current day.
        Break closestBreak = null;
        long closestBreakDuration = 0;

        // find closest break for the day (if any)
        for (int i=0; i < friendBreaks.size(); i++) {
            Break breakNode = friendBreaks.get(i);

            if (currentDay == breakNode.getIntDay()) {

                GregorianCalendar now = new GregorianCalendar();

                GregorianCalendar breakStart = new GregorianCalendar();
                breakStart.set(Calendar.HOUR_OF_DAY,breakNode.getStart().getHour());
                breakStart.set(Calendar.MINUTE,(breakNode.getStart().getMinute()));
                breakStart.set(Calendar.SECOND,0);

                long timeDiffInMinutes = getDateDiff(now, breakStart, TimeUnit.MINUTES);

                GregorianCalendar breakEnd = new GregorianCalendar();
                breakEnd.set(Calendar.HOUR_OF_DAY,breakNode.getEnd().getHour());
                breakEnd.set(Calendar.MINUTE,breakNode.getEnd().getMinute());
                breakEnd.set(Calendar.SECOND,0);

                //check if currently in break.
                long breakDuration = getDateDiff(breakStart, breakEnd, TimeUnit.MINUTES);

                if (timeDiffInMinutes < 0 && Math.abs(timeDiffInMinutes) <= breakDuration) {
                    //is currently in break! // Loop stops here.
                    closestBreak = breakNode;
                    closestBreakMin = timeDiffInMinutes;
                    closestBreakDuration = breakDuration;
                    break;

                } else if (timeDiffInMinutes < closestBreakMin) {
                    //this is to handle if there are multiple breaks in one day. The goal is to show when is the CLOSEST break.
                    closestBreak = breakNode;
                    closestBreakMin = timeDiffInMinutes;
                    closestBreakDuration = breakDuration;
                }
                else if (timeDiffInMinutes > 0 && closestBreakMin < 0) {
                    //There is still a break in the current day, while the closest break is already passed, so the coming break has priority. (get it?)
                    closestBreak = breakNode;
                    closestBreakMin = timeDiffInMinutes;
                    closestBreakDuration = breakDuration;
                }
            }
        }

        String text = "";

        if (friendBreaks.isEmpty()){
            text = "No breaks at all";

        } else if (closestBreak == null) {
            text = "No breaks today";

        } else {
            if (closestBreakMin > 0) {
                // Next break in timediffminutes
                text = "Next break at : " + closestBreak.getFromTime();
            }
            else if (closestBreakMin < 0 && Math.abs(closestBreakMin) <= closestBreakDuration) {
                text = "In break until : " + closestBreak.getToTime();
            }
            else {
                // break is over.
                text = "No more breaks today";
            }

        }
        Log.d("Debug bt: ", ""+text);

        return text;
    }

    /**Theres a mismatch between our original int values of weekdays with the java.utils.calendar integer values of weekdays.*/
    private int adaptDayOfWeek(int weekday) {
        int newValue = 0;
        switch(weekday) {
            case 1:
                newValue = 7;
                break;
            case 2:
                newValue = 1;
                break;
            case 3:
                newValue = 2;
                break;
            case 4:
                newValue = 3;
                break;
            case 5:
                newValue = 4;
                break;
            case 6:
                newValue = 5;
                break;
            case 7:
                newValue = 6;
                break;
        }
        return newValue;
    }

    /**Prints user's location*/
    public void printLocation(){
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(UserManager.getInstance().getCurrentUser().getLastLocation().getLat(),UserManager.getInstance().getCurrentUser().getLastLocation().getLng())).title("Me")) ;
    }
}
