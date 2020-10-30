package com.mega.wbfootball.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mega.wbfootball.R;
import com.mega.wbfootball.controllers.GlobalController;
import com.mega.wbfootball.controllers.activity.TeamDetailsActivity;
import com.mega.wbfootball.model.TeamsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder> {
    GlobalController globalController;
    Context context;
    List<TeamsModel.Teams> teamsList;
    TeamsModel.Teams team;
    public class TeamsViewHolder extends RecyclerView.ViewHolder {
        TextView teamName;
        TextView teamId;
        ImageView teamImage;
        public TeamsViewHolder(@NonNull View itemView) {
            super(itemView);

            teamId= itemView.findViewById(R.id.team_id);
            teamName = itemView.findViewById(R.id.team_name);
            teamImage = itemView.findViewById(R.id.team_logo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    globalController.NextIntent(TeamDetailsActivity.class, teamId.getText().toString());
//                    Toast.makeText(context,,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public TeamsAdapter(Context context, List<TeamsModel.Teams> teamsList){
        this.context = context;
        this.globalController = new GlobalController(context);
        this.teamsList = teamsList;
    }


    @NonNull
    @Override
    public TeamsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teams_list,parent,false);
        return new TeamsViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TeamsViewHolder holder, int position) {
        team = teamsList.get(position);
        holder.teamId.setText(team.getIdTeam());
        holder.teamName.setText(team.getStrTeam() + " / " + team.getStrStadium());
        Picasso.get().load(team.getStrTeamBadge()).into(holder.teamImage);
    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }
}
