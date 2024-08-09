package main;
import java.util.ArrayList;
public class Cult {
    public ArrayList<CultMember> cult;
    private Leader leader;
    private boolean started;//0 not started, 1 started
    private String god;
    private Player shaman;
    public Cult(){
        cult=new ArrayList<CultMember>();
        this.leader=null;
        this.god="Loki"; //make it get from input
        this.shaman=null;
        this.started=false;
        cult.add(leader);
    }
    public void setLeader(Leader leader){
        this.leader=leader;
    }
    public void addMember(Player p){
        p.isMember=true;
        CultMember c=p.makeCultMember();
        cult.add(c);
    }
}
