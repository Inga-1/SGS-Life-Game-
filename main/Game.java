package main;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Game implements Runnable{
    ArrayList<Player> actives;
    private int maxsize; //from input
    Size size;
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

                //check for EVENTS and executes them
                checkEvents();

                //checks for normal ending
                if(this.cult.started && this.cult.cult.size()<=1){terminateGame();}

                //for OUTPUT
                murdered=0;
                deaths=0;
                suicides=0;
                babiesBorn=0;
                newMembers=0;
                membersEscaped=0;
                membersKickedOut=0;
                //maybe we could also display at end of each round the cult stats

                //for EVENTS
                maxFaith=0;//mass suicide event
                minFaith=0;//rebellion event
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
                }
                Thread.sleep(500);
                // Making thread sleep for 0.5 seconds

                //OUTPUT
                System.out.println(+murdered+" people were murdered,");
                System.out.println(+deaths+" people died of natural causes or illness,");
                System.out.println(+suicides+" people committed suicide,");
                System.out.println(+babiesBorn+" children were born,");
                System.out.println("\nIN THE CULT:");
                System.out.println(+newMembers+" new members were recruited,");
                System.out.println(+membersEscaped+" members have betrayed us and run away,");
                System.out.println(+membersKickedOut+" members were kicked out.\n");

                Thread.sleep(500);
                //CONTINUE?
                Scanner myObj = new Scanner(System.in);
                System.out.println("Do you want to continue with the story? Press 1 to continue or 2 to quit:");
                int o=Integer.parseInt(myObj.nextLine());
                switch(o) {
                    case 1:
                        break;
                    case 2:
                        terminateGame();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + o);
                }
                Thread.sleep(500);
            }
        }
        // Catch block to handle exception
        catch (InterruptedException e) {
            System.out.println("The End.");
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
            int idsender=0;
            if (board.getBoardElement(i).getFirst()!=null){
                idsender=board.getBoardElement(i).getFirst();
            }
            int yourIndex=0;
            if (board.getBoardElement(i).getSecond()!=null){
                yourIndex=board.getBoardElement(i).getSecond();
            }
            //i know here we get info about sender but its int so not the full tuple???
            Player sender= actives.get(idsender);
            switch (el) {
                case MEET:

                    //THIS IS ALL GONNA GO IN DEFAULT
                    //we do the checks when someone wants to meet us
                    //who receives the message is a stranger and the sender is also a stranger to the receiver
                    //and we also check if they both have space
                    //we just need to add to board

                    //they also have already put themselves in our board and us in theirs
                    //we just return them a NONE message
                    break;

                case RECRUITED:
                    if(!p.isMember){
                       this.cult.addMember(p);
                    }else{
                        sender.cultMember.pray();
                        //to even send the recruit message you have to be a member first, so no need to check
                    }
                    break;
                case FRIEND:

                    //same for this as in meet
                    //we check when we do the action, not the reaction
                    if(p.FriendsCounter<=this.size.getAmountFriends() && sender.FriendsCounter<=this.size.getAmountFriends()){
                        int newPositionP=p.findEmptySpotInBoard(this.size.BeginningFriendsInterval(),this.size.EndFriendsInterval());
                        int newPositionS=sender.findEmptySpotInBoard(this.size.BeginningFriendsInterval(),this.size.EndFriendsInterval());
                        p.board.setBoardElement(newPositionP, new Tuple(idsender, newPositionS));
                        p.FriendsCounter++;
                        sender.board.setBoardElement(newPositionS, new Tuple(idsender, newPositionP));
                        sender.FriendsCounter++;

                        p.board.board[senderPosition]=null;
                        p.decreaseConsts(senderPosition);
                        sender.board.board[yourIndex]=null;
                        p.decreaseConsts(yourIndex);
                    }else{
                        //send them NONE message
                    }

                    break;
                case LOVER:
                    Random rand = new Random();
                    int rand_int1 = rand.nextInt(2);
                    if(p.board.board[2].getFirst()==null  && p.board.board[2].getSecond()==null && rand_int1 == 1){
                        p.board.setBoardElement(2, new Tuple<>(idsender,2));
                        sender.board.setBoardElement(2,new Tuple<>(p.id,2));

                        p.board.board[senderPosition]=null;
                        p.decreaseConsts(senderPosition);
                        sender.board.board[yourIndex]=null;
                        p.decreaseConsts(yourIndex);
                        //remove lover from previous position in array and you from theirs
                    }else{
                        //send NONE message
                    }
                    //if(3rd place of the array not taken){ SetBoard(2=index) to be his lover}

                     break;
                case CHILD:
                      p.MakeChildren(p, sender);
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
                case FAILEDKILL: // do we need to have it?
                    if(sender.isMember && p.isMember && p.cultMember.role== CultMember.Role.LEADER){
                        p.kill(p,sender);
                        attemptsOnLeader++;
                    }
                    break;
                case FAILEDESCAPE:
                    if(sender.isMember && p.isMember && p.cultMember.role== CultMember.Role.LEADER){
                        //idk, maybe put in enemies of leader
                    }
                    break;
                default:
                    //When we are SENDING messages, NOT RECEIVING

                    Random random = new Random();
                    int j=random.nextInt(25);
                    int idreceiver=random.nextInt(activesSize);
                    Player receiver= actives.get(idreceiver);

                    if (j<4){
                        //meet
                        if(!p.isInBoard(idreceiver) && p.MeetsCounter<=this.size.getAmountAcq() && receiver.MeetsCounter<=this.size.getAmountAcq()){
                            //in BOARD
                            int newPositionP=p.findEmptySpotInBoard(this.size.BeginningFriendsInterval(),this.size.EndFriendsInterval());
                            int newPositionR=receiver.findEmptySpotInBoard(this.size.BeginningFriendsInterval(),this.size.EndFriendsInterval());
                            p.board.setBoardElement(newPositionP, new Tuple(idreceiver, newPositionR));
                            p.MeetsCounter++;
                            sender.board.setBoardElement(newPositionR, new Tuple(idreceiver, newPositionP));
                            sender.MeetsCounter++;

                            //in PROFILE(messages)
                            //send meet message
                        }else{
                            //one of the default actions
                        }
                    } else if (j<7) {
                        if(p.stats.getAge()>19) {
                            if (!p.isInFriends(idreceiver)
                                    && p.MeetsCounter<=this.size.getAmountFriends()
                                    && receiver.MeetsCounter<=this.size.getAmountFriends()
                                    && p.isInAcq(idreceiver)) {
                                //send friend message
                            } else {
                                //one of the default actions
                            }
                        }
                    } else if(j<9){
                        if(p.stats.getAge()>19){}
                        //lover
                    } else if (j<11) {
                        if(p.stats.getAge()>19){}
                        //argue
                    } else if (j<15) {
                        if(p.isMember){
                            p.cultMember.pray();
                        }
                    } else if (j<19) {
                        if(p.isMember && p.stats.getWillpower()>=1){
                            p.cultMember.question();
                        }
                    } else if (j<24) {
                        if(p.isMember && p.stats.getAge()>19){}
                        //recruit
                        //just sends recruit message
                    } else {
                        if(p.stats.getAge()>19){}
                        //dies
                        //maybe we could do two methods, suicide and death for illness, that do the exact same thing
                        //(which is probably similar to killed method), but one updates suicides and the other deaths;

                        //also suicide might be only if willpower is 4 or 5
                    }

                    //meet(4),friend(3),find lover(2),argue(3)
                    //if member: pray,question(4)(4),recruit(3)
                    //RARE: die of illness(1)


                    /*if (p.isMember == true){
                        p.pray();}
                    else{
                        //ZOSTAWIC DO OGARNIECIA RAZEM
                       //int Random_message = rand.nextInt(10);
                       //int Random_player_from_board = rand.nextInt(length of aquqitance board);
                       //sending random message to random member on players board
                    }*/
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


    //------------------------------------EVENTS-----------------------------------------------------------------------------------------------------------------------------
    public void checkEvents(){
        if(maxFaith==this.cult.cult.size()*0.5){
            massSuicide();
        } else if (minFaith==this.cult.cult.size()*0.5) {
            rebellion();
        } else if (attemptsOnLeader>=5) {
            conArtist();
        }
    }
    public void massSuicide(){
        System.out.println(this.cultLeader.name+" "+this.cultLeader.surname+" reached their goal.\nAll their followers gave their lives for them in a mass suicide drinking CocaCola with bleach, the sacred drink.\nThey felt fulfilled for a while, they even wanted to start another cult. \nWhen they were about to skip town the cops arrested them. \nWhat he didn’t know was that there was a survivor, their lover sneaked out during the turmoil and their testimony was useful to catch them. \nNow "+this.cultLeader.name+" spends the rest of their days in prison.");
        terminateGame();
    }

    public void conArtist(){
        System.out.println("After 5 murder attempts, "+this.cultLeader.name+" "+this.cultLeader.surname+" realizes their charisma won’t work this time. \nThem and their lover sneaked out during the night. \nSome members waited months thinking they left for a prophecy, however it was later revealed that the leader was a con artist that started the cult in order to become a millionaire. \nSome say they bought a house somewhere in the Caribbean where they live with their lover and beloved dog Loki; others say they started another cult in Polynesia.  \nAlmost everyone went back to their lives, even though they still hope that the prophecy will be fulfilled.");
        terminateGame();
    }

    public void rebellion(){
        System.out.println("There was always some tension in the cult, most members weren’t 100% convinced by "+this.cultLeader.name+" "+this.cultLeader.surname+", but still they stuck around to see where this was going. \n"+this.cultLeader.name+" thought his charisma would be enough to pull it off, however he didn’t expect that his members would start questioning them and turn against them. \nAnd as quickly as it started it quickly vanished.");
        terminateGame();
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    public void terminateGame(){
        gameThread.interrupt();
    }
}
