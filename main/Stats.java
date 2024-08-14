package main;
import java.util.ArrayList;
import java.util.Random;

public class Stats {
    private ArrayList<Integer> stats;
    private static final int WILL = 0;
    private static final int CHARISMA = 1;
    private static final int FAITH = 2;
    private static final int AGE = 3;

    public Stats() {
        stats = new ArrayList<>();
        stats.add(0); // Willpower
        stats.add(0); // Charisma
        stats.add(0); // Faith
        stats.add(0); // Age
    }

    public int getWillpower() {
        return stats.get(WILL);
    }

    public int getCharisma() {
        return stats.get(CHARISMA);
    }

    public int getFaith() {
        return stats.get(FAITH);
    }

    public int getAge() {
        return stats.get(AGE);
    }

    public void setWillpower(int will) {
        stats.set(WILL, will);
    }

    public void setCharisma(int charisma) {
        stats.set(CHARISMA, charisma);
    }

    public void setFaith(int faith) {
        stats.set(FAITH, faith);
    }

    public void setAge(int age) {
        stats.set(AGE, age);
    }
    public void randomizeStats() {
        Random random = new Random();
        setFaith(0);//change in 5 when becoming Cult Member, then max is 10
        setAge(random.nextInt(51));

        setCharisma(random.nextInt(5));
        setWillpower(random.nextInt(5));
    }

    public String toString(){
        int a=getAge();
        int f=getFaith();
        int c=getCharisma();
        int w=getWillpower();

        return "age("+a+"), faith("+f+"), charisma("+c+"), willpower("+w+")";
    }
}
