package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import ultramirinc.champs_mood.R;

import ultramirinc.champs_mood.R;

/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class FriendsFragment extends Fragment {

    private RecyclerView recyclerView;

    private List<MyFriend> friends = new ArrayList<>();

    private Context context = getContext();



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        addFriends();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(new MyAdapter(friends));

        return view;
    }


    private void addFriends() {
        friends.add(new MyFriend("Owen Bross", "Hungry", "In Break"));
        friends.add(new MyFriend("Gab Cote", "Lit", "Break in 15 minutes"));
        friends.add(new MyFriend("Francois Kekesi", "Working", "In Break"));
        friends.add(new MyFriend("Dany", "Programming", "Break in 1 hour"));
        friends.add(new MyFriend("Alex", "Studying", "Break in 1.5 hour"));
        friends.add(new MyFriend("Ming", "Chilling", "In Break"));
    }

}

