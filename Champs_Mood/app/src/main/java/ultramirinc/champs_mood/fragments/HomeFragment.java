package ultramirinc.champs_mood.fragments;
//DONE
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import ultramirinc.champs_mood.FriendProfilActivity;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.User;


/**
 * Created by Etienne Berube on 2017-03-23.
 * This class is the homepage of the app. When it is launched. It appears as the first tab the user will see.
 * He will then see his current mood and the location of his friends on the map.
 */


public class HomeFragment extends Fragment implements Observer, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, GoogleMap.OnInfoWindowClickListener {
    /**The map from Google Map*/
    private GoogleMap mMap;
    /**The Google Api Client Object is required to access Google's APIs*/
    private GoogleApiClient mGoogleApiClient;
    /**Contains the visual layout of the fragment*/
    private View view;
    /**The location request in order to get information from the GPS*/
    private LocationRequest mLocationRequest;
    /**Contains list of the user's Friends*/
    private List<User> friends = Collections.synchronizedList(new ArrayList<User>());

    public HomeFragment(){

    }
    /**Inflates the visual layotu for the fragment*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        } else {
            view = inflater.inflate(R.layout.fragment_homepage, container, false);
        }


        SupportMapFragment mMap = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);

        createGoogleMapClient();

        mMap.getMapAsync(this);

        TextView name = (TextView)view.findViewById(R.id.mood);

        name.setOnTouchListener((v, event) -> false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        loadProfile();

        return view;
    }

    @Override
    /**Updates the data when some fields in the database change.*/
    public void update(Observable o, Object arg) {
        try {
            setUserAndPaintProfile((User) arg);
        }
        catch (Exception e) {
            //error not handeled
        }
    }
    /**Updates the View for the user's mood.*/
    public void updateView(User u) {
        TextView myMood = (TextView)view.findViewById(R.id.mood);
        myMood.setText(u.getMood());
    }

    @Override
    /**This method is called when the fragment's resumes.
     * It recreates the Google Api Client if needed.
     * */
    public void onResume(){
        super.onResume();
        if (mGoogleApiClient == null)
            createGoogleMapClient();

        if (mGoogleApiClient.isConnected()) {
            createGoogleMapClient();
        }

    }

