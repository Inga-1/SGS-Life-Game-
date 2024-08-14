package main;

public enum Messages {
    NONE("no messages"),
    MEET("meet"), //become acquaintances
    RECRUITED("recruited"), //become part of cult
    FRIEND("friend"), //become friends
    LOVER("lover"), //become lovers
    CHILD("make children"), //make children-->sigma function
    ARGUE("argue"), //arguing with you
    KILLED("killed"), //you have been killed
    FAILEDKILL("failed kill"),
    FAILEDESCAPE("failed escape");

    private String message;
    Messages(String m){this.message=m;}
    public String toString(){return message;}
}
