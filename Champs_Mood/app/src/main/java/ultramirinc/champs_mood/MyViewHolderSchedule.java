package ultramirinc.champs_mood;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by William on 2017-04-13.
 */

public class MyViewHolderSchedule extends RecyclerView.ViewHolder{

    private Button delete;
    private TextView dayView;
    private TextView fromTimeView;
    private TextView toTimeView;

    public MyViewHolderSchedule(View itemView) {
        super(itemView);

        delete = (Button) itemView.findViewById(R.id.delete);
        dayView = (TextView) itemView.findViewById(R.id.day);
        fromTimeView = (TextView) itemView.findViewById(R.id.fromTime);
        toTimeView = (TextView) itemView.findViewById(R.id.toTime);
    }

    public void bind(Break myBreak){
        dayView.setText(myBreak.getDay());
        fromTimeView.setText(myBreak.getFromTime());
        toTimeView.setText(myBreak.getToTime());
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }

    public TextView getDayView() {
        return dayView;
    }

    public void setDayView(TextView dayView) {
        this.dayView = dayView;
    }

    public TextView getFromTimeView() {
        return fromTimeView;
    }

    public void setFromTimeView(TextView fromTimeView) {
        this.fromTimeView = fromTimeView;
    }

    public TextView getToTimeView() {
        return toTimeView;
    }

    public void setToTimeView(TextView toTimeView) {
        this.toTimeView = toTimeView;
    }
}
