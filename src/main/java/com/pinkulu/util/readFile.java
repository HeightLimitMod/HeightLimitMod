package com.pinkulu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.JSONObject;

public class readFile {
    public static boolean isInvalid;
    public static int limit;

    public static void read(String gametype, String map){
        try {
            File myObj = new File("./config/HeightLimitMod/limits.json");
            Scanner myReader = new Scanner(myObj);
            StringBuilder final_string = new StringBuilder();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                final_string.append(data);
            }
            JSONObject obj = new JSONObject(String.valueOf(final_string));
            if(obj.has(gametype.toLowerCase())) {
                if (obj.getJSONObject(gametype.toLowerCase()).has(map.toLowerCase())) {
                    limit = obj.getJSONObject(gametype.toLowerCase()).getInt(map.toLowerCase());
                    isInvalid = false;
                }
                else isInvalid = true;
            }
            else isInvalid = true;
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            isInvalid = true;
            e.printStackTrace();
        }
    }
}
