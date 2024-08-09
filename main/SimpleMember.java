package main;

public class SimpleMember extends CultMember{
    private final Role role;
    public SimpleMember(int id,int size,Cult cult){
        super(id,size,cult);
        role=Role.NONE;
    }
}
