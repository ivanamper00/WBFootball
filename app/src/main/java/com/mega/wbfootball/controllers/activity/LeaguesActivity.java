package com.mega.wbfootball.controllers.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mega.wbfootball.R;
import com.mega.wbfootball.adapters.LeaguesAdapter;
import com.mega.wbfootball.controllers.GlobalController;
import com.mega.wbfootball.model.LeagueModel;

import java.util.List;

public class LeaguesActivity extends AppCompatActivity {
    GlobalController globalController;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leagues);
        globalController = new GlobalController(LeaguesActivity.this);
        recyclerView = findViewById(R.id.leagues_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        List<LeagueModel.League> leagueList = globalController.retrieveLeagues();
        LeaguesAdapter adapter = new LeaguesAdapter(this, leagueList);
        recyclerView.setAdapter(adapter);

    }
    public void onBackPressed(){
        globalController.NextIntent(MainActivity.class);
        finish();
    }
}