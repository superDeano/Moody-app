package ultramirinc.champs_mood.models;


import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by admin on 2017-04-23.
 */
@IgnoreExtraProperties
public class User {
    private String uId;
    private String firstName;
    private String lastName;
    public User(){
        //this constructor is required
    }

    public User(String userId, String firstName, String lastName) {
        this.uId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserId() {
        return uId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
