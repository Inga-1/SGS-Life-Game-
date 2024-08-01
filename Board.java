import java.util.Arrays;
public class Board{
    private Tuple[] board;
    public Board(char name, int b){
        board=new Tuple[b];
        Arrays.fill(board, new Tuple(null,null));
        board[0]=new Tuple<>(name,1);
    }
    //public Tuple get
    public String toString(){
        String s=new String();
        s+="[";
        for(Tuple<Board,Profile> i:board){
            if(i==null){
                s+="( , )";
            }else{s+=i.toString();}
        }
        s+="]";
        return s;
    }
    public Tuple<Board,Profile> getBoardElement(int i){
        return board[i];
    }
    public void setBoardElement(int i, Tuple t){
        board[i]=t;
    }
    public Tuple[] getBoard(){
        return board;
    }
}
