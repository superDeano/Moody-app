package ultramirinc.champs_mood.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ultramirinc.champs_mood.R;

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
        nofications.add(new Notification("Owen Bross", 1, true));
        nofications.add(new Notification("Gab Cote", 2, false));
        nofications.add(new Notification("Francois Kekesi", 3, true));
        nofications.add(new Notification("Dany", 2, true));
        nofications.add(new Notification("Alex", 1, false));
        nofications.add(new Notification("Ming", 3, false));
    }

}
