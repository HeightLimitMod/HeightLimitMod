package com.pinkulu.hlm.util;

import com.pinkulu.hlm.HeightLimitMod;
import com.pinkulu.hlm.events.HeightLimitListener;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtil {
    public static boolean isInvalid;
    public static int limit;

    public static void read(String gametype, String map) {
        try {
            File myObj = new File("./config/HeightLimitMod/limits.json");
            Scanner myReader = new Scanner(myObj);
            StringBuilder final_string = new StringBuilder();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                final_string.append(data);
            }
            JSONObject obj = new JSONObject(String.valueOf(final_string));
            if (obj.has(gametype.toLowerCase())) {
                if (obj.getJSONObject(gametype.toLowerCase()).has(map.toLowerCase())) {
                    limit = obj.getJSONObject(gametype.toLowerCase()).getInt(map.toLowerCase());
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
