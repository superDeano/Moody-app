package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ultramirinc.champs_mood.R;

/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class FriendsFragment extends Fragment {

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



        /*
        for(MyObject m : cities){
            Log.d("debug", m.getName());
        }
        */




        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(new MyAdapter(friends));

        return view;
    }


    @Override
    public void onResume(){
        super.onResume();
        Handler h = new Handler();
        /*h.postDelayed(new Runnable() {
            @Override
            public void run() {



                h.postDelayed(this, time)
            }
        }, 5 min);
        */

        h.removeCallbacksAndMessages(null);
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

