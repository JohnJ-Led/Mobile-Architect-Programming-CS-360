package com.project.weightmanagementsystems;

public class Goals {

    private long mId;
    private long mUid;
    private String mGoal;
    private String mDate;

    public Goals(){}

    public Goals(long id){
        mId = id;
    }

    public long getId(){
        return mId;
    }
    public void setId(long id){
        mId = id;
    }
    public long getUid(){
        return mUid;
    }
    public void setUid(long Uid){
        mUid = Uid;
    }
    public String getGoal(){
        return mGoal;
    }
    public void setGoal(String goal){
        mGoal = goal;
    }
    public String getDate(){
        return mDate;
    }
    public void setDate(String Date){
        mDate = Date;
    }
}
