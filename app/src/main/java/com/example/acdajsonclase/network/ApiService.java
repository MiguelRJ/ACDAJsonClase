package com.example.acdajsonclase.network;

import com.example.acdajsonclase.ui.E5Repo.model.Repo;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by usuario on 6/02/18.
 */

public interface ApiService {

    /**
     * Llamada teniendo en cuenta la base url creada en apiadapter
     * @param username
     * @return
     */
    @GET("/users/{username}/repos")
    Call<ArrayList<Repo>> reposForUser(@Path("username") String username);

}
