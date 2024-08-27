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
    public Player(int id, Game game){
        this.ChildrenCounter = 0;
        this.FriendsCounter = 0;
        this.MeetsCounter = 0;
        this.EnemiesCounter=0;

        this.id=id;
        this.game=game;
        this.size=this.game.size;
        this.cult=this.game.cult;

        this.status=1;
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

        board=new Board(id,this.size);
        profile=new Profile(this.size);
        config=new Tuple<>(board,profile);
    }
    public String toString(){
        String bs=board.toString();
        String ps=profile.toString();
        Tuple<String,String> test=new Tuple<>(bs,ps);
        return "id: "+ this.id+"\nstatus: "+ this.status+"\nname: "+ this.name+"\nsurname: "+ this.surname+"\ngender: "+ this.gender+"\n\nstats: "+ this.stats.toString()+"\n\nboard: "+bs+"\nprofile: "+ps+"\nconfiguration: "+ test;
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

        for (int i=0;i<=nChildren;i++) {
            //creates child and puts it in actives
            int newID=this.game.findEmptySpotInActives();
            if(newID>=0) {
                Player playerChild = new Player(newID, this.game); //zeby ID sie zgadzalo
                playerChild.stats.setAge(0);//the age is randomised at first, but we put it to zero

                //finds the index child is gonna have in parent's board
                int newPositionOne = findEmptySpotInBoard(this.size.BeginningChildrenInterval(), this.size.EndChildrenInterval());
                int newPositionTwo = player2.findEmptySpotInBoard(this.size.BeginningChildrenInterval(), this.size.EndChildrenInterval());
                if (newPositionOne >= 0 && newPositionTwo >= 0) {

                    this.game.actives[newID] = playerChild;

                    //puts child in both parents' boards and them in child's board
                    this.board.setBoardElement(newPositionOne, new Tuple<>(playerChild.id, this.size.getParentOneIndex()));
                    player2.board.setBoardElement(newPositionTwo, new Tuple<>(playerChild.id, this.size.getParentTwoIndex()));
                    playerChild.board.setBoardElement(this.size.getParentOneIndex(), new Tuple<>(this.id, newPositionOne)); //dodac indeksy
                    playerChild.board.setBoardElement(this.size.getParentTwoIndex(), new Tuple<>(player2.id, newPositionTwo)); //dodac indeksy

                    //updates const of number of active players
                    game.activesSize++;
                    game.possibleChildren--;
                    this.ChildrenCounter++;
                    player2.ChildrenCounter++;
                    game.babiesBorn++;

                    //if one of the parents is in cult, the child is a member from birth
                    if (this.isMember || player2.isMember) {
                        this.cult.addMember(playerChild);
                        game.childrenInCult++;
                    }
                }
            }
        }
    }

    public int sigma(){
        //check how many children person can have
        int possible=this.game.size.getAmountChildren()-this.ChildrenCounter;
        //check how many children possible in universe
        //int available=this.game.size.maxSize-this.game.activesSize;
        if (this.game.possibleChildren < possible) {
            possible=this.game.possibleChildren;
        }
        if(possible>3){possible=3;}
        return possible;
    }

    public CultMember makeMember(){
        this.cultMember= new SimpleMember(this);
        return this.cultMember;
        //add Exception (try/catch)
    }

    //for naming an heir
    public void makeLeader(){
        Leader c = new Leader(this);
        this.cultMember=c;
        c.stats.setAge(this.stats.getAge());
        this.game.cultLeader=c;
        this.game.cult.setLeader(c);
    }

    public boolean isInBoard(int idPerson){
        for (int j = 0; j < this.board.len; j++) {
            int idNeeded=-1;
            if(this.board.board[j]!=null && this.board.board[j].getFirst()!=null) {
                idNeeded = this.board.board[j].getFirst();
            }
            if (idNeeded == idPerson) {
                return true;
            }
        }
        return false;
    }
    public int indexInBoard(int idPerson){
        for (int j = 0; j < this.board.len; j++) {
            int idNeeded=-1;
            if(this.board.board[j]!=null && this.board.board[j].getFirst()!=null) {
                idNeeded = this.board.board[j].getFirst();
            }
            if (idNeeded == idPerson) {
                return j;
            }
        }
        return -1;
    }
    public boolean isInFriends(int idperson){
        int beginning=this.game.size.BeginningFriendsInterval();
        int end=this.game.size.EndFriendsInterval();
        return find(idperson, beginning, end);
    }

    private boolean find(int idperson, int beginning, int end) {
        for(int k = beginning; k <= end; k++){
            int idNeeded=-1;
            if(this.board.board[k]!=null && this.board.board[k].getFirst()!=null) {
                idNeeded = this.board.board[k].getFirst();
            }
            if (idNeeded == idperson) {
                return true;
            }
        }
        return false;
    }

    public boolean isInAcq(int idPerson){
        int beginning=this.game.size.BeginningAcqInterval();
        int end=this.game.size.EndAcqInterval();
        return find(idPerson, beginning, end);
    }
    public boolean isInEnemies(int idPerson){
        int beginning=this.game.size.BeginningEnemiesInterval();
        int end=this.game.size.EndEnemiesInterval();
        return find(idPerson, beginning, end);
    }

    public boolean alreadyRecruited(){
        for(int i=0; i<profile.len; i++){
            Messages m=profile.getProfileElement(i);
            if(m==Messages.RECRUITED){
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
            if(this.board.board[i]==null || this.board.board[i].getFirst()==null){return i;}
        }
        return -1;
    }
}
