package com.group9.grouptivity;

import java.util.List;

public class User {
    public String emailAddress;
    public List<Group> groupMessages;
    public List<String> invites;
    public String password;

    public User(String emailAddress){
        this.emailAddress = emailAddress;
    }
}
