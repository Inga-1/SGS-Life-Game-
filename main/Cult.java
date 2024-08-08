package main;
import java.util.ArrayList;
public class Cult {
    private ArrayList<CultMember> cult;
    private Leader leader;
    private int started;//0 not started, 1 started
    private String god;
    private Player shaman;
    public Cult(Leader leader){
        cult=new ArrayList<CultMember>();
        this.leader=leader;
        this.god="Loki"; //make it get from input
        this.shaman=null;
        this.started=0;
        cult.add(leader);
    }
    public void addMember(Player p){
        p.cultMember=true;
        //find out how to change player in cult member everywhere
    }
}
