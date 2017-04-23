package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import ultramirinc.champs_mood.FriendProfilActivity;
import ultramirinc.champs_mood.R;

/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class FriendsFragment extends Fragment{

    private RecyclerView recyclerView;
    private List<Person> friends = new ArrayList<>();
    private Context context = getContext();


    public FriendsFragment(){
        addFriends();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyAdapter(friends));

        TextView textName = (TextView)view.findViewById(R.id.name);

        /*textName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), FriendProfilActivity.class);
                startActivity(intent);
            }
        });*/

        return view;

    }

    private void addFriends() {
        friends.add(new Person("Owen Bross", "Hungry", "In Break", true));
        friends.add(new Person("Gab Cote", "Lit", "Break in 15 minutes", true));
        friends.add(new Person("Francois Kekesi", "Working", "In Break", true));
        friends.add(new Person("Dany", "Programming", "Break in 1 hour", true));
        friends.add(new Person("Alex", "Studying", "Break in 1.5 hour", true));
        friends.add(new Person("Ming", "Chilling", "In Break", true));
    }

}
