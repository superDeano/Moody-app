package ultramirinc.champs_mood.managers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Observable;

import ultramirinc.champs_mood.models.Notification;
import ultramirinc.champs_mood.models.User;

/**
 * Created by Bata on 2017-05-02.
 */
public class NotificationManager {

    DatabaseReference databaseNotifications;

    public void saveNotification(Notification n) {
        this.databaseNotifications = FirebaseDatabase.getInstance().getReference("notifications");
        n.setId(this.databaseNotifications.push().getKey().toString());
        this.databaseNotifications.child(n.getId()).setValue(n);
    }

    public void deleteNotificaton(Notification n) {
        this.databaseNotifications = FirebaseDatabase.getInstance().getReference("notifications");
        this.databaseNotifications.child(n.getId()).setValue(n);
    }

}

