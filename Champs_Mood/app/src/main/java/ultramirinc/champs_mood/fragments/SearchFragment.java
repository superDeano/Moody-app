package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ultramirinc.champs_mood.R;

/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Person> people = new ArrayList<>();
    private Context context = getContext();

    public SearchFragment(){
        addPeople();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(new MyAdapterSearch(people));

        return view;
    }

    private void addPeople() {
        people.add(new Person("Owen Bross", "Hungry", "In Break", true));
        people.add(new Person("Gab Cote", "Lit", "Break in 15 minutes", false));
        people.add(new Person("Francois Kekesi", "Working", "In Break", true));
        people.add(new Person("Dany", "Programming", "Break in 1 hour", true));
        people.add(new Person("Alex", "Studying", "Break in 1.5 hour", false));
        people.add(new Person("Ming", "Chilling", "In Break", false));
    }

}
