package com.mega.wbfootball.controllers.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.mega.wbfootball.R;
import com.mega.wbfootball.adapters.LiveGamesAdapter;
import com.mega.wbfootball.controllers.GlobalController;
import com.mega.wbfootball.database.LiveScoreApi;
import com.mega.wbfootball.model.LiveScoreModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GamesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GlobalController globalController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        globalController = new GlobalController(GamesActivity.this);
        globalController.startLoading();
        recyclerView = findViewById(R.id.live_games_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        getLiveGames();
    }

    public void onBackPressed(){
        globalController.NextIntent(MainActivity.class);
        finish();
    }

    public void getLiveGames(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LiveScoreApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LiveScoreApi api = retrofit.create(LiveScoreApi.class);

        Call<List<LiveScoreModel>> call = api.getLiveGames();

        call.enqueue(new Callback<List<LiveScoreModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<LiveScoreModel>> call, retrofit2.Response<List<LiveScoreModel>> response) {
                List<LiveScoreModel> standings = response.body();
                List<LiveScoreModel> teamsList = standings;
//                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+standings.get(1).getEmbed().substring(standings.get(1).getEmbed().indexOf("iframe src='") + 12,standings.get(1).getEmbed().indexOf("'", standings.get(1).getEmbed().indexOf("iframe src='") + 12)));
                LiveGamesAdapter adapter = new LiveGamesAdapter(GamesActivity.this, teamsList);
                recyclerView.setAdapter(adapter);
                globalController.stopLoading();
            }
            @Override
            public void onFailure(Call<List<LiveScoreModel>> call, Throwable t) {
                Toast.makeText(GamesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                setErrors(PLAYERS,t.getMessage());
            }
        });
    }
}