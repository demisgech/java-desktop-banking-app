package com.codewithdemis.core;

import com.codewithdemis.models.User;

public class Session {
    private  static Session instance;
    private User currentUser;

    private Session(){

    }

    public static Session getInstance() {
        if(instance == null)
            instance = new Session();
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void login(User currentUser) {
        this.currentUser = currentUser;
    }
    public void logout(){
        this.currentUser = null;
    }

    public boolean isLoggedIn(){
        return this.currentUser != null;
    }
}
