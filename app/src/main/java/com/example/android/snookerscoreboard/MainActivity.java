package com.example.android.snookerscoreboard;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.os.SystemClock;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    int scoreAOld = 0;
    int scoreACurrent = 0;
    int scoreBOld = 0;
    int scoreBCurrent = 0;
    int frameScoreA = 0;
    int frameScoreB = 0;
    int pointsDifference = 0;
    int pointsAvailable = 147;
    int numberOfReds = 15;
    int yellowAvailable = 1;
    int greenAvailable = 1;
    int brownAvailable = 1;
    int blueAvailable = 1;
    int pinkAvailable = 1;
    int blackAvailable = 1;

    Chronometer frameTimer;
    ImageButton start;

    long timeWhenStopped = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameTimer = (Chronometer) findViewById(R.id.timer);
        start = (ImageButton) findViewById(R.id.startFrame);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameTimer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                frameTimer.start();
            }
        });

    }

    /**
     * Displays the score for Player A.
     */
    public void displayForPlayerA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.scoreA);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the frame score for Player A.
     */
    public void displayFrameForPlayerA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.frameScoreA);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Adds 1 point Player A for the red ball.
     */
    public void redA(View v) {
        scoreAOld = scoreACurrent;
        scoreACurrent = scoreACurrent + 1;
        displayForPlayerA(scoreACurrent);
        calcPointsDiff();
        numberOfReds = numberOfReds - 1;
        calcPointsAvail();
    }

    /**
     * Adds 2 points Player A for the yellow ball.
     */
    public void yellowA(View v) {
        scoreAOld = scoreACurrent;
        scoreACurrent = scoreACurrent + 2;
        displayForPlayerA(scoreACurrent);
        removeColors();
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Adds 3 points Player A for the green ball.
     */
    public void greenA(View v) {
        scoreAOld = scoreACurrent;
        scoreACurrent = scoreACurrent + 3;
        displayForPlayerA(scoreACurrent);
        removeColors();
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Adds 4 points Player A for the brown ball.
     */
    public void brownA(View v) {
        scoreAOld = scoreACurrent;
        scoreACurrent = scoreACurrent + 4;
        displayForPlayerA(scoreACurrent);
        removeColors();
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Adds 5 points Player A for the blue ball.
     */
    public void blueA(View v) {
        scoreAOld = scoreACurrent;
        scoreACurrent = scoreACurrent + 5;
        displayForPlayerA(scoreACurrent);
        removeColors();
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Adds 6 points Player A for the pink ball.
     */
    public void pinkA(View v) {
        scoreAOld = scoreACurrent;
        scoreACurrent = scoreACurrent + 6;
        displayForPlayerA(scoreACurrent);
        removeColors();
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Adds 7 points Player A for the black ball.
     */
    public void blackA(View v) {
        scoreAOld = scoreACurrent;
        scoreACurrent = scoreACurrent + 7;
        displayForPlayerA(scoreACurrent);
        removeColors();
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Undoes the last score added to Player A.
     */
    public void undoA(View v) {
        if (scoreACurrent == scoreAOld + 1) {
            numberOfReds = numberOfReds + 1;
        }
        scoreACurrent = scoreAOld;
        displayForPlayerA(scoreACurrent);
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Displays the score for Player B.
     */
    public void displayForPlayerB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.scoreB);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the frame score for Player B.
     */
    public void displayFrameForPlayerB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.frameScoreB);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Adds 1 point Player B for the red ball.
     */
    public void redB(View v) {
        scoreBOld = scoreBCurrent;
        scoreBCurrent = scoreBCurrent + 1;
        displayForPlayerB(scoreBCurrent);
        calcPointsDiff();
        numberOfReds = numberOfReds - 1;
        calcPointsAvail();
    }

    /**
     * Adds 2 points Player B for the yellow ball.
     */
    public void yellowB(View v) {
        scoreBOld = scoreBCurrent;
        scoreBCurrent = scoreBCurrent + 2;
        displayForPlayerB(scoreBCurrent);
        removeColors();
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Bdds 3 points Player B for the green ball.
     */
    public void greenB(View v) {
        scoreBOld = scoreBCurrent;
        scoreBCurrent = scoreBCurrent + 3;
        displayForPlayerB(scoreBCurrent);
        removeColors();
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Adds 4 points Player B for the brown ball.
     */
    public void brownB(View v) {
        scoreBOld = scoreBCurrent;
        scoreBCurrent = scoreBCurrent + 4;
        displayForPlayerB(scoreBCurrent);
        removeColors();
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Adds 5 points Player B for the blue ball.
     */
    public void blueB(View v) {
        scoreBOld = scoreBCurrent;
        scoreBCurrent = scoreBCurrent + 5;
        displayForPlayerB(scoreBCurrent);
        removeColors();
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Adds 6 points Player B for the pink ball.
     */
    public void pinkB(View v) {
        scoreBOld = scoreBCurrent;
        scoreBCurrent = scoreBCurrent + 6;
        displayForPlayerB(scoreBCurrent);
        removeColors();
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Adds 7 points Player B for the black ball.
     */
    public void blackB(View v) {
        scoreBOld = scoreBCurrent;
        scoreBCurrent = scoreBCurrent + 7;
        displayForPlayerB(scoreBCurrent);
        removeColors();
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Undoes the last score added to Player B.
     */
    public void undoB(View v) {
        if (scoreBCurrent == scoreBOld + 1) {
            numberOfReds = numberOfReds + 1;
        }
        scoreBCurrent = scoreBOld;
        displayForPlayerB(scoreBCurrent);
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Resets all variables apart from frame score to initial values
     */
    public void resetVariables() {
        scoreAOld = 0;
        scoreACurrent = 0;
        scoreBOld = 0;
        scoreBCurrent = 0;
        pointsDifference = 0;
        pointsAvailable = 147;
        numberOfReds = 15;
        yellowAvailable = 1;
        greenAvailable = 1;
        brownAvailable = 1;
        blueAvailable = 1;
        pinkAvailable = 1;
        blackAvailable = 1;
        timeWhenStopped = 0;
    }

    /**
     * Ends the frame and resets all variables.
     */
    public void endFrame(View v) {
        if (scoreACurrent > scoreBCurrent) {
            frameScoreA = frameScoreA + 1;
            displayFrameForPlayerA(frameScoreA);
            resetVariables();
            frameTimer.setBase(SystemClock.elapsedRealtime());
            frameTimer.stop();
            displayForPlayerA(scoreACurrent);
            displayForPlayerB(scoreBCurrent);
            displayPointsAvailable(pointsAvailable);
            displayPointsDifference(pointsDifference);
            Toast msg = Toast.makeText(getBaseContext(), "Player A wins the frame!", Toast.LENGTH_LONG);
            msg.show();
        } else if (scoreBCurrent > scoreACurrent) {
            frameScoreB = frameScoreB + 1;
            displayFrameForPlayerB(frameScoreB);
            resetVariables();
            frameTimer.setBase(SystemClock.elapsedRealtime());
            frameTimer.stop();
            displayForPlayerA(scoreACurrent);
            displayForPlayerB(scoreBCurrent);
            displayPointsAvailable(pointsAvailable);
            displayPointsDifference(pointsDifference);
            Toast msg = Toast.makeText(getBaseContext(), "Player B wins the frame!", Toast.LENGTH_LONG);
            msg.show();
        } else {
            Toast msg = Toast.makeText(getBaseContext(), "Scores are level. Re-spot the Black!", Toast.LENGTH_LONG);
            msg.show();
        }
    }

    /**
     * Pauses the timer
     */
    public void pauseTimer(View v) {
        timeWhenStopped = frameTimer.getBase() - SystemClock.elapsedRealtime();
        frameTimer.stop();
    }

    /**
     * Displays the points difference.
     */
    public void displayPointsDifference(int pointsDiff) {
        TextView scoreView = (TextView) findViewById(R.id.pointsDifference);
        scoreView.setText(String.valueOf("Pts Diff: " + pointsDiff));
    }

    /**
     * Calculates the points difference.
     */
    public void calcPointsDiff() {
        if (scoreACurrent > scoreBCurrent) {
            pointsDifference = scoreACurrent - scoreBCurrent;
            displayPointsDifference(pointsDifference);
        } else if (scoreBCurrent > scoreACurrent) {
            pointsDifference = scoreBCurrent - scoreACurrent;
            displayPointsDifference(pointsDifference);
        } else {
            pointsDifference = 0;
            displayPointsDifference(pointsDifference);
        }
    }

    /**
     * Displays the points available.
     */
    public void displayPointsAvailable(int pointsAvail) {
        TextView scoreView = (TextView) findViewById(R.id.pointsAvailable);
        scoreView.setText(String.valueOf("Pts Avl: " + pointsAvail));
    }

    /**
     * Calculates the points available.
     */
    public void calcPointsAvail() {
        if (numberOfReds > 0) {
            pointsAvailable = numberOfReds * 8 + 27;
            displayPointsAvailable(pointsAvailable);
        } else if (yellowAvailable > 0) {
            pointsAvailable = 27;
            displayPointsAvailable(pointsAvailable);
        } else if (greenAvailable > 0) {
            pointsAvailable = 25;
            displayPointsAvailable(pointsAvailable);
        } else if (brownAvailable > 0) {
            pointsAvailable = 22;
            displayPointsAvailable(pointsAvailable);
        } else if (blueAvailable > 0) {
            pointsAvailable = 18;
            displayPointsAvailable(pointsAvailable);
        } else if (pinkAvailable > 0) {
            pointsAvailable = 13;
            displayPointsAvailable(pointsAvailable);
        } else if (blackAvailable > 0) {
            pointsAvailable = 7;
            displayPointsAvailable(pointsAvailable);
        } else {
            pointsAvailable = 0;
            displayPointsAvailable(pointsAvailable);
        }
    }

    /**
     * Calculates if any reds are remaining and removes colors if necessary.
     */
    public void removeColors() {
        if (numberOfReds > 0) {
            ;
        } else if (yellowAvailable > 0) {
            yellowAvailable = 0;
        } else if (greenAvailable > 0) {
            greenAvailable = 0;
        } else if (brownAvailable > 0) {
            brownAvailable = 0;
        } else if (blueAvailable > 0) {
            blueAvailable = 0;
        } else if (pinkAvailable > 0) {
            pinkAvailable = 0;
        } else if (blackAvailable > 0) {
            blackAvailable = 0;
        }
    }

    /**
     * Resets all variables.
     */
    public void resetAll(View v) {
        resetVariables();
        frameScoreA = 0;
        frameScoreB = 0;
        displayForPlayerA(scoreACurrent);
        displayForPlayerB(scoreBCurrent);
        displayFrameForPlayerA(frameScoreA);
        displayFrameForPlayerB(frameScoreB);
        displayPointsAvailable(pointsAvailable);
        displayPointsDifference(pointsDifference);
        frameTimer.setBase(SystemClock.elapsedRealtime());
        frameTimer.stop();
        timeWhenStopped = 0;
    }

}