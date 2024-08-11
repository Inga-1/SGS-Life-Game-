package main;

public enum Size {
    SMALL(25,10),
    MEDIUM(50,25),
    LARGE(100,40);

    int actives;
    int possibleChildren;
    int maxSize;
    Size(int actives, int children){
        this.actives=actives;
        this.possibleChildren=children;
        this.maxSize=actives+children;
    }

    //children
    public int getAmountChildren(){
        switch (this){
            case SMALL:
                return 5;
                break;
            case MEDIUM:
                return 7;
                break;
            case LARGE:
                return 14;
                break;
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

    //siblings
    public int getAmountSiblings(){
        return getAmountChildren()-1;
    }
    public int BeginningSiblingsInterval(){
        return getParentTwoIndex()+1;
    }
    public int EndSiblingsInterval(){
        return getParentTwoIndex()+getAmountSiblings();
    }

    //friends
    public int getAmountFriends(){
        switch (this){
            case SMALL:
                return 6;
            break;
            case MEDIUM:
                return 18;
            break;
            case LARGE:
                return 36;
            break;
        }
    }

    public int BeginningFriendsInterval(){
        return EndSiblingsInterval()+1;
    }
    public int EndFriendsInterval(){
        return EndSiblingsInterval()+getAmountFriends();
    }

    //acquaintances
    public int getAmountAcq(){
        switch (this){
            case SMALL:
                return 10;
            break;
            case MEDIUM:
                return 24;
            break;
            case LARGE:
                return 47;
            break;
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
            break;
            case MEDIUM:
                return 15;
            break;
            case LARGE:
                return 25;
            break;
        }
    }

    public int BeginningEnemiesInterval(){
        return EndAcqInterval()+1;
    }
    public int EndEnemiesInterval(){
        return EndAcqInterval()+getAmountEnemies();
    }
}
