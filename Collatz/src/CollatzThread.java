
public class CollatzThread extends Thread {
	
	 public static void collatzRecursive(long n) {    	
	    	System.out.print(n + " ");
	    	if (n == 1) return;
	   		else if (n % 2 == 0) collatzRecursive(n / 2);
	   		else collatzRecursive(3*n + 1);
	    } 
	 
	    public static void main(String[] args) {
	    	//Track execution time.
	    	long startTime = System.nanoTime();
	    	long[] prev = new long[1000000];
	    	int maxCount = 0;
	    	long whichNum = 0;
	    	
	    	//Number crunching time.
	    	for (long i = 1; i < 1000000; i++) {
	    		int count = 0;
	    		boolean tooBig = false;
	        	long startNum = i;
	        	long n = startNum;
	        	//While the sequence hasn't resolved.
	        	while (n != 1) {
	        		if (n >= 1000000) tooBig = true;
	        		else tooBig = false;
	        		//If the number is too big to check or there isn't a sequence for it.
	        		if (tooBig || prev[(int) n] == 0) {
	             		if (n % 2 == 0) {
	        				n = n / 2;
	        			}
	        			else if (n % 2 != 0) {
	        				n = (3*n) + 1;
	        			}
	        			count++;
	        		}
	        		//There's already a sequence generated for this number.
	        		else {
	        			//System.out.print("Existing entry found!");
	        			count += prev[(int) n];
	        			break;
	        		}
	        	}
	        	
	        	//Add the sequence length for the number just processed.
	        	prev[(int) i] = count;
	        	
	        	System.out.print("Sequence length for " + startNum + ": " + count);
	        	System.out.println();
	        	
	        	//If the sequence was longer than the current maxCount, replace with count.
	        	if (count > maxCount) {
	        		maxCount = count;
	        		whichNum = startNum;
	        	}
	        } 
	    	System.out.print("Longest sequence was " + maxCount + " from " + whichNum + ": ");
	    	//Show the sequence for the longest number less than 1,000,000.
	    	collatzRecursive(whichNum);
	        prev = null;
	        long endTime = System.nanoTime();
	        System.out.println();
	        System.out.println("Took " + ((double)(endTime - startTime)/1000000000) + " seconds.");
	    }
}
