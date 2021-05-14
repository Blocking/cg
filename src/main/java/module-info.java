module com.example.cg.demo {

    requires javafx.controls;
    requires com.dlsc.formsfx;
    requires velocity.engine.core;
    requires lombok;
    requires commons.lang3;
    requires slf4j.api;
    requires java.sql;
    requires mysql.connector.java;
    exports com.example.cg.bean;
    exports com.example.cg.demo;
}