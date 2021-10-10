package com.pinkulu.util;

import com.google.gson.Gson;
import gg.essential.api.utils.Multithreading;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.Sys;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class APICaller {
    public static String Version;
    public static String Info;
    public static void get() {
        OkHttpClient client = new OkHttpClient();
        Multithreading.runAsync(() -> {
            Request request = new Request.Builder()
                    .url("https://api.pinkulu.com/HeightLimitMod/Limits")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        String myRespones = response.body().string();
                        BufferedWriter writer = new BufferedWriter(new FileWriter("./config/HeightLimitMod/limits.json"));
                        writer.write(myRespones);

                        writer.close();
                        System.out.println("thingy: " + writer);
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
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
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