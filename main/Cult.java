package main;
import java.util.HashSet;

public class Cult {
    public HashSet<Integer> cult=new HashSet<>();
    protected Leader leader;
    boolean started;//0 not started, 1 started
    String god;
    public Cult(String godName){
        this.god=godName; //make it get from input
        this.started=false;
    }

    public String toString(){
        return cult.toString();
    }
    public void setLeader(Leader leader){
        this.leader=leader;
        cult.add(leader.id);
    }
    public void addMember(Player p){
        if(p.status==1) {
            if(!this.started){this.started=true;}
            p.isMember = true;
            p.hasBeenMember=true;
            cult.add(p.makeMember());
            if (p.ChildrenCounter > 0) {
                for (int i = p.size.BeginningChildrenInterval(); i <= p.size.EndChildrenInterval(); i++) {
                    if (p.board.board[i] != null && p.board.board[i].getFirst() != null) {
                        int id = p.board.board[i].getFirst();
                        Player child = p.game.actives[id];
                        if (child.stats.getAge() < 19) {
                            child.isMember = true;
                            cult.add(child.makeMember());
                            p.game.newMembers++;
                        }
                    }
                }
            }
        }
    }

    public boolean isInCult(Player c){
        CultMember cMem=c.cultMember;
        if(cMem!=null) {
            return cult.contains(cMem.id);
        }else{return false;}
    }
    public void removeMember(Player p){
        p.isMember=false;
        cult.remove(p.cultMember.id);
        p.cultMember=null;
    }
}
