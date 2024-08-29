package main;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static main.Messages.FAILEDESCAPE;


public class Game implements Runnable{
    Player[] actives;
    protected final int maxsize; //from input
    final Size size;
    private final int activesSize;
    protected Leader cultLeader;
    protected Cult cult;
    public Game(Size size, String godName){ //make it get from input
        this.size=size;
        this.maxsize=size.maxSize;
        this.activesSize=size.actives;

        actives= new Player[this.maxsize];
        Arrays.fill(actives, null);
        this.cult=new Cult(godName);
        Player pl=new Player(0,this);
        Leader leader=new Leader(pl);
        this.cult.setLeader(leader);
        this.cultLeader=leader;
        actives[0]=leader;

        for(int i=1;i<this.activesSize-1;i++){
            Player p=new Player(i,this);
            actives[i]=p;
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
    int met;
    int newFriends;
    int newLovers;
    int murdered;
    int deaths;
    int suicides;
    int babiesBorn;
    int newMembers;
    int childrenInCult;
    int membersEscaped;
    int membersKickedOut;
    int deadInCult;

    //for EVENTS
    int maxFaith;//mass suicide event
    int minFaith;//rebellion event
    int attemptsOnLeader;//con artist event

    @Override
    public void run() {
        try {
            while(gameThread!=null){
                //check for EVENTS and executes them
                checkEvents();

                //checks for normal ending
                if(this.cult.started && this.cult.cult.size()<=1){terminateGame();}
                if(activesSize==0){terminateGame();}

                //for OUTPUT
                met=0;
                newFriends=0;
                newLovers=0;
                murdered=0;
                deaths=0;
                suicides=0;
                babiesBorn=0;
                newMembers=0;
                childrenInCult=0;
                membersEscaped=0;
                membersKickedOut=0;
                deadInCult=0;
                //maybe we could also display at end of each round the cult stats

                //for EVENTS
                maxFaith=0;//mass suicide event
                minFaith=0;//rebellion event
                //attemps on Leader (con artist event) is outside loop (doesnt get reset)

                for(Player p:actives){
                    if (p!=null) {

                        //update age
                        int age = p.stats.getAge();
                        p.stats.setAge(age + 1);
                        age=p.stats.getAge();
                        if (age > 99) {
                            die(p, false);
                            this.deaths++;
                        }

                        //check if alive after updating age
                        if (p.status == 1) {
                            update(p);
                        }
                    }
                }
                Thread.sleep(500);
                // Making thread sleep for 0.5 seconds

                //OUTPUT
                System.out.println("\n\n-------------------WHAT HAPPENED THIS YEAR:--------------------");
                System.out.println(met+" people have met,");
                System.out.println(newFriends+" people have become friends,");
                System.out.println(newLovers+" couples have formed,");
                System.out.println(murdered+" people were murdered,");
                System.out.println(deaths+" people died of natural causes or illness,");
                System.out.println(suicides+" people committed suicide,");
                System.out.println(babiesBorn+" children were born,");
                System.out.println("Number of people in universe: "+nPeople());

                System.out.println("\nnMembers: "+nMembers());
                System.out.println("isEveryoneMember: "+isEveryoneMember());
                System.out.println("minFaith: "+minFaith);
                System.out.println("maxFaith: "+maxFaith);
                System.out.println("Attempts on leader: "+attemptsOnLeader);

                System.out.println("\nIN THE CULT:");
                System.out.println(newMembers+" new members were recruited,");
                System.out.println(childrenInCult+" children were born in the cult,");
                System.out.println(membersEscaped+" members have betrayed us and run away,");
                System.out.println(membersKickedOut+" members were kicked out.");
                System.out.println(deadInCult+" members have died.\n");
                System.out.println("Number of members: "+cult.cult.size()+"\n\n");

                Thread.sleep(500);
                //pause
                //CONTINUE?
                Scanner myObj = new Scanner(System.in);
                System.out.println("Do you want to continue with the story? Press 1 to continue or 2 to quit:");
                int o = 0;
                boolean valid = false;
                while (!valid){
                    try {
                        o = Integer.parseInt(myObj.nextLine());
                        if (o == 1 || o == 2) {
                            valid = true;
                        } else {
                            System.out.println("Invalid choice! Please press 1 to continue or 2 to quit.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid choice! Please press 1 to continue or 2 to quit.");
                    }
                }

                switch(o) {
                    case 1:
                        //continue
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
        for(int i=1;i<p.profile.len;i++){
            if (p.status==1){
                //i is position of sender in board(index of profile)
                Messages el=p.profile.getProfileElement(i);
                int idsender=-1;
                int yourIndex=-1;
                Tuple<Integer,Integer> tup = null;
                if (p.board.getBoardElement(i)!=null && p.board.getBoardElement(i).getFirst()!=null && p.board.getBoardElement(i).getSecond()!=null){
                    idsender=p.board.getBoardElement(i).getFirst();
                    yourIndex=p.board.getBoardElement(i).getSecond();
                    tup=p.board.getBoardElement(i);

                    //i know here we get info about sender but its int so not the full tuple???
                    Player sender= actives[idsender];
                    if(sender!=null && el!=null) {
                        switch (el) {
                            case NONE:
                                if(sender.status==1) {
                                    sendMessage(p, sender, Messages.NONE);
                                    defaultMethod(p);
                                }
                                break;
                            case MEET:
                                if(sender.status==1){
                                    sendMessage(p, sender, Messages.NONE);
                                    met++;
                                }
                                break;

                            case RECRUITED:
                                //double check this because might have happend already before in the loop (from someone else)
                                if (!p.isMember && sender.status==1 && !cult.isInCult(p)) {
                                    this.cult.addMember(p);
                                    if (!p.isInBoard(this.cultLeader.id) && p.MeetsCounter < this.size.getAmountAcq()
                                            && this.cultLeader.MeetsCounter < this.size.getAmountAcq()) {
                                        int newPositionP=p.findEmptySpotInBoard(this.size.BeginningAcqInterval(),this.size.EndAcqInterval());
                                        int newPositionR=this.cultLeader.findEmptySpotInBoard(this.size.BeginningAcqInterval(),this.size.EndAcqInterval());
                                        if (newPositionP>=0 && newPositionR>=0) {
                                            p.board.setBoardElement(newPositionP, new Tuple<>(this.cultLeader.id, newPositionR));
                                            p.MeetsCounter++;
                                            this.cultLeader.board.setBoardElement(newPositionR, new Tuple<>(p.id, newPositionP));
                                            this.cultLeader.MeetsCounter++;

                                            //in PROFILE(messages)
                                            //send meet message
                                            sendMessage(p, this.cultLeader, Messages.MEET);
                                        }
                                    }
                                    newMembers++;
                                }
                                break;
                            case FRIEND:

                                //same for this as in meet
                                //we check when we do the action, not the reaction

                                //double check this because might have happend already before in the loop (from someone else)
                                //and exceeded limits
                                if (sender.status==1 && p.FriendsCounter < this.size.getAmountFriends() && sender.FriendsCounter < this.size.getAmountFriends()) {
                                    int newPositionP = p.findEmptySpotInBoard(this.size.BeginningFriendsInterval(), this.size.EndFriendsInterval());
                                    int newPositionS = sender.findEmptySpotInBoard(this.size.BeginningFriendsInterval(), this.size.EndFriendsInterval());
                                    if(newPositionP>=0 && newPositionS>=0){
                                        p.board.setBoardElement(newPositionP, new Tuple<>(idsender, newPositionS));
                                        p.FriendsCounter++;
                                        sender.board.setBoardElement(newPositionS, new Tuple<>(idsender, newPositionP));
                                        sender.FriendsCounter++;

                                        p.board.board[i] = null;
                                        p.decreaseConsts(i);
                                        sender.board.board[yourIndex] = null;
                                        p.decreaseConsts(yourIndex);

                                        newFriends++;
                                    }
                                } else {
                                    //sender becomes receiver
                                    if(sender.status==1){sendMessage(p, sender, Messages.NONE);}
                                }

                                break;
                            case LOVER:
                                //double check this because might have happend already before in the loop (from someone else)
                                //and exceeded limits
                                Random rand = new Random();
                                int rand_int1 = rand.nextInt(2);
                                if (sender.status==1 && p.board.board[1].getFirst() == null && p.board.board[1].getSecond() == null && rand_int1 == 1) {
                                    p.board.setBoardElement(1, new Tuple<>(idsender, 2));
                                    sender.board.setBoardElement(1, new Tuple<>(p.id, 2));

                                    p.board.board[i] = null;
                                    p.decreaseConsts(i);
                                    sender.board.board[yourIndex] = null;
                                    p.decreaseConsts(yourIndex);
                                    //remove lover from previous position in array and you from theirs

                                    newLovers++;
                                } else {
                                    if(sender.status==1){sendMessage(p, sender, Messages.NONE);}
                                }
                                //if(3rd place of the array not taken){ SetBoard(2=index) to be his lover}

                                break;
                            case CHILD:
                                //no need for double check because we check lover just once in loop (and there is only one lover and always the same)
                                if(actives[idsender].status==1) {
                                    if (p.sigma() > 0) {
                                        p.MakeChildren(sender);
                                    }
                                    sendMessage(p, sender, Messages.NONE);
                                }
                                break;
                            case KILLED:
                                if(sender.status==1) {
                                    if (p.isLeader) {
                                        updateLeaderPosition();
                                    }
                                    killed(sender, p);
                                }
                                break;
                            case ARGUE:
                                //if someone argues with leader they get kicked out
                                if(sender.status==1) {
                                    if (sender.isMember && p.isMember && p.cultMember.role == CultMember.Role.LEADER) {
                                        this.cultLeader.kickOut(sender);
                                        membersKickedOut++;
                                    }

                                    Random randArgue = new Random();
                                    int senderThrow = randArgue.nextInt(25);
                                    int pThrow = randArgue.nextInt(25);
                                    senderThrow += sender.stats.getCharisma() + sender.stats.getWillpower();
                                    pThrow += p.stats.getCharisma() + p.stats.getWillpower();

                                    //who "loses" puts the other in enemies, if its already enemy tries to kill
                                    if (senderThrow > pThrow) {
                                        if (p.EnemiesCounter < this.size.getAmountEnemies() && !p.isInEnemies(sender.id)) {
                                            int newEnemyPosition = p.findEmptySpotInBoard(this.size.BeginningEnemiesInterval(), this.size.EndEnemiesInterval());
                                            if (newEnemyPosition >= 0) {
                                                p.board.setBoardElement(newEnemyPosition, tup);
                                                sendMessage(p, sender, Messages.NONE);
                                            }
                                        } else if (p.isInEnemies(sender.id)) {
                                            p.kill(sender);
                                        } else {
                                            sendMessage(p, sender, Messages.NONE);
                                        }
                                    } else {
                                        if (sender.EnemiesCounter < this.size.getAmountEnemies() && !sender.isInEnemies(p.id)) {
                                            int newEnemyPosition = sender.findEmptySpotInBoard(this.size.BeginningEnemiesInterval(), this.size.EndEnemiesInterval());
                                            if (newEnemyPosition >= 0) {
                                                sender.board.setBoardElement(newEnemyPosition, p.board.getBoardElement(yourIndex));
                                                sendMessage(p, sender, Messages.NONE);
                                            }
                                        } else if (sender.isInEnemies(p.id)) {
                                            sender.kill(p);
                                        } else {
                                            sendMessage(sender, p, Messages.NONE);
                                        }
                                    }
                                }

                                break;
                            case FAILEDKILL:
                                if(sender.status==1) {
                                    if (sender.isMember && p.isMember && p.cultMember.role == CultMember.Role.LEADER) {
                                        sendMessage(p, sender, Messages.KILLED);
                                        attemptsOnLeader++;
                                    } else {
                                        if (sender.EnemiesCounter < this.size.getAmountEnemies() && !sender.isInEnemies(p.id)) {
                                            //if someone tries to kill you,it becomes your enemy
                                            int newEnemyPosition = p.findEmptySpotInBoard(this.size.BeginningEnemiesInterval(), this.size.EndEnemiesInterval());
                                            if (newEnemyPosition >= 0) {
                                                p.board.setBoardElement(newEnemyPosition, tup);
                                            }
                                        } else if (sender.isInEnemies(p.id)) {
                                            sender.kill(p);
                                        } else {
                                            sendMessage(sender, p, Messages.NONE);
                                        }
                                    }
                                }
                                break;
                            case FAILEDESCAPE:
                                if (sender.status==1 && sender.isMember && p.isMember && p.cultMember.role == CultMember.Role.LEADER) {
                                    if (sender.EnemiesCounter < this.size.getAmountEnemies() && !sender.isInEnemies(p.id)) {
                                        //idk, maybe put in enemies of leader
                                        int newDefectorPosition = p.findEmptySpotInBoard(this.size.BeginningEnemiesInterval(), this.size.EndEnemiesInterval());
                                        if (newDefectorPosition >= 0) {
                                            p.board.setBoardElement(newDefectorPosition, tup);
                                        }
                                    }else if (sender.isInEnemies(p.id)) {
                                        sender.kill(p);
                                    } else {
                                        sendMessage(sender, p, Messages.NONE);
                                    }
                                }
                                break;
                            default:
                                //When we are SENDING messages, NOT RECEIVING
                                if(sender.status==1) {
                                    sendMessage(p, sender, Messages.NONE);
                                    defaultMethod(p);
                                }


                                //PROBABILITIES
                                //meet(4),friend(3),find lover(2),argue(3)
                                //if member: pray,question(4)(4),recruit(3)
                                //RARE: die of illness(2)


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
                        p.getProfile().setProfileElement(i,Messages.NONE);
                    }else{defaultMethod(p);}
                }else{defaultMethod(p);}
            }
        }
        //for EVENTS
        int faith=p.stats.getFaith();
        if(faith>=10){maxFaith++;}else if(faith<=0 && p.hasBeenMember){minFaith++;};
    }

    //----------------------DEFAULT---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void defaultMethod(Player p){
        Random random = new Random();
        int j = random.nextInt(101);

        if (j<20){
            //meet
            meet(p);
        } else if (j<30) {
            friend(p);
        } else if(j<38){
            lover(p);
            //lover
        } else if (j<48) {
            argue(p);
            //argue
        } else if (j<63) {
            if(p.isMember && p.stats.getFaith()>0 && p.stats.getFaith()<10){
                p.cultMember.pray();
            }
        } else if (j<78) {
            if(p.isMember && p.stats.getWillpower()>=1 && p.stats.getFaith()<10){
                p.cultMember.question();
            }
        } else if (j<98) {
            recruit(p);
        } else{
            if(p.stats.getAge()>49){
                boolean isSuicide= p.stats.getWillpower() > 3 && p.stats.getFaith() < 5;
                if(p.isLeader){updateLeaderPosition();}
                die(p,isSuicide);
            }
            //dies
            //maybe we could a method with two options, suicide
            //(only if you have a certain willpower and a low enough faith)
            //and death for illness, that do the exact same thing
            //(which is probably similar to killed method), but one updates suicides and the other deaths;

            //also suicide might be only if willpower is 4 or 5
        }
        if(p.isMember || p.isLeader){
            if(p.MeetsCounter==0){meet(p);}
        }
    }


    public void meet(Player p){
        int idreceiver = -1;
        Player receiver = null;
        Random random=new Random();
        boolean condition=true;
        while (condition){
            idreceiver=random.nextInt(actives.length);
            if(actives[idreceiver]!=null){
                receiver= actives[idreceiver];
                condition=false;
            }
        }
        if(receiver.id!=p.id && receiver.status==1 && !p.isInBoard(idreceiver) && p.MeetsCounter<this.size.getAmountAcq() && receiver.MeetsCounter<this.size.getAmountAcq()){
            //in BOARD
            int newPositionP=p.findEmptySpotInBoard(this.size.BeginningAcqInterval(),this.size.EndAcqInterval());
            int newPositionR=receiver.findEmptySpotInBoard(this.size.BeginningAcqInterval(),this.size.EndAcqInterval());
            if (newPositionP>=0 && newPositionR>=0) {
                p.board.setBoardElement(newPositionP, new Tuple<>(idreceiver, newPositionR));
                p.MeetsCounter++;
                receiver.board.setBoardElement(newPositionR, new Tuple<>(p.id, newPositionP));
                receiver.MeetsCounter++;

                //in PROFILE(messages)
                //send meet message
                sendMessage(p, receiver, Messages.MEET);
            }
        }
    }

    public void friend(Player p){
        int idreceiver = -1;
        Player receiver = null;
        Random random=new Random();
        boolean condition=p.MeetsCounter>0;
        while (condition){
            int i=random.nextInt(this.size.getAmountAcq())+this.size.BeginningAcqInterval();
            if(p.board.board[i]!=null && p.board.board[i].getFirst()!=null){
                idreceiver= p.board.board[i].getFirst();
                receiver=actives[idreceiver];
                condition=false;
            }
        }
        if (p.stats.getAge()>19 && receiver!=null && !p.isInFriends(idreceiver)
                && p.FriendsCounter<this.size.getAmountFriends()
                && receiver.FriendsCounter<this.size.getAmountFriends()) {
            //send friend message
            sendMessage(p,receiver,Messages.FRIEND);
        }
    }

    public void lover(Player p){
        int idreceiver = -1;
        Player receiver = null;
        Random random=new Random();
        boolean condition=p.MeetsCounter>0 || p.FriendsCounter>0 || p.EnemiesCounter>0;
        while (condition){
            int i=random.nextInt(this.size.getAmountAcq()+this.size.getAmountFriends()+this.size.getAmountEnemies())+this.size.BeginningFriendsInterval();
            if(p.board.board[i]!=null && p.board.board[i].getFirst()!=null){
                idreceiver= p.board.board[i].getFirst();
                receiver=actives[idreceiver];
                condition=false;
            }
        }
        if(p.stats.getAge()>19 && receiver!=null){
            if(p.board.board[1]==null || (p.board.board[1].getFirst()==null && p.board.board[1].getSecond()==null)){
                //send LOVER message-->you dont have a lover and ask them to be it
                sendMessage(p,receiver,Messages.LOVER);
            }else if(p.ChildrenCounter<this.size.getAmountChildren() && receiver.ChildrenCounter<this.size.getAmountChildren()){
                //send CHILD message-->you have a lover and want to make children with them
                sendMessage(p,receiver,Messages.CHILD);
            }
        }
    }

    public void argue(Player p){
        int idreceiver = -1;
        Player receiver = null;
        Random random=new Random();
        boolean condition=p.MeetsCounter>0;
        while (condition){
            int i=random.nextInt(this.size.getAmountAcq())+this.size.BeginningAcqInterval();
            if(p.board.board[i]!=null && p.board.board[i].getFirst()!=null){
                idreceiver= p.board.board[i].getFirst();
                receiver=actives[idreceiver];
                condition=false;
            }
        }
        if(p.stats.getAge()>19 && receiver!=null && receiver.status==1){
            sendMessage(p,receiver,Messages.ARGUE);
        }
    }

    public void recruit(Player p){
        int idreceiver = -1;
        int yourIndex=-1;
        Player receiver = null;
        Random random=new Random();
        boolean condition=p.MeetsCounter>0;
        while (condition){
            int i=random.nextInt(this.size.getAmountAcq())+this.size.BeginningAcqInterval();
            if(p.board.board[i]!=null && p.board.board[i].getFirst()!=null){
                idreceiver= p.board.board[i].getFirst();
                yourIndex=p.board.board[i].getSecond();
                receiver=actives[idreceiver];
                condition=false;
            }
        }
        if((p.stats.getAge() > 19) && (receiver != null) && (receiver.id != p.id) && (p.status == 1) && (receiver.status == 1) && p.isMember && !receiver.isMember && !cult.isInCult(receiver) && !receiver.alreadyRecruited()){
            Random r = new Random();
            int pThrow=r.nextInt(30);
            int rThrow=r.nextInt(30);
            pThrow+=p.stats.getCharisma();
            rThrow+=receiver.stats.getWillpower();
            if(pThrow>=rThrow){
                sendMessage(p,receiver,Messages.RECRUITED);
            }else{
                p.cultMember.pray();
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    
    public String seeActives(){
        return Arrays.toString(actives);
    }

    public int findEmptySpotInActives(){
        for(int i=0;i<actives.length;i++){
            if(actives[i]==null){return i;}
        }
        return -1;
    }

    public void sendMessage(Player sender, Player receiver, Messages message){
        int receiverindex=sender.indexInBoard(receiver.id);
        if(receiverindex>=0) {
            int senderindex = sender.board.getBoardElement(receiverindex).getSecond();
            receiver.profile.setProfileElement(senderindex, message);
        }
    }

//-----------METHODS FOR DYING----------------------------------------------------------------------------------------------
    public void killed(Player killer,Player victim){
        victim.status=0;//not active anymore

        if (victim.isMember) {
            this.cult.removeMember(victim);
            deadInCult++;
        }

        clearRelationships(victim);
        actives[victim.id]=null;
        murdered++;

    }

    public void die(Player p, boolean isSuicide) {
        p.status = 0;

        if (p.isMember) {
            this.cult.removeMember(p);
            deadInCult++;
        }

        clearRelationships(p);
        actives[p.id]=null;

        if (isSuicide) {
            suicides++;
        } else {
            deaths++;
        }
    }

    private void clearRelationships(Player p) {
        //p is the dead one
        for (int i = 0; i < p.board.board.length; i++) {
            Tuple<Integer, Integer> relationship = p.board.board[i];
            if (relationship != null && relationship.getFirst()!=null && relationship.getSecond()!=null) {
                int friendId = relationship.getFirst();
                int indexInFriendBoard = relationship.getSecond();
                Player friend = actives[friendId];
                if (friend != null) {
                    friend.profile.setProfileElement(indexInFriendBoard,null);
                    friend.board.board[indexInFriendBoard] = null;
                    friend.decreaseConsts(indexInFriendBoard);
                }
            }
        }
    }

    public void updateLeaderPosition(){
        Tuple<Integer, Integer> heirTup=this.cultLeader.board.board[this.size.BeginningChildrenInterval()];
        if(heirTup!=null && heirTup.getFirst()!=null && heirTup.getSecond()!=null) {
            Player heir = actives[heirTup.getFirst()];
            heir.makeLeader();
            System.out.println("We have a new leader!");
        }else{
            civilWar(); //CIVIL WAR EVENT
        }
    }

    //------------------------------------EVENTS-----------------------------------------------------------------------------------------------------------------------------
    public void checkEvents(){
        if(maxFaith>=(int) Math.ceil(cult.cult.size()*0.5)){
            massSuicide();
        } else if (minFaith>=(int) Math.ceil(cult.cult.size()*0.5)) {
            rebellion();
        } else if (attemptsOnLeader>=3) {
            conArtist();
        }
    }
    public void massSuicide(){
        if (cult.cult.size()>19) {
            System.out.println(this.cultLeader.name + " " + this.cultLeader.surname + " reached their goal.\nAll their followers gave their lives for them in a mass suicide drinking CocaCola with bleach, the sacred drink.\nThey felt fulfilled for a while, they even wanted to start another cult. \nWhen they were about to skip town the cops arrested them. \nWhat he didn’t know was that there was a survivor, their lover sneaked out during the turmoil and their testimony was useful to catch them. \nNow " + this.cultLeader.name + " spends the rest of their days in prison.");
            terminateGame();
        }
    }

    public void conArtist(){
        System.out.println("After 5 murder attempts, "+this.cultLeader.name+" "+this.cultLeader.surname+" realizes their charisma won’t work this time. \nThem and their lover sneaked out during the night. \nSome members waited months thinking they left for a prophecy, however it was later revealed that the leader was a con artist that started the cult in order to become a millionaire. \nSome say they bought a house somewhere in the Caribbean where they live with their lover and beloved dog Loki; others say they started another cult in Polynesia.  \nAlmost everyone went back to their lives, even though they still hope that the prophecy will be fulfilled.");
        terminateGame();
    }

    public void rebellion(){
        System.out.println("There was always some tension in the cult, most members weren’t 100% convinced by "+this.cultLeader.name+" "+this.cultLeader.surname+", but still they stuck around to see where this was going. \n"+this.cultLeader.name+" thought his charisma would be enough to pull it off, however he didn’t expect that his members would start questioning them and turn against them. \nAnd as quickly as it started it quickly vanished.");
        terminateGame();
    }

    public void civilWar(){
        if(cult.cult.size()>19) {
            System.out.println("For a moment " + this.cultLeader.name + " " + this.cultLeader.surname + " had it all, the big following they had always wanted.\nBut as all things nothing last forever.\nAfter their death, since they were childless, the leader position was left empty and up for grabs.\n" + this.cultLeader.name + " would have never imagined that their right-hand man and closest friend would betray his memory.\nTheir \"friend\" tried to take over the cult claiming " + this.cultLeader.name + " had named him heir, but not everyone was happy though. \nSome members said their god " + this.cult.god + " spoke to them saying they were the rightful heirs to the cult. \nMany leaders followed, each one murdered after a short period. \nThe media called it the \"deadly leaderless cult\", more than half of its members died trying to take over the cult and after many deaths the police intervened saving the few survivors");
        }else{
            System.out.println("Unfortunately, our dear leader " + this.cultLeader.name + " " + this.cultLeader.surname + " has left us too soon, without being able to achieve their goal and without an heir to succeed them. \nPerhaps they started their cult too late, or perhaps in the end the supreme "+ cult.god +" did not favor them as much as they believed. \nIn any case, " + this.cultLeader.name + " was unable to recruit enough believers in their cult. \nMay "+cult.god+" bless them.");
        }
        terminateGame();
    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public boolean isEveryoneMember(){
        for (Player p: actives){
            if(p!=null && !p.isMember){return false;}
        }
        return true;
    }

    public int nPeople(){
        int n=0;
        for(Player p: actives){
            if(p!=null && p.status==1){
                n++;
            }
        }
        return n;
    }
    public int nPeopleNotRemoved(){
        int n=0;
        for(Player p: actives){
            if(p!=null && p.status==0){
                n++;
            }
        }
        return n;
    }

    public int nMembers(){
        int n = 0;
        for (Player p: actives){
            if(p!=null && p.isMember){n++;}
        }
        return n;
    }

    public void terminateGame(){
        gameThread.interrupt();
    }
}
