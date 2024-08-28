package main;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //--------------------- FOR SIZE -------------------------------------------------------------------------------------------------------------------------------------
        Scanner myObj = new Scanner(System.in);
        int sizeNum = 0;
        Size size;
        String god = null;

        // Enter username and press Enter
        System.out.println("Choose a size - Type 1 for SMALL, 2 for MEDIUM or 3 for LARGE:");
        boolean valid = false;
        while (!valid){
            try {
                sizeNum = Integer.parseInt(myObj.nextLine());
                if (sizeNum == 1 || sizeNum == 2 || sizeNum == 3) {
                    valid = true;
                } else {
                    System.out.println("Invalid choice! Please choose the size of the universe by pressing: 1, 2, 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice! Please choose the size of the universe by pressing: 1, 2, 3.");
            }
        }
        switch (sizeNum){
            case 1:
                size=Size.SMALL;
                break;
            case 2:
                size=Size.MEDIUM;
                break;
            case 3:
                size=Size.LARGE;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + sizeNum);
        }
        System.out.println("What do you want the name of our almighty god to be?");
        boolean valid2 = false;
        while (!valid2) {
            god = myObj.nextLine();
            if (!god.matches(".*[^a-zA-Z].*")) {
                valid2 = true;
            }else{
                System.out.println("Invalid choice! Please insert a name without digits or special characters in it.");
            }
        }



        System.out.println("Chosen size: "+size.name+" ");
        System.out.println("Name of our god: "+god+"\n\n");

        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        //Game game=new Game(size);
        //game.startGame();
        //System.out.println(game.seeActives());



        /*Player pl=new Player(0,game);
        Leader l=new Leader(pl);
        game.cult.setLeader(l);

        Player player = null;
        for(int i=1; i<7; i++){
            player=new Player(i,game);
            game.cult.addMember(player);
        }
        Player player2=new Player(7,game);
        System.out.println(game.cult.cult.size());
        System.out.println(game.cult);

        game.killed(player2,player);

        System.out.println(game.deadInCult);
        System.out.println(game.cult.cult.size());
        System.out.println(game.cult);
        */


        /*Player p=new Player('a',10,10);
        System.out.println(p.toString());
        Board b=p.getBoard();*/
        //Game game= new Game(10);
        //System.out.println(game.seeActives());
        /*game.startGame();
        game.gameThread.sleep(100000);
        game.gameThread.interrupt();*/

        //DOWNCASTING
        /*Player p=new Player(1,10,10);
        Player l= new Leader(p.id,p.getBoard().len,p.getProfile().len);
        Leader l2=(Leader)l;*/

        //STILL TO ADD:
        //methods for actions
        //death by age (add age stat)-->payoff in profile?
    }
}