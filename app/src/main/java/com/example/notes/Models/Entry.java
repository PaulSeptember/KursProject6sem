package com.example.notes.Models;

public class Entry {
    public String password;
    public String login;
    public String title;

    public Entry(String t, String l, String p){
        password = p;
        login = l;
        if (t.isEmpty()){
            title = "Unnamed";
        }else {
            title = t;
        }
    }

    public Entry(String l, String p){
        password = p;
        login = l;
        title = "Unnamed";
    }
}
