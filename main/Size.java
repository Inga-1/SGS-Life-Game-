package main;

public enum Size {
    SMALL(25,10, "small"),
    MEDIUM(50,25, "medium"),
    LARGE(100,40, "large");

    int actives;
    int possibleChildren;
    int maxSize;
    String name;

    Size(int actives, int children, String name){
        this.actives=actives;
        this.possibleChildren=children;
        this.maxSize=actives+children;
        this.name=name;
    }

    //children
    public int getAmountChildren(){
        switch (this){
            case SMALL:
                return 5;
            case MEDIUM:
                return 7;
            case LARGE:
                return 14;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public int BeginningChildrenInterval(){
        return 3;
    }
    public int EndChildrenInterval(){
        return getAmountChildren()+2;
    }

    //parents
    public int getParentOneIndex(){
        int x=this.EndChildrenInterval()+1;
        return x;
    }
    public int getParentTwoIndex(){
        int x=this.EndChildrenInterval()+2;
        return x;
    }

    //friends
    public int getAmountFriends(){
        switch (this){
            case SMALL:
                return 6;
            case MEDIUM:
                return 18;
            case LARGE:
                return 36;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public int BeginningFriendsInterval(){
        return getParentTwoIndex()+1;
    }
    public int EndFriendsInterval(){
        return getParentTwoIndex()+getAmountFriends();
    }

    //acquaintances
    public int getAmountAcq(){
        switch (this){
            case SMALL:
                return 14;
            case MEDIUM:
                return 30;
            case LARGE:
                return 60;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public int BeginningAcqInterval(){
        return EndFriendsInterval()+1;
    }
    public int EndAcqInterval(){
        return EndFriendsInterval()+getAmountAcq();
    }

    //enemies
    public int getAmountEnemies(){
        switch (this){
            case SMALL:
                return 5;
            case MEDIUM:
                return 15;
            case LARGE:
                return 25;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public int BeginningEnemiesInterval(){
        return EndAcqInterval()+1;
    }
    public int EndEnemiesInterval(){
        return EndAcqInterval()+getAmountEnemies();
    }
}