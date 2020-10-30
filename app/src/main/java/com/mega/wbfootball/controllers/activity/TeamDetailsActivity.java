package com.mega.wbfootball.controllers.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mega.wbfootball.R;
import com.mega.wbfootball.controllers.GlobalController;
import com.mega.wbfootball.model.TeamsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamDetailsActivity extends AppCompatActivity {
    GlobalController globalController;
    TextView teamName;
    TextView alternate;
    TextView formed;
    TextView stadium;
    TextView leagues;
    TextView stadiumDescription;
    TextView stadiumLocation;
    TextView stadiumCapacity;
    ImageView teamBadge;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);
        String passedArg = getIntent().getExtras().getString("data");
        globalController = new GlobalController(TeamDetailsActivity.this);
        teamName = findViewById(R.id.team_details_team_name);
        alternate = findViewById(R.id.team_details_alternate);
        formed = findViewById(R.id.team_details_formed);
        stadium = findViewById(R.id.team_details_stadium_name_key);
        leagues = findViewById(R.id.team_details_leagues);
        stadiumDescription = findViewById(R.id.team_details_stadium_details);
        stadiumLocation = findViewById(R.id.team_details_stadium_location);
        stadiumCapacity = findViewById(R.id.team_details_capacity);
        teamBadge = findViewById(R.id.team_details_team_badge);

        List<TeamsModel.Teams> teams = globalController.retrieveTeams();


        for(int i = 0; i < teams.size(); i++){
            TeamsModel.Teams team = teams.get(i);
            if(team.getIdTeam().equals(passedArg)){
                teamName.setText(team.getStrTeam());
                alternate.setText("\""+team.getStrAlternate()+ "\"");
                formed.setText("Formed: "+team.getIntFormedYear());
                stadium.setText(team.getStrStadium() + "(" + team.getStrKeywords() + ")");
                leagues.setText(team.getStrLeague() + "\n" + team.getStrLeague2() + "\n" + team.getStrLeague3() +  "\n" + team.getStrLeague4() +  "\n" + team.getStrLeague5());
                stadiumDescription.setText(team.getStrDescriptionEN());
                stadiumLocation.setText("Location: "+team.getStrStadiumLocation());
                stadiumCapacity.setText("Capacity: "+team.getIntStadiumCapacity());
                Picasso.get().load(team.getStrTeamBadge()).into(teamBadge);
//                teamBadge.setText(team.getStrTeam());
                break;
            }
        }


    }
}