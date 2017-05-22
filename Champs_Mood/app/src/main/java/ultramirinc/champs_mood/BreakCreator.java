package ultramirinc.champs_mood;
//Done
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import java.util.Calendar;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Etienne Berube on 2017-03-23.
 * This class is a Pop-up that appears when the user wants to create a new Break Object for his schedule
 */


public class BreakCreator extends DialogFragment implements AdapterView.OnItemSelectedListener{
    /**Is true if the user cancelled the break creation*/
    private boolean isCancelled = false;
    /**Day of the break*/
    private String day;
    /**The starting hour of the break*/
    private int startHour = -1;
    /**The starting minute of the break*/
    private int startMinute = -1;
    /**The ending hour of the break*/
    private int endHour = -1;
    /**The ending minute of the break*/
    private int endMinute = -1;
    /**Contains the view for the start time*/
    private TextView startTime;
    /**Contains the view for the end time*/
    private TextView endTime;
    /**Contains the contaxt of the attached activity*/
    private Context context;
    /**Contains the drop-down menu of the days of the week*/
    private Spinner listDay;
    /**The TimePickerDialog for the starting time*/
    private TimePickerDialog start;
    /**The TimePickerDialog for the ending time*/
    private TimePickerDialog end;
    /**Contains the listener when the break is ready*/
    private OnBreakReadyListener mListener;

    /**A Custom listener for when the break is ready.*/
    public interface OnBreakReadyListener {
        void onBreakReady(String breakString);
    }

    public BreakCreator(){
    }


    /**Removes the keyboard from the screen when the dialog appears.*/
    @Override
    public void onResume(){
      super.onResume();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**Creates a new instance of this dialog.*/
    public static BreakCreator newInstance(int title) {
        BreakCreator frag = new BreakCreator();
        Bundle args = new Bundle();
        args.putInt("Break", title);
        frag.setArguments(args);
        return frag;
    }

    /**Gets context from attached activity.*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){
            a=(Activity) context;
        }

    }

    /**Inflates the visual layout of the dialog.*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_break_creator, container);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Create your break");

        return view;
    }

    /**Sets listeners when the layout is inflated.*/
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();

        startTime = (TextView) view.findViewById(R.id.start_time);
        endTime = (TextView) view.findViewById(R.id.end_time);

        ImageButton pickTime1 = (ImageButton) view.findViewById(R.id.button1);

        pickTime1.setOnClickListener(v -> {
            TimePickerDialog.OnTimeSetListener listener = (view1, hourOfDay, minute) -> {
                startHour = hourOfDay;
                startMinute = minute;
                setStartTimeView();
            };

            start = new TimePickerDialog(context, listener, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));

            start.show();
        });

        ImageButton pickTime2 = (ImageButton) view.findViewById(R.id.button2);

        pickTime2.setOnClickListener(v -> {

            TimePickerDialog.OnTimeSetListener listener = (view12, hourOfDay, minute) -> {
                endHour = hourOfDay;
                endMinute = minute;
                setEndTimeView();
            };

            end = new TimePickerDialog(context, listener, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));

            end.show();
        });


         listDay =(Spinner) view.findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.list_day_of_week, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        listDay.setAdapter(adapter);

        Button save = (Button) view.findViewById(R.id.save_button);

        save.setOnClickListener(v -> {
            if(checkIfComplete()){
                day = listDay.getSelectedItem().toString();

                OnBreakReadyListener parent = (OnBreakReadyListener) getActivity();



                parent.onBreakReady(day+":"+startHour+":"+startMinute+":"+endHour+":"+endMinute);

                dismiss();
            }else {
                if(startHour >= endHour){
                    Toast toast = Toast.makeText(context, "Careful, your break is quite long", Toast.LENGTH_LONG);
                    toast.show();
                }
                if(startMinute == -1 && startHour == -1 && endMinute == -1 && endHour == -1) {
                    Toast toast = Toast.makeText(context, "Unable to create Break, check again", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        Button cancel = (Button) view.findViewById(R.id.cancel_button);

        cancel.setOnClickListener( v -> {
            //Nothing happens
            isCancelled = true;
            dismiss();
        });
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**Selects a day from the dropdown menu when it is clicked on.*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        day = (String)listDay.getItemAtPosition(position);
    }

    /**Sets a default value when no day is selected in the dropdown menu*/
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        listDay.setSelection(0);
        day = (String)listDay.getSelectedItem();

    }

    /**Setter for startTimeView*/
    public void setStartTimeView(){


        if(startMinute != -1 && startMinute != -1){
            String stringStartHour = "" + startHour;
            String stringStartMinute;

            if(startMinute < 10) {
                stringStartMinute = "0" + startMinute;
            }else{
                stringStartMinute = "" + startMinute;
            }
                startTime.setText(stringStartHour + ":" + stringStartMinute);

        }else{
            startTime.setText("Hr:Min");
        }
    }
    /**Setter for endTimeView*/
    public void setEndTimeView(){
        if(endMinute != -1 && endMinute != -1){
            String stringEndHour = "" + endHour;
            String stringEndMinute;


            if(endMinute < 10) {
                stringEndMinute = "0" + endMinute;
            }else{
                stringEndMinute = "" + endMinute;
            }
            endTime.setText(stringEndHour + ":" + stringEndMinute);

        }else{
            endTime.setText("Hr:Min");
        }
    }
    /**Check if the potential break is complete or if certain fields are missing.*/
    public boolean checkIfComplete(){
        if((listDay.getSelectedItem() != null && startMinute != -1 && startHour != -1 && endMinute != -1 && endHour != -1)
            || (startHour == endHour && startMinute == endMinute)){

            if(startHour<endHour)
                return true;
            else{
                if((startMinute < endMinute) && (startHour == endHour))
                    return true;
                else
                    return false;
            }
        }else
            return false;
    }
    /**Getter for isCancelled*/
    public boolean isCancelled() {
        return isCancelled;
    }
    /**Getter for day*/
    public String getDay() {
        return day;
    }
    /**Getter for startHour*/
    public int getStartHour() {
        return startHour;
    }
    /**Getter for startMinute*/
    public int getStartMinute() {
        return startMinute;
    }
    /**Getter for endHour*/
    public int getEndHour() {
        return endHour;
    }
    /**Getter for endMinute*/
    public int getEndMinute() {
        return endMinute;
    }
    /**Getter for mListener*/
    public OnBreakReadyListener getListener() {
        return mListener;
    }
    /**Setter for mListener*/
    public void setListener(OnBreakReadyListener listener) {
        this.mListener = mListener;
    }
}

