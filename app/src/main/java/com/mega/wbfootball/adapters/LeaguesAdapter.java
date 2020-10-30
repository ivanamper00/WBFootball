package com.mega.wbfootball.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mega.wbfootball.R;
import com.mega.wbfootball.controllers.GlobalController;
import com.mega.wbfootball.controllers.activity.MainActivity;
import com.mega.wbfootball.model.LeagueModel;

import java.util.List;

public class LeaguesAdapter extends RecyclerView.Adapter<LeaguesAdapter.LeagueViewHolder> {
    CountDownTimer countDownTimer;
    GlobalController globalController;
    Context context;
    List<LeagueModel.League> LeagueList;
    LeagueModel.League league;
    public class LeagueViewHolder extends RecyclerView.ViewHolder {
        TextView leagueName;
        TextView leagueId;
        public LeagueViewHolder(@NonNull View itemView) {
            super(itemView);
            leagueName = itemView.findViewById(R.id.leagues);
            leagueId = itemView.findViewById(R.id.leagues_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    globalController.startLoading();
                    loop();
                    globalController.clearContents();
                    globalController.setDefaultLeague(leagueId.getText().toString());
                    globalController.saveUpcoming();
                    globalController.saveHighlights();
                    globalController.saveTeams();
                    globalController.saveLeagues();
                }
            });
        }
    }

    public LeaguesAdapter (Context context, List<LeagueModel.League> LeagueList){
        this.LeagueList = LeagueList;
        this.context = context;
        globalController = new GlobalController(this.context);
    }

    @NonNull
    @Override
    public LeagueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.league_list,parent,false);
        return new LeagueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueViewHolder holder, int position) {
        league = LeagueList.get(position);
        holder.leagueName.setText(league.getStrLeague());
        holder.leagueId.setText(league.getIdLeague());
    }

    @Override
    public int getItemCount() {
        return LeagueList.size();
    }

    public void loop(){
        loadIntent();
    }

    public void loadIntent(){
        countDownTimer = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(globalController.retrieveUpcoming() == null ||
                        globalController.retrieveHighlights() == null ||
                        globalController.retrieveTeams() == null ||
                        globalController.retrieveLeagues() == null){
                    loop();
                }else{
                    globalController.stopLoading();
                    globalController.NextIntent(MainActivity.class);
                }

            }
        }.start();
    }

}
