package ultramirinc.champs_mood.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.User;

/**
 * Created by William on 2017-04-06.
 */

public class MyViewHolderSearch extends RecyclerView.ViewHolder{
    private String isFriend;
    private TextView nameView;
    private TextView moodView;
    private TextView breakTextView;

    private Button button;

    public MyViewHolderSearch(View itemView) {
        super(itemView);
        button = (Button) itemView.findViewById(R.id.poke);
        nameView = (TextView) itemView.findViewById(R.id.name);
        moodView = (TextView) itemView.findViewById(R.id.mood);
        breakTextView = (TextView) itemView.findViewById(R.id.breakText);
    }

    public void bind(User person){
        nameView.setText(person.getName());

        if(person.getMood() == null) {
            moodView.setText("No mood");
        }else if(person.getMood().equals("")){
            moodView.setText("No mood");
        }else {
            moodView.setText(person.getMood());
        }

        breakTextView.setText(person.getBreakTextTemp());
        isFriend = (UserManager.getInstance().getCurrentUser().getFriendStatus(person));
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

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
