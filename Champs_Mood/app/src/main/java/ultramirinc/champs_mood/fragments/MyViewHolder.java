package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ultramirinc.champs_mood.FriendProfilActivity;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.UserAccount;

/**
 * Created by William on 2017-04-04.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView nameView;
    private TextView moodView;
    private TextView breakTextView;

    public MyViewHolder(View itemView) {
        super(itemView);

        nameView = (TextView) itemView.findViewById(R.id.name);
        moodView = (TextView) itemView.findViewById(R.id.mood);
        breakTextView = (TextView) itemView.findViewById(R.id.breakText);
    }

    public void bind(UserAccount myFriend){
        nameView.setText(myFriend.getName());
        moodView.setText(myFriend.getMood());
        breakTextView.setText(myFriend.getBreakTextTemp());
    }

}
