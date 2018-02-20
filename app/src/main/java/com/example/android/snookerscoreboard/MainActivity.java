package com.example.android.snookerscoreboard;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    // Declare/initialise variables for tracking the scores
    int scoreACurrent, scoreBCurrent, frameScoreA, frameScoreB = 0;
    int pointsDifference = 0;
    int pointsAvailable = 147;
    // numberOfReds => number of reds still available to pot
    int numberOfReds = 15;
    int potValue, foulValue;
    // These variables indicate the points available remaining for potting each of the colours
    int yellowAvailable = 2;
    int greenAvailable = 3;
    int brownAvailable = 4;
    int blueAvailable = 5;
    int pinkAvailable = 6;
    int blackAvailable = 7;
    // targetBallCurrent => indicates which ball should be potted next. 0 = any colour, 1 = a red, 2 = yellow, 3 = green etc.
    int targetBallCurrent;
    int frameNumber = 1;
    // Stacks used to store multiple old values of scores to enable mutliple undos
    Stack<Integer> scoreAStack;
    Stack<Integer> scoreBStack;
    Stack<Integer> targetBallStack;

    // Declare the variables for all views
    TextView playerAName, playerBName, scoreViewA, scoreViewB, frameScoreView, ptsDiff, ptsAvl;
    RadioButton switchA, switchB;
    Switch freeBallSwitch;
    // finalRed => Indicates if the final red ball has just been potted. Used to allow correct enabling of ball buttons before going down to just the coloured balls
    Boolean finalRed;
    ImageButton redBall, yellowBall, greenBall, brownBall, blueBall, pinkBall, blackBall;

    // Declare the variables requried for the alert dialogs
    AlertDialog.Builder builder;
    View welcomeView, breakView, resetView, endFrameView, frameSummaryView, matchSummaryView;
    AlertDialog welcomeDialog, breakDialog, resetDialog, endFrameDialog, frameSummaryDialog, matchSummaryDialog;
    EditText enterPlayerA, enterPlayerB;
    Spinner matchFormat;
    int matchFormatInt;
    String nameAEntered, nameBEntered, matchFormatChosen;
    RadioButton breakA, breakB;
    String nextBreaker;
    TextView frameSummaryTitle, nextBreakerView, frameWinner, frameScoreSummary;
    TextView matchWinner, matchScoreSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise all required variables
        playerAName = findViewById(R.id.playerAName);
        playerBName = findViewById(R.id.playerBName);
        scoreViewA = findViewById(R.id.scoreA);
        scoreViewB = findViewById(R.id.scoreB);
        frameScoreView = findViewById(R.id.frameScore);
        ptsDiff = findViewById(R.id.pointsDifference);
        ptsAvl = findViewById(R.id.pointsAvailable);
        switchA = findViewById(R.id.switchA);
        switchB = findViewById(R.id.switchB);
        freeBallSwitch = findViewById(R.id.freeBallSwitch);
        redBall = findViewById(R.id.redBall);
        yellowBall = findViewById(R.id.yellowBall);
        greenBall = findViewById(R.id.greenBall);
        brownBall = findViewById(R.id.brownBall);
        blueBall = findViewById(R.id.blueBall);
        pinkBall = findViewById(R.id.pinkBall);
        blackBall = findViewById(R.id.blackBall);
        finalRed = false;
        targetBallCurrent = 1;
        scoreAStack = new Stack<Integer>();
        scoreBStack = new Stack<Integer>();
        targetBallStack = new Stack<Integer>();

        // Run enable buttons method to enable correct buttons active on app startup
        enableButtons();

        // Create the Welcome dialog
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        welcomeView = inflater.inflate(R.layout.dialog_welcome, null);
        enterPlayerA = welcomeView.findViewById(R.id.enterPlayerA);
        enterPlayerB = welcomeView.findViewById(R.id.enterPlayerB);
        matchFormat = welcomeView.findViewById(R.id.matchFormat);
        breakView = inflater.inflate(R.layout.dialog_break, null);
        breakA = breakView.findViewById(R.id.breakA);
        breakB = breakView.findViewById(R.id.breakB);
        breakA.setChecked(true);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.matchFormatArray, R.layout.spinner_welcome_style);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_welcome_dropdown_style);
        // Apply the adapter to the spinner
        matchFormat.setAdapter(adapter);

        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(welcomeView)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nameAEntered = enterPlayerA.getText().toString().trim();
                        playerAName.setText(nameAEntered);
                        nameBEntered = enterPlayerB.getText().toString().trim();
                        playerBName.setText(nameBEntered);
                        matchFormatChosen = matchFormat.getSelectedItem().toString();
                        matchFormatInt = Integer.parseInt(matchFormatChosen);
                        breakA.setText(nameAEntered);
                        breakB.setText(nameBEntered);
                        displayFrameScore();
                        welcomeDialog.dismiss();
                        breakDialog.show();
                    }
                });
        welcomeDialog = builder.create();
        if (savedInstanceState == null) {
            welcomeDialog.show();
        }

        // Create the Select Player to Break dialog
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(breakView)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (breakA.isChecked()) {
                            switchA.setChecked(true);
                            playerAName.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
                            scoreViewA.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
                            playerBName.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
                            scoreViewB.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
                            nextBreaker = nameBEntered;
                        } else {
                            switchB.setChecked(true);
                            playerBName.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
                            scoreViewB.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
                            playerAName.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
                            scoreViewA.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
                            nextBreaker = nameAEntered;
                        }
                        breakDialog.dismiss();
                    }
                });
        breakDialog = builder.create();

        // Create the Reset dialog
        resetView = inflater.inflate(R.layout.dialog_reset, null);
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(resetView)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetDialog.dismiss();
                        resetAll();
                        welcomeDialog.show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetDialog.dismiss();
                    }
                });
        resetDialog = builder.create();

        // Create the End Frame dialog
        endFrameView = inflater.inflate(R.layout.dialog_end_frame, null);
        frameSummaryView = inflater.inflate(R.layout.dialog_frame_summary, null);
        matchSummaryView = inflater.inflate(R.layout.dialog_match_summary, null);
        frameSummaryTitle = frameSummaryView.findViewById(R.id.frameSummaryTitle);
        nextBreakerView = frameSummaryView.findViewById(R.id.nextBreakerView);
        frameWinner = frameSummaryView.findViewById(R.id.frameWinner);
        frameScoreSummary = frameSummaryView.findViewById(R.id.frameScoreSummary);
        matchWinner = matchSummaryView.findViewById(R.id.matchWinner);
        matchScoreSummary = matchSummaryView.findViewById(R.id.matchScoreSummary);
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(endFrameView)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                updateFrameScore();
                if ((frameScoreA > matchFormatInt / 2) || (frameScoreB > matchFormatInt / 2)) {
                    if (scoreACurrent > scoreBCurrent) {
                        frameWinner.setText(getResources().getString(R.string.frameWinner, nameAEntered));
                        frameScoreSummary.setText(getResources().getString(R.string.frameScoreSummary, scoreACurrent, scoreBCurrent));
                    } else {
                        frameWinner.setText(getResources().getString(R.string.frameWinner, nameBEntered));
                        frameScoreSummary.setText(getResources().getString(R.string.frameScoreSummary, scoreBCurrent, scoreACurrent));
                    }
                    frameSummaryTitle.setText(getResources().getString(R.string.frameSummary, frameNumber));
                    nextBreakerView.setText("");
                    if (frameScoreA > frameScoreB) {
                        matchWinner.setText(getResources().getString(R.string.matchWinner, nameAEntered));
                        matchScoreSummary.setText(getResources().getString(R.string.matchScoreSummary, frameScoreA, frameScoreB));
                    } else {
                        matchWinner.setText(getResources().getString(R.string.matchWinner, nameBEntered));
                        matchScoreSummary.setText(getResources().getString(R.string.matchScoreSummary, frameScoreB, frameScoreA));
                    }
                    endFrameDialog.dismiss();
                    frameSummaryDialog.show();
                } else {
                    if (scoreACurrent > scoreBCurrent) {
                        frameWinner.setText(getResources().getString(R.string.frameWinner, nameAEntered));
                        frameScoreSummary.setText(getResources().getString(R.string.frameScoreSummary, scoreACurrent, scoreBCurrent));
                    } else {
                        frameWinner.setText(getResources().getString(R.string.frameWinner, nameBEntered));
                        frameScoreSummary.setText(getResources().getString(R.string.frameScoreSummary, scoreBCurrent, scoreACurrent));
                    }
                    frameSummaryTitle.setText(getResources().getString(R.string.frameSummary, frameNumber));
                    nextBreakerView.setText(getResources().getString(R.string.nextBreaker, frameNumber + 1, nextBreaker));
                }
                endFrame();
                endFrameDialog.dismiss();
                frameSummaryDialog.show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                endFrameDialog.dismiss();
            }
        });
        endFrameDialog = builder.create();

        // Create the Frame Summary dialog
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(frameSummaryView)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        frameNumber += 1;
                        frameSummaryDialog.dismiss();
                        if ((frameScoreA > matchFormatInt / 2) || (frameScoreB > matchFormatInt / 2)) {
                            matchSummaryDialog.show();
                        }
                    }
                });
        frameSummaryDialog = builder.create();

        // Create the Match Summary dialog
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(matchSummaryView)
                .setPositiveButton(R.string.newMatch, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        matchSummaryDialog.dismiss();
                        resetAll();
                        welcomeDialog.show();
                    }
                });
        matchSummaryDialog = builder.create();
    }

    //Save the state of the variables and text after the screen orientation changes.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save all variables
        savedInstanceState.putInt("scoreACurrent", scoreACurrent);
        savedInstanceState.putInt("scoreBCurrent", scoreBCurrent);
        savedInstanceState.putInt("targetBallCurrent", targetBallCurrent);
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
        savedInstanceState.putString("playerAName", nameAEntered);
        savedInstanceState.putString("playerBName", nameBEntered);
        savedInstanceState.putString("matchFormat", matchFormatChosen);
        savedInstanceState.putBoolean("finalRed", finalRed);
        savedInstanceState.putInt("potValue", potValue);

        // Save correct active state of ball buttons
        if (redBall.isEnabled()) {
            savedInstanceState.putBoolean("redBallState", true);
        } else {
            savedInstanceState.putBoolean("redBallState", false);
        }
        if (yellowBall.isEnabled()) {
            savedInstanceState.putBoolean("yellowBallState", true);
        } else {
            savedInstanceState.putBoolean("yellowBallState", false);
        }
        if (greenBall.isEnabled()) {
            savedInstanceState.putBoolean("greenBallState", true);
        } else {
            savedInstanceState.putBoolean("greenBallState", false);
        }
        if (brownBall.isEnabled()) {
            savedInstanceState.putBoolean("brownBallState", true);
        } else {
            savedInstanceState.putBoolean("brownBallState", false);
        }
        if (blueBall.isEnabled()) {
            savedInstanceState.putBoolean("blueBallState", true);
        } else {
            savedInstanceState.putBoolean("blueBallState", false);
        }
        if (pinkBall.isEnabled()) {
            savedInstanceState.putBoolean("pinkBallState", true);
        } else {
            savedInstanceState.putBoolean("pinkBallState", false);
        }
        if (blackBall.isEnabled()) {
            savedInstanceState.putBoolean("blackBallState", true);
        } else {
            savedInstanceState.putBoolean("blackBallState", false);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    //Restore the state of the variables and text after the screen orientation changes.
    @Override
    public void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        if (saveInstanceState != null) {
            // Restore all variables
            scoreACurrent = saveInstanceState.getInt("scoreACurrent");
            scoreBCurrent = saveInstanceState.getInt("scoreBCurrent");
            targetBallCurrent = saveInstanceState.getInt("targetBallCurrent");
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
            nameAEntered = saveInstanceState.getString("playerAName");
            nameBEntered = saveInstanceState.getString("playerBName");
            matchFormatChosen = saveInstanceState.getString("matchFormat");
            finalRed = saveInstanceState.getBoolean("finalRed");
            potValue = saveInstanceState.getInt("potValue");
            redBall.setEnabled(saveInstanceState.getBoolean("redBallState"));
            yellowBall.setEnabled(saveInstanceState.getBoolean("yellowBallState"));
            greenBall.setEnabled(saveInstanceState.getBoolean("greenBallState"));
            brownBall.setEnabled(saveInstanceState.getBoolean("brownBallState"));
            blueBall.setEnabled(saveInstanceState.getBoolean("blueBallState"));
            pinkBall.setEnabled(saveInstanceState.getBoolean("pinkBallState"));
            blackBall.setEnabled(saveInstanceState.getBoolean("blackBallState"));
            scoreViewA.setText(String.valueOf(scoreACurrent));
            scoreViewB.setText(String.valueOf(scoreBCurrent));
            ptsDiff.setText(String.valueOf("Pts Diff: " + pointsDifference));
            ptsAvl.setText(String.valueOf("Pts Avl: " + pointsAvailable));
            playerAName.setText(String.valueOf(nameAEntered));
            playerBName.setText(String.valueOf(nameBEntered));
            frameScoreView.setText(getResources().getString(R.string.frameScore, frameScoreA, matchFormatChosen, frameScoreB));

            // Set correct active player on restore
            if (switchA.isChecked()) {
                playerAName.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
                scoreViewA.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
                playerBName.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
                scoreViewB.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
            } else if (switchB.isChecked()) {
                playerAName.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
                scoreViewA.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
                playerBName.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
                scoreViewB.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
            }
        }

        // Displays the welcome dialog if the orientation is changed before details have been entered
        if (nameAEntered == null) {
            welcomeDialog.show();
        }
    }

    /**
     * Switches active player
     */
    public void switchPlayer(View view) {
        finalRed = false;
        if (switchA.isChecked()) {
            playerAName.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
            scoreViewA.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
            playerBName.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
            scoreViewB.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
            targetBallCurrent = 1;
            freeBallSwitch.setChecked(false);
            enableButtons();
        } else if (switchB.isChecked()) {
            playerAName.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
            scoreViewA.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
            playerBName.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
            scoreViewB.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
            targetBallCurrent = 1;
            freeBallSwitch.setChecked(false);
            enableButtons();
        }
        scoreAStack.clear();
        scoreBStack.clear();
        targetBallStack.clear();
    }

    /**
     * Displays the score for Player A.
     */
    public void displayForPlayerA(int score) {
        scoreViewA.setText(String.valueOf(score));
    }

    /**
     * Displays the score for Player B.
     */
    public void displayForPlayerB(int score) {
        scoreViewB.setText(String.valueOf(score));
    }

    /**
     * Displays the frame score
     */
    public void displayFrameScore() {
        frameScoreView.setText(getResources().getString(R.string.frameScore, frameScoreA, matchFormatChosen, frameScoreB));
    }

    /**
     * Enables/Disables the buttons as required based on current target ball
     */
    public void enableButtons() {
        // If there are still reds on the table
        if (numberOfReds > 0) {
            // If the current target ball is a red
            if (targetBallCurrent == 1) {
                redBall.setEnabled(true);
                yellowBall.setEnabled(false);
                greenBall.setEnabled(false);
                brownBall.setEnabled(false);
                blueBall.setEnabled(false);
                pinkBall.setEnabled(false);
                blackBall.setEnabled(false);
                freeBallSwitch.setEnabled(true);
            // If the current target ball is any colour
            } else if (targetBallCurrent == 0) {
                redBall.setEnabled(false);
                yellowBall.setEnabled(true);
                greenBall.setEnabled(true);
                brownBall.setEnabled(true);
                blueBall.setEnabled(true);
                pinkBall.setEnabled(true);
                blackBall.setEnabled(true);
                freeBallSwitch.setEnabled(false);
            }
        // If the final red has just been potted
        } else if (finalRed) {
            redBall.setEnabled(false);
            yellowBall.setEnabled(true);
            greenBall.setEnabled(true);
            brownBall.setEnabled(true);
            blueBall.setEnabled(true);
            pinkBall.setEnabled(true);
            blackBall.setEnabled(true);
            freeBallSwitch.setEnabled(false);
        } else {
            redBall.setEnabled(false);
            yellowBall.setEnabled(false);
            greenBall.setEnabled(false);
            brownBall.setEnabled(false);
            blueBall.setEnabled(false);
            pinkBall.setEnabled(false);
            blackBall.setEnabled(false);
            freeBallSwitch.setEnabled(true);
            // If only the colours remaining, activating each in turn
            if (yellowAvailable == 2) {
                yellowBall.setEnabled(true);
            } else if (greenAvailable == 3) {
                greenBall.setEnabled(true);
            } else if (brownAvailable == 4) {
                brownBall.setEnabled(true);
            } else if (blueAvailable == 5) {
                blueBall.setEnabled(true);
            } else if (pinkAvailable == 6) {
                pinkBall.setEnabled(true);
            } else if (blackAvailable == 7) {
                blackBall.setEnabled(true);
            } else if (scoreACurrent == scoreBCurrent) {
                blackBall.setEnabled(true);
            }
        }
    }

    /**
     * Enables all the buttons when Free Ball switch is turned on
     */
    public void enableAllButtons(View v) {
        if (freeBallSwitch.isChecked()) {
            if (numberOfReds > 0) {
                redBall.setEnabled(true);
            }
            if (yellowAvailable == 2) {
                yellowBall.setEnabled(true);
            }
            if (greenAvailable == 3) {
                greenBall.setEnabled(true);
            }
            if (brownAvailable == 4) {
                brownBall.setEnabled(true);
            }
            if (blueAvailable == 5) {
                blueBall.setEnabled(true);
            }
            if (pinkAvailable == 6) {
                pinkBall.setEnabled(true);
            }
            if (blackAvailable == 7) {
                blackBall.setEnabled(true);
            }
        } else {
            enableButtons();
        }
    }

    /**
     * Adds points for potting a ball
     */
    public void addPoints(int potValue) {
        // Update score stacks with previous scores
        scoreAStack.push(scoreACurrent);
        scoreBStack.push(scoreBCurrent);
        // Actions if the current player has a free ball
        if (freeBallSwitch.isChecked()) {
            if (switchA.isChecked()) {
                scoreACurrent += targetBallCurrent;
                displayForPlayerA(scoreACurrent);
            } else if (switchB.isChecked()) {
                scoreBCurrent += targetBallCurrent;
                displayForPlayerB(scoreBCurrent);
            }
            if (targetBallCurrent == 1) {
                targetBallCurrent = 0;
            }
            freeBallSwitch.setChecked(false);
        // Actions if the current player does not have a free ball
        } else {
            if (switchA.isChecked()) {
                scoreACurrent += potValue;
                displayForPlayerA(scoreACurrent);
            } else if (switchB.isChecked()) {
                scoreBCurrent += potValue;
                displayForPlayerB(scoreBCurrent);
            }
        }
        calcPointsDiff();
        calcPointsAvail();
        enableButtons();
    }

    /**
     * Updates the current target ball when a coloured ball is potted
     */
    public void updateTargetBall(int potValue) {
        targetBallStack.push(targetBallCurrent);
        // If the final red was just potted next target is the yellow ball
        if (finalRed) {
            targetBallCurrent = 2;
            finalRed = false;
        // If all reds have been potted and the correct colour is potted
        } else if ((numberOfReds == 0 && !freeBallSwitch.isChecked()) || (targetBallCurrent == potValue)) {
            if (potValue == 2) {
                yellowAvailable = 0;
            }
            if (potValue == 3) {
                greenAvailable = 0;
            }
            if (potValue == 4) {
                brownAvailable = 0;
            }
            if (potValue == 5) {
                blueAvailable = 0;
            }
            if (potValue == 6) {
                pinkAvailable = 0;
            }
            if (potValue == 7) {
                blackAvailable = 0;
            }
            if (potValue < 7) {
                targetBallCurrent = potValue + 1;
            } else {
                targetBallCurrent = potValue;
            }
            freeBallSwitch.setChecked(false);
        // If there is currently a free ball situation
        } else if (freeBallSwitch.isChecked()) {
        // If a colour is potted and there are still reds remaining
        } else {
            targetBallCurrent = 1;
        }
    }

    /**
     * Adds 1 point for potting a red ball and sets current target ball
     */
    public void potRed(View v) {
        numberOfReds -= 1;
        targetBallStack.push(targetBallCurrent);
        // If final red has just been potted and it was not a free ball situation
        if (numberOfReds == 0 && !freeBallSwitch.isChecked()) {
            targetBallCurrent = 2;
            finalRed = true;
        // If final red has just been potted and it was a free ball situation
        } else if (numberOfReds == 0 && freeBallSwitch.isChecked()) {
            targetBallCurrent = 1;
            finalRed = true;
        // If it is a free ball situation
        } else if (freeBallSwitch.isChecked()) {
            targetBallCurrent = 1;
        // If it is a regular situation
        } else {
            targetBallCurrent = 0;
        }
        potValue = 1;
        addPoints(potValue);
    }

    /**
     * Adds 2 points for potting the yellow ball and sets current target ball
     */
    public void potYellow(View v) {
        potValue = 2;
        updateTargetBall(potValue);
        addPoints(potValue);
    }

    /**
     * Adds 3 points for potting the green ball and sets current target ball
     */
    public void potGreen(View v) {
        potValue = 3;
        updateTargetBall(potValue);
        addPoints(potValue);
    }

    /**
     * Adds 4 points for potting the brown ball and sets current target ball
     */
    public void potBrown(View v) {
        potValue = 4;
        updateTargetBall(potValue);
        addPoints(potValue);
    }

    /**
     * Adds 5 points for potting the blue ball and sets current target ball
     */
    public void potBlue(View v) {
        potValue = 5;
        updateTargetBall(potValue);
        addPoints(potValue);
    }

    /**
     * Adds 6 points for potting the pink ball and sets current target ball
     */
    public void potPink(View v) {
        potValue = 6;
        updateTargetBall(potValue);
        addPoints(potValue);
    }

    /**
     * Adds 7 points for potting the black ball and sets current target ball
     */
    public void potBlack(View v) {
        potValue = 7;
        updateTargetBall(potValue);
        addPoints(potValue);
    }

    /**
     * Adds points to the opponent for a foul
     */
    public void addFoul(int foulValue) {
        scoreAStack.push(scoreACurrent);
        scoreBStack.push(scoreBCurrent);
        targetBallStack.push(targetBallCurrent);
        if (switchA.isChecked()) {
            scoreBCurrent = scoreBCurrent + foulValue;
            displayForPlayerB(scoreBCurrent);
        } else if (switchB.isChecked()) {
            scoreACurrent = scoreACurrent + foulValue;
            displayForPlayerA(scoreACurrent);
        }
        calcPointsDiff();
        calcPointsAvail();
    }

    /**
     * Adds 4 points to the opponent for a foul
     */
    public void foul4(View view) {
        foulValue = 4;
        addFoul(foulValue);
    }

    /**
     * Adds 5 points to the opponent for a foul
     */
    public void foul5(View view) {
        foulValue = 5;
        addFoul(foulValue);
    }

    /**
     * Adds 6 points to the opponent for a foul
     */
    public void foul6(View view) {
        foulValue = 6;
        addFoul(foulValue);
    }

    /**
     * Adds 7 points to the opponent for a foul
     */
    public void foul7(View view) {
        foulValue = 7;
        addFoul(foulValue);
    }

    /**
     * Un-does the last points added when the 'Undo' button is pressed
     */
    public void undo(View v) {
        // Checks if stacks are empty and no more undo's can take place
        if (scoreAStack.isEmpty() || scoreBStack.isEmpty()) {
            return;
        }
        // If the last ball potted was a red this adds one back to the available reds
        if (switchA.isChecked() && (scoreACurrent == scoreAStack.peek() + 1)) {
            numberOfReds = numberOfReds + 1;
        }
        if (switchB.isChecked() && (scoreBCurrent == scoreBStack.peek() + 1)) {
            numberOfReds = numberOfReds + 1;
        }
        // Replaces colours back on to the table if applicable
        if (((scoreACurrent == scoreAStack.peek() + 2) || (scoreBCurrent == scoreBStack.peek() + 2)) && (yellowAvailable == 0)) {
            yellowAvailable = 2;
        }
        if (((scoreACurrent == scoreAStack.peek() + 3) || (scoreBCurrent == scoreBStack.peek() + 3)) && (greenAvailable == 0)) {
            greenAvailable = 3;
        }
        if (((scoreACurrent == scoreAStack.peek() + 4) || (scoreBCurrent == scoreBStack.peek() + 4)) && (brownAvailable == 0)) {
            brownAvailable = 4;
        }
        if (((scoreACurrent == scoreAStack.peek() + 5) || (scoreBCurrent == scoreBStack.peek() + 5)) && (blueAvailable == 0)) {
            blueAvailable = 5;
        }
        if (((scoreACurrent == scoreAStack.peek() + 6) || (scoreBCurrent == scoreBStack.peek() + 6)) && (pinkAvailable == 0)) {
            pinkAvailable = 6;
        }
        if (((scoreACurrent == scoreAStack.peek() + 7) || (scoreBCurrent == scoreBStack.peek() + 7)) && (blackAvailable == 0)) {
            blackAvailable = 7;
        }
        // Resets scores and target ball to previous values and displays them
        scoreACurrent = scoreAStack.pop();
        scoreBCurrent = scoreBStack.pop();
        displayForPlayerA(scoreACurrent);
        displayForPlayerB(scoreBCurrent);
        targetBallCurrent = targetBallStack.pop();
        calcPointsDiff();
        calcPointsAvail();
        enableButtons();
    }

    /**
     * Displays the points difference.
     */
    public void displayPointsDifference(int pointsDiff) {
        ptsDiff.setText(String.valueOf(getResources().getString(R.string.pntsDiffBlank) + pointsDiff));
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
        ptsAvl.setText(String.valueOf(getResources().getString(R.string.pntsAvlBlank) + pointsAvail));
    }

    /**
     * Calculates the points available.
     */
    public void calcPointsAvail() {
        pointsAvailable = (numberOfReds * 8) + yellowAvailable + greenAvailable + brownAvailable + blueAvailable + pinkAvailable + blackAvailable;
        displayPointsAvailable(pointsAvailable);
    }

    /**
     * Opens the End Frame dialog when the 'End Frame' button is pressed
     */
    public void openEndFrameDialog(View view) {
        if (scoreACurrent != scoreBCurrent) {
            endFrameDialog.show();
        } else {
            Toast scoresLevel = Toast.makeText(getBaseContext(), "Scores are level", Toast.LENGTH_LONG);
            scoresLevel.show();
        }
    }

    /**
     * Updates frame score
     */
    public void updateFrameScore() {
        if (scoreACurrent > scoreBCurrent) {
            frameScoreA += 1;
            displayFrameScore();
        } else if (scoreBCurrent > scoreACurrent) {
            frameScoreB += 1;
            displayFrameScore();
        }
    }

    /**
     * Ends the frame and resets all variables
     */
    public void endFrame() {
        resetVariables();
        displayForPlayerA(scoreACurrent);
        displayForPlayerB(scoreBCurrent);
        displayPointsAvailable(pointsAvailable);
        displayPointsDifference(pointsDifference);
        if (nextBreaker == nameAEntered) {
            nextBreaker = nameBEntered;
            switchA.setChecked(true);
            playerAName.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
            scoreViewA.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
            playerBName.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
            scoreViewB.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
        } else {
            nextBreaker = nameAEntered;
            switchB.setChecked(true);
            playerBName.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
            scoreViewB.setBackground(getResources().getDrawable(R.drawable.activeplayerbackground));
            playerAName.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
            scoreViewA.setBackground(getResources().getDrawable(R.drawable.inactiveplayerbackground));
        }
    }

    /**
     * Opens the Reset Dialog when the 'Reset All' button is pressed
     */
    public void openResetDialog(View view) {
        resetDialog.show();
    }

    /**
     * Resets all variables. Used when resetting the app
     */
    public void resetAll() {
        resetVariables();
        frameScoreA = 0;
        frameScoreB = 0;
        frameNumber = 1;
        displayForPlayerA(scoreACurrent);
        displayForPlayerB(scoreBCurrent);
        displayFrameScore();
        displayPointsAvailable(pointsAvailable);
        displayPointsDifference(pointsDifference);
    }

    /**
     * Resets all variables apart from frame score to initial values. Used when starting a new frame
     */
    public void resetVariables() {
        scoreACurrent = 0;
        scoreBCurrent = 0;
        pointsDifference = 0;
        pointsAvailable = 147;
        numberOfReds = 15;
        yellowAvailable = 2;
        greenAvailable = 3;
        brownAvailable = 4;
        blueAvailable = 5;
        pinkAvailable = 6;
        blackAvailable = 7;
        enterPlayerA.setText("");
        enterPlayerB.setText("");
        matchFormat.setSelection(0);
        targetBallCurrent = 1;
        enableButtons();
        scoreAStack.clear();
        scoreBStack.clear();
        targetBallStack.clear();
    }
}