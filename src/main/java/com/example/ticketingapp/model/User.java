package com.example.ticketingapp.model;

import com.example.ticketingapp.util.GlobalLogger;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String username;
    private String password;

    User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        GlobalLogger.logInfo("the Id is changed to " + id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        GlobalLogger.logInfo("the username is changed to " + username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        GlobalLogger.logInfo("the password is changed to " + password);
    }
}
