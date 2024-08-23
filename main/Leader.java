package main;

public class Leader extends CultMember{

    public Leader(int id, Game game) {
        super(id,game);
        this.role=Role.LEADER;
        this.stats.setCharisma(4);
        this.stats.setWillpower(4);
        this.stats.setFaith(10);
        this.isLeader=true;
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
}
