package ultramirinc.champs_mood;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ultramirinc.champs_mood.models.User;


public class FriendProfilActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private User friendProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendprofil);

        Intent intent = getIntent();

        try {
            String userId = intent.getExtras().get("userId").toString();
            LoadFriendProfile(userId);
        }
        catch (Exception e) {
            //error occured, don't do nothing.
        }

        SupportMapFragment mMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMap.getMapAsync(this);

    }

    public void LoadFriendProfile(String userId) {
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        Query userQuery = databaseUsers.orderByChild("id").equalTo(userId);
        ValueEventListener loadInfoListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    friendProfile = singleSnapshot.getValue(User.class);
                }
                TextView name = (TextView) findViewById(R.id.profil_text);
                name.setText(friendProfile.getName());
                TextView mood = (TextView) findViewById(R.id.mood);
                mood.setText(friendProfile.getMood());
                TextView breakText = (TextView) findViewById(R.id.breakText);
                breakText.setText(friendProfile.getBreakText());
                try {
                    TextView floor = (TextView) findViewById(R.id.floorLevel);
                    if (friendProfile.getShareFloor()) {
                        floor.setText(Integer.toString(friendProfile.getFloorLevel()));
                    }
                    else {
                        floor.setText("Floor not shared");
                    }
                }catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userQuery.addListenerForSingleValueEvent(loadInfoListener);
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
}
