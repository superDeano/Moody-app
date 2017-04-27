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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ultramirinc.champs_mood.FriendProfilActivity;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.UserAccount;

/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class FriendsFragment extends Fragment{

    private RecyclerView recyclerView;
    private List<UserAccount> friends = new ArrayList<>();
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
        recyclerView.setAdapter(new MyAdapter(friends, getContext()));

        /*TextView textName = (TextView)view.findViewById(R.id.name);

        Iterator<UserAccount> it = friends.iterator();

        while(it.hasNext()){
            UserAccount p = it.next();
            p.
        }*/

        return view;

    }

    private void addFriends() {
        friends.add(new UserAccount("Owen Bross", "Hungry", "In Break", true));
        friends.add(new UserAccount("Gab Cote", "Lit", "Break in 15 minutes", true));
        friends.add(new UserAccount("Francois Kekesi", "Working", "In Break", true));
        friends.add(new UserAccount("Dany", "Programming", "Break in 1 hour", true));
        friends.add(new UserAccount("Alex", "Studying", "Break in 1.5 hour", true));
        friends.add(new UserAccount("Ming", "Chilling", "In Break", true));
    }

}
