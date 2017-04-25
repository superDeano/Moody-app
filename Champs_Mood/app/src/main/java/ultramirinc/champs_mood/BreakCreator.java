package ultramirinc.champs_mood;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import java.util.Calendar;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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

import ultramirinc.champs_mood.fragments.TimePicker;

public class BreakCreator extends DialogFragment implements AdapterView.OnItemSelectedListener{

    private boolean isCancelled = false;
    private String day;
    private int startHour = -1;
    private int startMinute = -1;
    private int endHour = -1;
    private int endMinute = -1;
    private TextView startTime;
    private TextView endTime;
    private Context context;
    private Spinner listDay;
    private TimePickerDialog start;
    private TimePickerDialog end;
    private OnBreakReadyListener mListener;

    public interface OnBreakReadyListener {
        void onBreakReady(String breakString);
    }

    @Override
    public void onResume(){
      super.onResume();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    public static BreakCreator newInstance(int title) {
        BreakCreator frag = new BreakCreator();
        Bundle args = new Bundle();
        args.putInt("Break", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){
            a=(Activity) context;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_break_creator, container);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("Create Your break");



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();

        startTime = (TextView) view.findViewById(R.id.start_time);
        endTime = (TextView) view.findViewById(R.id.end_time);

        ImageButton pickTime1 = (ImageButton) view.findViewById(R.id.button1);

        pickTime1.setOnClickListener(new View.OnClickListener() {//TODO optimize
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                        startHour = hourOfDay;
                        startMinute = minute;
                        setStartTimeView();
                    }
                };

                start = new TimePickerDialog(context, listener, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));

                start.show();
                //Old
                /*TimePicker mTimePicker = new TimePicker();
                Bundle bundle = new Bundle();
                bundle.putInt("id", 1);
                mTimePicker.setArguments(bundle);
                mTimePicker.setTargetFragment(BreakCreator.this, 0);
                mTimePicker.show(getFragmentManager(), "Start time");
                */
            }
        });

        ImageButton pickTime2 = (ImageButton) view.findViewById(R.id.button2);

        pickTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                        endHour = hourOfDay;
                        endMinute = minute;
                        setEndTimeView();
                    }
                };

                end = new TimePickerDialog(context, listener, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));

                end.show();

                /*
                TimePicker mTimePicker = new TimePicker();
                Bundle bundle = new Bundle();
                bundle.putInt("id", 2);
                mTimePicker.setArguments(bundle);
                mTimePicker.setTargetFragment(BreakCreator.this, 0);
                mTimePicker.show(getFragmentManager(), "Start time");
                */
            }
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
                if(startHour > endHour){
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        day = (String)listDay.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        listDay.setSelection(0);
        day = (String)listDay.getSelectedItem();

    }

    //Change the implements to add this again (TimePicker.SetOnClockSomething)
    /*
    @Override
    public void onTimeSetDialog(String inputText) {
        String[] temp = inputText.split(":");
        if(Integer.parseInt(temp[0])==1){
            startHour = Integer.parseInt(temp[1]);
            startMinute = Integer.parseInt(temp[2]);
            setStartTimeView();
        } else if(Integer.parseInt(temp[0])==2){
            endHour = Integer.parseInt(temp[1]);
            endMinute = Integer.parseInt(temp[2]);
            setEndTimeView();
        }else{
            startMinute = -1;
            startHour = -1;
            endMinute = -1;
            endHour = -1;
        }

    }
    */

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

    public boolean checkIfComplete(){
        if(listDay.getSelectedItem() != null && startMinute != -1 && startHour != -1 && endMinute != -1 && endHour != -1){

            if(startHour<endHour)
                return true;
            else{
                if(startMinute < endMinute)
                    return true;
                else
                    return false;
            }
        }else
            return false;
    }


    //ONE TIME USE INTERFACE
    /*
    public interface OnBreakReady{
        public void onBreakReadyListener(String text);
    }
    */

    public boolean isCancelled() {
        return isCancelled;
    }

    public String getDay() {
        return day;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public OnBreakReadyListener getmListener() {
        return mListener;
    }

    public void setmListener(OnBreakReadyListener mListener) {
        this.mListener = mListener;
    }
}
