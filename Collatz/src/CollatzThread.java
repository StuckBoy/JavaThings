
public class CollatzThread {
	
	public static void collatzRecursive(long n) {    	
    	System.out.print(n + " ");
    	if (n == 1) return;
   		else if (n % 2 == 0) collatzRecursive(n / 2);
   		else collatzRecursive(3*n + 1);
    } 
	
	public static void main(String[] args) {
	    //Track execution time.
	    long startTime = System.nanoTime();
	    Array prev = new Array();	    	
	    	
	    long theMax = 0;
	    long theNum = 0;
	    	
	    NumCruncher crunchatizer = new NumCruncher(1,250000, prev);
	    NumCruncher leCrunch = new NumCruncher(250001, 500000, prev);
	    NumCruncher crunchberry = new NumCruncher(500001, 750000, prev);
	    NumCruncher oops = new NumCruncher(750001, 1000000, prev);
	    	
	    crunchatizer.start();
	    leCrunch.start();
	    crunchberry.start();
	    oops.start();
	    while(true) {
	    	try {
	    		crunchatizer.join();
	    		leCrunch.join();
	    		crunchberry.join();
	    		oops.join();
	    		break;
	    	} catch (InterruptedException e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    for(long c = 1; c < 1000000; c++) {
	    	if (prev.getNum(c) > theMax) {
	    		theMax = prev.getNum(c);
	    		theNum = c;
	    	}
	    	System.out.println("Sequence length for " + c + ": " + prev.getNum(c));
	    }
	    
	   	//Number crunching time.
	    long endTime = System.nanoTime();
	    System.out.println();
	    System.out.println("Took " + ((double)(endTime - startTime)/1000000000) + " seconds.");
	        
	    System.out.print("Longest sequence was " + theMax + " from " + theNum + ": ");
	    //Show the sequence for the longest number less than 1,000,000.
	    collatzRecursive(theNum);
			
	}
}

class Array { 
	static long[] prev = new long[1000000]; 
	
	public static boolean checkNum(long n) {
		if (prev[(int)n] == 0) return true;
		else return false;
	}
	
	public static long getNum(long b) {
		long num = prev[(int) b];
		return num;
	}
	
	public static void setNum(long a, int i) {
		prev[(int) a] = i;
	}
}

class NumCruncher extends Thread {
	
	long start;
	long stop;
	Array prev;
	int maxim;
	long neo;
	
	public NumCruncher(long a, long b, Array s) {
		start = a;
		stop = b;
		prev = s;
	}
	
	public synchronized void run() {
	//	long startTime = System.nanoTime();
    	for (long i = start; i < stop; i++) {
    		int count = 0;
    		boolean tooBig = false;
        	long startNum = i;
        	long n = startNum;
        	//While the sequence hasn't resolved.
        	synchronized(prev) {
        		while (n != 1) {
        			if (n >= 1000000) tooBig = true;
        			else tooBig = false;
        		//If the number is too big to check or there isn't a sequence for it.
        			if (tooBig || prev.checkNum(n)) {
             			if (n % 2 == 0) n = n / 2;
             			else n = (3*n) + 1;
             			count++;
        			}
        		//There's already a sequence generated for this number.
        			else {
        			//System.out.print("Existing entry found!");
        				count += prev.getNum(n);
        				break;
        			}
        		}
        	//Add the sequence length for the number just processed.
        		prev.setNum(i, count);
        	}
//        	System.out.print("Sequence length for " + startNum + ": " + count);
 //       	System.out.println();
        	//If the sequence was longer than the current maxCount, replace with count.
        	if (count > maxim) {
        		maxim = count;
        		neo = startNum;
        	}
        }
  //      long endTime = System.nanoTime();
 //       System.out.println();
//        System.out.println("Took " + ((double)(endTime - startTime)/1000000000) + " seconds.");
	}
}