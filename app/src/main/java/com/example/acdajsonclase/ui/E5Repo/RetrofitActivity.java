package com.example.acdajsonclase.ui.E5Repo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acdajsonclase.R;
import com.example.acdajsonclase.network.ApiAdapter;
import com.example.acdajsonclase.network.ApiService;
import com.example.acdajsonclase.network.RestClient;
import com.example.acdajsonclase.ui.E3ContactosGson.model.Person;
import com.example.acdajsonclase.ui.E5Repo.model.Repo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * link de repos de la api de github
 * https://api.github.com/users/MiguelRJ/repos
 */
public class RetrofitActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtUser;
    Button btnRepo;
    RecyclerView rvRepos;

    private ReposAdapter adapter;
    private ArrayList<Repo> repos;
    private String WEB;
    private String USER;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        edtUser = (EditText) findViewById(R.id.edtUser);
        btnRepo = (Button) findViewById(R.id.btnRepo);
        btnRepo.setOnClickListener(this);
        rvRepos = (RecyclerView) findViewById(R.id.rvRepo);
        adapter = new ReposAdapter();
        rvRepos.setAdapter(adapter);
        rvRepos.setLayoutManager(new LinearLayoutManager(this));

        rvRepos.addOnItemTouchListener(new RecyclerTouchListener(this,rvRepos, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Uri uri = Uri.parse((String) repos.get(position).getHtmlUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(), "No hay un navegador",
                            Toast.LENGTH_SHORT).show();
                Toast.makeText(RetrofitActivity.this, "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(RetrofitActivity.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));

        Gson gson = new GsonBuilder()
                .setDateFormat("dd-MM-yyy'T'HH:mm:ssZ")
                .create();

        apiService = ApiAdapter.getApiService();
    }

    @Override
    public void onClick(View view) {
        if (view == btnRepo) {

            USER = edtUser.getText().toString();

            if (USER.isEmpty()){

                showError("Indica un usuario");

            } else {

                WEB = "https://api.github.com/users/" + USER + "/repos";

                Call<ArrayList<Repo>> call = ApiAdapter.getApiService().reposForUser(USER);

                call.enqueue(new Callback<ArrayList<Repo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Repo>> call, Response<ArrayList<Repo>> response) {
                        // si recibe respuesta entra aqui aunque sea un error 404
                        int statusCode = response.code();
                        if (response.isSuccessful()) {
                            repos = response.body();
                            adapter.setRepos(repos);
                            showError("response");
                        } else {
                            StringBuilder message = new StringBuilder();
                            message.append("Error en la descarga: "+response.code()+"\n");
                            if (response.body() != null){
                                message.append(response.body()+"\n");
                            }
                            if (response.errorBody() != null){
                                message.append(response.errorBody()+"\n");
                            }
                            showError(message.toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Repo>> call, Throwable t) {
                        if (t != null){
                            showError("Fallo en la comunicacion: "+t.getMessage());
                        }
                    }
                });

            }

        }
    }


    /**
            * Para mostrar errores hara un Toast y aparte mostrara el error en pantalla
     *
             * @param message
     */
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
