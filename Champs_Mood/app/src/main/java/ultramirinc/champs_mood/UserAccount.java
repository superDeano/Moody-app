package ultramirinc.champs_mood;

import java.util.Calendar;
import android.location.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import java.util.GregorianCalendar;
import java.util.Iterator;

import java.util.List;

/**
 * Created by Étienne Bérubé on 2017-02-07.
 */

public class UserAccount {
    public static final int IN_BREAK = 0;
    public static final int NOT_IN_BREAK = 1;

    private String id;
    private String name;
    private String mood;
    private String breakText; //TODO temporary
    private boolean isFriend; //TODO temporary
    private Collection<UserAccount> friendList = Collections.synchronizedList(new ArrayList<>());
    private ArrayList<Break> breaks = new ArrayList<Break>();
    private Location mLastLocation;

    public UserAccount() {
        this.name = "DEFAULT";
        this.mood = "DEFAULT_MOOD";
    }

    public UserAccount(String name, String mood, String breakText, boolean isFriend) { //TODO temporary
        this.name = name;
        this.mood = mood;
        this.breakText = breakText;
        this.isFriend = isFriend;
    }

    public UserAccount(String id, String name, String mood, Location location) { //Possible change
        this.id = id;
        this.name = name;
        this.mood = mood;
        this.mLastLocation = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public void populateFriendList(ArrayList<UserAccount> friends){ //TODO update method
        for(UserAccount u: friends)
            this.friendList.add(u);
    }

    public void addToFriendList(UserAccount user){
        friendList.add(user);
    }

    public Collection<UserAccount> getFriendList() {
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

    public boolean isFriend(UserAccount user){

        Iterator<UserAccount> it = friendList.iterator();

        while(it.hasNext()){
            UserAccount friend = it.next();

            if(friend.getId().equals(user.getId())){
                return true;
            }
        }
        return false;
    }

    public String getFriendStatus(UserAccount user){
        if(isFriend(user)){
            return "Poke !";
        }
        else{
            return "Add";
        }
    }

    public String getFriendStatusTemp(){ //TODO temporary
        if(isFriend){
            return "Poke !";
        }
        else{
            return "Add";
        }
    }

    public String getBreakTextTemp() {
        return breakText;
    } //TODO temporary

    public ArrayList<Break> getBreaks() {
        return breaks;
    }

    public void setBreaks(ArrayList<Break> breaks) {
        this.breaks = breaks;

    }
}
