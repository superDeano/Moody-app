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
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.User;

/**
 * Created by William on 2017-04-06.
 */

public class MyAdapterSearch extends RecyclerView.Adapter<MyViewHolderSearch> {
    private Context context;
    private List<User> list;

    public MyAdapterSearch(List<User> list, Context context) {
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
        User person = list.get(position);

        //load profile
        myViewHolderSearch.getNameView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("listener Debug", "Coucou");
                Intent intent = new Intent(context, FriendProfilActivity.class);
                intent.putExtra("userId", person.getId());
                //TODO pass id instead of name
                context.startActivity(intent);
            }
        });
        //handle action link (add or poke)
        myViewHolderSearch.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean added = UserManager.getInstance().getCurrentUser().addToFriendList(person);
                if (added) {
                    // will update our friend list.
                    UserManager.getInstance().editUserInformations(UserManager.getInstance().getCurrentUser());
                }
            }
        });
        myViewHolderSearch.bind(person);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
