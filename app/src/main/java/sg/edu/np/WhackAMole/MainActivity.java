package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */
    private static final String TAG = MainActivity.class.getSimpleName();
    int randomLocation = 0;
    int score = 0;
    int scoreToAdvance = 2;
    Button hole1_btn, hole2_btn, hole3_btn;
    TextView score_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Finished Pre-Initialisation!");

        hole1_btn = (Button) findViewById(R.id.hole1);
        hole2_btn = (Button) findViewById(R.id.hole2);
        hole3_btn = (Button) findViewById(R.id.hole3);
        score_view = (TextView) findViewById(R.id.score);

        hole1_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V){
                doCheck(hole1_btn);
            }
        });
        hole2_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V){
                doCheck(hole2_btn);
            }
        });
        hole3_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V){
                doCheck(hole3_btn);
            }
        });

        setNewMole();

    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        if (checkButton.getText().toString() ==  "*"){
            Log.v(TAG, "Button Hit!");

            score += 1;
        }

        else {
            Log.v(TAG, "Button Missed!");
            score -= 1;
        }

        if(score >= scoreToAdvance){
            nextLevelQuery();
        }
        setNewMole();
    }

    private void nextLevelQuery(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning! Insane Whack a mole coming!");
        builder.setMessage("Would you like to advance to advanced Whack a mole?");
        builder.setCancelable(false);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Log.v(TAG, "User decline!");
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


        Log.v(TAG, "Advance option given to user!");
    }

    private void nextLevel(){
        Bundle extras = new Bundle();
        extras.putInt("score", score);


        Intent intent = new Intent(MainActivity.this, Main2Activity.class);

        intent.putExtras(extras);
        startActivity(intent);

    }

    private void setNewMole() {
        score_view.setText(Integer.toString(score));
        Random ran = new Random();
        randomLocation = ran.nextInt(3);
        hole1_btn.setText("0");
        hole2_btn.setText("0");
        hole3_btn.setText("0");

        switch(randomLocation) {
            case 0:
                hole1_btn.setText("*");
                break;
            case 1:
                hole2_btn.setText("*");
                break;
            case 2:
                hole3_btn.setText("*");
                break;
        }
    }
}