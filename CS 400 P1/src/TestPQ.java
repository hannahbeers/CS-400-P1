/**
 * Filename:   TestPQ.java
 * Project:    p1TestPQ
 * Authors:    Debra Deppeler, Hannah Beers
 * Email:	   hlbeers@wisc.edu
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    002
 * 
 * Note: Warnings are suppressed on methods that construct new instances of 
 * generic PriorityQueue types.  The exceptions that occur are caught and 
 * such types are not able to be tested with this program.
 * 
 * Due Date:   Before 10pm on September 17, 2018
 * Version:    2.0
 * 
 * Credits:    None
 * 
 * Bugs:       no known bugs
 */


import java.util.NoSuchElementException;

/**
 * Runs black-box unit tests on the priority queue implementations
 * passed in as command-line arguments (CLA).
 * 
 * If a class with the specified class name does not exist 
 * or does not implement the PriorityQueueADT interface,
 * the class name is reported as unable to test.
 * 
 * If the class exists, but does not have a default constructor,
 * it will also be reported as unable to test.
 * 
 * If the class exists and implements PriorityQueueADT, 
 * and has a default constructor, the tests will be run.  
 * 
 * Successful tests will be reported as passed.
 * 
 * Unsuccessful tests will include:
 *     input, expected output, and actual output
 *     
 * Example Output:
 * Testing priority queue class: PQ01
 *    5 PASSED
 *    0 FAILED
 *    5 TOTAL TESTS RUN
 * Testing priority queue class: PQ02
 *    FAILED test00isEmpty: unexpectedly threw java.lang.NullPointerException
 *    FAILED test04insertRemoveMany: unexpectedly threw java.lang.ArrayIndexOutOfBoundsException
 *    3 PASSED
 *    2 FAILED
 *    5 TOTAL TESTS RUN
 * 
 *   ... more test results here
 * 
 * @author deppeler
 */
public class TestPQ {

	// set to true to see call stack trace for exceptions
	private static final boolean DEBUG = false;

	/**
	 * Run tests to determine if each Priority Queue implementation
	 * works as specified. User names the Priority Queue types to test.
	 * If there are no command-line arguments, nothing will be tested.
	 * 
	 * @param args names of PriorityQueueADT implementation class types 
	 * to be tested.
	 */
	public static void main(String[] args) {
		for (int i=0; i < args.length; i++) 
			test(args[i]);

		if ( args.length < 1 ) 
			print("no PQs to test");
	}

	/** 
	 * Run all tests on each priority queue type that is passed as a classname.
	 * 
	 * If constructing the priority queue in the first test causes exceptions, 
	 * then no other tests are run.
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 */
	private static void test(String className) {
		print("Testing priority queue class: "+className);
		int passCount = 0;
		int failCount = 0;
		try {

			if (test00isEmpty(className)) passCount++; else failCount++;		
			if (test01getMaxEXCEPTION(className)) passCount++; else failCount++;

			if (test02removeMaxEXCEPTION(className)) passCount++; else failCount++;
			if (test03insertRemoveOne(className)) passCount++; else failCount++;
			if (test04insertRemoveMany(className)) passCount++; else failCount++;

			if (test05duplicatesAllowed(className)) passCount++; else failCount++;
			if (test06manyDataItems(className)) passCount++; else failCount++;
			if (test07orderInserted(className)) passCount++; else failCount++;
			if (test08noSuchElement(className)) passCount++; else failCount++;
			if (test09insertNull(className)) passCount++; else failCount++;
			if (test10correctMiddleInsert(className)) passCount++; else failCount++;
			






			String passMsg = String.format("%4d PASSED", passCount);
			String failMsg = String.format("%4d FAILED", failCount);
			print(passMsg);
			print(failMsg);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			if (DEBUG) e.printStackTrace();
			print(className + " FAIL: Unable to construct instance of " + className);
		} finally {
			String msg = String.format("%4d TOTAL TESTS RUN", passCount+failCount);
			print(msg);
		}

	}
	
