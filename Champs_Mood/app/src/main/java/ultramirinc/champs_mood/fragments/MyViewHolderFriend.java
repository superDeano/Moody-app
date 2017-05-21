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

    private TextView nameView;
    private TextView moodView;
    private TextView breakTextView;
    private Button mButton;

    public MyViewHolderFriend(View itemView) {
        super(itemView);
        mButton = (Button) itemView.findViewById(R.id.poke);
        nameView = (TextView) itemView.findViewById(R.id.name);
        moodView = (TextView) itemView.findViewById(R.id.mood);
        breakTextView = (TextView) itemView.findViewById(R.id.breakText);
        breakTextView.setText("");

    }


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

    public TextView getNameView() {
        return nameView;
    }

    public void setNameView(TextView nameView) {
        this.nameView = nameView;
    }

    public TextView getMoodView() {
        return moodView;
    }

    public void setMoodView(TextView moodView) {
        this.moodView = moodView;
    }

    public TextView getBreakTextView() {
        return breakTextView;
    }

    public void setBreakTextView(TextView breakTextView) {
        this.breakTextView = breakTextView;
    }

    public void setBreakTextText(String s){
        breakTextView.setText(s);
    }

    public Button getButton() {
        return mButton;
    }

    public void setButton(Button mButton) {
        this.mButton = mButton;
    }
}
