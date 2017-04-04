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

import ultramirinc.champs_mood.fragments.TimePicker;

public class ScheduleAdder extends AppCompatActivity implements DialogInterface.OnDismissListener {
    private Context context = this;
    static int startMinute = 0 ;
    static int startHour = 0;
    static int endHour = 0;
    static int endMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_adder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

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

    public static int getStartMinute() {
        return startMinute;
    }

    public static void setStartMinute(int startMinute) {
        ScheduleAdder.startMinute = startMinute;
    }

    public static int getStartHour() {
        return startHour;
    }

    public static void setStartHour(int startHour) {
        ScheduleAdder.startHour = startHour;
    }

    public static int getEndHour() {
        return endHour;
    }

    public static void setEndHour(int endHour) {
        ScheduleAdder.endHour = endHour;
    }

    public static int getEndMinute() {
        return endMinute;
    }

    public static void setEndMinute(int endMinute) {
        ScheduleAdder.endMinute = endMinute;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        //TODO do this shit
    }



}