	/** Tests if PQ order is correctly preserved after inserting an element
	 *  that should be added to the middle of the queue.
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 * @return false if test fails and true if it passes
	 * @throws InstantiationException
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 */
	private static boolean test10correctMiddleInsert(String className)
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		int expectedValue = 30;
		int actualValue = 0;
		try {
			pq.insert(10);
			pq.insert(30);
			pq.insert(20);
			actualValue = pq.getMax();
			if(actualValue == expectedValue) {
				return true;
			}	
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test10correctMiddleInsert: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test10correctMiddleInsert: 10, 30, and 20 inserted into PQ. correctMiddleInsert should "
				+ "have returned " + expectedValue + ", but instead returned " + actualValue);
		return false;

	}
	
	/** Returns true if null value is properly added to the PQ
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 * @return false if test fails and true if it passes
	 * @throws InstantiationException
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 */
	private static boolean test09insertNull(String className)
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.insert(null);
			if(pq.getMax() == null)
				return true;
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test09insertNull: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test09insertNull: insertNull should have returned Null after insert()"
				+ " and getMax() on a null value, but instead returned " + pq.getMax());
		return false;
	}
	
	/** Returns true if returns a NoSuchElementException when all 3 elements originally in
	 *  the PQ are removed, and user attempts to remove another element.
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 * @return false if test fails and true if it passes
	 * @throws InstantiationException
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 */
	private static boolean test08noSuchElement(String className)
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.insert(5);
			pq.insert(6);
			pq.insert(2);
			pq.removeMax();
			pq.removeMax();
			pq.removeMax();
			pq.removeMax();
		} catch (NoSuchElementException e) {
			return true;		
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test08noSuchElement: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test08noSuchElement: noSuchElement should have thrown NoSuchElementExcpeption when"
				+ "attempting to remove more elements than exist, but test returned false.");
		return false;
	}
	
	/** Tests if PQ inserts items in correct max to min order.
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 * @return false if test fails and true if it passes
	 * @throws InstantiationException
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 */
	private static boolean test07orderInserted(String className)
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		int expectedValue = 3;
		int actualValue = 0;
		String currentValue = "2, 3";
		try {
			pq.insert(2);
			pq.insert(3);
			actualValue = pq.getMax();
			if(actualValue == expectedValue) {
				pq.insert(1);
				currentValue = "1";
				actualValue = pq.getMax();
				if(actualValue == expectedValue) {
					pq.insert(4);
					currentValue = "4";
					expectedValue = 4;
					actualValue = pq.getMax();
					if(actualValue == expectedValue)
						return true;
				}
			}
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test07orderInserted: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test07orderInserted: " + currentValue + "  was inserted to PQ. orderInserted returned " + actualValue + 
				" instead of expected value (" + expectedValue + ")");
		return false;
	}
	
	/** Test fails if the internal data structure does not expand properly to allow many items 
	 *  to be added and removed in max to min order
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 * @return false if test fails and true if it passes
	 * @throws InstantiationException
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 */
	private static boolean test06manyDataItems(String className)
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		int expectedSize = 1;
		int actualSize = 0;
		try {
			pq.insert(1);
			if(pq.getMax() == 1) {
				actualSize++;
				pq.insert(2);
				if(pq.getMax() == 2) {
					actualSize++;
					pq.insert(3);
					if(pq.getMax() == 3) {
						actualSize++;
						pq.removeMax();
						if(pq.getMax() == 2) {
							actualSize--;
							pq.removeMax();
							if(pq.getMax() == 1) {
								actualSize--;
								if(actualSize == expectedSize) {
									return true;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test06manyDataItems: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test06manyDataItems: 1,2,3 entered into PQ. then 3 and 2 are removed, so expected size of PQ "
				+ "should be " + expectedSize + ", manyDataItems found PQ has a size of " + actualSize + ". ");
		return false;
	}
	
	/** Test fails if duplicate values are not able to be inserted (and then removed).
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 * @return false if test fails and true if it passes
	 * @throws InstantiationException
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 */
	private static boolean test05duplicatesAllowed(String className)
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		int expectedValue = 10;
		int actualValue = 0;
		try {
			pq.insert(new Integer(10));
			pq.insert(new Integer(10));
			actualValue = pq.removeMax();
			if(actualValue == expectedValue) {
				actualValue = pq.removeMax();
			if(actualValue == expectedValue)
				return true;
			}
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test05duplicatesAllowed: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test05duplicatesAllowed: " + expectedValue + "  was inserted twice to PQ. duplicatesAllowed returned " + actualValue + 
				" instead of expected value (" + expectedValue + ") after removing element from PQ");
		return false;
	}

	/** Confirms that insertRemoveMany inserts many items and fails if removeMax does not 
	 *  return the max values in the priority order.
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 * @return false if test fails and true if it passes
	 * @throws InstantiationException
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 */
	private static boolean test04insertRemoveMany(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		int expectedValue = 20;
		int actualValue = 0;
		try {
			pq.insert(new Integer(10));
			pq.insert(new Integer(20));
			actualValue = pq.removeMax();
			if(actualValue == expectedValue) {
				actualValue = pq.removeMax();
				expectedValue = 10;
			if(actualValue == expectedValue)
				return true;
			}
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test04insertRemoveMany: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test04insertRemoveMany: " + expectedValue + " was inserted to PQ. insertRemoveMany returned " + actualValue + 
				" instead of expected value (" + expectedValue + ") after removing element from PQ");
		return false;
	}

	/** Tests if program correctly inserts one item and fails if removeMax is not the 
	 *  same value as was inserted
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 * @return false if test fails and true if it passes
	 * @throws InstantiationException
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 */
	private static boolean test03insertRemoveOne(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		int expectedValue = 10;
		int actualValue = 0;
		try {
			pq.insert(new Integer(10));
			actualValue = pq.removeMax();
			if(actualValue == expectedValue) {
				return true;
			}
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test03insertRemoveOne: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test03insertRemoveOne: " + expectedValue + "  was inserted to PQ. insertRemoveOne returned " + actualValue + 
				" instead of expected value (" + expectedValue + ") after removing element from PQ");
		return false;
	}

	/** Confirms that removeMaxEXCEPTION throws NoSuchElementException if called on 
	 * an empty priority queue.  Any other exception indicates a fail.
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 * @return false if test fails and true if it passes
	 * @throws InstantiationException
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 */
	private static boolean test02removeMaxEXCEPTION(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.removeMax();
		} catch (NoSuchElementException e) {
			return true;			
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test02removeMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test02removeMaxEXCEPTION: removeMaxEXCEPTION did not throw NoSuchElement exception on newly constructed PQ");
		return false;
	}

	/** DO NOT EDIT -- provided as an example
	 * Confirm that getMax throws NoSuchElementException if called on 
	 * an empty priority queue.  Any other exception indicates a fail.
	 * 
	 * @param className name of the priority queue implementation to test.
	 * @return true if getMax on empty priority queue throws NoSuchElementException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static boolean test01getMaxEXCEPTION(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.getMax();
		} catch (NoSuchElementException e) {
			return true;			
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test01getMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test01getMaxEXCEPTION: getMax did not throw NoSuchElement exception on newly constructed PQ");
		return false;
	}

	/** DO NOT EDIT THIS METHOD
	 * @return true if able to construct Integer priority queue and 
	 * the instance isEmpty.
	 * 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static boolean test00isEmpty(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
		if (pq.isEmpty()) 
			return true;
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test00isEmpty: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test00isEmpty: isEmpty returned false on newly constructed PQ");
		return false;
	}

	/** DO NOT EDIT THIS METHOD
	 * Constructs a max Priority Queue of Integer using the class that is name.
	 * @param className The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<Integer> newIntegerPQ(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();	
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<Integer>) obj;
		}
		return null;
	}

	/** DO NOT EDIT THIS METHOD
	 * Constructs a max Priority Queue of Double using the class that is named.
	 * @param className The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<Double> newDoublePQ(final String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();	
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<Double>) obj;
		}
		return null;
	}

	/** DO NOT EDIT THIS METHOD
	 * Constructs a max Priority Queue of String using the class that is named.
	 * @param className The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<String> newStringPQ(final String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();	
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<String>) obj;
		}
		return null;
	}


	/** DO NOT EDIT THIS METHOD
	 * Write the message to the standard output stream.
	 * Always adds a new line to ensure each message is on its own line.
	 * @param message Text string to be output to screen or other.
	 */
	private static void print(String message) {
		System.out.println(message);
	}

}