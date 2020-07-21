package com.github.nikeapp.view;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UrbanApi {


    public void results() {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://mashape-community-urban-dictionary.p.rapidapi.com/define?term=wat")
                    .get()
                    .addHeader("x-rapidapi-host", "mashape-community-urban-dictionary.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "62e2eab0d7mshe0129a42c2d722ap119deejsn4d4b8a17a024")
                    .build();

            try {
                Response response = client.newCall(request).execute();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
