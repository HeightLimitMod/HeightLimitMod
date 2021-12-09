package com.pinkulu.hlm.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pinkulu.hlm.events.HeightLimitListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtil {
    public static boolean isInvalid;
    public static int limit;
    private static final JsonParser PARSER = new JsonParser();

    public static void read(String gametype, String map) {
        try {
            File myObj = new File("./config/HeightLimitMod/limits.json");
            Scanner myReader = new Scanner(myObj);
            StringBuilder final_string = new StringBuilder();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                final_string.append(data);
            }
            JsonObject obj = PARSER.parse(String.valueOf(final_string)).getAsJsonObject();
            if (obj.has(gametype.toLowerCase())) {
                if (obj.getAsJsonObject(gametype.toLowerCase()).has(map.toLowerCase())) {
                    limit = obj.getAsJsonObject(gametype.toLowerCase()).get(map.toLowerCase()).getAsInt();
                    isInvalid = false;
                } else isInvalid = true;
            } else isInvalid = true;
            myReader.close();
        } catch (FileNotFoundException e) {
            try{
                APICaller.get();
                HeightLimitListener.shouldCheck = true;
                HeightLimitListener.checked = false;
            }catch (Exception ex){
                System.out.println("An error occurred.");
                isInvalid = true;
                e.printStackTrace();
            }
        }
    }
}
