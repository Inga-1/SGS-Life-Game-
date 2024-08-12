package main;
import java.util.ArrayList;
public class Game implements Runnable{
    private ArrayList<Player> actives;
    private int maxsize; //from input
    private Size size;
    private int activesSize;
    private int possibleChildren;
    private Leader cultLeader;
    protected Cult cult;
    public Game(Size size){ //make it get from input
        this.size=size;
        this.maxsize=size.maxSize;
        this.activesSize=size.actives;
        this.possibleChildren=size.possibleChildren;

        actives= new ArrayList<Player>();
        this.cult=new Cult();
        Leader leader=new Leader(0,size,this);
        this.cult.setLeader(leader);
        this.cultLeader=leader;
        actives.add(leader);

        for(int i=1;i<size.actives;i++){
            Player p=new Player(i,size,this);
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
        Board board=p.getBoard();
        for(int i=1;i<profile.len;i++){
            //i is position of sender in board(index of profile)
            int senderPosition=i;
            Messages el=profile.getProfileElement(i);
            int idsender=board.getBoardElement(i).getFirst();
            Player sender= actives.get(idsender);
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
                case KILLED:
                    killed(sender,p);
                    break;
                case ARGUE:
                    //if someone argues with leader they get kicked out
                    if(sender.isMember && p.isMember && p.cultMember.role== CultMember.Role.LEADER){
                        this.cultLeader.kickOut(sender.cultMember);
                    }
                    break;
                case FAILEDKILL:
                    if(sender.isMember && p.isMember && p.cultMember.role== CultMember.Role.LEADER){
                        p.kill(sender);
                    }
                    break;
                case FAILEDESCAPE:
                    if(sender.isMember && p.isMember && p.cultMember.role== CultMember.Role.LEADER){
                        sender.cultMember.hardWork();
                    }
                    break;
                default:
                    //random action, according to relationship to random player
                    //make it have a probability to happen otherwise we have too many interactions going on
                    //if the player is a cult member, they could also just pray
                    break;
            }
        }
    }
    public String seeActives(){
        return actives.toString();
    }
    public void killed(Player killer,Player victim){
        victim.status=0;//not active anymore
        if(victim.isMember){
            int i=victim.getCultIndex();
            this.cult.cult.remove(i);
        }
        actives.remove(victim.id);
        System.out.println(victim.id+" has been killed by "+killer.id);
    }
}
