package com.mega.wbfootball.database;

import com.mega.wbfootball.model.EventsModel;
import com.mega.wbfootball.model.LeagueModel;
import com.mega.wbfootball.model.TeamsModel;
import com.mega.wbfootball.model.UpcomingModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SoccerApi {
    String BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/";


    @GET("lookup_all_teams.php")
    Call<TeamsModel> getTeams(@Query("id") String id);

    @GET("eventsnextleague.php")
    Call<UpcomingModel> getUpcoming(@Query("id") String id);

    @GET("eventspastleague.php")
    Call<EventsModel> getHighlights(@Query("id") String id);

    @GET("all_leagues.php")
    Call<LeagueModel> getLeagues();
//    id=4328
}
