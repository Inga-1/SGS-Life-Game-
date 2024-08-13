package main;
import java.util.ArrayList;
import java.util.Random;


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

    //for OUTPUT
    int murdered;
    int deaths;
    int suicides;
    int babiesBorn;
    int newMembers;
    int membersEscaped;
    int membersKickedOut;

    //for EVENTS
    int maxFaith;//mass suicide event
    int minFaith;//rebellion event
    int attemptsOnLeader;//con artist event

    @Override
    public void run() {
        try {
            while(gameThread!=null){
                System.out.println("It's still going...");

                //for OUTPUT
                int murdered=0;
                int deaths=0;
                int suicides=0;
                int babiesBorn=0;
                int newMembers=0;
                int membersEscaped=0;
                int membersKickedOut=0;
                //maybe we could also display at end of each round the cult stats


                //for EVENTS
                int maxFaith=0;//mass suicide event
                int minFaith=0;//rebellion event
                //attemps on Leader (con artist event) is outside loop (doesnt get reset)

                for(Player p:actives){

                    //update age
                    int age=p.stats.getAge();
                    p.stats.setAge(age+1);
                    if(age>49){
                        //die (same method used for the deaths in default)
                    }

                    if(p.status==1){update(p);}
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
                        attemptsOnLeader++;
                    }
                    break;
                case FAILEDESCAPE:
                    if(sender.isMember && p.isMember && p.cultMember.role== CultMember.Role.LEADER){
                        //idk, maybe put in enemies of leader
                    }
                    break;
                default:

                    Random random = new Random();
                    int j=random.nextInt(25);

                    if (j<4){
                        //meet
                    } else if (j<7) {
                        //friend
                    } else if(j<9){
                        //lover
                    } else if (j<11) {
                        //argue
                    } else if (j<15) {
                        if(p.isMember){}
                        //pray
                    } else if (j<19) {
                        if(p.isMember && p.stats.getWillpower()>=2){}
                        //question
                    } else if (j<24) {
                        if(p.isMember){}
                        //recruit
                    } else {
                        //dies
                        //maybe we could do two methods, suicide and death for illness, that do the exact same thing
                        //(which is probably similar to killed method), but one updates suicides and the other deaths;

                        //also suicide might be only if willpower is 4 or 5
                    }

                    //meet(4),friend(3),find lover(2),argue(3)
                    //if member: pray,question(4)(4),recruit(3)
                    //RARE: die of illness(1)


                    //random action, according to relationship to random player
                    //make it have a probability to happen otherwise we have too many interactions going on
                    //if the player is a cult member, they could also just pray
                    break;
            }
        }

        //for EVENTS
        int faith=p.stats.getFaith();
        if(faith==10){maxFaith++;} else if (faith==0 && p.isMember) {
            minFaith++;
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
