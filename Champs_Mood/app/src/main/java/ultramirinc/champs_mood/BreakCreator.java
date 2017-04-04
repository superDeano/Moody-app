package ultramirinc.champs_mood;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import ultramirinc.champs_mood.fragments.TimePicker;

public class BreakCreator extends DialogFragment implements AdapterView.OnItemSelectedListener, TimePicker.onClockTimeSetListener{

    public interface OnBreakReady{
        public Break onBreakReadyListener();
    }

    private String day;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private Context context= getActivity();

    public static BreakCreator newInstance(int title) {
        BreakCreator frag = new BreakCreator();
        Bundle args = new Bundle();
        args.putInt("Break", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SetContentView(R.layout.activity_break_creator);

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.list_day_of_week, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public static BreakCreator newInstance(String title) {
        BreakCreator frag = new BreakCreator();
        Bundle args = new Bundle();
        args.putString("Break Creator", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_break_creator, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView startTime = (TextView) view.findViewById(R.id.start_time);
        TextView endTime = (TextView) view.findViewById(R.id.end_time);

        Button pickTime1 = (Button) view.findViewById(R.id.button1);

        pickTime1.setOnClickListener(v -> {

            TimePicker mTimePicker = new TimePicker();
            Bundle bundle = new Bundle();
            bundle.putInt("id", 1);
            mTimePicker.setArguments(bundle);
            mTimePicker.setTargetFragment(BreakCreator.this, 0);
            mTimePicker.show(getFragmentManager(), "Start time");



        });

        Button pickTime2 = (Button) view.findViewById(R.id.button2);

        pickTime2.setOnClickListener(v -> {

            DialogFragment mTimePicker = new TimePicker();
            Bundle bundle = new Bundle();
            bundle.putInt("id", 2);
            mTimePicker.setArguments(bundle);
            mTimePicker.setTargetFragment(BreakCreator.this, 0);
            mTimePicker.show(getFragmentManager(), "End time");


        });


        Spinner listDay =(Spinner) view.findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.list_day_of_week, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        listDay.setAdapter(adapter);

        Button save = (Button) view.findViewById(R.id.save_button);
        Button cancel = (Button) view.findViewById(R.id.cancel_button);

        cancel.setOnClickListener( v -> {
            //Nothing happens
            dismiss();
        });
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onTimeSetDialog(String inputText) {
        String[] temp = inputText.split(":");
        if(Integer.parseInt(temp[0])==1){
            startHour = Integer.parseInt(temp[1]);
            startMinute = Integer.parseInt(temp[2]);
        } else if(Integer.parseInt(temp[0])==2){
            endHour = Integer.parseInt(temp[1]);
            endMinute = Integer.parseInt(temp[2]);
        }else{
            startMinute = -1;
            startHour = -1;
            endMinute = -1;
            endHour = -1;
        }

    }

    public void setStartTimeView(){}

}
