package com.example.toshiba.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.toshiba.retrofit.Adapter.AdapterForGitHubRepo;
import com.example.toshiba.retrofit.service.gitHubClient;
import com.example.toshiba.retrofit.service.gitHubRepo;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // list to show the data return from api
        final RecyclerView listView = (RecyclerView) findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(this));
        //
        // create okHttp logging
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        // add headers for each request
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // add header in all request
              Request.Builder  newRequest =  request.newBuilder().addHeader("Auzthorizan","annjada");


                return chain.proceed(newRequest.build());
            }
        });

        HttpLoggingInterceptor  loggingInterceptor= new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if(BuildConfig.DEBUG){
            okHttpClient.addInterceptor(loggingInterceptor);
        }


        // create connection
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build());

        Retrofit retrofit = builder.build();
        gitHubClient gitHubClient =  retrofit.create(gitHubClient.class);
        Call<List<gitHubRepo>> call =gitHubClient.reposForUser("fs-opensource");
        call.enqueue(new Callback<List<gitHubRepo>>() {
            @Override
            public void onResponse(Call<List<gitHubRepo>> call, Response<List<gitHubRepo>> response) {
                  if(response.body()!=null){
                      List<gitHubRepo> result =response.body();
                      AdapterForGitHubRepo  adapterForGitHubRepo = new AdapterForGitHubRepo(getApplicationContext(),result);
                      listView.setAdapter(adapterForGitHubRepo);
                  }

            }

            @Override
            public void onFailure(Call<List<gitHubRepo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error :(",Toast.LENGTH_LONG).show();
            }
        });

    }
}
