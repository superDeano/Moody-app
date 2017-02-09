package ultramirinc.champs_mood;

/**
 * Created by Étienne Bérubé on 2017-02-07.
 */

public class UserAccount {
    private long id;
    private String name;
    private String mood;

    public UserAccount(long id, String name, String mood) {
        this.id = id;
        this.name = name;
        this.mood = mood;
    }

    public UserAccount() {
        this.id = 0000000;
        this.name = "DEFAULT";
        this.mood = "DEFAULT_MOOD";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

}
