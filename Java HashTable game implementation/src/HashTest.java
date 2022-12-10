import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class HashTest {

    private static int input, debug;
    private static double load;
    private static int tableSize;

    public static void main(String[] args) {

        try {
            if (args.length < 2 || args.length > 3) {
                usage();
            }

            input = Integer.parseInt(args[0]);
            if (input != 1 && input != 2 && input != 3) {
                usage();
            }

            load = Double.parseDouble(args[1]);
            if (load > 1 || load < 0) {
                usage();
            }

            if (args.length > 2) {
                debug = Integer.parseInt(args[2]);
                if (debug != 0 && debug != 1) {
                    usage();
                    return;
                }
            } else {
                debug = 0;
            }

            tableSize = tableSize();

            /* Declaration of linear table and object, and double table and object */
            HashTable linTable = new HashTable<HashObject<Object>>(tableSize, 1);
            HashTable doubleTable = new HashTable<HashObject<Object>>(tableSize, 2);
            double stop = (load * tableSize);
            HashObject linObj;
            HashObject dubObj;

            /* Input: Random */
            if (input == 1) {
                Random rand = new Random();

                while (linTable.size() < stop && doubleTable.size() < stop) {
                    linObj = new HashObject(rand.nextInt());
                    dubObj = linObj;

                    linTable.put(linObj);
                    doubleTable.put(dubObj);

                }
            /* Input: Dates */
            } else if (input == 2) {
                long current = new Date().getTime();
                while (linTable.size() < stop && doubleTable.size() < stop) {
                    current += 1;
                    Date date = new Date(current);
                    linObj = new HashObject(date);
                    dubObj = new HashObject(date);
                    linTable.put(linObj);
                    doubleTable.put(dubObj);
                }
            /* Input: word-list */
            } else {
                Scanner scan = new Scanner((new File("word-list")));
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();

                    linObj = new HashObject(line);
                    dubObj = new HashObject(line);
                    if (linTable.size() < stop && doubleTable.size() < stop) {
                        linTable.put(linObj);
                        doubleTable.put(dubObj);
                    } else {
                        break;
                    }

                }
                scan.close();
            }
            printSum(linTable, doubleTable);

        } catch (Exception e) {
            usage();
        }
    }

    public static int tableSize() {
        int start = 95500;
        int end = 96000;

        Random num = new Random();
        int a = num.nextInt(end) + 1;
        int a2 = num.nextInt(end) + 1;

        int p = start+1;
        while (p <= end-2) {
            // check for twin primes
            if (isPrime(p, a) && isPrime(p+2, a)) {
                if (isPrime(p, a2) && isPrime(p+2, a2)) { //check twice with different random num
                    return p+2; //p is smallest twin prime, p+2 is second smallest twin prime
                } else {
                    p+=2;
                }
            } else {
                p+=2;
            }
        }
        return -1;
    }

    public static boolean isPrime(int p, int a) {
        double result = a;
        String binary = Integer.toBinaryString(p-1);

        //loop through the binary bits
        for (int i = 1; i < binary.length(); i++) { //i = 1 because we skip the first bit
            char c = binary.charAt(i);
            if (c == '0') {
                result = ((Math.pow(result, 2)) % p);
            } else {
                result = ((Math.pow(result, 2)) * a) % p;
            }
        }
        return result == 1;
    }

    public static void printSum(HashTable lin, HashTable dub) {
        String data;
        if (input == 1) {
            data = "java-util-Random";
        } else if (input == 2) {
            data = "Date";
        } else {
            data = "word-list";
        }

        /* Write to linear-dump and double-dump */
        if (debug == 1) {
            try {
                File linDump = new File("linear-dump");
                File doubleDump = new File("double-dump");

                FileWriter linear = new FileWriter(linDump);
                FileWriter duble = new FileWriter(doubleDump);

                /* loop through linear Hashtable and double Hashtable and retrieve HashObjects at occupied indexes */
                for (int i = 0; i < tableSize; i++) {
                    if (lin.get(i) != null) {
                        HashObject tmp = lin.get(i);
                        linear.write("table[" + i + "]: " + tmp + " " + tmp.getDuplicateCount() + " " + tmp.getProbeCount() + "\n");
                    }
                    if (dub.get(i) != null) {
                        HashObject tmp = dub.get(i);
                        duble.write("table[" + i + "]: " + tmp + " " + tmp.getDuplicateCount() + " " + tmp.getProbeCount() + "\n");
                    }
                }
                linear.close();
                duble.close();
            } catch (Exception e) {
                System.err.println("Error occurred during file creating/writing.");
            }
        }
        /* print summary of experiment on the console */
        System.out.println("A good table size is found: " + tableSize);
        System.out.println("Data source type: " + data);

        System.out.println("\nUsing Linear Hashing....");
        System.out.println("Input " + lin.getReferenceCount() + " elements, of which " + lin.getDuplicates() + " duplicates");
        System.out.println("load factor = " + load + ", Avg. no. of probes " + (lin.averageProbes()));

        System.out.println("\nUsing Double Hashing....");
        System.out.println("Input " + dub.getReferenceCount() + " elements, of which " + dub.getDuplicates() + " duplicates");
        System.out.println("load factor = " + load + ", Avg. no. of probes " + dub.averageProbes());
    }

    public static void usage() {
        System.err.println("Java HashTest <input type> <load factor> [<debug level>]");
    }
}