    @Override
    /**This method is called when the fragments pauses.
     * Disconnects the Google Api Client if needed.*/
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected() || mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.disconnect();
        }
    }
    /**This method is called when the Google Api Client is connected.*/
    public void onConnected(@Nullable Bundle bundle) {
        createRequestLocation();
        //startLocationUpdates();

    }
    /**This method creates the Google Api Client used for the Google Map.*/
    private void createGoogleMapClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    /**Creates the location requests in order to get the location from the user's phone*/
    private void createRequestLocation(){
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(30000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    /**This method is called when the map is ready. Sets the map for the user interactions
     *  (Places, proper zoom, markers, etc.).*/
    public void onMapReady(GoogleMap googleMap) {
        Log.d("debug", "in");
        mMap = googleMap;

        LatLng champlain = new LatLng(45.5164522,-73.52062409999996);

        //mMap.addMarker(new MarkerOptions().position(champlain).title("Champlain College"));

        CameraPosition pos = CameraPosition.builder().target(champlain).bearing(-103).build(); //rotate map

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(champlain));//Move camera to Champlain
        //mMap.addMarker(new MarkerOptions().position(champlain).title("Champlain College"));

        mMap.setMinZoomPreference((float)17.3);
        mMap.setMaxZoomPreference(20);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setOnInfoWindowClickListener(this);

    }
    /**This method is called when the connection between the Google Api Client and the user is lost.*/
    public void onConnectionSuspended(int i) {
    }

    @Override
    /**This method is called when the connection between the Google Api Client and the user
     * doesn't work.*/
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("Client error", "Google client did not connect");

    }

    @Override
    /**This method protects the JVM to re-inflate the view twice, causing an error.*/
    public void onDestroyView() {

        FragmentManager fm = getFragmentManager();

        Fragment xmlFragment = fm.findFragmentById(R.id.map);
        if (xmlFragment != null) {
            fm.beginTransaction().remove(xmlFragment).commit();
        }

        super.onDestroyView();
    }

    @Override
    /**Handles the user's interaction with certain component when there should be no interactions.*/
    public void onClick(View v) {
        //Empty
    }
    /**This method takes a User and sets the visual layout accordingly to certain fields and variables.*/
    public void setUserAndPaintProfile(User u) {

        friends = new ArrayList<User>();

        updateView(u);

        User currentUser = u;

        getFriends(u);
        setDataListeners(u);
    }
    /**Gets the current User's informations.*/
    private void loadProfile() {
        UserManager.getInstance().deleteObservers();
        UserManager.getInstance().addObserver(this);
        UserManager.getInstance().getUserInformations();
    }
    /**Gets the current User's friends.*/
    public void getFriends(User u){

        User currentUser = u;

        DatabaseReference usersTable = FirebaseDatabase.getInstance().getReference("users");
        usersTable.child(currentUser.getuId()).child("friendList").addListenerForSingleValueEvent(new ValueEventListener() {


            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot friendKey: snapshot.getChildren()) {
                    FirebaseDatabase.getInstance().getReference("users").child(friendKey.getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                        public void onDataChange(DataSnapshot friendSnapshot) {

                            User friend = friendSnapshot.getValue(User.class);
                            friends.add(friend);
                            LatLng temp = new LatLng(friend.getLastLocation().getLat(), friend.getLastLocation().getLng());
                            if(friend.isLocationShared()) {
                                Marker tempMarker = mMap.addMarker(new MarkerOptions().position(temp).title(friend.getName()));
                                tempMarker.showInfoWindow();
                                tempMarker.setTag(friend.getId());
                            }


                        }
                        public void onCancelled(DatabaseError firebaseError) {
                            //nothing
                        }


                    });
                }
                //fillMap(friends);
            }
            public void onCancelled(DatabaseError firebaseError) {
                //empty
            }
        });
    }
    /**Sets Listeners for the map to re-draw each time a friend changes its location.*/
    public void setDataListeners(User u){
        User currentUser = u;

        DatabaseReference usersTable = FirebaseDatabase.getInstance().getReference("users");
        usersTable.child(currentUser.getuId()).child("friendList").addValueEventListener(new ValueEventListener() {


            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot friendKey: snapshot.getChildren()) {
                    FirebaseDatabase.getInstance().getReference("users").child(friendKey.getValue(String.class)).addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot friendSnapshot) {

                            User u = friendSnapshot.getValue(User.class);
                            Iterator<User> iterator = friends.iterator();
                            User toDelete = null;
                            User toAdd = null;

                            while(iterator.hasNext()){
                                User friend = iterator.next();
                                if(friend.getId().equals(u.getId())){
                                    toDelete = friend;
                                    toAdd = u;
                                }else{
                                    toAdd = u;
                                }
                            }

                            if(toDelete != null)
                                friends.remove(toDelete);
                            if(toAdd != null)
                                friends.add(toAdd);

                            drawMap();
                        }
                        public void onCancelled(DatabaseError firebaseError) {
                            //nothing
                        }
                    });
                }

            }
            public void onCancelled(DatabaseError firebaseError) {
                //empty
            }
        });
    }

    @Override
    /**Sets action when user interacts with the information window of a marker.*/
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(getContext(), FriendProfilActivity.class);
        intent.putExtra("userId", (String) marker.getTag());
        getContext().startActivity(intent);
    }
    /**Draws the map accordingly to the user's friends locations*/
    public void drawMap(){
        mMap.clear();
        Log.d("Draw Map debug", ""+friends.size());
        for(User friend: friends){
            Log.d("Debug Location hp: ", ""+ friend.getName()+ "\n" + friend.getLastLocation().getLat());
            LatLng temp = new LatLng(friend.getLastLocation().getLat(), friend.getLastLocation().getLng());
            if(friend.isLocationShared()) {
                Marker tempMarker = mMap.addMarker(new MarkerOptions().position(temp).title(friend.getName()));
                tempMarker.showInfoWindow();
                tempMarker.setTag(friend.getId());
            }
        }

    }
}
