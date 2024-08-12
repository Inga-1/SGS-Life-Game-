package main;

public class Leader extends CultMember{

    public Leader(int id, Size size, Game game) {
        super(id, size,game);
        this.role=Role.LEADER;
    }
    //diff stats compared to others

    //kick out members
    public void kickOut(CultMember member){
        this.cult.cult.remove(member);
        member.isMember=false;
        member.cultMember=null;
        System.out.println(member.id+"has been kicked out from the Cult.");
    }

        //happens when  member tries to escape and fails --> hard work
        //or when member argues with leader -->kick out
        //or when member tries to kill leader --> kill member


    //elect priests and shaman
    public void electShaman(CultMember p){
        //make shaman class
    }
    public void electPriest(CultMember p){
        //make priest class
    }
}
