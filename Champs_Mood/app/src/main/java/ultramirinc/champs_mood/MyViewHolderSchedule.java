package ultramirinc.champs_mood;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by William on 2017-04-13.
 */

public class MyViewHolderSchedule extends RecyclerView.ViewHolder{

    private TextView dayView;
    private TextView fromTimeView;
    private TextView toTimeView;

    public MyViewHolderSchedule(View itemView) {
        super(itemView);

        dayView = (TextView) itemView.findViewById(R.id.day);
        fromTimeView = (TextView) itemView.findViewById(R.id.fromTime);
        toTimeView = (TextView) itemView.findViewById(R.id.toTime);
    }

    public void bind(Break myBreak){
        dayView.setText(myBreak.getDay());
        fromTimeView.setText(myBreak.getFromTime());
        toTimeView.setText(myBreak.getToTime());
    }

}
