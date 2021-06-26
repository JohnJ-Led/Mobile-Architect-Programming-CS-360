package com.project.weightmanagementsystems;

public class Weight {

    private long mId;
    private long mUid;
    private String mWeight;
    private String mDate;
    private String mUnits;

    public Weight(){}

    public Weight(long id){
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
    public String getWeight(){
        return mWeight;
    }
    public void setWeight(String weight){
        mWeight = weight;
    }

    public String getUnits(){
        return mUnits;
    }
    public void setUnits(String units){
        mUnits = units;
    }

    public String getDate(){
        return mDate;
    }
    public void setDate(String Date){
        mDate = Date;
    }

}
