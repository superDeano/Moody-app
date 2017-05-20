package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ultramirinc.champs_mood.FriendProfilActivity;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.models.User;

/**
 * Created by William on 2017-04-04.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView nameView;
    private TextView moodView;
    private TextView breakTextView;
    private Button mButton;

    public MyViewHolder(View itemView) {
        super(itemView);
        mButton = (Button) itemView.findViewById(R.id.poke);
        nameView = (TextView) itemView.findViewById(R.id.name);
        moodView = (TextView) itemView.findViewById(R.id.mood);
        breakTextView = (TextView) itemView.findViewById(R.id.breakText);

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

        //breakTextView.setText(myFriend.getBreakTextTemp());
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
