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
        if (p.ChildrenCounter>0){
            for(int i=p.size.BeginningChildrenInterval();i<=p.size.EndChildrenInterval();i++){
                if(p.board.board[i]!=null && p.board.board[i].getFirst()!=null){
                    int id=p.board.board[i].getFirst();
                    Player child=p.game.actives[id];
                    if(child.stats.getAge()<19){
                        child.isMember=true;
                        CultMember childC=child.makeMember();
                        cult.add(childC);
                    }
                }
            }
        }
    }
    public void removeMember(Player p){
        p.isMember=false;
        int i=p.cultMember.getCultIndex();
        cult.remove(i);
    }
}
