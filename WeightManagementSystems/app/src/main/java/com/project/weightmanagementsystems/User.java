package com.project.weightmanagementsystems;

public class User {

    private long mId;
    private String mUser;
    private String mPassword;
    private Goals mGoals;

    public User(){}

    public User(long id){
        mId = id;
    }

    public long getId(){
        return mId;
    }
    public void setId(long id){
        mId = id;
    }
    public String getUser(){
        return mUser;
    }
    public void setUser(String user){
        mUser = user;
    }
    public String getPassword(){
        return mPassword;
    }
    public void setPassword(String password){
        mPassword = password;
    }
    public Goals getGoals(){
        return mGoals;
    }
    public void setGoals(Goals goals){
        mGoals = goals;
    }

}
