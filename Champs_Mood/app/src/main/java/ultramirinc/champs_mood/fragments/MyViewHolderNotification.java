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
    /**Contains the Button of the person that sent the notification*/
    private Button button;
    /**Contains the TextView of the name of the person that sent the notification*/
    private TextView sentByView;
    /**Contains the TextView of the notification's type*/
    private TextView typeView;
    /**Contains the TextView of the friend status of the person that sent the notification*/
    private TextView isFriendView;

    public MyViewHolderNotification(View itemView) {
        super(itemView);
        button = (Button) itemView.findViewById(R.id.button);
        sentByView = (TextView) itemView.findViewById(R.id.name);
        typeView = (TextView) itemView.findViewById(R.id.message);
        isFriendView = (TextView) itemView.findViewById(R.id.button);
    }
    /**Binds the notifications's person name, type and person friend status to their respective TextView variables.*/
    public void bind(Notification notification){
        sentByView.setText(notification.getSentBy());
        typeView.setText(notification.getMessage());
        isFriendView.setText(notification.getFriendStatus());
    }
    /**Getter for sentByView.*/
    public TextView getSentByView() {
        return sentByView;
    }
    /**Setter for sentByView.*/
    public void setSentByView(TextView sentByView) {
        this.sentByView = sentByView;
    }
    /**Getter for typeView.*/
    public TextView getTypeView() {
        return typeView;
    }
    /**Setter for typeView.*/
    public void setTypeView(TextView typeView) {
        this.typeView = typeView;
    }
    /**Getter for isFriendView.*/
    public TextView getIsFriendView() {
        return isFriendView;
    }
    /**Setter for isFriendView.*/
    public void setIsFriendView(TextView isFriendView) {
        this.isFriendView = isFriendView;
    }
    /**Getter for button.*/
    public Button getButton() {
        return button;
    }
    /**Setter for button.*/
    public void setButton(Button button) {
        this.button = button;
    }
}
