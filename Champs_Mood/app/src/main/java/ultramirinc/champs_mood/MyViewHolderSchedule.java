package ultramirinc.champs_mood;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ultramirinc.champs_mood.models.Break;


/**
 * Created by William on 2017-04-13.
 * Transforms raw Data into a visual interpretation for the user
 */

public class MyViewHolderSchedule extends RecyclerView.ViewHolder{
    /**Contains the Button of the break's delete button*/
    private Button delete;
    /**Contains the TextView of the break's day*/
    private TextView dayView;
    /**Contains the TextView of the break's begin time*/
    private TextView fromTimeView;
    /**Contains the TextView of the break's end time*/
    private TextView toTimeView;

    public MyViewHolderSchedule(View itemView) {
        super(itemView);

        delete = (Button) itemView.findViewById(R.id.delete);
        dayView = (TextView) itemView.findViewById(R.id.day);
        fromTimeView = (TextView) itemView.findViewById(R.id.fromTime);
        toTimeView = (TextView) itemView.findViewById(R.id.toTime);
    }
    /**Binds the break's day, begin time and end time to their respective TextView variables.*/
    public void bind(Break myBreak){
        dayView.setText(myBreak.getDay());
        fromTimeView.setText(myBreak.getFromTime());
        toTimeView.setText(myBreak.getToTime());
    }
    /**Getter for delete.*/
    public Button getDelete() {
        return delete;
    }
    /**Setter for delete.*/
    public void setDelete(Button delete) {
        this.delete = delete;
    }
    /**Getter for dayView.*/
    public TextView getDayView() {
        return dayView;
    }
    /**Setter for dayView.*/
    public void setDayView(TextView dayView) {
        this.dayView = dayView;
    }
    /**Getter for fromTimeView.*/
    public TextView getFromTimeView() {
        return fromTimeView;
    }
    /**Setter for fromTimeView.*/
    public void setFromTimeView(TextView fromTimeView) {
        this.fromTimeView = fromTimeView;
    }
    /**Getter for toTimeView.*/
    public TextView getToTimeView() {
        return toTimeView;
    }
    /**Setter for toTimeView.*/
    public void setToTimeView(TextView toTimeView) {
        this.toTimeView = toTimeView;
    }
}
