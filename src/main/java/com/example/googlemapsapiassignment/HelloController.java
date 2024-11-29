package com.example.googlemapsapiassignment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

public class HelloController {

    @FXML
    private TextField locationField;

    @FXML
    private Button fetchButton;

    @FXML
    private TextArea resultArea;

    private static final String GOOGLE_API_KEY = "AIzaSyBaJTjqGvWR7MItf2ruUE1ILLb92lbqnuk";

    @FXML
    public void initialize() {
        fetchButton.setOnAction(event -> {
            String location = locationField.getText().trim();
            if (location.isEmpty()) {
                resultArea.setText("Please enter a location.");
                return;
            }

            try {
                JSONObject locationData = LocationFetcher.getLocationDataFromGoogle(location, GOOGLE_API_KEY);
                if (locationData != null) {
                    double latitude = (double) locationData.get("latitude");
                    double longitude = (double) locationData.get("longitude");
                    resultArea.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
                } else {
                    resultArea.setText("Location not found.");
                }
            } catch (Exception e) {
                resultArea.setText("Error fetching data: " + e.getMessage());
            }
        });
    }
}
