package com.group9.grouptivity.firebase;


import java.util.Collections;
import java.util.List;

public class GroupMessage {
    public String id;
    private String name;
    public List<UserAccount> users;

    public GroupMessage(String name){
        this.name = name;
    }

    /** Returns the name of the GroupMessage. */
    public String getName() {
        return this.name;
    }
}
