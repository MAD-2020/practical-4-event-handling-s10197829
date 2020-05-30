package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {

    int randomLocation;
    Button[] buttons;
    TextView aScore_view;
    int score;
    boolean gameStart = false;
    CountDownTimer cTimer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent getIntent = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        aScore_view = findViewById(R.id.aScore);
        score = 0;

        aScore_view.setText(String.valueOf(score));
        buttons = new Button[9];

        for(int i=0; i < buttons.length; i++){

            String buttonID = "ahole" + (i + 1);

            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

            buttons[i] = findViewById(resID);
            final int finalI = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (gameStart) {

                        if (buttons[finalI].getText() == "*") {
                            score++;
                            Log.v("Whack-A-Mole-2.0", "Hit, Score added!");
                        } else {
                            score--;
                            Log.v("Whack-A-Mole-2.0", "Missed, Score deducted!");
                        }
                        aScore_view.setText(String.valueOf(score));
                    }
                }
            });
        }
        Log.v("Whack-A-Mole-2.0", "Current User Score: " + String.valueOf(score));
        clearMoles();
        readyTimer();

    }
    private void readyTimer(){

        new CountDownTimer(10000,1000){
            @Override
            public void onTick(long l) {
                Toast.makeText(Main2Activity.this,"Get ready in " + String.valueOf(l/1000) + " seconds",Toast.LENGTH_SHORT).show();
                Log.v("Whack-A-Mole-2.0", "Ready CountDown!" + String.valueOf(l/1000)) ;
            }

            @Override
            public void onFinish() {
                Toast.makeText(Main2Activity.this,"Go!",Toast.LENGTH_SHORT).show();
                Log.v("Whack-A-Mole-2.0", "Ready CountDown Complete!");
                gameStart = true;
                placeMoleTimer();
            }

        }.start();
    }
    private void placeMoleTimer(){
        new CountDownTimer(1000000,1000){
            @Override
            public void onTick(long l) {
                setNewMole();
            }
            @Override
            public void onFinish() {

            }
        }.start();
    }
    @Override
    protected void onStart(){
        super.onStart();
    }


    public void setNewMole()
    {


        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        Random ran = new Random();
        randomLocation = ran.nextInt(9);
        clearMoles();
        buttons[randomLocation].setText("*");
        Log.v("Whack-A-Mole-2.0","New Mole Location");

    }

    public void clearMoles(){
        for(Button i : buttons){
            i.setText("0");
        }
    }
}