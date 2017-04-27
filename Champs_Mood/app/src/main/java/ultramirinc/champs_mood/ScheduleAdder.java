package ultramirinc.champs_mood;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ultramirinc.champs_mood.fragments.MyAdapter;
import ultramirinc.champs_mood.fragments.TimePicker;

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

    public void populateList(){
        //TODO get shit from database
        breakList.add(new Break(new Time(1, 30), new Time(2, 00), "Monday"));
        breakList.add(new Break(new Time(1, 30), new Time(2, 00), "Monday"));
        breakList.add(new Break(new Time(1, 30), new Time(2, 00), "Tuesday"));
        breakList.add(new Break(new Time(1, 30), new Time(2, 00), "Wednesday"));
        breakList.add(new Break(new Time(1, 30), new Time(2, 00), "Wednesday"));
        breakList.add(new Break(new Time(1, 30), new Time(2, 00), "Friday"));
    }




    @Override
    public void onBreakReady(String breakString) {
        String[] temp = breakString.split(":");
        String day = temp[0];
        Log.d("debug", ""+day);
        int startMinute = Integer.parseInt(temp[2]);
        int startHour = Integer.parseInt(temp[1]);
        int endMinute = Integer.parseInt(temp[4]);
        int endHour = Integer.parseInt(temp[3]);
        Break mBreak = new Break(new ultramirinc.champs_mood.Time(startHour, startMinute),
                new ultramirinc.champs_mood.Time(endHour, endMinute), day);
        //TODO send information to DATABASE
        breakList.add(mBreak);
        Collections.sort(breakList);
        recyclerView.getAdapter().notifyDataSetChanged();//Try <--------------------------------------------------------------
        Log.d("Debug", "breakCreated");
    }
}
