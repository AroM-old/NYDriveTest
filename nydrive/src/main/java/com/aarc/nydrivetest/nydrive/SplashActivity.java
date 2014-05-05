package com.aarc.nydrivetest.nydrive;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.aarc.nydrivetest.nydrive.db.DBHelper;
import com.aarc.nydrivetest.nydrive.quiz.Constants;
import com.aarc.nydrivetest.nydrive.quiz.GamePlay;
import com.aarc.nydrivetest.nydrive.quiz.Question;

import java.io.IOException;
import java.util.List;

public class SplashActivity extends Activity implements OnClickListener{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        //////////////////////////////////////////////////////////////////////
        //////// GAME MENU  /////////////////////////////////////////////////
        Button playBtn = (Button) findViewById(R.id.startbtn);
        playBtn.setOnClickListener(this);
        Button settingsBtn = (Button) findViewById(R.id.settingsbtn);
        settingsBtn.setOnClickListener(this);
        Button rulesBtn = (Button) findViewById(R.id.rulesbtn);
        rulesBtn.setOnClickListener(this);
        Button exitBtn = (Button) findViewById(R.id.exitbtn);
        exitBtn.setOnClickListener(this);
    }


    /**
     * Listener for game menu
     */
    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.startbtn :
                //once logged in, load the main page
                //Log.d("LOGIN", "User has started the game");

                //Get Question set //
                List<Question> questions = getQuestionSetFromDb();

                //Initialise Game with retrieved question set ///
                GamePlay c = new GamePlay();
                c.setQuestions(questions);
                c.setNumRounds(getNumQuestions());
                ((NYTestApplication)getApplication()).setCurrentGame(c);

                //Start Game Now.. //
                i = new Intent(this, QuestionActivity.class);
                startActivityForResult(i, Constants.PLAYBUTTON);
                break;

            case R.id.rulesbtn :
                i = new Intent(this, RulesActivity.class);
                startActivityForResult(i, Constants.RULESBUTTON);
                break;

            case R.id.settingsbtn :
                i = new Intent(this, SettingsActivity.class);
                startActivityForResult(i, Constants.SETTINGSBUTTON);
                break;

            case R.id.exitbtn :
                finish();
                break;
        }

    }


    /**
     * Method that retrieves a random set of questions from
     * the database for the given difficulty
     * @return
     * @throws Error
     */
    private List<Question> getQuestionSetFromDb() throws Error {
        int diff = getDifficultySettings();
        int numQuestions = getNumQuestions();
        DBHelper myDbHelper = new DBHelper(this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }
        List<Question> questions = myDbHelper.getQuestionSet(diff, numQuestions);
        myDbHelper.close();
        return questions;
    }


    /**
     * Method to return the difficulty settings
     * @return
     */
    private int getDifficultySettings() {
        SharedPreferences settings = getSharedPreferences(Constants.SETTINGS, 0);
        int diff = settings.getInt(Constants.DIFFICULTY, Constants.MEDIUM);
        return diff;
    }

    /**
     * Method to return the number of questions for the game
     * @return
     */
    private int getNumQuestions() {
        SharedPreferences settings = getSharedPreferences(Constants.SETTINGS, 0);
        int numRounds = settings.getInt(Constants.NUM_ROUNDS, 20);
        return numRounds;
    }

}