package com.mega.wbfootball.controllers.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.mega.wbfootball.R;
import com.mega.wbfootball.controllers.GlobalController;
import com.mega.wbfootball.model.LeagueModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView teams;
    TextView stats;
    TextView sched;
    TextView games;
    TextView currentLeague;
    WebView webView;
    GlobalController globalController;
    String url;
    int flag;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalController = new GlobalController(MainActivity.this);
        setContentView(R.layout.activity_main);
        teams = findViewById(R.id.teams);
        stats = findViewById(R.id.statistics);
        sched = findViewById(R.id.schedules);
        games = findViewById(R.id.games);
        currentLeague = findViewById(R.id.current_league);
        flag =0 ;
//        webView = findViewById(R.id.jump);
//        webView.getSettings().setJavaScriptEnabled(true);
//        globalController.startLoading();


        List<LeagueModel.League> leagues = globalController.retrieveLeagues();
        for (int i = 0; i < leagues.size(); i++) {
            if (leagues.get(i).getIdLeague().equals(globalController.getDefaultLeague())) {
                currentLeague.setText("Current League: " + leagues.get(i).getStrLeague());
                break;
            }
        }

//        saveHighlights();

        teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalController.NextIntent(TeamsActivity.class);
                finish();
            }
        });

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalController.NextIntent(LeaguesActivity.class);
                finish();
            }
        });

        sched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalController.NextIntent(SchedulesActivity.class);
                finish();
            }
        });

        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalController.NextIntent(GamesActivity.class);
                finish();
            }
        });
    }

    public void onBackPressed(){
        if(flag == 1){
            finish();
        }
        else{
            flag++;
            Toast.makeText(MainActivity.this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
        }

    }

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }

//    public void saveHighlights() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(JumpApi.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JumpApi api = retrofit.create(JumpApi.class);
//        Call<JumpModel> call = api.getJump();
//
//        call.enqueue(new Callback<JumpModel>() {
//            @SuppressLint("ClickableViewAccessibility")
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onResponse(Call<JumpModel> call, retrofit2.Response<JumpModel> response) {
//                JumpModel standings = response.body();
//                JumpModel teamsList = standings;
//
//                String changedUrl = "<div style='width:100%;height:0px;position:relative;padding-bottom:56.250%;'><iframe src='" +
//                        teamsList.getName() +
//                        "' frameborder='0' width='100%' height='100%' fullscreen' style='width:100%;height:120%;position:absolute;left:0px;top:0px;overflow:hidden;'></iframe></div>";
//
//
//                if(Integer.parseInt(teamsList.getStatus()) == 1){
//                    webView.loadData(changedUrl, "text/html", "UTF-8");
//                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa  taninga!" + teamsList.getName());
//                    setUrl(teamsList.getName());
//                    globalController.stopLoading();
//                    webView.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getUrl()));
//                            startActivity(intent);
//                            return false;
//                        }
//                    });
//                }else{
//                    webView.setVisibility(View.INVISIBLE);
//                    globalController.stopLoading();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<JumpModel> call, Throwable t) {
//                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa  taninga!" + t.getMessage());
////                setErrors(PLAYERS,t.getMessage());
//                globalController.stopLoading();
//            }
//        });
//    }
}