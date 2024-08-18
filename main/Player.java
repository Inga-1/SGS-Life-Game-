package main;

import main.Board;

import java.util.Random;

public class Player{
    protected int MeetsCounter;
    protected int FriendsCounter;
    protected int ChildrenCounter;
    protected int EnemiesCounter;

    protected int id;
    protected boolean isMember;
    protected boolean isLeader;

    protected CultMember cultMember;
    protected Cult cult;

    protected Game game;
    protected Size size;
    protected Stats stats;
    protected String name;
    protected String surname;
    protected char gender;

    protected Board board;
    protected Profile profile;
    protected Tuple<Board, Profile> config;
    protected int status; //0 dead, 1 alive
    public Player(int id, Size size, Game game){
        this.ChildrenCounter = 0;
        this.FriendsCounter = 0;
        this.MeetsCounter = 0;
        this.EnemiesCounter=0;

        this.id=id;
        this.game=game;
        this.size=size;

        status=1;
        Stats stats=new Stats();
        stats.randomizeStats();
        this.stats=stats;

        Data data=new Data();
        this.name=data.name;
        this.surname=data.surname;
        this.gender=data.gender;

        isMember=false;
        isLeader=false;
        this.cultMember=null;

        board=new Board(id,size);
        profile=new Profile(size);
        config=new Tuple<>(board,profile);
    }
    public String toString(){
        String bs=board.toString();
        String ps=profile.toString();
        Tuple<String,String> test=new Tuple<>(bs,ps);
        String s="id: "+this.id+"\nstatus: "+this.status+"\nname: "+this.name+"\nsurname: "+this.surname+"\ngender: "+this.gender+"\n\nstats: "+this.stats.toString()+"\n\nboard: "+bs+"\nprofile: "+ps+"\nconfiguration: "+test.toString();
        return s;
    }
    public Board getBoard(){
        return board;
    }
    public Profile getProfile(){
        return profile;
    }
    public CultMember getCultMember(){
        int i = this.cult.cult.indexOf(this.cultMember);
        return this.cult.cult.get(i);
        //add Exception (try/catch)
    }
    public int getCultIndex(){
        int i = this.cult.cult.indexOf(this.cultMember);
        return i;
        //add Exception (try/catch)
    }

    public void kill(Player victim){
        Random r=new Random();
        int outcome=r.nextInt(2);
        if(outcome==1){
            this.game.sendMessage(this,victim,Messages.KILLED);
        }else{
            this.game.sendMessage(this,victim,Messages.FAILEDKILL);
        }
    }

    public void MakeChildren(Player player2){
        int possible=sigma();
        Random random = new Random();
        int nChildren=random.nextInt(possible+1);
        this.ChildrenCounter+=nChildren;
        this.game.babiesBorn+=nChildren;

        for (int i=0;i<=nChildren;i++) {
            //creates child and puts it in actives
            int newID=this.game.findEmptySpotInActives();
            Player playerChild = new Player(newID, this.size, this.game); //zeby ID sie zgadzalo
            playerChild.stats.setAge(0);//the age is randomised at first, but we put it to zero
            this.game.actives[newID]=playerChild;

            //finds the index child is gonna have in parent's board
            int beginning=this.size.BeginningChildrenInterval();
            int end=this.size.EndChildrenInterval();
            int newPositionOne=findEmptySpotInBoard(beginning,end);
            int newPositionTwo=player2.findEmptySpotInBoard(beginning,end);

            //updates const of number of active players
            this.game.activesSize++;

            //puts child in both parents' boards and them in child's board
            this.board.setBoardElement(newPositionOne, new Tuple(playerChild.id, this.size.getParentOneIndex()));
            player2.board.setBoardElement(newPositionTwo, new Tuple(playerChild.id, this.size.getParentTwoIndex()));
            playerChild.board.setBoardElement(this.size.getParentOneIndex(), new Tuple(this.id,newPositionOne)); //dodac indeksy
            playerChild.board.setBoardElement(this.size.getParentTwoIndex(), new Tuple(player2.id,newPositionTwo)); //dodac indeksy

            //if one of the parents is in cult, the child is a member from birth
            if (this.isMember || player2.isMember) {
                this.cult.addMember(playerChild.makeMember());
            }
        }
    }

    public int sigma(){
        //check how many children person can have
        int possible=this.game.size.getAmountChildren()-this.ChildrenCounter;
        //check how many children possible in universe
        int available=this.game.size.maxSize-this.game.activesSize;
        if (available < possible) {
            possible=available;
        }
        return possible;
    }

    public SimpleMember makeMember(){
        SimpleMember c = new SimpleMember(this.id,board.size,this.game);
        this.cultMember=c;
        return (SimpleMember) this.cultMember;
        //add Exception (try/catch)
    }

    //for naming an heir
    public void makeLeader(){
        Leader c = new Leader(this.id,board.size,this.game);
        this.cultMember=c;
        this.game.cultLeader=c;
        this.game.cult.setLeader(c);
    }

    public boolean isInBoard(int idperson){
        for (int j = 0; j < this.board.len; j++) {
            int idneeded = this.board.board[j].getFirst();
            if (idneeded == idperson) {
                return true;
            }
        }
        return false;
    }
    public int indexInBoard(int idperson){
        for (int j = 0; j < this.board.len; j++) {
            int idneeded = this.board.board[j].getFirst();
            if (idneeded == idperson) {
                return j;
            }
        }
        return -1;
    }
    public boolean isInFriends(int idperson){
        int beginning=this.game.size.BeginningFriendsInterval();
        int end=this.game.size.EndFriendsInterval();
        for(int k = beginning; k <= end; k++){
            int idneeded = this.board.board[k].getFirst(); // idk if that's key and if its not better to do just second loop
            if (idneeded == idperson) {
                return true;
            }
        }
        return false;
    }

    public boolean isInAcq(int idperson){
        int beginning=this.game.size.BeginningAcqInterval();
        int end=this.game.size.EndAcqInterval();
        for(int k = beginning; k <= end; k++){
            int idneeded = this.board.board[k].getFirst(); // idk if that's key and if its not better to do just second loop
            if (idneeded == idperson) {
                return true;
            }
        }
        return false;
    }
    public boolean isInEnemies(int idperson){
        int beginning=this.game.size.BeginningEnemiesInterval();
        int end=this.game.size.EndEnemiesInterval();
        for(int k = beginning; k <= end; k++){
            int idneeded = this.board.board[k].getFirst(); // idk if that's key and if its not better to do just second loop
            if (idneeded == idperson) {
                return true;
            }
        }
        return false;
    }

    public void decreaseConsts(int index){
        if (index>=this.game.size.BeginningAcqInterval() && index<=this.game.size.EndAcqInterval()){
            this.MeetsCounter--;
        } else if (index>=this.game.size.BeginningFriendsInterval() && index<=this.game.size.EndFriendsInterval()) {
            this.FriendsCounter--;
        } else if (index>=this.game.size.BeginningChildrenInterval() && index<=this.game.size.EndChildrenInterval()) {
            this.ChildrenCounter--;
        } else if (index>=this.game.size.BeginningEnemiesInterval() && index<=this.game.size.EndEnemiesInterval()) {
            this.EnemiesCounter--;
        }
    }

    public int findEmptySpotInBoard(int beginning,int end){
        for(int i=beginning;i<=end;i++){
            if(this.board.board[i].getFirst()==null){return i;}
        }
        return 0;
    }
}
