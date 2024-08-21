package main;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //--------------------- FOR SIZE -------------------------------------------------------------------------------------------------------------------------------------
        Scanner myObj = new Scanner(System.in);
        int sizeNum;
        Size size;

        // Enter username and press Enter
        System.out.println("Choose a size - Type 1 for SMALL, 2 for MEDIUM or 3 for LARGE:");
        sizeNum = Integer.parseInt(myObj.nextLine());
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




        System.out.println("Chosen size: "+size.name+"\n\n");
        //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        Game game=new Game(size);
        game.startGame();
        game.gameThread.sleep(60000);


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