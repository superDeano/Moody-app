package ultramirinc.champs_mood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private boolean isConnected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        Button bTrue = (Button) findViewById(R.id.btrue);
        Button bFalse = (Button) findViewById(R.id.bfalse);
        */


        if(!isConnected){
            //Start Login Activity blblblbl
            startLoginActivity();
        }
    }

    public void startLoginActivityView(View view){
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }

    public void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
