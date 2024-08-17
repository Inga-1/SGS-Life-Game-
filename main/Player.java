package main;

import main.Board;

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

    public void MakeChildren(Player player1, Player player2){}
        //if(ChildrenCounter <  // size of how many children player can have){
           //if(size - actives > 0){
           // ChildrenCounter ++;
           // //Player Id/ child = new Player(all of the stuff)
           //actives.add(id/child)
          // id/child get method
          // player1.SetBoardElement(ChildrenCounter + beggining index of children depending on size of the world, child tuple)
        // player2.SetBoardElement(ChildrenCounter + beggining index of children depending on size of the world , child tuple)
        // get metgod fpr player1 tuple
        // id/child.SetBoardElement(place set by size of the world, tuple of player1)
        // get method for player2 tuple
        // id/child.SetBoardElement(place set by the world +1 , tuple of player2)
           // W SRODKU  if(Player1 isMember == true || Player2 isMember == true){ SETNAC MU ZE JEST W CULT
    //}
           //}


    /*public void MakeChildren(Player player1, Player player2){
        if( player1.ChildrenCounter < getAmountChildren(size) && player2.ChildrenCounter < getAmountChildren(size)){
            if( size - size.actives > 0){ //spr czy dostepne dane
                this.ChildrenCounter++;
                Player PlayerChild = new Player(size.actives + 1,size,this); //zeby ID sie zgadzalo
                actives.add(PlayerChild);
                player1.SetBoardElement(3+ player1.ChildrenCounter, newTuple(PlayerChild.id ,3+ player1.ChildrenCounter)); //dodac indeksy
                player2.SetBoardElement(3+ player2.ChildrenCounter, newTuple(PlayerChild.id ,3+ player2.ChildrenCounter)); //dodac indeksy
                PlayerChild.SetBoardElement(); //dodac indeksy
                PlayerChild.SetBoardElement(); //dodac indeksy
                if(player1.isMember() == true || player2.isMember() == true){
                    addMember(p);
                    
                }
            }
        }
    }
    */

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

    public void kill(Player killer,Player victim){
        //send kill message
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
