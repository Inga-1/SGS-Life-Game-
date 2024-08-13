package main;

import main.Board;

public class Player extends Game{
    protected int MeetsCounter;
    protected int FriendsCounter;
    protected int ChildrenCounter;
    protected int id;
    protected boolean isMember;
    protected CultMember cultMember;
    protected Cult cult;
    protected Board board;
    protected Profile profile;
    protected Tuple<Board, Profile> config;
    protected int status; //0 dead, 1 alive
    public Player(int id, int size, Cult cult){
        this.ChildrenCounter = ChildrenCounter;
        ChildrenCounter = 0;
        this.FriendsCounter = FriendsCounter;
        FriendsCounter = 0;
        this.MeetsCounter = MeetsCounter;
        MeetsCounter = 0;
        this.id=id;
        this.cult=cult;
        status=1;
        isMember=false;
        this.cultMember=null;
        board=new Board(id,size);
        profile=new Profile(size);
        config=new Tuple<>(board,profile);
    }
    public String toString(){
        String bs=board.toString();
        String ps=profile.toString();
        Tuple<String,String> test=new Tuple<>(bs,ps);
        String s="id: "+this.id+"\nboard: "+bs+"\nprofile: "+ps+"\nconfiguration: "+test.toString();
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
    public MakeChildren(Player player1, PLayer player2){
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
    }
    public SimpleMember makeMember(){
        SimpleMember c = new SimpleMember(this.id,board.len,this.cult);
        this.cultMember=c;
        return (SimpleMember) this.cultMember;
        //add Exception (try/catch)
    }

    public void kill(Player killer,Player victim){ 
        victim.status=0;
        if(victim.isMember){
            int i=victim.getCultIndex();
            this.cult.cult.remove(i);
        }
        actives.remove(victim.id);
        System.out.println(victim.id+" has been killed by "+killer.id);
    }
}

/*QUESTIONS:
- Array with info?
*/
