package ultramirinc.champs_mood.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ultramirinc.champs_mood.R;

/**
 * Created by William on 2017-04-06.
 */

public class MyViewHolderNotification extends RecyclerView.ViewHolder{

    private TextView sentByView;
    private TextView typeView;
    private TextView isFriendView;

    public MyViewHolderNotification(View itemView) {
        super(itemView);

        sentByView = (TextView) itemView.findViewById(R.id.name);
        typeView = (TextView) itemView.findViewById(R.id.message);
        isFriendView = (TextView) itemView.findViewById(R.id.button);
    }

    public void bind(Notification notification){
        sentByView.setText(notification.getSentBy());
        typeView.setText(notification.getMessage());
        isFriendView.setText(notification.getFriendStatus());
    }

}
