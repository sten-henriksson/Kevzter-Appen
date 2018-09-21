package com.example.oauthtest.kevzterfinal;

public class Globals{
    private static Globals instance;

    // Global variable
    private int data = 0;
    private String user;
    private String email;
    private String picture;
    // Restrict the constructor from being instantiated
    private Globals(){}

    public void setData(int d){
        this.data=d;
    }
    public int getData(){
        return this.data;
    }
    public String getUser(){
        return this.user;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPicture(){
        return this.picture;
    }
    public void setUser(String d){
        this.user=d;
    }
    public void setEmail(String d){
        this.email=d;
    }
    public void setPicture(String d){
        this.picture=d;
    }


    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}