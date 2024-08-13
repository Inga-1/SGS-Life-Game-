import java.util.Arrays;
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
            int idsender=board.getBoardElement(i).getFirst(); //i know here we get info about sender but its int so not the full tuple???
            
            Player sender= actives.get(idsender);
            switch (el){
                case MEET:
                    Tuple SenderId = sender.getBoardElement(0);
                    if(p.board.contains(SenderId)){

                       //losing on stats
                    }
                    else{
                        p.MeetsCounter++;
                        p.SetBoardElement(size-1, SenderId);}
                    break;
                case RECRUITED:
                    if( p.isMember != true // && stats are ok){
                       addMember(p)
                    }
                    else{if(sender.isMember == true){sender.pray()}}
                    break;
                case FRIEND:
                    
                    //if(p.board[//range for friends].contains(SenderId)){
                       
                        //losing on stats
                        
                    }
                    else{p.FriendsCounter++;
                         // gaining on stats
                        //if (sender not in space in array for friends){check for null place in friends sector in array==> while loop==> when we find it setBoard to this friend
                        }
                    break;
                case LOVER:
                    int rand_int1 = rand.nextInt(1);
                    if(p.board[2] == null  && rand_int == 1){
                        p.SetBoardElement(2, SenderId)
                    }
                        else{//losing on stats}
                    //if(3rd place of the array not taken){ SetBoard(2=index) to be his lover}
                     
                     break;
                case CHILD:
                      MakeChildren(Player p, Player sender)
        
                    break;
                case KILLED:
                    //if(p.statsofanger > threshhold){p.kill(sender)}
                    // if(sender.statsofanger > threshhold){}sender.kill(p)}
                   //else{}
                    break;
                case ARGUE:
                    //if someone argues with leader they get kicked out
                    if(sender.isMember && p.isMember && p.cultMember.role== CultMember.Role.LEADER){
                        this.cultLeader.kickOut(sender.cultMember);
                    }
                    break;
                case FAILEDKILL: // do we need to have it?
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
    
}
