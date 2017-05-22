package ultramirinc.champs_mood;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.Break;
import ultramirinc.champs_mood.models.Time;
import ultramirinc.champs_mood.models.User;

/**
 * Created by Etienne Berube on 2017-03-23.
 * This class allows the user to manage existing breaks or create new ones.
 */

public class ScheduleAdder extends AppCompatActivity implements BreakCreator.OnBreakReadyListener{

    private Context context = this;
    private List<Break> breakList= new ArrayList<>();
    private RecyclerView recyclerView;

    public ScheduleAdder(){
        populateList();
        Collections.sort(breakList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_adder);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapterSchedule(breakList));



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Breaks");

        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        marginLayoutParams.setMargins(0, toolbar.getHeight() , 0,0);
        recyclerView.setLayoutParams(marginLayoutParams);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BreakCreator mDialog = new BreakCreator();
                //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                mDialog.show(getSupportFragmentManager(), "start break Creator");
                InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

            }
        });
    }

    public void populateList() {
        DatabaseReference breaksReference = FirebaseDatabase.getInstance().getReference("breaks");
        Query breakQuery = breaksReference.orderByChild("userId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    breakList.add(singleSnapshot.getValue(Break.class));
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        breakQuery.addListenerForSingleValueEvent(postListener);
    }


    @Override
    public void onBreakReady(String breakString) {
        User currentUser = UserManager.getInstance().getCurrentUser();

        if (currentUser == null) {
            return;
        }

        String[] temp = breakString.split(":");
        String day = temp[0];
        Log.d("debug", ""+day);
        int startMinute = Integer.parseInt(temp[2]);
        int startHour = Integer.parseInt(temp[1]);
        int endMinute = Integer.parseInt(temp[4]);
        int endHour = Integer.parseInt(temp[3]);
        Break mBreak = new Break(new Time(startHour, startMinute),
                new Time(endHour, endMinute), day, currentUser.getId());
        breakList.add(mBreak);
        saveBreakToDb(mBreak);
        Collections.sort(breakList);
        recyclerView.getAdapter().notifyDataSetChanged();
        Log.d("Debug", "breakCreated");
    }

    private void saveBreakToDb(Break breakItem) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("breaks");
        DatabaseReference generatedId = ref.push();
        breakItem.setId(generatedId.getKey());
        ref.child(generatedId.getKey()).setValue(breakItem);
    }
}
