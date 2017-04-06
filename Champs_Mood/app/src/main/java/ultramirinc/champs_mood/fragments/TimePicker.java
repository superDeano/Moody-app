package ultramirinc.champs_mood.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.Calendar;

import ultramirinc.champs_mood.R;

/**
 * Created by Étienne Bérubé on 2017-04-02.
 */

public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private int hour;
    private int minute;
    private int id;

    public interface onClockTimeSetListener {
        void onTimeSetDialog (String inputText);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            this.id = bundle.getInt("id", 0);
        }

        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;

        onClockTimeSetListener listener = (onClockTimeSetListener) getTargetFragment();
        listener.onTimeSetDialog(id+":"+hour+":"+minute);

        dismiss();

    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
