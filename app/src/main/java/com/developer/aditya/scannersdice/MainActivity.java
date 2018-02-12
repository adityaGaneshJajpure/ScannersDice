package com.developer.aditya.scannersdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView scoreText ;
    private boolean isPlayerTurn = true;
    private ImageView diceImage;
    private int currentDiceValue=1;
    private int playerScore=0,computerScore=0,turnScore=0;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        diceImage = (ImageView)findViewById(R.id.dice);
        scoreText = (TextView)findViewById(R.id.userLabel);
    }
    public void updateUI(int n)
    {
        scoreText.setText("Players Score : "+playerScore+"\n"+"Computers Score : "+computerScore+"\n"+"Turn Score : "+turnScore);
        switch (n)
        {
            case 1 : diceImage.setImageResource(R.drawable.dice1); break;
            case 2 : diceImage.setImageResource(R.drawable.dice2); break;
            case 3 : diceImage.setImageResource(R.drawable.dice3); break;
            case 4 : diceImage.setImageResource(R.drawable.dice4); break;
            case 5 : diceImage.setImageResource(R.drawable.dice5); break;
            case 6 : diceImage.setImageResource(R.drawable.dice6);
        }
    }
    public void computerTurn()
    {
        if(!isPlayerTurn)
        {
            //lets do some cheating ;-)
            if(turnScore<30)
            {
                rollCall(null);
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        computerTurn();
                    }
                },1000);
            }
            else {
                holdCall(null);
            }
        }
    }
    public void resetCall(View view) {
        playerScore=0;
        computerScore=0;
        turnScore=0;
        updateUI(1);
    }
    public void holdCall(View view)
    {
        if(isPlayerTurn)
            playerScore+=turnScore;
        else {
            computerScore += turnScore;
        }
            turnScore = 0;
            isPlayerTurn = !isPlayerTurn;
            updateUI(1);

        if(computerScore>100||playerScore>100)
        {
            Toast.makeText(MainActivity.this,(computerScore>100?"Computer Won":"Player Won"),Toast.LENGTH_SHORT).show();
            resetCall(null);
        }
        if(!isPlayerTurn)
        {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerTurn();
                }
            },1000);
        }
    }
    public void rollCall(View view) {
        currentDiceValue = new Random().nextInt(6)+1;
        if(currentDiceValue==1)
        {
            holdCall(null);
        }
        else {
            turnScore+=currentDiceValue;
            updateUI(currentDiceValue);
        }
    }
}
