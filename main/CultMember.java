package main;

public abstract class CultMember extends Player{
    public enum Role {
        LEADER,SHAMAN,PRIEST,NONE
    }
    protected Role role;
    public CultMember(int id,Size size,Game game){
        super(id,size,game);
        this.role=Role.NONE;
    }
    public void pray(){
        //changes faith stats
    }
    public void question(){
        //lowers faith
    }
    public void hardWork(){
        //lowers health and happiness
    }
    public void changeRole(Role r){
        role=r;
    }
}
