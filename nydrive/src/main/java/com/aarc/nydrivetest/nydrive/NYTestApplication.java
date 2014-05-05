/**
 *
 */
package com.aarc.nydrivetest.nydrive;

import android.app.Application;

import com.aarc.nydrivetest.nydrive.quiz.GamePlay;

/**
 * @author rob
 *
 */
public class NYTestApplication extends Application{
    private GamePlay currentGame;

    /**
     * @param currentGame the currentGame to set
     */
    public void setCurrentGame(GamePlay currentGame) {
        this.currentGame = currentGame;
    }

    /**
     * @return the currentGame
     */
    public GamePlay getCurrentGame() {
        return currentGame;
    }
}
