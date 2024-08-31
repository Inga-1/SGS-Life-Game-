package main;

import java.util.Random;

public abstract class CultMember extends Player{
    public enum Role {
        LEADER,NONE
    }
    protected Role role;
    public CultMember(Player p){
        super(p.id,p.game);

        this.ChildrenCounter = p.ChildrenCounter;
        this.FriendsCounter = p.FriendsCounter;
        this.MeetsCounter = p.MeetsCounter;
        this.EnemiesCounter=p.EnemiesCounter;

        this.status=p.status;
        this.stats=p.stats;
        this.name=p.name;
        this.surname=p.surname;
        this.gender=p.gender;

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
        if(f>0 && f<10) {
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
                } else if(!isInBoard(this.game.cultLeader.id) && this.MeetsCounter<this.size.getAmountAcq() && this.game.cultLeader.MeetsCounter<this.size.getAmountAcq()){
                    //in BOARD
                    int newPositionP=findEmptySpotInBoard(this.size.BeginningAcqInterval(),this.size.EndAcqInterval());
                    int newPositionR=this.game.cultLeader.findEmptySpotInBoard(this.size.BeginningAcqInterval(),this.size.EndAcqInterval());
                    if (newPositionP>=0 && newPositionR>=0) {
                        board.setBoardElement(newPositionP, new Tuple<>(this.game.cultLeader.id, newPositionR));
                        MeetsCounter++;
                        this.game.cultLeader.board.setBoardElement(newPositionR, new Tuple<>(this.id, newPositionP));
                        this.game.cultLeader.MeetsCounter++;

                        //in PROFILE(messages)
                        //send meet message
                        game.sendMessage(game.actives[this.id], this.game.cultLeader, Messages.MEET);
                    }
                }
            } else if (f>0){
                Random r= new Random();
                int n=r.nextInt(2);
                this.stats.setFaith(f - 1 -n);
            }
        }
    }

    public void escape(){
        cult.removeMember(game.actives[this.id]);
        this.game.membersEscaped++;
    }

    public void changeRole(Role r){
        role=r;
    }
}
