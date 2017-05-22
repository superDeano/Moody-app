package ultramirinc.champs_mood.models;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;


/**
 * Created by Amir Osman on 2017-04-23.
 * This is the template for a typical user.
 */
@IgnoreExtraProperties
public class User {
    /**Code for if the user is in break*/
    public static final int IN_BREAK = 0;
    /**Code for if the user is not in break*/
    public static final int NOT_IN_BREAK = 1;
    /**The user's Id*/
    private String uId;
    /**The user's name*/
    private String name;
    /**The user's name lowered*/
    private String nameLowered;
    /**The mood of the user*/
    private String mood;
    /**If the user's location is shared*/
    private boolean isLocationShared;
    /**The break text of the user*/
    private String breakText;
    /**Whether he is a friend or not with another user*/
    private boolean isFriend;
    /**A list of his friends*/
    private ArrayList<String> friendList = new ArrayList<>();
    /**A list of his breaks*/
    private ArrayList<Break> breaks = new ArrayList<>();
    /**The user's location*/
    private MyLocation lastLocation = new MyLocation();
    /**The floor of the user*/
    private int floor;

    public User() {
    }

    public User(String userId, String name) {
        this.uId = userId;
        this.name = name;
        this.nameLowered = name.toLowerCase();
        this.isLocationShared = true;
        this.floor = 1;
    }

