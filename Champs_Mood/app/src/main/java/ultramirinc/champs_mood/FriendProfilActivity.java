package ultramirinc.champs_mood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FriendProfilActivity extends AppCompatActivity {

    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendprofil);

        Intent intent = getIntent();


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userName= null;
            } else {
                userName= extras.getString("NAME");
            }
        } else {
            userName= (String) savedInstanceState.getSerializable("NAME");
        }

        TextView name = (TextView) findViewById(R.id.profil_text);
        name.setText(userName);


    }
}
