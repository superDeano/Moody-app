package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ultramirinc.champs_mood.FriendProfilActivity;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.UserAccount;

/**
 * Created by William on 2017-04-06.
 */

public class MyAdapterSearch extends RecyclerView.Adapter<MyViewHolderSearch> {
    private Context context;
    private List<UserAccount> list;

    public MyAdapterSearch(List<UserAccount> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolderSearch onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_search,viewGroup,false);
        return new MyViewHolderSearch(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderSearch myViewHolderSearch, int position) {
        UserAccount person = list.get(position);

        myViewHolderSearch.getNameView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("listener Debug", "Coucou");
                Intent intent = new Intent(context, FriendProfilActivity.class);

                intent.putExtra("NAME", myViewHolderSearch.getNameView().getText().toString());
                //TODO pass id instead of name
                context.startActivity(intent);
            }
        });

        myViewHolderSearch.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO implement poke or add
            }
        });
        myViewHolderSearch.bind(person);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
