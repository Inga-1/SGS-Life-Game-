package main;
import java.util.ArrayList;
public class Game implements Runnable{
    private ArrayList<Player> actives;
    private int maxsize; //from input
    private Leader cultLeader;
    private Cult cult;
    public Game(int size){ //make it get from input
        this.maxsize=size;

        actives= new ArrayList<Player>();
        Leader leader=new Leader(0,size,size);
        this.cultLeader=leader;
        this.cult=null;
        actives.add(leader);

        for(int i=1;i<size;i++){
            Player p=new Player(i,size,size);
            actives.add(p);
        }
        //array of all players
        //size means number of players
        //some active, some not
        //set a min (so that game works) and max (so that laptop doesnt explode)

        //initialize cult and leader too

        //find a way to stop game when cult falls apart (members=0) but without counting the beginnings
    }
    Thread gameThread;
    public void startGame(){
        gameThread= new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        try {
            while(gameThread!=null){
                System.out.println("It's still going...");
                for(Player p:actives){
                    update(p);
                    System.out.println("updated");
                    Thread.sleep(500);
                }
                // Making thread sleep for 0.5 seconds
            }
        }
        // Catch block to handle exception
        catch (InterruptedException e) {
            System.out.println("stop");
        }
    }
    //create methods to update each configuration (feedback function)
    //use them here
    //find a way to print the messages to user

    //find a way to time these
    public void update(Player p){
        //checks all messages and gives appropriate response
        //remember to delete messages after reading them
        Profile profile=p.getProfile();
        for(int i=1;i<profile.len;i++){
            //i is position of sender in board(index of profile)
            int senderPosition=i;
            Messages el=profile.getProfileElement(i);
            switch (el){
                case MEET:
                    //when meeting we add the player to our board (in acquaintances interval) and us in the player's board
                    //if full notify
                    break;
                case RECRUITED:
                    //when recruited we add ourselves to the cult
                    break;
                case FRIEND:
                    //when befriending we add the player to our board (in friends interval)and us in the player's board
                    //if full notify and reject --> negative consequence
                    break;
                case LOVER:
                    //when becoming lovers we add the player to our board (in lover place)and us in the player's board
                    //if full notify and reject --> negative consequence
                    break;
                case CHILD:
                    //when making children, check sigma function first (max number of children possible)
                    //if parents in cult, the kids will be members too
                    //add children to players board (in children interval) and parents in children board (in parents interval)
                    break;
                case ARGUE:
                    //idk yet
                    break;
                case KILLED:
                    //if killed get removed from actives
                    break;
                default:
                    //random action, according to relationship to random player
                    break;
            }
        }
    }
    public String seeActives(){
        return actives.toString();
    }
}
