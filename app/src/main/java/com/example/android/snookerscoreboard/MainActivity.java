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

    int scoreAOld, scoreACurrent, scoreBOld, scoreBCurrent, frameScoreA, frameScoreB = 0;
    int pointsDifference = 0;
    int pointsAvailable = 147;
    int numberOfReds = 15;
    int yellowAvailable, greenAvailable, brownAvailable, blueAvailable, pinkAvailable, blackAvailable = 1;

    TextView scoreViewA, scoreViewB, frameScoreViewA, frameScoreViewB, ptsDiff, ptsAvl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreViewA = (TextView) findViewById(R.id.scoreA);
        scoreViewB = (TextView) findViewById(R.id.scoreB);
        frameScoreViewA = (TextView) findViewById(R.id.frameScoreA);
        frameScoreViewB = (TextView) findViewById(R.id.frameScoreB);
        ptsDiff = (TextView) findViewById(R.id.pointsDifference);
        ptsAvl = (TextView) findViewById(R.id.pointsAvailable);
    }

    //Save the state of the variables and text after the screen orientation changes.

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("scoreAOld", scoreAOld);
        savedInstanceState.putInt("scoreACurrent", scoreACurrent);
        savedInstanceState.putInt("scoreBOld", scoreBOld);
        savedInstanceState.putInt("scoreBCurrent", scoreBCurrent);
        savedInstanceState.putInt("frameScoreA", frameScoreA);
        savedInstanceState.putInt("frameScoreB", frameScoreB);
        savedInstanceState.putInt("pointsDifference", pointsDifference);
        savedInstanceState.putInt("pointsAvailable", pointsAvailable);
        savedInstanceState.putInt("numberOfReds", numberOfReds);
        savedInstanceState.putInt("yellowAvailable", yellowAvailable);
        savedInstanceState.putInt("greenAvailable", greenAvailable);
        savedInstanceState.putInt("brownAvailable", brownAvailable);
        savedInstanceState.putInt("blueAvailable", blueAvailable);
        savedInstanceState.putInt("pinkAvailable", pinkAvailable);
        savedInstanceState.putInt("blackAvailable", blackAvailable);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        if (saveInstanceState != null) {
            scoreAOld = saveInstanceState.getInt("scoreAOld");
            scoreACurrent = saveInstanceState.getInt("scoreACurrent");
            scoreBOld = saveInstanceState.getInt("scoreBOld");
            scoreBCurrent = saveInstanceState.getInt("scoreBCurrent");
            frameScoreA = saveInstanceState.getInt("frameScoreA");
            frameScoreB = saveInstanceState.getInt("frameScoreB");
            pointsDifference = saveInstanceState.getInt("pointsDifference");
            pointsAvailable = saveInstanceState.getInt("pointsAvailable");
            numberOfReds = saveInstanceState.getInt("numberOfReds");
            yellowAvailable = saveInstanceState.getInt("yellowAvailable");
            greenAvailable = saveInstanceState.getInt("greenAvailable");
            brownAvailable = saveInstanceState.getInt("brownAvailable");
            blueAvailable = saveInstanceState.getInt("blueAvailable");
            pinkAvailable = saveInstanceState.getInt("pinkAvailable");
            blackAvailable = saveInstanceState.getInt("blackAvailable");

            scoreViewA.setText(String.valueOf(scoreACurrent));
            scoreViewB.setText(String.valueOf(scoreBCurrent));
            frameScoreViewA.setText(String.valueOf(frameScoreA));
            frameScoreViewB.setText(String.valueOf(frameScoreB));
            ptsDiff.setText(String.valueOf("Pts Diff: " + pointsDifference));
            ptsAvl.setText(String.valueOf("Pts Avl: " + pointsAvailable));

        }

    }

    /**
     * Displays the score for Player A.
     */
    public void displayForPlayerA(int score) {
        scoreViewA.setText(String.valueOf(score));
    }

    /**
     * Displays the frame score for Player A.
     */
    public void displayFrameForPlayerA(int score) {
        frameScoreViewA.setText(String.valueOf(score));
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
        scoreViewB.setText(String.valueOf(score));
    }

    /**
     * Displays the frame score for Player B.
     */
    public void displayFrameForPlayerB(int score) {
        frameScoreViewB.setText(String.valueOf(score));
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
    }

    /**
     * Ends the frame and resets all variables.
     */
    public void endFrame(View v) {
        if (scoreACurrent > scoreBCurrent) {
            frameScoreA = frameScoreA + 1;
            displayFrameForPlayerA(frameScoreA);
            resetVariables();
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
     * Displays the points difference.
     */
    public void displayPointsDifference(int pointsDiff) {
        ptsDiff.setText(String.valueOf(getResources().getString(R.string.pntsDiff) + pointsDiff));
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
        ptsAvl.setText(String.valueOf("Pts Avl: " + pointsAvail));
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
    }

}