package com.example.cg.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author zhangxiaoyu
 * @date 2021/3/12
 */
public class Mysql {
    private StringProperty host = new SimpleStringProperty("localhost");

    private StringProperty user = new SimpleStringProperty("");
    private StringProperty password = new SimpleStringProperty("");
    private StringProperty database = new SimpleStringProperty("");
    private StringProperty URL = new SimpleStringProperty("jdbc:mysql://localhost:3306");

    public String getHost() {
        return host.get();
    }

    public StringProperty hostProperty() {
        return host;
    }

    public String getUser() {
        return user.get();
    }

    public StringProperty userProperty() {
        return user;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getURL() {
        return URL.get();
    }

    public StringProperty URLProperty() {
        return URL;
    }

    public String getDatabase() {
        return database.get();
    }

    public StringProperty databaseProperty() {
        return database;
    }

}
