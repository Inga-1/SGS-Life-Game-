package main;

import java.util.Arrays;

public class Profile {
    private Messages[] profile;
    protected int len;
    public Profile(Size s){
        int p=s.maxSize;
        this.len=p;
        profile=new Messages[p];
        Arrays.fill(profile,Messages.NONE);
    }
    public String toString(){
        String s=new String();
        s+="[";
        for(Messages i:profile){if(i!=null){s+=" "+i.toString();}else{s+=" no messages";}}
        s+="]";
        return s;
    }
    public Messages getProfileElement(int i){
        return profile[i];
    }
    public void setProfileElement(int i,Messages e){
        profile[i]=e;
    }
    public Messages[] getProfile(){
        return profile;
    }
    }