package com.mega.wbfootball.controllers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mega.wbfootball.database.SoccerApi;
import com.mega.wbfootball.model.EventsModel;
import com.mega.wbfootball.model.LeagueModel;
import com.mega.wbfootball.model.TeamsModel;
import com.mega.wbfootball.model.UpcomingModel;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class GlobalController {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String UPCOMING_EVENTS = "upcoming";
    public static final String HIGHLIGHTS = "highlights";
    public static final String UPDATES = "updates";
    public static final String GAMES_SEASON_LEAGUE = "stats";
    public static final String TEAMS_SEASON_LEAGUE = "teams";
    public static final String LEAGUE = "league";
    public static final String STANDING_SEASON_LEAGUE = "standings";
    public static final String CURRENT_LEAGUE = "current_league";
    public static final String CURRENT_SEASON = "Current season";
    public static final String TEAMS_SEASON_LEAGUE_ERROR = "teams_err";
    public static final String LEAGUE_ERROR = "league_err";
    public static final String UPCOMING_EVENTS_ERROR = "upcoming_err";
    public static final String HIGHLIGHTS_ERROR = "highlights_err";

    ProgressDialog pdLoading;
    Context context;
    Intent intent;
    Activity activity;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HashMap<String, String> errors;

    @SuppressLint("CommitPrefEdits")
    public GlobalController(Context context){
        this.pdLoading = new ProgressDialog(context);
        this.context = context;
        this.activity = (Activity) context;
        this.intent = activity.getIntent();
        this.sharedPreferences = this.context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public String getErrors(String key) {
        return sharedPreferences.getString(key,"");
    }

    public void setErrors(String key, String value) {
        editor.putString(key ,value);
        editor.commit();
    }

    public String getDefaultLeague(){
        return sharedPreferences.getString(CURRENT_LEAGUE,"4328");
    }
    public void setDefaultLeague(String league){
        editor.putString(CURRENT_LEAGUE ,league);
        editor.commit();
    }

    public void clearContents(){
        this.editor.clear();
        this.editor.commit();
    }

    //Next Intent w/ Data Function
    public void NextIntent(Class toClass, String data) {
        Intent intent = new Intent(context, toClass);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }
    //Next Intent w/o Data Function
    public void NextIntent(Class toClass) {
        NextIntent(toClass, "");
    }

    //Retrofit Builder Function
    public SoccerApi getRetrofitBuilder() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SoccerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SoccerApi api = retrofit.create(SoccerApi.class);
        return api;
    }

    // Loading Function
    public void startLoading(String text){
        pdLoading.setMessage("\t" + text);
        pdLoading.setCancelable(false);
        pdLoading.show();
    }
    public void startLoading(){
        startLoading("Please Wait...");
    }
    public void stopLoading(){
        pdLoading.dismiss();
    }


    public <T> void saveData(String calling, List<T> object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        editor.putString(calling ,json);
        editor.commit();
    }

    public List<TeamsModel.Teams> retrieveTeams(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(TEAMS_SEASON_LEAGUE,"");
        Type type = new TypeToken<List<TeamsModel.Teams>>(){}.getType();
        List<TeamsModel.Teams> objects = gson.fromJson(json, type);
        return objects;
    }
    public List<UpcomingModel.Event> retrieveUpcoming(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(UPCOMING_EVENTS,"");
        Type type = new TypeToken<List<UpcomingModel.Event>>(){}.getType();
        List<UpcomingModel.Event> objects = gson.fromJson(json, type);
        return objects;
    }
    public List<EventsModel.Event> retrieveHighlights(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(HIGHLIGHTS,"");
        Type type = new TypeToken<List<EventsModel.Event>>(){}.getType();
        List<EventsModel.Event> objects = gson.fromJson(json, type);
        return objects;
    }

    public List<LeagueModel.League> retrieveLeagues(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LEAGUE,"");
        Type type = new TypeToken<List<LeagueModel.League>>(){}.getType();
        List<LeagueModel.League> objects = gson.fromJson(json, type);
        return objects;
    }

    public void saveTeams(String id){

        Call<TeamsModel> call = getRetrofitBuilder().getTeams(id);

        call.enqueue(new Callback<TeamsModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<TeamsModel> call, retrofit2.Response<TeamsModel> response) {
                TeamsModel standings = response.body();
                List<TeamsModel.Teams> teamsList = standings.getTeams();
                saveData(TEAMS_SEASON_LEAGUE, teamsList);
//                setSuccess(PLAYERS,"Players..");
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Teams Saved!");
            }
            @Override
            public void onFailure(Call<TeamsModel> call, Throwable t) {
//                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                setErrors(TEAMS_SEASON_LEAGUE_ERROR,t.getMessage());
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+ t.getMessage());
            }
        });
    }

    public void saveUpcoming(String id){

        Call<UpcomingModel> call = getRetrofitBuilder().getUpcoming(id);

        call.enqueue(new Callback<UpcomingModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<UpcomingModel> call, retrofit2.Response<UpcomingModel> response) {
                UpcomingModel standings = response.body();
                List<UpcomingModel.Event> teamsList = standings.getEvents();
                saveData(UPCOMING_EVENTS, teamsList);
//                setSuccess(PLAYERS,"Players..");
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Upcoming Saved!");
            }
            @Override
            public void onFailure(Call<UpcomingModel> call, Throwable t) {
//                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                setErrors(UPCOMING_EVENTS_ERROR,t.getMessage());
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+ t.getMessage());
            }
        });
    }

    public void saveHighlights(String id){

        Call<EventsModel> call = getRetrofitBuilder().getHighlights(id);

        call.enqueue(new Callback<EventsModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<EventsModel> call, retrofit2.Response<EventsModel> response) {
                EventsModel standings = response.body();
                List<EventsModel.Event> teamsList = standings.getEvents();
                saveData(HIGHLIGHTS, teamsList);
//                setSuccess(PLAYERS,"Players..");
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Highlights Saved!");
            }
            @Override
            public void onFailure(Call<EventsModel> call, Throwable t) {
//                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                setErrors(HIGHLIGHTS_ERROR,t.getMessage());
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+ t.getMessage());
            }
        });
    }

    public void saveLeagues(){

        Call<LeagueModel> call = getRetrofitBuilder().getLeagues();

        call.enqueue(new Callback<LeagueModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<LeagueModel> call, retrofit2.Response<LeagueModel> response) {
                LeagueModel gamesModel = response.body();
                List<LeagueModel.League> gameResponse = gamesModel.getLeagues();
                saveData(LEAGUE, gameResponse);
//                setSuccess(PLAYERS,"Players..");
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Leagues Saved!");
            }
            @Override
            public void onFailure(Call<LeagueModel> call, Throwable t) {
//                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                setErrors(LEAGUE_ERROR,t.getMessage());
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+ t.getMessage());
            }
        });
    }

    public void saveTeams(){
        saveTeams(getDefaultLeague());
    }
    public void saveUpcoming(){
        saveUpcoming(getDefaultLeague());
    }
    public void saveHighlights(){
        saveHighlights(getDefaultLeague());
    }

//
//
//    public void saveTeams(String season, int league){
//
//        Call<TeamsModel> call = getRetrofitBuilder().getTeams(season,league);
//
//        call.enqueue(new Callback<TeamsModel>() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onResponse(Call<TeamsModel> call, retrofit2.Response<TeamsModel> response) {
//                TeamsModel teamsModel = response.body();
//                List<TeamsModel.Response> teamsResponse = teamsModel.getResponse();
//                saveData(TEAMS_SEASON_LEAGUE, teamsResponse);
////                setSuccess(PLAYERS,"Players..");
//                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Teams Saved!");
//            }
//            @Override
//            public void onFailure(Call<TeamsModel> call, Throwable t) {
//                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
////                setErrors(PLAYERS,t.getMessage());
//            }
//        });
//    }
//
//    public void saveGames(){
//        saveGames(getDefaultSeason(),getDefaultLeague());
//    }
//    public void saveTeams(){
//        saveTeams(getDefaultSeason(),getDefaultLeague());
//    }
//    public void saveStandings(){
//        saveStandings(getDefaultSeason(),getDefaultLeague());
//    }
//
//
}
