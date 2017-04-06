package ultramirinc.champs_mood.fragments;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ultramirinc.champs_mood.R;

/**
 * Created by William on 2017-04-04.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    List<Person> list;

    public MyAdapter(List<Person> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_friend,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Person myFriend = list.get(position);

        myViewHolder.bind(myFriend);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
