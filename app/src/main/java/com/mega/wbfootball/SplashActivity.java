package com.mega.wbfootball;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.mega.wbfootball.controllers.GlobalController;
import com.mega.wbfootball.controllers.activity.MainActivity;

import static com.mega.wbfootball.controllers.GlobalController.HIGHLIGHTS_ERROR;
import static com.mega.wbfootball.controllers.GlobalController.LEAGUE_ERROR;
import static com.mega.wbfootball.controllers.GlobalController.TEAMS_SEASON_LEAGUE_ERROR;
import static com.mega.wbfootball.controllers.GlobalController.UPCOMING_EVENTS_ERROR;

public class SplashActivity extends AppCompatActivity {
CountDownTimer countDownTimer;
GlobalController globalController;
int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        globalController = new GlobalController(SplashActivity.this);
        globalController.clearContents();
        globalController.saveTeams();
        globalController.saveHighlights();
        globalController.saveUpcoming();
        globalController.saveLeagues();
        loop();
        flag = 0;

    }
    public void loop() {
        start();
    }

    public void start() {
        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (globalController.retrieveTeams() == null ||
                        globalController.retrieveLeagues() == null ||
                        globalController.retrieveHighlights() == null ||
                        globalController.retrieveUpcoming() == null) {

                    errorChecker();

                    loop();
                } else {
                    globalController.NextIntent(MainActivity.class);
                    finish();
                }
            }
        }.start();

    }

    public void errorHolder(){
        if(flag == 0){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Failed To Connect, Try To Restart the Application!");
            alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
            flag++;
        }
    }

    public void errorChecker(){
        if(globalController.getErrors(TEAMS_SEASON_LEAGUE_ERROR).contains("timeout")){
            globalController.saveTeams();
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Requesting for Teams");
            globalController.setErrors(TEAMS_SEASON_LEAGUE_ERROR,"");
        }else if(!globalController.getErrors(TEAMS_SEASON_LEAGUE_ERROR).isEmpty()){
            errorHolder();
        }
        if(globalController.getErrors(LEAGUE_ERROR).contains("timeout")){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Requesting for League");
            globalController.saveLeagues();
            globalController.setErrors(LEAGUE_ERROR,"");
        }else if(!globalController.getErrors(LEAGUE_ERROR).isEmpty()){
            errorHolder();
        }
        if(globalController.getErrors(HIGHLIGHTS_ERROR).contains("timeout")){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Requesting for Highlights");
            globalController.saveHighlights();
            globalController.setErrors(HIGHLIGHTS_ERROR,"");
        }else if(!globalController.getErrors(HIGHLIGHTS_ERROR).isEmpty()){
            errorHolder();
        }
        if(globalController.getErrors(UPCOMING_EVENTS_ERROR).contains("timeout")){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Requesting for Upcoming");
            globalController.saveUpcoming();
            globalController.setErrors(UPCOMING_EVENTS_ERROR,"");
        }else if(!globalController.getErrors(UPCOMING_EVENTS_ERROR).isEmpty()){
            errorHolder();
        }
    }
}