package main;

import main.Board;

public class Player{
    protected int id;
    protected boolean isMember;
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
    public SimpleMember makeMember(){
        SimpleMember c = new SimpleMember(this.id,board.size,this.game);
        this.cultMember=c;
        return (SimpleMember) this.cultMember;
        //add Exception (try/catch)
    }

    public void kill(Player victim){
        //to implement
    }
}

/*QUESTIONS:
- Array with info?
*/
