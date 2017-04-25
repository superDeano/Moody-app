package ultramirinc.champs_mood;

import android.location.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Étienne Bérubé on 2017-02-07.
 */

public class UserAccount {

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
