package ultramirinc.champs_mood.models;


import android.location.Location;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;


/**
 * Created by admin on 2017-04-23.
 */
@IgnoreExtraProperties
public class User {
    public static final int IN_BREAK = 0;
    public static final int NOT_IN_BREAK = 1;

    private String uId;
    private String name;
    private String nameLowered;
    private String mood;
    private boolean isLocationShared;
    private String breakText; //TODO temporary
    private boolean isFriend; //TODO temporary
    private ArrayList<String> friendList = new ArrayList<>();
    private ArrayList<Break> breaks = new ArrayList<>();
    //private Location mLastLocation = new Location("fused");
    private MyLocation lastLocation = new MyLocation();
    private int floor;
    private boolean shareFloor;

    public User() {
    }

    public User(String userId, String name) {
        this.uId = userId;
        this.name = name;
        this.nameLowered = name.toLowerCase();
        this.isLocationShared = true;
        this.floor = 1;
    }

    public String getId() {
        return uId;
    }

    public void setId(String id) {
        this.uId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.nameLowered = name.toLowerCase();
    }

    public boolean isShareFloor() {
        return shareFloor;
    }

    public String getMood() {
        return mood;
    }

    public void setShareFloor(boolean value) {
        this.shareFloor = value;
    }
    /*public boolean getShareFloor() {
        return this.shareFloor;
    }*/

    public void setFloorLevel(int value) {
        this.floor = value;
    }
    public int getFloorLevel() {
        return this.floor;
    }
    public void setMood(String mood) {
        this.mood = mood;
    }

    // TODO verify behaviour of this
    public void populateFriendList(ArrayList<User> friends){ //TODO update method
        for(User u: friends)
            this.friendList.add(u.getId());
    }

    public boolean addToFriendList(User user){
        if (!isFriend(user))
        {
            friendList.add(user.getId());
            return true;
        }
        return false;
    }
    public boolean addToFriendList(String userId){
        if (!isFriend(userId))
        {
            friendList.add(userId);
            return true;
        }
        return false;
    }

    public ArrayList<String> getFriendList() {
        return friendList;
    }

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

    public String getFriendStatus(User user){
        if(isFriend(user)){
            return "Poke !";
        }
        else{
            return "Add";
        }
    }

    public void setBreakText(String breakText){
        this.breakText = breakText;
    }

    public String getFriendStatusTemp(){ //temporary
        if(isFriend){
            return "Poke !";
        }
        else{
            return "Add";
        }
    }

    public String getBreakTextTemp(){ //temporary
        return breakText;
    }

    public String getBreakText() {//TODO debug
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

    public ArrayList<Break> getBreaks() {
        return breaks;
    }

    public void setBreaks(ArrayList<Break> breaks) {
        this.breaks = breaks;
    }

    public boolean checkIsFriend(User possibleFriend){
        boolean status = false;

        if(friendList.contains(possibleFriend)){
            status = true;
        }

        return status;
    }

    public static int getInBreak() {
        return IN_BREAK;
    }

    public static int getNotInBreak() {
        return NOT_IN_BREAK;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public boolean isLocationShared() {
        return isLocationShared;
    }

    public void setLocationShared(boolean locationShared) {
        isLocationShared = locationShared;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

    /*public Location getmLastLocation() {
        return mLastLocation;
    }

    public void setmLastLocation(Location mLastLocation) {
        this.mLastLocation = mLastLocation;
    }
    */

    public MyLocation getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(MyLocation lastLocation) {
        this.lastLocation = lastLocation;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean removeFromFriendList(User user){
        friendList.remove(user.getId());
        return true;
    }

    public String getNameLowered() {
        return nameLowered;
    }

    public void setNameLowered(String nameLowered) {
        this.nameLowered = nameLowered;
    }

}
