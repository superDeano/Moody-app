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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ScheduleAdder extends AppCompatActivity{
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_adder);
<<<<<<< HEAD
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
=======


                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
>>>>>>> origin/Tabbed_main_activity
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog mDialog = new Dialog(context);
                mDialog.setContentView(R.layout.activity_break_creator);


                Button save = (Button) mDialog.findViewById(R.id.save_button);
                Button cancel;(Button) mDialog.findViewById(R.id.save_button);
                Button pickTime1;
                Button picktime2;
                TextView startTime;
                TextView endTime;
                Spinner listDay;



            }
        });
    }

}
