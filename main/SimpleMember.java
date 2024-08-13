package main;

public class SimpleMember extends CultMember{
    //concrete implementation of CultMember
    public SimpleMember(int id,Size size,Game game){
        super(id,size,game);
        this.stats.setFaith(5);
    }
    //add makeShaman and makePriest on the model of makeMember
}
