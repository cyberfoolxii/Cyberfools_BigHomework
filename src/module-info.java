module OOP.BigHomework {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires java.net.http;
    requires java.security.jgss;
    requires com.google.gson;

    exports Application.java;
    opens Application.java;
}