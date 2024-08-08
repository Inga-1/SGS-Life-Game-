package main;

abstract class CultMember extends Player{
    public enum Role {
        LEADER,SHAMAN,PRIEST,NONE
    }
    private Role role;
    public CultMember(int id,int b,int p){
        super(id,b,p);
        this.role=Role.NONE;
    }
    public void pray(){
        //changes stats
    }
    public void changeRole(Role r){
        role=r;
    }
}
