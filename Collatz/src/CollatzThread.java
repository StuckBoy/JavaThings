
public class CollatzThread {
	
	public static void collatzRecursive(long n) {    	
    	//Recursive Collatz printer. Cleaner to use, but more overhead on large numbers.
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

	    //Number crunching time.
	    NumCruncher crunchatizer = new NumCruncher(1,200000, prev);
	    NumCruncher leCrunch = new NumCruncher(200001, 400000, prev);
	    NumCruncher crunchberry = new NumCruncher(400001, 600000, prev);
	    NumCruncher oops = new NumCruncher(600001, 800000, prev);
	    NumCruncher crunchling = new NumCruncher(800001, 1000000, prev);
	    
	    
	    crunchatizer.start();
	    leCrunch.start();
	    crunchberry.start();
	    oops.start();
	    crunchling.start();
	    
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
	    
	    long threadFinish = System.nanoTime();
	    
	    for(long c = 1; c < 1000000; c++) {
	    	if (prev.getNum(c) > theMax) {
	    		theMax = prev.getNum(c);
	    		theNum = c;
	    	}
	    	System.out.println("Length of " + c + ": " + prev.getNum(c));
	    }
	    
	    long endTime = System.nanoTime();
	    System.out.println();
	    System.out.println("All threads finished in " + ((double)(threadFinish - startTime)/1000000000) + " seconds.");
	    System.out.println("Took " + ((double)(endTime - startTime)/1000000000) + " seconds.");	        
	    System.out.print("Longest sequence: " + theMax + " from " + theNum + ": ");
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
    	for (long i = start; i < stop; i++) {
    		int count = 0;
    		boolean tooBig = false;
        	long startNum = i;
        	long n = startNum;
        	//While the sequence hasn't resolved.
        	while (n != 1) {
        		if (n >= 1000000) tooBig = true;
        		else tooBig = false;
        		//If the number is too big to check or there isn't a sequence for it.
        		synchronized(prev) {
        			if (tooBig || prev.checkNum(n)) {
        				if (n % 2 == 0) n = n / 2;
        				else n = (3*n) + 1;
        				count++;
        			}
        		//There's already a sequence generated for this number.
        			else {
        				count += prev.getNum(n);
        				break;
        			}
        		}
        	}
        	//Add the sequence length for the number just processed.
        	synchronized(prev) {
        		prev.setNum(i, count);
        	}
        	//If the sequence was longer than the current maxCount, replace with count.
        	if (count > maxim) {
        		maxim = count;
        		neo = startNum;
        	}
        }
 	}
}