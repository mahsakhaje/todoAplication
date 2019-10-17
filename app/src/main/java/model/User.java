package model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private String UserName;
    private String password;
    private UUID id;

    public UUID getId() {
        return id;
    }

    public User() {
        this(UUID.randomUUID());

    }

    public User(UUID id) {
        this.id = id;


    }

    public String getUserName() {

        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
