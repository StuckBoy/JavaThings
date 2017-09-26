

public class collatzMain { 
	
    public static void collatz(long n) {
    	
    	System.out.print(n + " ");
    	if (n == 1) return;
   		else if (n % 2 == 0) collatz(n / 2);
   		else collatz(3*n + 1);
    }

    
    public static void main(String[] args) {
    	long startTime = System.nanoTime();
    	long[] prev = new long[1000000];
    	int maxCount = 0;
    	long whichNum = 0;
    	
    	for (long i = 1; i < 1000000; i++) {
    		int count = 0;
    		boolean tooBig = false;
        	long startNum = i;
        	long n = startNum;
        	while (n != 1) {
        		if (n >= 1000000) tooBig = true;
        		else tooBig = false;
        		if (tooBig || prev[(int) n] == 0) {
             		if (n % 2 == 0) {
        				n = n / 2;
        			}
        			else if (n % 2 != 0) {
        				n = (3*n) + 1;
        			}
        			count++;
        		}
        		else {
        			//System.out.print("Existing entry found!");
        			count += prev[(int) n];
        			break;
        		}
        	}
        	
        	prev[(int) i] = count;
        	
        	System.out.print("Sequence length for " + startNum + ": " + count);
        	System.out.println();
        	if (count > maxCount) {
        		maxCount = count;
        		whichNum = startNum;
        	}
        } 
    	System.out.print("Longest sequence was " + maxCount + " from " + whichNum + ": ");
    	collatz(whichNum);
        prev = null;
        long endTime = System.nanoTime();
        System.out.println();
        System.out.println("Took " + ((double)(endTime - startTime)/1000000000) + " seconds.");
    }
    
}
