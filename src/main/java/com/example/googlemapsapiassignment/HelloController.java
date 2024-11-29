package com.example.googlemapsapiassignment;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.Map;

public class HelloController {

    @FXML
    private TextField pointAField;
    @FXML
    private TextField pointBField;
    @FXML
    private TextArea resultTextArea;

    @FXML
    public void calculateDistance() {
        String pointA = pointAField.getText();
        String pointB = pointBField.getText();

        if (pointA.isEmpty() || pointB.isEmpty()) {
            resultTextArea.setText("Both Point A and Point B must be entered.");
            return;
        }
        try {
            LocationFetcher fetcher = new LocationFetcher();
            Map<String, Double> pointACoords = fetcher.getLocationDataFromGoogle(pointA, "AIzaSyBaJTjqGvWR7MItf2ruUE1ILLb92lbqnuk");
            Map<String, Double> pointBCoords = fetcher.getLocationDataFromGoogle(pointB, "AIzaSyBaJTjqGvWR7MItf2ruUE1ILLb92lbqnuk");
            if (pointACoords == null || pointBCoords == null) {
                resultTextArea.setText("Error: Could not fetch coordinates for one or both locations.");
                return;
            }
            double pointALat = pointACoords.get("latitude");
            double pointALng = pointACoords.get("longitude");
            double pointBLat = pointBCoords.get("latitude");
            double pointBLng = pointBCoords.get("longitude");
            double distance = calculateHaversineDistance(pointALat, pointALng, pointBLat, pointBLng);
            resultTextArea.setText("The distance between " + pointA + " and " + pointB + " is: " + distance + " km");
        } catch (Exception e) {
            resultTextArea.setText("An error occurred: " + e.getMessage());
        }
    }



    private double calculateHaversineDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