    /**Getter for the user's id.*/
    public String getId() {
        return uId;
    }
    /**Setter's for the user's id.*/
    public void setId(String id) {
        this.uId = id;
    }
    /**Getter for the user's name.*/
    public String getName() {
        return name;
    }
    /**Setter for the user's name.*/
    public void setName(String name) {
        this.name = name;
        this.nameLowered = name.toLowerCase();
    }
    /**Getter for the mood.*/
    public String getMood() {
        return mood;
    }
    /**Setter for the mood.*/
    public void setMood(String mood) {
        this.mood = mood;
    }
    /**Adds a user to the friendList.*/
    public boolean addToFriendList(User user){
        if (!isFriend(user))
        {
            friendList.add(user.getId());
            return true;
        }
        return false;
    }
    /**Adds a user to the friendList.*/
    public boolean addToFriendList(String userId){
        if (!isFriend(userId))
        {
            friendList.add(userId);
            return true;
        }
        return false;
    }
    /**Returns the friendList of the user.*/
    public ArrayList<String> getFriendList() {
        return friendList;
    }
    /**Getter for the break status.*/
    public String getBreakStatus() {
        GregorianCalendar cal = new GregorianCalendar();
        int currentDay = cal.get(Calendar.DAY_OF_WEEK);
        String today;

        switch (currentDay) {
            case 2:
                today = "Monday";
                break;
            case 3:
                today = "Tuesday";
                break;
            case 4:
                today = "Wednesday";
                break;
            case 5:
                today = "Thursday";
                break;
            case 6:
                today = "Friday";
                break;
            default:
                today = "else";
        }


        return "";
    }
    /**Returns whether or not the user is friend with another user.*/
    public boolean isFriend(User user){

        Iterator<String> it = friendList.iterator();

        while(it.hasNext()){
            String friendId = it.next();

            if(friendId.equals(user.getId())){
                return true;
            }
        }
        return false;
    }
    /**Returns whether or not the user is friend with another user.*/
    public boolean isFriend(String userId){

        Iterator<String> it = friendList.iterator();

        while(it.hasNext()){
            String friendId = it.next();

            if(friendId.equals(userId)){
                return true;
            }
        }
        return false;
    }
    /**Gets the friend status.*/
    public String getFriendStatus(User user){
        if(isFriend(user)){
            return "Poke !";
        }
        else{
            return "Add";
        }
    }
    /**Setter for the breakText.*/
    public void setBreakText(String breakText){
        this.breakText = breakText;
    }
    /**Getter for the breakText.*/
    public String getBreakText() {
        ArrayList<Break> temp = new ArrayList<>();

        Collections.sort(breaks);
        Iterator<Break> iterator = this.breaks.iterator();

        GregorianCalendar mCalendar = new GregorianCalendar();
        int dayInt = mCalendar.get(Calendar.DAY_OF_WEEK);
        int currentHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = mCalendar.get(Calendar.MINUTE);

        String dayName = "";

        switch (dayInt){
            case (1): dayName = "Monday"; break;
            case (2): dayName = "Tuesday"; break;
            case (3): dayName = "Wednesday"; break;
            case (4): dayName = "Thursday"; break;
            case (5): dayName = "Friday"; break;
            default: dayName = "Other";break;
        }

        //find subset of Elements with same day
        while(iterator.hasNext()){
            Break tempBreak = iterator.next();
            if(tempBreak.getDay().equals(dayName)){
                temp.add(tempBreak);
            }
        }

        for(int i = 0; i < temp.size(); i++){
            Break tempBreak = temp.get(i);

            if(currentHour < tempBreak.getStart().getHour()){
                breakText = "Break in" + tempBreak.getTimeDifference() + " minutes";
                break;
            }
            if (currentHour == tempBreak.getStart().getHour()){
                if(currentMinute < tempBreak.getStart().getMinute()){
                    breakText = "Break in" + tempBreak.getTimeDifference() + " minutes";
                    break;
                }else{
                    breakText = "In break";
                    break;
                }
            }
            if(currentHour >= tempBreak.getStart().getHour() && currentHour <= tempBreak.getEnd().getHour()){
                breakText = "In break";
                break;
            }

            if(currentHour == tempBreak.getEnd().getHour()){
                if(currentMinute < tempBreak.getEnd().getMinute()){
                    breakText = "In break";
                    break;
                }
            }
            if(i == (temp.size() - 1) && (((currentHour == tempBreak.getEnd().getHour())
                    && (currentMinute > tempBreak.getEnd().getMinute())) || (currentHour > tempBreak.getEnd().getHour()))){
                breakText = "No more breaks today";
                break;
            }
        }

        return breakText;
    }
    /**Gets the user's breaks.*/
    public ArrayList<Break> getBreaks() {
        return breaks;
    }
    /**Sets the user's breaks.*/
    public void setBreaks(ArrayList<Break> breaks) {
        this.breaks = breaks;
    }
    /**Gets the user Id.*/
    public String getuId() {
        return uId;
    }
    /**Sets the user Id.*/
    public void setuId(String uId) {
        this.uId = uId;
    }
    /**Getter for isLocationShared.*/
    public boolean isLocationShared() {
        return isLocationShared;
    }
    /**Setter for isLocationShared.*/
    public void setLocationShared(boolean locationShared) {
        isLocationShared = locationShared;
    }
    /**Getter for isFriend.*/
    public boolean isFriend() {
        return isFriend;
    }
    /**Setter for isFriend.*/
    public void setFriend(boolean friend) {
        isFriend = friend;
    }
    /**Sets the user's friendList.*/
    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }
    /**Getter for the user's location.*/
    public MyLocation getLastLocation() {
        return lastLocation;
    }
    /**Setter for the user's location.*/
    public void setLastLocation(MyLocation lastLocation) {
        this.lastLocation = lastLocation;
    }
    /**Getter for the user's floor.*/
    public int getFloor() {
        return floor;
    }
    /**Setter for the user's floor.*/
    public void setFloor(int floor) {
        this.floor = floor;
    }
    /**Removes a user from the user's friendList*/
    public boolean removeFromFriendList(User user){
        friendList.remove(user.getId());
        return true;
    }
    /**Gets the user's name lowered*/
    public String getNameLowered() {
        return nameLowered;
    }
    /**Sets the user's name lowered*/
    public void setNameLowered(String nameLowered) {
        this.nameLowered = nameLowered;
    }

}
