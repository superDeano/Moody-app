package ultramirinc.champs_mood;

import android.location.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Étienne Bérubé on 2017-02-07.
 */

public class UserAccount {
    private int id;
    private String name;
    private String mood;
    private Collection<UserAccount> friendList = Collections.synchronizedList(new ArrayList<>());
    private ArrayList<Break> breaks = new ArrayList<Break>();
    private Location mLastLocation;

    public UserAccount(int id, String name, String mood, Location location) {
        this.id = id;
        this.name = name;
        this.mood = mood;
        this.mLastLocation = location;
    }

    public UserAccount() {
        this.id = 0000000;
        this.name = "DEFAULT";
        this.mood = "DEFAULT_MOOD";
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
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

    public Collection<UserAccount> getFriendList() {
        return friendList;
    }
}
