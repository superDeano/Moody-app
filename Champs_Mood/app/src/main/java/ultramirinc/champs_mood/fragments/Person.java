package ultramirinc.champs_mood.fragments;

/**
 * Created by William on 2017-04-06.
 */

public class Person {

    private String name;
    private String mood;
    private String breakText;
    private boolean isFriend;

    public Person(String name, String mood, String breakText, boolean isFriend) {
        this.name = name;
        this.mood = mood;
        this.breakText = breakText;
        this.isFriend = isFriend;
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

    public String getBreakText() {
        return breakText;
    }

    public void setBreakText(String breakText) {
        this.breakText = breakText;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
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
