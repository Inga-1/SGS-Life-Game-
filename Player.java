public class Player{
    private char name;
    private Board board;
    private Profile profile;
    private Tuple<Board,Profile> config;
    public Player(char name, int b,int p){
        this.name=name;
        board=new Board(name,b);
        profile=new Profile(p);
        config=new Tuple<>(board,profile);
    }
    public String toString(){
        String bs=board.toString();
        String ps=profile.toString();
        Tuple<String,String> test=new Tuple<>(bs,ps);
        String s="name: "+this.name+"\nboard: "+bs+"\nprofile: "+ps+"\nconfiguration: "+test.toString();
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
- Make board and profile their own classes?
- Inheritance of classes (scheme)?
- How to change board and profile (getters/setters)?
- Array with info?
*/
