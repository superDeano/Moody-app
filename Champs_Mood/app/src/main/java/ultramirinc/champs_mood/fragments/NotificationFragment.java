package ultramirinc.champs_mood.fragments;
//Done
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import ultramirinc.champs_mood.R;
import ultramirinc.champs_mood.models.Notification;

/**
 * Created by Etienne Berube on 2017-03-23.
 * This class is a tab that contains all of the notifications for a given User. It is a tab under TabActivity.
 */

public class NotificationFragment extends Fragment {
    /**Contains the RecyclerView of the layout*/
    private RecyclerView recyclerView;
    /**Manages the LinearLayout of layout*/
    private LinearLayoutManager mLayoutManager;
    /**Contains a list of all the user's notifications*/
    private List<Notification> nofications = new ArrayList<>();
    /**Context from attached activity*/
    private Context context = getContext();

    public NotificationFragment(){

    }
    /**Inflates the visual aspect of the Fragment.*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_notification, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(new MyAdapterNotification(nofications, getContext()));
        addNotifications();
        return view;
    }
    /**Adds a notification to the list.*/
    private void addNotifications() {
        LoadNotifications();
    }

    /**Gets notification from the Database.*/
    private void LoadNotifications() {
        DatabaseReference breaksReference = FirebaseDatabase.getInstance().getReference("notifications");
        Query breakQuery = breaksReference.orderByChild("recipientId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nofications.clear();
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    nofications.add(singleSnapshot.getValue(Notification.class));
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Not Handled
            }
        };
        breakQuery.addValueEventListener(postListener);
    }

}
