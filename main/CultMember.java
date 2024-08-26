package main;

import java.util.Random;

public abstract class CultMember extends Player{
    public enum Role {
        LEADER,SHAMAN,PRIEST,NONE
    }
    protected Role role;
    public CultMember(Player p){
        super(p.id,p.game);

        this.ChildrenCounter = p.ChildrenCounter;
        this.FriendsCounter = p.FriendsCounter;
        this.MeetsCounter = p.MeetsCounter;
        this.EnemiesCounter=p.EnemiesCounter;

        status=p.status;
        this.stats=p.stats;

        board=p.board;
        profile=p.profile;
        config=p.config;

        this.role=Role.NONE;
        this.cultMember=this;
        this.isMember=true;
        this.isLeader=p.isLeader;
    }
    public void pray(){
        int f = this.stats.getFaith();
        if(f>0 || f<10) {
            this.stats.setFaith(f + 1);
        }
    }
    public void question(){
        int f=this.stats.getFaith();
        if(f<10){
            if (f <= 0 && !this.isLeader) {
                Random rand = new Random();
                int n = rand.nextInt(2);
                if (n == 1) {
                    escape();
                } else if (this.isInBoard(this.game.cultLeader.id)) {
                    this.game.sendMessage(this, this.game.cultLeader, Messages.FAILEDESCAPE);
                } else if (this.MeetsCounter < this.size.getAmountAcq()
                        && this.game.cultLeader.MeetsCounter < this.size.getAmountAcq()) {
                    this.game.sendMessage(this, this.game.cultLeader, Messages.MEET);
                }
            } else {
                this.stats.setFaith(f - 1);
            }
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
