package ultramirinc.champs_mood;


import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.Break;
import ultramirinc.champs_mood.models.Time;
import ultramirinc.champs_mood.models.User;


public class FriendProfilActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private User friendProfile;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendprofil);

        Intent intent = getIntent();

        try {
            String userId = intent.getExtras().get("userId").toString();
            loadFriendProfile(userId);
        }
        catch (Exception e) {
            //error occured, don't do nothing.
        }

        Button button = (Button) findViewById(R.id.friendShipButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().toString().equals("Follow")) {
                    follow(friendProfile);
                }
                else {
                    unFollow(friendProfile);
                }
            }
        });

        SupportMapFragment mMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMap.getMapAsync(this);
    }

    public void loadFriendProfile(String userId) {
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        Query userQuery = databaseUsers.orderByChild("id").equalTo(userId);
        ValueEventListener loadInfoListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    friendProfile = singleSnapshot.getValue(User.class);
                }

                latLng = new LatLng(friendProfile.getLastLocation().getLat(),
                        friendProfile.getLastLocation().getLng());
                Log.d("Location Debug: ", latLng.toString());
                mMap.addMarker(new MarkerOptions().position(latLng).title(friendProfile.getName()));

                TextView name = (TextView) findViewById(R.id.profil_text);
                name.setText(friendProfile.getName());

                TextView mood = (TextView) findViewById(R.id.mood);
                mood.setText(friendProfile.getMood());

                TextView breakText = (TextView) findViewById(R.id.breakText);
                breakText.setText(friendProfile.getBreakText());

                CheckBreakStatus();
                updateFriendShipButton(UserManager.getInstance().getCurrentUser().isFriend(friendProfile));

                try {
                    TextView floor = (TextView) findViewById(R.id.floorLevel);
                    if (friendProfile.isShareFloor()) {
                        floor.setText(Integer.toString(friendProfile.getFloorLevel()));
                    }
                    else {
                        floor.setText("Floor not shared");
                    }
                }catch (Exception e) {
                    //empty
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userQuery.addListenerForSingleValueEvent(loadInfoListener);
    }

    private boolean unFollow(User userToUnfollow) {
        boolean done = UserManager.getInstance().getCurrentUser().removeFromFriendList(userToUnfollow);
        //save
        if (done) {
            UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());
            updateFriendShipButton(false);
        }
        return done;
    }
    private void follow(User userToFollow) {
        boolean done = UserManager.getInstance().getCurrentUser().addToFriendList(userToFollow);
        //save
        if (done) {
            UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());
            updateFriendShipButton(true);
        }
    }
    public void updateFriendShipButton(boolean isFriend) {
        Button button = (Button) findViewById(R.id.friendShipButton);

        String textButton = isFriend ? "Unfollow" : "Follow";
        button.setText(textButton);
    }

    private void CheckBreakStatus() {

        ArrayList<Break> friendBreaks = new ArrayList<>();
        //Loading things from db
        DatabaseReference breaksReference = FirebaseDatabase.getInstance().getReference("breaks");
        Query breakQuery = breaksReference.orderByChild("userId").equalTo(friendProfile.getId());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    friendBreaks.add(singleSnapshot.getValue(Break.class));
                }
                java.util.GregorianCalendar current = new java.util.GregorianCalendar();
                int currentDay = current.get(java.util.Calendar.DAY_OF_WEEK);
                boolean breakToday = false;
                for (int i=0; i < friendBreaks.size(); i++) {
                    Break breakNode = friendBreaks.get(i);
                    if (currentDay == breakNode.getIntDay()+1) {
                        breakToday = true;
                        Time timeDiff = breakNode.getTimeDifference();
                        String text = "Break at: " + breakNode.getFromTime() + "-" + breakNode.getToTime();
                        TextView tv = (TextView) findViewById(R.id.breakText);
                        if (tv != null) {
                            tv.setText(text);
                        }
                    }
                }
                if (!breakToday) {
                    TextView tv = (TextView) findViewById(R.id.breakText);
                    if (tv != null) {
                        tv.setText("No breaks today");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        breakQuery.addListenerForSingleValueEvent(postListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("debug", "in");
        mMap = googleMap;
        mMap.clear();

        // Add a marker in Sydney and move the camera
        LatLng champlain = new LatLng(45.5164522,-73.52062409999996);







        CameraPosition pos = CameraPosition.builder().target(champlain).bearing(-103).build(); //rotate map
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(pos));


        mMap.setMinZoomPreference((float)17.3);
        mMap.setMaxZoomPreference(20);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
    }
}
