package ultramirinc.champs_mood.models;

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
    private String id;
    private String recipientId;
    private String senderId;
    private boolean isFriend;

    public Notification() {}

    public Notification(String sentBy, int type, boolean isFriend, String senderId, String recipientId) {

        this.sentBy = sentBy;
        this.type = type;
        this.isFriend = isFriend;
        this.recipientId = recipientId;
        this.senderId = senderId;

        if(this.type == Notification_type.followed_you.getNumVal()) {
            message = "Followed you!";
        }
        else if(this.type == Notification_type.poked_you.getNumVal()){
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
       return this.senderId;
    }
    public void setSenderId(String senderId){
         this.senderId = senderId;
    }
    public String getRecipientId(){
        return this.recipientId;
    }
    public void setRecipientId(String recipientId){
        this.recipientId = recipientId;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getFriendStatus(){
        if(isFriend){
            return "Poke !";
        }
        else{
            return "Follow";
        }
    }

}
