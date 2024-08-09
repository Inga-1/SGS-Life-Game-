package main;

import main.Board;

public class Player{
    protected int id;
    protected boolean isMember;
    protected CultMember cultMember;
    protected Cult cult;
    protected Board board;
    protected Profile profile;
    protected Tuple<Board, Profile> config;
    protected int status; //0 dead, 1 alive
    public Player(int id, int size, Cult cult){
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
    public SimpleMember makeCultMember(){
        SimpleMember c = new SimpleMember(this.id,board.len,this.cult);
        this.cultMember=c;
        return (SimpleMember) this.cultMember;
        //add Exception (try/catch)
    }

}

/*QUESTIONS:
- Array with info?
*/
