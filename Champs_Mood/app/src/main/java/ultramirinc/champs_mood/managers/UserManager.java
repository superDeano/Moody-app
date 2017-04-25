package ultramirinc.champs_mood.managers;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;
import java.util.Observer;

import ultramirinc.champs_mood.fragments.ProfilFragment;
import ultramirinc.champs_mood.models.User;

/**
 * Created by Bata on 2017-04-25.
 */

public class UserManager extends Observable {
    //Our database reference object
    DatabaseReference databaseUsers;

    public void editUserInformations(User userInformations) {
        this.databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        this.databaseUsers.child(userInformations.getUserId()).setValue(userInformations);
    }

    public void addUserInformations(User userInformations) {
        //simple validation of the object:
        this.databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        this.databaseUsers.child(userInformations.getUserId()).setValue(userInformations);
    }

    public void getUserInformations() {
        this.databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        Query userQuery = this.databaseUsers.orderByChild("userId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = null;
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    user = singleSnapshot.getValue(User.class);
                }
                // Set the class as changed so the update method gets called!.
                setChanged();
                if (user == null) {
                    notifyObservers("newUser");
                }
                else
                    notifyObservers(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        userQuery.addListenerForSingleValueEvent(postListener);
    }
}
