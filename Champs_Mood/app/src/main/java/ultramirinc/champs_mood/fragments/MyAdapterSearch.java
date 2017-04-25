package ultramirinc.champs_mood.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.UserAccount;

/**
 * Created by William on 2017-04-06.
 */

public class MyAdapterSearch extends RecyclerView.Adapter<MyViewHolderSearch> {

    List<UserAccount> list;

    public MyAdapterSearch(List<UserAccount> list) {
        this.list = list;
    }

    @Override
    public MyViewHolderSearch onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_search,viewGroup,false);
        return new MyViewHolderSearch(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderSearch myViewHolderSearch, int position) {
        UserAccount person = list.get(position);

        myViewHolderSearch.bind(person);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
