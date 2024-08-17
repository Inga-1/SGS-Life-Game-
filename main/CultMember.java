package main;

public abstract class CultMember extends Player{
    public enum Role {
        LEADER,SHAMAN,PRIEST,NONE
    }
    protected Role role;
    public CultMember(int id,Size size,Game game){
        super(id,size,game);
        this.role=Role.NONE;
        this.cultMember=this;
        this.isMember=true;
    }
    public void pray(){
        int f=this.stats.getFaith();
        this.stats.setFaith(f+1);
    }
    public void question(){
        int f=this.stats.getFaith();
        this.stats.setFaith(f-1);
    }
    public void changeRole(Role r){
        role=r;
    }
}
