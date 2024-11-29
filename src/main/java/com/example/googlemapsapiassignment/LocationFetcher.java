package com.example.googlemapsapiassignment;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LocationFetcher {

    public static JSONObject getLocationDataFromGoogle(String place, String apiKey) {
        place = place.replaceAll(" ", "+");
        String geocodeUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + place + "&key=" + apiKey;

        try {
            HttpURLConnection apiConnection = fetchApiResponse(geocodeUrl);
            if (apiConnection.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to Google Geocoding API");
                return null;
            }

            String jsonResponse = readApiResponse(apiConnection);
            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);
            JSONArray results = (JSONArray) resultsJsonObj.get("results");
            if (results.size() > 0) {
                JSONObject locationData = (JSONObject) ((JSONObject) results.get(0)).get("geometry");
                JSONObject location = (JSONObject) locationData.get("location");
                double latitude = (double) location.get("lat");
                double longitude = (double) location.get("lng");

                JSONObject locationDetails = new JSONObject();
                locationDetails.put("latitude", latitude);
                locationDetails.put("longitude", longitude);
                return locationDetails;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String readApiResponse(HttpURLConnection apiConnection) {
        try {
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(apiConnection.getInputStream());
            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }
            scanner.close();
            return resultJson.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
