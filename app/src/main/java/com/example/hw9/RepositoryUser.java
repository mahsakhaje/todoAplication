package com.example.hw9;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.User;

public class RepositoryUser {
    private static RepositoryUser instance;
    private User mUser;
    List<User> users;

    public static RepositoryUser getInstance() {
        if (instance == null)
            instance = new RepositoryUser();
        return instance;
    }

    private RepositoryUser() {
        users = new ArrayList<User>();
    }

    public void addUser(User user) {

        users.add(user);
    }

    public void deletUser(User user) {
        for (User user1 : users) {
            if (user1.getId() == user.getId())
                users.remove(user1);
        }

    }
    public List<User> getUsers(){
        return users;
    }

}
