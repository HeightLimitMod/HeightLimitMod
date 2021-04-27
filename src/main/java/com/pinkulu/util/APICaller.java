package com.pinkulu.util;

import club.sk1er.mods.core.util.Multithreading;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class APICaller {
    public static int limit;
    public static String Version;
    public static String Info;
    public static boolean isInvalid;
    public static void get(String Mode, String MapName) {
        OkHttpClient client = new OkHttpClient();
        Multithreading.runAsync(() -> {
            Request request = new Request.Builder()
                    .url("https://api.pinkulu.com/HeightLimitMod/BedWars/" + Mode + "/" + MapName)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String myRespones = response.body().string();
                        if (myRespones.contains("Invalid map name")) {
                            isInvalid = true;
                        } else {
                            JsonResponse Jresponse = new Gson().fromJson(myRespones, JsonResponse.class);
                            isInvalid = false;
                            limit = Jresponse.Limit;
                        }
                    }
                }
            });
        });
    }
    public static void getVersion() {
        OkHttpClient client = new OkHttpClient();
        Multithreading.runAsync(() -> {
            Request request = new Request.Builder()
                    .url("https://api.pinkulu.com/HeightLimitMod/version")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String myRespones = response.body().string();
                        if (!myRespones.contains("error")) {
                            JsonResponse Jresponse = new Gson().fromJson(myRespones, JsonResponse.class);
                            Version = Jresponse.Version;
                            Info = Jresponse.Info;
                        }
                    }
                }
            });
        });
    }
}