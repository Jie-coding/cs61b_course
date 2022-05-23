public class OffByN implements CharacterComparator{
    private int N;

    public OffByN(int n){
        this.N = n;
    }

    @Override
    public boolean equalChars(char x, char y){
        int result = x - y;
        if (result == N || result == -N){
            return true;
        }
        else {return false;}
    }
}
