package ultramirinc.champs_mood.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView; //There

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.TabActivity;

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

    @Override//Should work
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        //Toolbar toolbar = (Toolbar) view.findViewById(R.id.search_bar);
        //toolbar.setTitle("");
        //toolbar.inflateMenu(R.menu.search_menu);

        //MenuItem item = toolbar.getMenu().findItem(R.id.search);


        /*
        android.support.v7.widget.SearchView sv = (android.support.v7.widget.SearchView) view.findViewById(R.id.sv);
        int color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        sv.setBackgroundColor(color);



        ((EditText)sv.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(Color.WHITE);
        ((EditText)sv.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(Color.WHITE);
        ((EditText)sv.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHint("Search");


        try {
            Field searchField = android.support.v7.widget.SearchView.class
                    .getDeclaredField("mSearchButton");
            searchField.setAccessible(true);
            ImageView searchBtn = (ImageView) searchField.get(sv);
            searchBtn.setImageResource(R.drawable.ic_action_search);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setPadding(0,0,0,0);



        //MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //getActivity().getActionBar().setDisplayShowHomeEnabled(false);
        */

        SearchView sv = (SearchView) view.findViewById(R.id.search_bar_2).findViewById(R.id.sv2);
        sv.setIconifiedByDefault(false);

        Toolbar parent =(Toolbar) view.findViewById(R.id.search_bar_2);
        parent.setPadding(0,0,0,0);//for tab otherwise give space in tab
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
