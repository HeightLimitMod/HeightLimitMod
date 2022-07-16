package com.pinkulu.heightlimitmod.util;

import cc.polyfrost.oneconfig.utils.Multithreading;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class APICaller {
    public static String Version;
    public static String Info;
    private static boolean firstJoin = false;

    public static void get() {
        OkHttpClient client = new OkHttpClient();
        Multithreading.runAsync(() -> {
            Request request = new Request.Builder()
                    .url("https://maps.pinkulu.com/")
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
                    }
                }
            });
        });
    }
    /*
    public static void getVersion() {
        OkHttpClient client = new OkHttpClient();
        Multithreading.runAsync(() -> {
            Request request = new Request.Builder()
                    .url("https://maps.pinkulu.com/version.json")
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
                            if (!firstJoin && Config.shouldNotifyUpdate) {
                                firstJoin = true;
                                try {
                                    if (Double.parseDouble(APICaller.Version) > Double.parseDouble(HeightLimitMod.VERSION)) {
                                        EssentialAPI.getNotifications().push("Height Limit Mod", "Version: " +
                                                APICaller.Version + " is available.\nYour Version: "
                                                + HeightLimitMod.VERSION + ".\nChanges: " + APICaller.Info + ".\nClick Here to update.", 5f, () -> {
                                            UDesktop.browse(URI.create("https://modrinth.com/mod/hlm"));
                                            return Unit.INSTANCE;
                                        });
                                    }
                                } catch (NullPointerException | NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });
        });
    }
     */
}