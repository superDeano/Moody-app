package ultramirinc.champs_mood.fragments;

/**
 * Created by William on 2017-04-06.
 */

public class Notification {

    private String sentBy;
    private int type;
    private static final int FRIEND_REQUEST = 1;
    private static final int NOW_FRIEND = 2;
    private static final int POKED_YOU = 3;
    private String message;
    private String senderId;
    private boolean isFriend;

    public Notification(String sentBy, int type, boolean isFriend) {

        this.sentBy = sentBy;
        this.type = type;
        this.isFriend = isFriend;

        if(this.type == 1){
            message = "Friend request !";
        }
        else if(this.type == 2){
            message = "Now your friend !";
        }
        else if(this.type == 3){
            message = "Poked you !";
        }
        else{
            message = "Hello";
        }

    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static int getFriendRequest() {
        return FRIEND_REQUEST;
    }

    public static int getNowFriend() {
        return NOW_FRIEND;
    }

    public static int getPokedYou() {
        return POKED_YOU;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public String getSenderId(){
       return senderId;
    }

    public String getFriendStatus(){
        if(isFriend){
            return "Poke !";
        }
        else{
            return "Add";
        }
    }

}
