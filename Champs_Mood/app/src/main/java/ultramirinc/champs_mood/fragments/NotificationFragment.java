package ultramirinc.champs_mood.fragments;

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
import ultramirinc.champs_mood.models.Break;
import ultramirinc.champs_mood.models.Notification;

/**
 * Created by Étienne Bérubé on 2017-03-23.
 */

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Notification> nofications = new ArrayList<>();
    private Context context = getContext();

    public NotificationFragment(){
        addNotifications();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(new MyAdapterNotification(nofications, getContext()));

        return view;
    }

    private void addNotifications() {
       /* nofications.add(new Notification("Owen Bross", 1, true));
        nofications.add(new Notification("Gab Cote", 2, false));
        nofications.add(new Notification("Francois Kekesi", 3, true));
        nofications.add(new Notification("Dany", 2, true));
        nofications.add(new Notification("Alex", 1, false));
        nofications.add(new Notification("Ming", 3, false)); */
        LoadNotifications();
    }

    private void LoadNotifications() {
        DatabaseReference breaksReference = FirebaseDatabase.getInstance().getReference("notifications");
        Query breakQuery = breaksReference.orderByChild("recipientId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    nofications.add(singleSnapshot.getValue(Notification.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        breakQuery.addListenerForSingleValueEvent(postListener);
    }
}
