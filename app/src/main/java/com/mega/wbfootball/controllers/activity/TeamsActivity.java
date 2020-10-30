package com.mega.wbfootball.controllers.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mega.wbfootball.R;
import com.mega.wbfootball.adapters.TeamsAdapter;
import com.mega.wbfootball.controllers.GlobalController;
import com.mega.wbfootball.model.TeamsModel;

import java.util.List;

public class TeamsActivity extends AppCompatActivity {
    GlobalController globalController;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        globalController = new GlobalController(TeamsActivity.this);
        recyclerView = findViewById(R.id.teams_recycler);
        List<TeamsModel.Teams> teams = globalController.retrieveTeams();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        TeamsAdapter adapter = new TeamsAdapter(TeamsActivity.this, teams);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed(){
        globalController.NextIntent(MainActivity.class);
        finish();
    }
}