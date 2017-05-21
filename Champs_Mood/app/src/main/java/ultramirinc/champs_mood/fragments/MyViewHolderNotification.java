package ultramirinc.champs_mood.fragments;
//Done
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.models.Notification;

/**
 * Created by William on 2017-04-06.
 * This class makes the transition between the raw data and a visual interpretation for the user's notifications.
 */

public class MyViewHolderNotification extends RecyclerView.ViewHolder{
    private Button button;
    private TextView sentByView;
    private TextView typeView;
    private TextView isFriendView;

    public MyViewHolderNotification(View itemView) {
        super(itemView);
        button = (Button) itemView.findViewById(R.id.button);
        sentByView = (TextView) itemView.findViewById(R.id.name);
        typeView = (TextView) itemView.findViewById(R.id.message);
        isFriendView = (TextView) itemView.findViewById(R.id.button);
    }

    public void bind(Notification notification){
        sentByView.setText(notification.getSentBy());
        typeView.setText(notification.getMessage());
        isFriendView.setText(notification.getFriendStatus());
    }

    public TextView getSentByView() {
        return sentByView;
    }

    public void setSentByView(TextView sentByView) {
        this.sentByView = sentByView;
    }

    public TextView getTypeView() {
        return typeView;
    }

    public void setTypeView(TextView typeView) {
        this.typeView = typeView;
    }

    public TextView getIsFriendView() {
        return isFriendView;
    }

    public void setIsFriendView(TextView isFriendView) {
        this.isFriendView = isFriendView;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
