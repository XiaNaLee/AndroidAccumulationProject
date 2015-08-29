package com.accumulation.lee.androidaccumulationproject.demo;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * com.accumulation.lee.utils.retrofit.demo
 * Created by lee on 15/8/18.
 * Email:lee131483@gmail.com
 */
public interface GitHubClient {
    @GET("/repos/{owner}/{repo}/contributors")
    rx.Observable<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo
    );

    static class Contributor {
        public String login;
        public int contributions;
    }
}
