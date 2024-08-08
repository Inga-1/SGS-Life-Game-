package main;

import main.Board;

public class Player{
    protected int id;
    protected boolean cultMember;
    protected Board board;
    protected Profile profile;
    protected Tuple<Board, Profile> config;
    protected int status; //0 dead, 1 alive
    public Player(int id, int b,int p){
        this.id=id;
        status=1;
        cultMember=false;
        board=new Board(id,b);
        profile=new Profile(p);
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

}

/*QUESTIONS:
- Array with info?
*/
