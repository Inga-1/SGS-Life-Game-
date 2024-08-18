package main;
import java.util.ArrayList;
public class Cult {
    public ArrayList<CultMember> cult;
    private Leader leader;
    boolean started;//0 not started, 1 started
    String god;
    public Cult(){
        cult=new ArrayList<CultMember>();
        this.god="Loki"; //make it get from input
        this.started=false;
        cult.add(leader);
    }
    public void setLeader(Leader leader){
        this.leader=leader;
    }
    public void addMember(Player p){
        if(!this.started){this.started=true;}
        p.isMember=true;
        CultMember c=p.makeMember();
        cult.add(c);
    }
    public void removeMember(Player p){
        p.isMember=false;
        cult.add(p.cultMember);
    }
}
