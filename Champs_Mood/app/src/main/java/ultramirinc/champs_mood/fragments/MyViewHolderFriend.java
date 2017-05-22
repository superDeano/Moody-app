package ultramirinc.champs_mood.fragments;
//Done
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.models.User;

/**
 * Created by William on 2017-04-04.
 * This class assigns a given value to a View object, allowing the user to see the information for a given friend
 */

public class MyViewHolderFriend extends RecyclerView.ViewHolder{
    /**Contains the TextView of the friend's name*/
    private TextView nameView;
    /**Contains the TextView of the friend's mood*/
    private TextView moodView;
    /**Contains the TextView of the friend's break text*/
    private TextView breakTextView;
    /**Contains the Button of the friend's poke button*/
    private Button mButton;

    public MyViewHolderFriend(View itemView) {
        super(itemView);
        mButton = (Button) itemView.findViewById(R.id.poke);
        nameView = (TextView) itemView.findViewById(R.id.name);
        moodView = (TextView) itemView.findViewById(R.id.mood);
        breakTextView = (TextView) itemView.findViewById(R.id.breakText);
        breakTextView.setText("");

    }

    /**Binds the friend's name and mood to their respective TextView variables.*/
    public void bind(User myFriend){
        nameView.setText(myFriend.getName());

        if(myFriend.getMood() == null) {
            moodView.setText("No mood");
        }else if (myFriend.getMood().equals("")){
            moodView.setText("No Mood");
        }else {
            moodView.setText(myFriend.getMood());
        }

    }
    /**Getter for nameView.*/
    public TextView getNameView() {
        return nameView;
    }
    /**Setter for nameView.*/
    public void setNameView(TextView nameView) {
        this.nameView = nameView;
    }
    /**Getter for moodView.*/
    public TextView getMoodView() {
        return moodView;
    }
    /**Setter for moodView.*/
    public void setMoodView(TextView moodView) {
        this.moodView = moodView;
    }
    /**Getter for breakTextView.*/
    public TextView getBreakTextView() {
        return breakTextView;
    }
    /**Setter for breakTextView using a TextView variable.*/
    public void setBreakTextView(TextView breakTextView) {
        this.breakTextView = breakTextView;
    }
    /**Setter for breakTextView using a String variable.*/
    public void setBreakTextText(String s){
        breakTextView.setText(s);
    }
    /**Getter for mButton.*/
    public Button getButton() {
        return mButton;
    }
    /**Setter for mButton.*/
    public void setButton(Button mButton) {
        this.mButton = mButton;
    }
}
