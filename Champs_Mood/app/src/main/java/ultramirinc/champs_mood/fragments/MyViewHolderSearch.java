package ultramirinc.champs_mood.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.UserAccount;

/**
 * Created by William on 2017-04-06.
 */

public class MyViewHolderSearch extends RecyclerView.ViewHolder{

    private TextView nameView;
    private TextView moodView;
    private TextView breakTextView;
    private TextView isFriendView;

    public MyViewHolderSearch(View itemView) {
        super(itemView);

        nameView = (TextView) itemView.findViewById(R.id.name);
        moodView = (TextView) itemView.findViewById(R.id.mood);
        breakTextView = (TextView) itemView.findViewById(R.id.breakText);
        isFriendView = (TextView) itemView.findViewById(R.id.poke);
    }

    public void bind(UserAccount person){
        nameView.setText(person.getName());
        moodView.setText(person.getMood());
        breakTextView.setText(person.getBreakTextTemp());
        isFriendView.setText(person.getFriendStatusTemp());
    }

}
