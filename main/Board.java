package main;

import java.util.Arrays;
public class Board{
    protected Tuple<Integer,Integer>[] board;
    protected int len;
    private Size size;
    public Board(int id, Size s){
        int b=s.maxSize;
        board=new Tuple[b];

        this.size=size;
        this.len=b;

        Arrays.fill(board, new Tuple(null,null));
        board[0]=new Tuple<Integer,Integer>(id,1);
    }
    //public main.Tuple get
    public String toString(){
        String s=new String();
        s+="[";
        for(Tuple<Integer, Integer> i:board){
            if(i==null){
                s+="( , )";
            }else{s+=i.toString();}
        }
        s+="]";
        return s;
    }
    public Tuple<Integer, Integer> getBoardElement(int i){
        return board[i];
    }
    public void setBoardElement(int i, Tuple t){
        board[i]=t;
    }
    public Tuple[] getBoard(){
        return board;
    }
}
