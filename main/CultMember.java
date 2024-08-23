package main;

import java.util.Random;

public abstract class CultMember extends Player{
    public enum Role {
        LEADER,SHAMAN,PRIEST,NONE
    }
    protected Role role;
    public CultMember(int id,Game game){
        super(id,game);
        this.role=Role.NONE;
        this.cultMember=this;
        this.isMember=true;
    }
    public void pray(){
        int f=this.stats.getFaith();
        this.stats.setFaith(f+1);
    }
    public void question(){
        int f=this.stats.getFaith();
        if(f<=0 && !this.isLeader){
            Random rand= new Random();
            int n= rand.nextInt(2);
            if(n==1){
                escape();
            }else if(this.isInBoard(this.game.cultLeader.id)){
                this.game.sendMessage(this,this.game.cultLeader,Messages.FAILEDESCAPE);
            }else if (this.MeetsCounter<this.size.getAmountAcq()
                    && this.game.cultLeader.MeetsCounter<this.size.getAmountAcq()){
                this.game.sendMessage(this,this.game.cultLeader,Messages.MEET);
            }
        }else {
            this.stats.setFaith(f - 1);
        }
    }

    public int getCultIndex(){
        return this.cult.cult.indexOf(this);
    }

    public void escape(){
        this.cult.cult.remove(this);
        this.isMember=false;
        this.cultMember=null;
        this.game.membersEscaped++;
    }

    public void changeRole(Role r){
        role=r;
    }
}
