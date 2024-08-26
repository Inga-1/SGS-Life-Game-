package main;

public class SimpleMember extends CultMember{
    //concrete implementation of CultMember
    public SimpleMember(Player p){
        super(p);
        this.stats.setFaith(5);
    }
}
