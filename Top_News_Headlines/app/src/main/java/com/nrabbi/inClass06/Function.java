// Nazmul Rabbi
// Function.java
// In Class Assignment 06 : ITCS 4180
// Group 20

package com.nrabbi.inClass06;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Function {
    public static String excuteGet(String targetURL, String urlParameters) {
        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("content-type", "application/json;  charset=utf-8");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(false);
            InputStream is;
            int status = connection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK)
                is = connection.getErrorStream();
            else
                is = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }

            rd.close();
            return response.toString();

        } catch (Exception e) {
            return null;

        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}
