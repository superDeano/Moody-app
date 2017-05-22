package ultramirinc.champs_mood.fragments;
//Done
import android.app.ProgressDialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.User;


/**
 * Created by Etienne Berube on 2017-03-23.
 * This class allows the user to search for friends or potential friends on the database. It is a tab for TabActivity.
 */

public class SearchFragment extends Fragment {
    /**Contains the layout's RecyclerView*/
    private RecyclerView recyclerView;
    /**Contains a list with the people found by the search query*/
    private List<User> people = new ArrayList<>();
    /**Contains the context of the attached activity*/
    private Context context = getContext();
    /**Contains a reference to the database used*/
    DatabaseReference databaseUsers;
    /**Contains a progress dialog*/
    ProgressDialog progressDialog;

    public SearchFragment() {
    }
    /**Sets certain parameters when the fragment is created.*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }
    /**Inflates the visual layout to the fragment.*/
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
            public boolean onQueryTextSubmit(String query) {//TODO remove clikc of search view twice

                Log.d("search debug", "worked i guess");
                query = query.toLowerCase();


                recyclerView.getAdapter().notifyItemRangeRemoved(0,people.size());

                people.clear();

                searchUsers(query);

                sv.clearFocus();
                recyclerView.getAdapter().notifyItemRangeInserted(0, people.size());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Toolbar parent = (Toolbar) view.findViewById(R.id.search_bar);
        parent.setPadding(0, 0, 0, 0);
        parent.setContentInsetsAbsolute(0, 0);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(new MyAdapterSearch(people, getContext()));

        return view;

    }

    /**Searches for users with a given user name in the database*/
    public void searchUsers(String query) {

        this.databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        Query searchQuery = this.databaseUsers.orderByChild("nameLowered").startAt(query).endAt(query+"\uf8ff");

        progressDialog.setMessage("Searching...");
        progressDialog.show();
        ValueEventListener valuesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                recyclerView.getAdapter().notifyItemRangeRemoved(0,people.size());
                people.clear();

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    User tempUser = singleSnapshot.getValue(User.class);

                    if(!(UserManager.getInstance().getCurrentUser().getId().equals(tempUser.getId())))
                    people.add(tempUser);

                }
                recyclerView.getAdapter().notifyItemRangeInserted(0, people.size());

                progressDialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        searchQuery.addListenerForSingleValueEvent(valuesListener);
    }
}
