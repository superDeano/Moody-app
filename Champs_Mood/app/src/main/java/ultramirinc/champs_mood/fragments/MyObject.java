package ultramirinc.champs_mood.fragments;

/**
 * Created by William on 2017-04-04.
 */

public class MyObject {

    private String name;
    private String mood;

    public MyObject(String name, String mood) {
        this.name = name;
        this.mood = mood;
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
}
