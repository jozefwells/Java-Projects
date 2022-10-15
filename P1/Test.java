import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * 
 * The cache is a storage type, the cacheTest will read through the file word by
 * word, if the word it reads is already in the cache then it will move it to
 * the top of the cache and count the number of hits. If it is not already in
 * the cache, it will add the word to the top of the cache
 * 
 * @author Jozef Wells
 *
 */
public class Test {
	/**
	 * Creates a one level and a two level cache and reads through the file and
	 * counts the number of references and adds each word to the cache while
	 * counting the cache hits and one-level cache misses.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
//		long begin = System.currentTimeMillis();
//		System.out.println("End time: " + begin/1000); //seconds
		
		cache<String> cacheOne; // one-level cache
		cache<String> cacheTwo; // two-level cache

		double cacheOneHits = 0, cacheTwoHits = 0, cacheRefs = 0;
		double cacheOneMiss = 0, cacheTwoMiss = 0;

		if (args[0].equals("1")) { // one-level cache
			if (args.length != 3) {
				printUsage();
				return;				
			}
			try {
				cacheOne = new cache<String>(Integer.parseInt(args[1]));
				Scanner scan = new Scanner(new File(args[2]));
				while (scan.hasNextLine()) {
					String line = scan.nextLine();
					StringTokenizer tokenize = new StringTokenizer(line, " ");
					cacheRefs += tokenize.countTokens();

					while (tokenize.hasMoreTokens()) {
						String word = tokenize.nextToken();

						if (cacheOne.hit(word)) { //cacheOne hit counter
							cacheOneHits++;
						} else {
							cacheOneMiss++;
						}
						cacheOne.add(word);
					}
				}
				scan.close();

				System.out.println(".......................................");				
				System.out.println("The number of cache references: " + cacheRefs);
				System.out.println("The number of cache hits: " + cacheOneHits);
				System.out.println("The cache hit ratio: " + (cacheOneHits / cacheRefs) + "\n");
				
			} catch (Exception e) {
				System.err.println("Error: File not Found");
				printUsage();
			}
		} else if (args[0].equals("2")) { // Two-level cache
			if (args.length != 4) {
				printUsage();
				return;
			}
			if (Integer.parseInt(args[1]) >= Integer.parseInt(args[2])) { // user creates first cache larger than second
				System.err.println("Error: Second cache size must be larger than first cache size");
				printUsage();
				return;
			}
			try {
				cacheOne = new cache<String>(Integer.parseInt(args[1]));
				cacheTwo = new cache<String>(Integer.parseInt(args[2]));
				
				Scanner scan = new Scanner(new File(args[3]));
				while (scan.hasNextLine()) {
					String line = scan.nextLine();
					StringTokenizer tokenize = new StringTokenizer(line, " ");
					cacheRefs += tokenize.countTokens();

					while (tokenize.hasMoreTokens()) {
						String word = tokenize.nextToken();

						if (cacheOne.hit(word)) { //cacheOne hit counter
							cacheOneHits++;
						} else {
							cacheOneMiss++;
							if (cacheTwo.hit(word)) { //cacheTwo hit counter
								cacheTwoHits++;
							} else {
								cacheTwoMiss++;
							}
						}
						cacheOne.add(word); //moves items to the top or adds to the cache at top
						cacheTwo.add(word);	
					}
				}
				scan.close();

				System.out.println(".......................................");
				System.out.println("The number of global references: " + cacheRefs);
				System.out.println("The number of global cache hits: " + (cacheOneHits + cacheTwoHits));
				System.out.println("The global hit ratio: " + ((cacheOneHits + cacheTwoHits)/cacheRefs) + "\n");
				
				System.out.println("The number of 1st-level references: " + cacheRefs);
				System.out.println("The number of 1st-level cache hits: " + cacheOneHits);
				System.out.println("1st-level cache hit ratio: " + (cacheOneHits / cacheRefs) + "\n");
				
				System.out.println("The number of 2nd-level references: " + cacheOneMiss);
				System.out.println("The number of 2nd-level cache hits: " + cacheTwoHits);
				System.out.println("2nd-level cache hit ratio: " + (cacheTwoHits / cacheOneMiss));

			} catch (Exception e) {
				System.err.println("Error: File not Found");
				printUsage();
			}

		} else {
			printUsage();
		}
//		long end = System.currentTimeMillis();
//		System.out.println("End time: " + end/1000); //seconds
	}
	
	public static void printUsage() {
		System.out.println("Usage: $ java Test 1 <cache size> <input textfile name> or "
				+ "$ java Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>");
	}
	
	

}
