package ultramirinc.champs_mood.models;

/**
 * Created by Bata on 2017-05-02.
 */

public enum Notification_type {
    followed_you(1), poked_you(3);

    private int numVal;

    Notification_type(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
