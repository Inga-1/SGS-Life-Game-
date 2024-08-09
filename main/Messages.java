package main;

public enum Messages {
    NONE(0),
    MEET(1), //become acquaintances
    RECRUITED(2), //become part of cult
    FRIEND(3), //become friends
    LOVER(4), //become lovers
    CHILD(5), //make children-->sigma function
    ARGUE(6), //arguing with you
    KILLED(7), //you have been killed
    FAILEDKILL(8),
    FAILEDESCAPE(9);

    private int message;
    Messages(int m){this.message=m;}
    public int toInt(){return message;}
    public String toString(){return ""+message+"";}
}
