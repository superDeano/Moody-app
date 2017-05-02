package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.models.User;


/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<User> people = new ArrayList<>();
    private Context context = getContext();
    DatabaseReference databaseUsers;


    public SearchFragment(){
        //addPeople();
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
                people.clear();
                SearchUsers(query);
                return true;
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

        recyclerView.setAdapter(new MyAdapterSearch(people, getContext()));

        /*
        SearchView editBreakText = (SearchView) view.findViewById(R.id.sv);
        editBreakText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        */

        return view;
    }
    /*
    private void addPeople() {
        people.add(new User("Owen Bross", "Hungry", "In Break", true));
        people.add(new User("Gab Cote", "Lit", "Break in 15 minutes", false));
        people.add(new User("Francois Kekesi", "Working", "In Break", true));
        people.add(new User("Dany", "Programming", "Break in 1 hour", true));
        people.add(new User("Alex", "Studying", "Break in 1.5 hour", false));
        people.add(new User("Ming", "Chilling", "In Break", false));
    }*/

    public void SearchUsers(String query) {
        this.databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        Query searchQuery = this.databaseUsers.orderByChild("name").startAt(query);

        ValueEventListener valuesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                people.clear();
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    people.add(singleSnapshot.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        searchQuery.addListenerForSingleValueEvent(valuesListener);
    }
}
