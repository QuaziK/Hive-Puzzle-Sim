import java.util.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;

class Tuple<X, Y> {
    public final X x;
    public final Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}

class HiveTile {
    char[] logotypes;
    Icon[] icontypes;
    char display;
    int deffault;
    int num;

    public HiveTile(char[] l){
        this.logotypes = l;
        this.num = 0;
        this.deffault = num;
    }

    public HiveTile(char[] l, int nn){
        this.logotypes = l;
        this.num = nn;
        this.deffault = num;
    }

    public HiveTile(Icon[] ia){
        this.icontypes = ia;
        this.num = 0;
        this.deffault = num;
    }

    public void goNext(){
        num++;
        if (num > 3) {
            num = 0;
        }
    }

    public void goPrevious(){
        num--;
        if (num < 0) {
            num = 3;
        }
    }

    public void setSeed(int nn){
        num = nn;
        deffault = num;
    }

    public void resetToDeffault(){
        num = deffault;
    }

    public String view(){
        display = logotypes[this.num];
        return display + "";
    }

    public Icon viewIcon(){
        return icontypes[this.num];
    }
}

public class HivePuzzle {

    Icon barsIcon = new ImageIcon("C:\\Users\\ykozh\\Documents\\GitHub\\Hive-Puzzle-Sim\\bars.png");
    Icon AcrossIcon = new ImageIcon("C:\\Users\\ykozh\\Documents\\GitHub\\Hive-Puzzle-Sim\\Across.png");
    Icon radioIcon = new ImageIcon("C:\\Users\\ykozh\\Documents\\GitHub\\Hive-Puzzle-Sim\\radio.png");
    Icon bugIcon = new ImageIcon("C:\\Users\\ykozh\\Documents\\GitHub\\Hive-Puzzle-Sim\\bug.png");

    Icon[] icontypes = {barsIcon,AcrossIcon,radioIcon,bugIcon};
    char[] logotypes = {404,412,415,418};
    HiveTile[] board = {new HiveTile(icontypes), new HiveTile(icontypes), new HiveTile(icontypes),
                        new HiveTile(icontypes), new HiveTile(icontypes), new HiveTile(icontypes),
                        new HiveTile(icontypes), new HiveTile(icontypes), new HiveTile(icontypes)};

    String seed = "000000000";

    ArrayList<Tuple<Integer, Integer>> moves = new ArrayList<Tuple<Integer, Integer>>();

    public HivePuzzle(Icon a, Icon b, Icon c, Icon d){
        barsIcon = a;
        AcrossIcon = b;
        radioIcon = c;
        bugIcon = d;
    }

    public void makeMove(int x, int y){
        Tuple<Integer, Integer> t = new Tuple<Integer,Integer>(x, y);
        moves.add(t);
        int target = matrixToLinear(x,y);
        int[] collat = getCollat(target);
        for (int ii = 0; ii<collat.length; ii++){
            try {
                board[collat[ii]].goNext();
            } catch (IndexOutOfBoundsException E) {
                // do nothing
            }
        }
    }

    public void resetSeed(){
        char[] seedc = seed.toCharArray();
        for (int ii = 0; ii < 9; ii++){
            board[ii].resetToDeffault();
        }
    }

    public void newSeed(){
        seed = generateSeed();
        char[] newseed = seed.toCharArray();
        seed = String.valueOf(newseed);
        for (int ii = 0; ii < 9; ii++){
            board[ii].setSeed((int)newseed[ii]);
        }
    }

    private String generateSeed(){
        char[] outc = new char[9];
        for (int ii = 0; ii < 9; ii++){
            outc[ii] = (char)(int)Math.floor(Math.random()*(3-0+1)+0);
        }
        return String.valueOf(outc);
    }

    public void undo(){
        Tuple<Integer,Integer> t = moves.get(moves.size()-1);
        moves.remove(moves.size()-1);
        int target = matrixToLinear(t.x,t.y);
        int[] collat = getCollat(target);
        for (int ii = 0; ii<collat.length; ii++){
            try {
                board[collat[ii]].goPrevious();
            } catch (IndexOutOfBoundsException E) {
                // do nothing
            }
        }
    }

    private int[] getCollat(int target){
        int[] collat = {target, target+3,target+6,target-3,target-6, -1, -1};
        switch (target%3) {
            case 0:
                collat[5] = target + 1;
                collat[6] = target + 2;
                break;
            case 1:
                collat[5] = target + 1;
                collat[6] = target - 1;
                break;
            case 2:
                collat[5] = target - 1;
                collat[6] = target - 2;
                break;
        }
        return collat;
    }

    private int matrixToLinear(int x, int y){
        return y+(x*3);
    }

    private Tuple<Integer, Integer> linearToMatrix(int n){
        return new Tuple<Integer,Integer>(n/3, n%3);
    }
}