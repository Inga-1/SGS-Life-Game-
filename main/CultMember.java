package main;
import java.util.Random;

public abstract class CultMember extends Player{
    public enum Role {
        LEADER,SHAMAN,PRIEST,NONE
    }
    protected Role role;
    public CultMember(int id,Size size,Game game){
        super(id,size,game);
        this.role=Role.NONE;
        this.cultMember=this;
    }
    public void pray(){
        int f=this.stats.getFaith();
        this.stats.setFaith(f+1);
    }
    public void question(){
        Random rand = new Random();
        int CoinFlip = rand.nextInt(1);
        if (super(FAITH) < 4 && super(WILL) > 2 && CoinFlip == 1) CultMember.escape();
        else {int f=this.stats.getFaith();
        this.stats.setFaith(f-1);}
    }
    public void escape(){
        this.cult.cult.remove(member);
        member.isMember=false;
        member.cultMember=null;
        System.out.println(member.id+"has escaped the cult.");
    }
    public void changeRole(Role r){
        role=r;
    }
}
