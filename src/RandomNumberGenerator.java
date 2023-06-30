import java.util.*;

public class RandomNumberGenerator {

    private ArrayList<Integer> usedNumbers;
    private Random r;
    private int maxNumber;

    public RandomNumberGenerator(int maxNumber) {
        r = new Random();
        usedNumbers = new ArrayList<Integer>();
        this.maxNumber = maxNumber;
    }

    public int nextIntegerWithoutReplacement() {
        Integer res = r.nextInt(maxNumber);
        if (usedNumbers.contains(res)) {
        	nextIntegerWithoutReplacement();
        }
        usedNumbers.add(res);
        if (usedNumbers.size()==maxNumber) {
            usedNumbers.clear();
        }
        return res;
    }

}