package main;

public abstract class CultMember extends Player{
    public enum Role {
        LEADER,SHAMAN,PRIEST,NONE
    }
    protected Role role;
    public CultMember(int id,int size,Cult cult){
        super(id,size,cult);
        this.role=Role.NONE;
    }
    public void pray(){
        //changes faith stats
    }
    public void hardWork(){
        //lowers health and happiness
    }
    public void changeRole(Role r){
        role=r;
    }
}
