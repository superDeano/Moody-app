package ultramirinc.champs_mood.models;

/**
 * Created by William on 2017-04-06.
 * This object contains all of the information or methods for a Notification
 */

public class Notification {

    /**The sending user*/
    private String sentBy;
    /**The type of the notification*/
    private int type;
    /**The code for a friend request*/
    private static final int FRIEND_REQUEST = 1;
    /**The code for a friend acceptation*/
    private static final int NOW_FRIEND = 2;
    /**The code for a poke*/
    private static final int POKED_YOU = 3;
    /**The message of the notification*/
    private String message;
    /**The Id of the notification*/
    private String id;
    /**The Id of the recipient*/
    private String recipientId;
    /**The Id of the sender*/
    private String senderId;
    /**Whether or not one user is friend with the other*/
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
    /**Getter for the sender's name.*/
    public String getSentBy() {
        return sentBy;
    }
    /**Setter for the sender's name.*/
    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
    /**Getter for the type of the notification.*/
    public int getType() {
        return type;
    }
    /**Setter for the type of the notification.*/
    public void setType(int type) {
        this.type = type;
    }
    /**Getter for the message of the notification.*/
    public String getMessage() {
        return message;
    }
    /**Setter for the message of the notification.*/
    public void setMessage(String message) {
        this.message = message;
    }
    /**Getter for whether or not they are friends.*/
    public boolean isFriend() {
        return isFriend;
    }
    /**Setter for whether or not they are friends.*/
    public void setFriend(boolean friend) {
        isFriend = friend;
    }
    /**Getter for the sender Id.*/
    public String getSenderId(){
       return this.senderId;
    }
    /**Setter for the sender Id.*/
    public void setSenderId(String senderId){
         this.senderId = senderId;
    }
    /**Getter for the recipient Id.*/
    public String getRecipientId(){
        return this.recipientId;
    }
    /**Setter for the recipient Id.*/
    public void setRecipientId(String recipientId){
        this.recipientId = recipientId;
    }
    /**Setter for the notification Id.*/
    public void setId(String id) {
        this.id = id;
    }
    /**Getter for the notification Id.*/
    public String getId() {
        return this.id;
    }
    /**Returns whether or not two user's are friends.*/
    public String getFriendStatus(){
        if(isFriend){
            return "Poke !";
        }
        else{
            return "Follow";
        }
    }

}
