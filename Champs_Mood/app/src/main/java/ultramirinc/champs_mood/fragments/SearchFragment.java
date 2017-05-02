package ultramirinc.champs_mood.fragments;

import android.app.ProgressDialog;
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
import android.view.inputmethod.InputMethodManager;
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
    ProgressDialog progressDialog;
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

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(new MyAdapterSearch(people, getContext()));

        SearchView sv = (SearchView) view.findViewById(R.id.search_bar).findViewById(R.id.sv);
        sv.setIconifiedByDefault(false);
        progressDialog = new ProgressDialog(getActivity());
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                Log.d("search debug", "worked i guess");
                people.clear();
                SearchUsers(query);
                recyclerView.getAdapter().notifyDataSetChanged();

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

        return view;

    }


    public void SearchUsers(String query) { //TODO non case sensitive

        this.databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        Query searchQuery = this.databaseUsers.orderByChild("name").startAt(query);

        progressDialog.setMessage("Searching...");
        progressDialog.show();
        ValueEventListener valuesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                people.clear();
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    people.add(singleSnapshot.getValue(User.class));
                }
                progressDialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        searchQuery.addListenerForSingleValueEvent(valuesListener);
    }
}
