package main;

public class Profile {
    private int[] profile;
    protected int len;
    public Profile(int p){
        this.len=p;
        profile=new int[p];
    }
    public String toString(){
        String s=new String();
        s+="[";
        for(int i:profile){s+=Integer.toString(i);}
        s+="]";
        return s;
    }
    public int getProfileElement(int i){
        return profile[i];
    }
    public void setProfileElement(int i,int e){
        profile[i]=e;
    }
    public int[] getProfile(){
        return profile;
    }
    }