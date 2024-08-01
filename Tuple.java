public class Tuple<T,S>{
    private T a;
    private S b;
    public Tuple(T a, S b){
        this.a=a;
        this.b=b;
    }
    public T getFirst(){return a;}
    public S getSecond(){return b;}
    public String toString(){
        String s=new String();
        if(a!=null){s+="("+a;}else{s+="( ";}
        if(b!=null){s+=","+b+")";}else{s+=", )";}
        return s;
    }
}
