package com.example.tictactoe;

import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private int roundCount;

    private boolean player1turn = true;

    private int activityCount = 1;

    private int player1points;
    private int player2points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.textview_p1);
        textViewPlayer2 = findViewById(R.id.textview_p2);

        textViewPlayer1.setBackgroundColor(Color.rgb(220,20,60));

        ImageButton resetButton = findViewById(R.id.resetButton_id);

        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                String buttonID = "button_"+i+j;
                int resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button)view).getText().toString().equals("")){
            return;
        }
        if (player1turn){
            ((Button) view).setText("X");
        }else {
            ((Button) view).setText("0");
        }

        roundCount++;

        activityStatus();

        if (checkForWins()){
            if (player1turn){
                player1wins();
            }else {
                player2wins();
            }
        }else if (roundCount == 9){
            draw();
        } else {
            player1turn = !player1turn;
        }
    }

    public boolean checkForWins(){
        String[][] field = new String[3][3];
        for (int i = 0; i <3; i++){
            for (int j = 0; j < 3; j++){
                field[i][j] = buttons [i][j].getText().toString();
            }
        }

        for (int i = 0; i <3; i++){
            if (field[i][0].equals(field[i][1])
            && field[i][0].equals(field[i][2])
            && !field[i][0].equals("")){
                return true;
            }
        }


        for (int i = 0; i <3; i++){
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return true;
            }
        }


        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")){
            return true;
        }


        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){
            return true;
        }

        return false;
    }

    public void player1wins(){
        player1points++;
        Toast.makeText(this, "Player 1 win", Toast.LENGTH_SHORT).show();
        updatePointsTable();
        resetBoard();
    }
    public void player2wins(){
        player2points++;
        Toast.makeText(this, "Player 2 win", Toast.LENGTH_SHORT).show();
        updatePointsTable();
        resetBoard();
    }

    public void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    public void updatePointsTable(){
        textViewPlayer1.setText("Player 1: "+player1points);
        textViewPlayer2.setText("Player 2: "+player2points);
    }

    public void resetBoard(){

        for (int i = 0; i < 3; i++){
            for (int j = 0; j <3; j++){
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        activityCount = 1;
        activityNum();
        player1turn = true;
    }

    public void resetGame(){
        resetBoard();
        player1points = 0;
        player2points = 0;
        updatePointsTable();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("Player1Points",player1points);
        outState.putInt("Player2Points",player2points);
        outState.putInt("RoundCount",roundCount);
        outState.putBoolean("Player1Turn",player1turn);
        outState.putInt("ActivityCount",activityCount);

        activityNum();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        player1points = savedInstanceState.getInt("Player1Points");
        player2points = savedInstanceState.getInt("Player2Points");
        roundCount = savedInstanceState.getInt("RoundCount");
        player1turn = savedInstanceState.getBoolean("Player1Turn");
        activityCount = savedInstanceState.getInt("ActivityCount");

        activityNum();
    }

    public void activityStatus(){
        activityCount++;

        if (activityCount%2 == 0){
            textViewPlayer1.setBackgroundColor(Color.rgb(58,133,0));
            textViewPlayer2.setBackgroundColor(Color.rgb(220,20,60));
        } else {
            textViewPlayer1.setBackgroundColor(Color.rgb(220,20,60));
            textViewPlayer2.setBackgroundColor(Color.rgb(58,133,0));
        }
    }

    public void activityNum(){
        if (activityCount%2 == 0){
            textViewPlayer1.setBackgroundColor(Color.rgb(58,133,0));
            textViewPlayer2.setBackgroundColor(Color.rgb(220,20,60));
        } else {
            textViewPlayer1.setBackgroundColor(Color.rgb(220,20,60));
            textViewPlayer2.setBackgroundColor(Color.rgb(58,133,0));
        }
    }
}
