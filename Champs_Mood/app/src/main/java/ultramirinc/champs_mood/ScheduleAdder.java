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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;

import ultramirinc.champs_mood.fragments.TimePicker;

public class ScheduleAdder extends AppCompatActivity implements DialogInterface.OnDismissListener, BreakCreator.OnBreakReady {
    private Context context = this;
    private ArrayList<Break> breakList= new ArrayList<Break>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_adder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        populateList(breakList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment mDialog = new BreakCreator();
                mDialog.show(getSupportFragmentManager(), "start break Creator");


                /*


                TextView startTime = (TextView) mDialog.findViewById(R.id.start_time);
                TextView endTime = (TextView) mDialog.findViewById(R.id.end_time);





                Button pickTime1 = (Button) mDialog.findViewById(R.id.button1);

                pickTime1.setOnClickListener(v -> {

                    DialogFragment mTimePicker = new TimePicker();
                    mTimePicker.show(getSupportFragmentManager(), "Start time");


                });

                Button pickTime2 = (Button) mDialog.findViewById(R.id.button2);

                pickTime2.setOnClickListener(v -> {

                    DialogFragment mTimePicker = new TimePicker();
                    mTimePicker.show(getSupportFragmentManager(), "End time");


                });


                Spinner listDay =(Spinner) mDialog.findViewById(R.id.spinner);

                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                        R.array.list_day_of_week, android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                listDay.setAdapter(adapter);

                Button save = (Button) mDialog.findViewById(R.id.save_button);
                Button cancel = (Button) mDialog.findViewById(R.id.cancel_button);

                cancel.setOnClickListener( v -> {
                    mDialog.dismiss();
                });
                 */



            }
        });
    }

    public void populateList(ArrayList<?> list){
        //TODO get shit from database
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        //TODO do this shit
    }


    @Override
    public void onBreakReadyListener(String text) {
        String[] temp = text.split(":");
        String day = temp[0];
        int startMinute = Integer.parseInt(temp[1]);
        int startHour = Integer.parseInt(temp[2]);
        int endMinute = Integer.parseInt(temp[3]);
        int endHour = Integer.parseInt(temp[4]);
        Break mBreak = new Break(new ultramirinc.champs_mood.Time(startHour, startMinute),
                new ultramirinc.champs_mood.Time(endHour, endMinute), day);
    }
}
