package ultramirinc.champs_mood.models;

/**
 * Created by Amir Osman on 2017-05-02.
 * This class helps manage the notifications
 */

public enum Notification_type {
    followed_you(1), poked_you(3);
    /**The actual integer value*/
    private int numVal;

    Notification_type(int numVal) {
        this.numVal = numVal;
    }
    /**Returns the code for the notification type.*/
    public int getNumVal() {
        return numVal;
    }
}
