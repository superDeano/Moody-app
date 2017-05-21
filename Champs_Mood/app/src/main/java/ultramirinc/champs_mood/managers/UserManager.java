package ultramirinc.champs_mood.managers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.Observable;
import ultramirinc.champs_mood.models.User;

/**
 * Created by Amir Osman on 2017-04-25.
 */
public class UserManager extends Observable {
    private User current_user;
    private static UserManager instance = null;
    //Our database reference object
    DatabaseReference databaseUsers;

    protected UserManager() {
        // Exists only to defeat instantiation.
        getUserInformations();
    }

    public User getCurrentUser() {
        if (this.current_user == null) {
            getUserInformations();
        }
        return this.current_user;
    }

    public void setCurrentUser(User user) {
        this.current_user = user;
    }
    public static UserManager getInstance() {
        if(instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void editUserInformations(User userInformations) {
        this.databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        this.databaseUsers.child(userInformations.getId()).setValue(userInformations);
    }

    public void addUserInformations(User userInformations) {
        //simple validation of the object:
        this.databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        this.databaseUsers.child(userInformations.getId()).setValue(userInformations);
    }

    public void clearCurrentUser() {
        this.current_user = null;
    }

    public void getUserInformations() {
        this.databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        Query userQuery = this.databaseUsers.orderByChild("id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = null;
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    user = singleSnapshot.getValue(User.class);

                    current_user = user;
                }
                // Set the class as changed so the update method gets called!.
                setChanged();
                if (user != null) {
                    notifyObservers(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userQuery.addListenerForSingleValueEvent(postListener);
    }
}
