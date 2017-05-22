package ultramirinc.champs_mood.managers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import ultramirinc.champs_mood.models.Notification;

/**
 * Created by Amir Osman on 2017-05-02.
 */
public class NotificationManager {

    public DatabaseReference databaseNotifications;

    public NotificationManager(){

    }

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

