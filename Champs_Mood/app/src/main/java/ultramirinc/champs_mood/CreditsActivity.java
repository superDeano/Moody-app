package ultramirinc.champs_mood;
//Done
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by William on 2017-05-04.
 * This activity shows the credits given to each person who directly or indirectly helped us achieve this project
 */

public class CreditsActivity extends AppCompatActivity {

    public CreditsActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Credits");

    }

}
