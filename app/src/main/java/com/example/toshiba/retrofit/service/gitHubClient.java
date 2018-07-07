package com.example.toshiba.retrofit.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface gitHubClient {

    @GET("/users/{user}/repos")
    Call<List<gitHubRepo>> reposForUser(@Path("user") String userName);

}
