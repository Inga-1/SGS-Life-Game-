package main;
import java.util.ArrayList;
import java.util.Random
public class Game implements Runnable{
    private ArrayList<Player> actives;
    private int maxsize; //from input
    private Leader cultLeader;
    protected Cult cult;
    public Game(int size){ //make it get from input
        this.maxsize=size;

        actives= new ArrayList<Player>();
        this.cult=new Cult();
        Leader leader=new Leader(0,size,this.cult);
        this.cult.setLeader(leader);
        this.cultLeader=leader;
        actives.add(leader);

        for(int i=1;i<size;i++){
            Player p=new Player(i,size,this.cult);
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
                    if(sender // not in board==> SHOULD I CHECK SENDER TUPLE???){
                        p.SetBoardElement(size-1, sender);
                       //gaining on stats
                    }
                    else{//losing on stats}
                    //Puting newly met person in array of aquaintances w przedziale set by size of the world
                    // if sender not in array of aquaintances : SetBoardElement(pre-last place){this met person}
                    break;
                case RECRUITED:
                    if( p.isMember == true){
                    }
                    else{addMember(p)}
                    break;
                case FRIEND:
                    if(sender // not in boards space for friends based on size==> SHOULD I CHECK SENDER TUPLE???){
                        //p.SetBoardElement(index of friends based on size + p.FriendsCounter , sender);
                       //gaining on stats
                    }
                    else{//losing on stats}
                    //if (sender not in space in array for friends){check for null place in friends sector in array==> while loop==> when we find it setBoard to this friend
                    break;
                case LOVER:
                    if(p.board[2] == null){
                        p.SetBoardElement(2, sendertuple?)
                    }
                        else{//losing on stats}
                    //if(3rd place of the array not taken){ SetBoard(2=index) to be his lover}
                    //else{// maybe some loss of charisma for sender???}
                    break;
                case CHILD:
                    p.MakeChildren(Player p, Player sender)
                    // if(there are places in the world && children array of p is not full && children array of sender not full){
                    // WE NEED TO MAKE CHILDREN SOMEHOW == WOULD DO THAT IN PLAYER CLASS EXTEND IT AND CALL IT FROM SUPER.METHODTOMAKECHILDREN
                    //{
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
                    if (p.isMember == true){
                        p.pray();}
                    else{
                        //ZOSTAWIC DO OGARNIECIA RAZEM
                       //int Random_message = rand.nextInt(10);
                       //int Random_player_from_board = rand.nextInt(length of aquqitance board);
                       //sending random message to random member on players board
                    }
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
