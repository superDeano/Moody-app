package ultramirinc.champs_mood.fragments;
//DONE
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.User;

/**
 * Created by William on 2017-04-06.
 * This class does the transition between the raw data and a visual interpretation for the results of a searched person
 */

public class MyViewHolderSearch extends RecyclerView.ViewHolder{
    /**Contains the String of the friend status of the person that is searched*/
    private String isFriend;
    /**Contains the TextView of the name of the person that is searched*/
    private TextView nameView;
    /**Contains the TextView of the mood of the person that is searched*/
    private TextView moodView;
    /**Contains the TextView of the break text of the person that is searched*/
    private TextView breakTextView;
    /**Contains the Button of the person that is searched*/
    private Button button;

    public MyViewHolderSearch(View itemView) {
        super(itemView);
        button = (Button) itemView.findViewById(R.id.poke);
        nameView = (TextView) itemView.findViewById(R.id.name);
        moodView = (TextView) itemView.findViewById(R.id.mood);
        breakTextView = (TextView) itemView.findViewById(R.id.breakText);
    }
    /**Binds the person's name, mood and friend status to their respective TextView variables.*/
    public void bind(User person){
        nameView.setText(person.getName());

        if(person.getMood() == null) {
            moodView.setText("No mood");
        }else if(person.getMood().equals("")){
            moodView.setText("No mood");
        }else {
            moodView.setText(person.getMood());
        }
        isFriend = (UserManager.getInstance().getCurrentUser().getFriendStatus(person));
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
    public void setBreakText(String text) {
        this.breakTextView.setText(text);
    }
    /**Getter for isFriendView.*/
    public String getIsFriend() {
        return isFriend;
    }
    /**Setter for isFriendView.*/
    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
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
