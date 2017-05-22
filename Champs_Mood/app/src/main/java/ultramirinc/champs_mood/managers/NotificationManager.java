package ultramirinc.champs_mood.managers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import ultramirinc.champs_mood.models.Notification;

/**
 * Created by Amir Osman on 2017-05-02.
 * This class manages the user's notification. This acts as a bridge between the database and the device.
 */
public class NotificationManager {
    /**A reference to the current database used*/
    public DatabaseReference databaseNotifications;

    public NotificationManager(){

    }
    /**Saves the notification in the database*/
    public void saveNotification(Notification n) {
        this.databaseNotifications = FirebaseDatabase.getInstance().getReference("notifications");
        n.setId(this.databaseNotifications.push().getKey().toString());
        this.databaseNotifications.child(n.getId()).setValue(n);
    }
    /**Deletes the notification from the database*/
    public void deleteNotificaton(Notification n) {
        this.databaseNotifications = FirebaseDatabase.getInstance().getReference("notifications");
        this.databaseNotifications.child(n.getId()).setValue(n);
    }

}

