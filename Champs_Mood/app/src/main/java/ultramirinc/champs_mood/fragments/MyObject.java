package ultramirinc.champs_mood.fragments;

/**
 * Created by William on 2017-04-04.
 */

public class MyObject {

    private String name;
    private String mood;
    private String breakText;

    public MyObject(String name, String mood, String breakText) {
        this.name = name;
        this.mood = mood;
        this. breakText = breakText;
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
}
