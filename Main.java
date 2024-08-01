//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Player p=new Player('a',10,10);
        System.out.println(p.toString());
        Board b=p.getBoard();
    }
}