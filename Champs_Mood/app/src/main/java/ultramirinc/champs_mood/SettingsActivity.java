package ultramirinc.champs_mood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.User;

/**
 * Created by Etienne Berube on 2017-03-23.
 * These are the settings the user can play with
 */

public class SettingsActivity extends AppCompatActivity {

    public SettingsActivity(){
    }
    /**Creates and inflate the visual layout for the activity.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");

        Button mEmailSignInButton = (Button) findViewById(R.id.logOut);
        Button mCreditsButton = (Button) findViewById(R.id.credits);


        User theUser = UserManager.getInstance().getCurrentUser();
        if (theUser != null) {
            TextView view = (TextView) findViewById(R.id.homepageText);
            view.setText(theUser.getName());
        }

        mEmailSignInButton.setOnClickListener(view -> logOut());

        mCreditsButton.setOnClickListener(view -> {
            Intent intent1 = new Intent(SettingsActivity.this, CreditsActivity.class);
            startActivity(intent1);
        });

    }
    /**Logs out the current user.*/
    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        UserManager.getInstance().clearCurrentUser();
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    /**Starts the activity of the breakCreator*/
    public void startBreakCreatorActivity(View view){
        Intent intent = new Intent(this, ScheduleAdder.class);

        startActivity(intent);
    }

}
