package main;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        /*Player p=new Player('a',10,10);
        System.out.println(p.toString());
        Board b=p.getBoard();*/
        Game game= new Game(10);
        game.startGame();
        game.gameThread.sleep(10000);
        game.gameThread.interrupt();
    }
}