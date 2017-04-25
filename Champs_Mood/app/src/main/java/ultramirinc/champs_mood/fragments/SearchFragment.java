package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.List;
import ultramirinc.champs_mood.R;

/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Person> people = new ArrayList<>();
    private Context context = this.getContext();

    public SearchFragment(){
        addPeople();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        SearchView sv = (SearchView) view.findViewById(R.id.search_bar).findViewById(R.id.sv);
        sv.setIconifiedByDefault(false);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("search debug", "worked i guess");
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("search debug", "worked i guess");
                return false;
            }
        });

        Toolbar parent =(Toolbar) view.findViewById(R.id.search_bar);
        parent.setPadding(0,0,0,0);
        parent.setContentInsetsAbsolute(0,0);

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
