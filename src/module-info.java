module OOP.BigHomework {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires java.net.http;
    requires java.security.jgss;
    requires async.http.client.netty.utils;
    requires async.http.client;
    requires org.slf4j;

    exports Application.java;
    opens Application.java;
}