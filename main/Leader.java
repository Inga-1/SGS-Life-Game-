package main;
import java.util.Random;

public class Leader extends CultMember{

    public Leader(Player p) {
        super(p);
        this.role=Role.LEADER;
        this.stats.setCharisma(4);
        this.stats.setWillpower(4);
        this.stats.setFaith(10);

        Random random=new Random();
        int a=random.nextInt(51)+20;
        this.stats.setAge(a);

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
