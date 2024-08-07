package main;
import java.util.ArrayList;
public class Cult {
    private ArrayList<Player> cult;
    private Leader leader;
    private int started;//0 not started, 1 started
    private String god;
    public Cult(Leader leader){
        cult=new ArrayList<Player>();
        this.leader=leader;
        this.god="Loki"; //make it get from input
        this.started=0;
        cult.add(leader);
    }
}
