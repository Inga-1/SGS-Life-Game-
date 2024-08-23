package main;

public class SimpleMember extends CultMember{
    //concrete implementation of CultMember
    public SimpleMember(int id,Game game){
        super(id,game);
        this.stats.setFaith(5);
    }
}
