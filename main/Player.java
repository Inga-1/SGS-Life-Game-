package main;

import main.Board;

public class Player{
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
        this.ChildrenCounter = Children_counter;
        Children_counter = 0;
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
        //if(ChildrenCounter < 5 // size of how many children player can have){
           //if(Plces in universe is enough){
        // if(Player1 isMember == true || Player2 isMember == true)
          //Player(id, size , cult){
        //isMember = true
        //}
        //else{Player(id, size, cult){ isMember = false}}
    //}
           //}
    }
    public SimpleMember makeMember(){
        SimpleMember c = new SimpleMember(this.id,board.len,this.cult);
        this.cultMember=c;
        return (SimpleMember) this.cultMember;
        //add Exception (try/catch)
    }

    public void kill(Player victim){

    }
}

/*QUESTIONS:
- Array with info?
*/
