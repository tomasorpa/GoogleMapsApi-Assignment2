module com.example.googlemapsapiassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens com.example.googlemapsapiassignment to javafx.fxml;
    exports com.example.googlemapsapiassignment;
}