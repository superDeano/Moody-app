package ultramirinc.champs_mood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.User;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();

        Button mEmailSignInButton = (Button) findViewById(R.id.logOut);


        User theUser = UserManager.getInstance().getCurrentUser();
        if (theUser != null) {
            TextView view = (TextView) findViewById(R.id.homepageText);
            view.setText(theUser.getName());
        }

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               logOut();
            }
        });
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        UserManager.getInstance().ClearCurrentUser();
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void startBreakCreatorActivity(View view){
        Intent intent = new Intent(this, ScheduleAdder.class);

        startActivity(intent);
    }

}
