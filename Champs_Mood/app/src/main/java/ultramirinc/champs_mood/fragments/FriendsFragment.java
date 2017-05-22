package ultramirinc.champs_mood.fragments;
//DONE
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.User;

/**
 * Created by Etienne Berube on 2017-03-23.
 * This class gives a visual interpretation of the friend list of the user. The friends are fetched from the Database
 * and transformed into visual elements.
 * This list is given as a tab for TabActivity
 */

public class FriendsFragment extends Fragment{

    /**Contains the RecyclerView from the visual layout*/
    private RecyclerView recyclerView;
    /**Contains the user's friends*/
    private List<User> friends = new ArrayList<>();
    /**Context from the attached activity */
    private Context context = getContext();


    public FriendsFragment(){
    }
    /**Creates the visual layout for fragment */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        loadFriends();

        recyclerView.setAdapter(new MyAdapterFriend(friends, getContext()));

        return view;
    }
    /**Adds or replace a friend in the friendlist*/
    private void addOrReplace(User user) {
        boolean found = false;
        int index = 0;
        for (User u: friends) {
            if( u.getId().equals(user.getId())) {
                found = true;
                break;
            }
            index++;
        }
        if (found) {
            friends.remove(index);
            friends.add(index, user);
        }
        else {
            friends.add(user);
        }
    }
    /**Loads the user's friends from the database */
    private void loadFriends() {
        User currentUser = UserManager.getInstance().getCurrentUser();

        DatabaseReference usersTable = FirebaseDatabase.getInstance().getReference("users");
        usersTable.child(currentUser.getuId()).child("friendList").addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot snapshot) {
                friends.clear();
                for (DataSnapshot friendKey: snapshot.getChildren()) {
                    FirebaseDatabase.getInstance().getReference("users").child(friendKey.getValue(String.class)).addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot friendSnapshot) {
                            addOrReplace(friendSnapshot.getValue(User.class));
                            recyclerView.getAdapter().notifyDataSetChanged();
                        }
                        public void onCancelled(DatabaseError firebaseError) {
                            //Not handled
                        }
                    });
                }
            }
            public void onCancelled(DatabaseError firebaseError) {
                //Not Handled
            }
        });
    }


}
