import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Demo {
	private final static int MAX_SIZE = 100;
	private final static int TEST_TIMES = 100;
	private final static int DEFAULT_INT = 0;
	private List<Integer> arrayList;
	private List<Integer> linkedList;

	public void init() {
		arrayList = new ArrayList<>(MAX_SIZE);
		linkedList = new LinkedList<>();
		for (int i = 0; i < MAX_SIZE; i++) {
			arrayList.add(i);
			linkedList.add(i);
		}
	}

	public void testAdd() {
		System.out.println("\ntest add:");
		long totalAddArrayList = 0;
		long totalRemoveArrayList = 0;
		long totalAddLinkedList = 0;
		long totalRemoveLinkedList = 0;
		long start, stop;
		for (int i = 0; i < TEST_TIMES; i++) {
			start = System.nanoTime();
			arrayList.add(i, DEFAULT_INT);
			stop = System.nanoTime();
			totalAddArrayList += (stop - start);

			start = System.nanoTime();
			arrayList.remove(i);
			stop = System.nanoTime();
			totalRemoveArrayList += (stop - start);
		}
		System.out.println(
				"In arrayList, add cost time: " + totalAddArrayList + ", remove cost time: " + totalRemoveArrayList);

		for (int i = 0; i < TEST_TIMES; i++) {
			start = System.nanoTime();
			linkedList.add(i, DEFAULT_INT);
			stop = System.nanoTime();
			totalAddLinkedList += (stop - start);

			start = System.nanoTime();
			linkedList.remove(i);
			stop = System.nanoTime();
			totalRemoveLinkedList += (stop - start);
		}
		System.out.println(
				"In linkedList, add cost time: " + totalAddLinkedList + ", remove cost time: " + totalRemoveLinkedList);
	}

	public void testFind() {
		System.out.println("\ntest find:");
		long start1 = System.currentTimeMillis();
		for (int i = 0; i < TEST_TIMES; i++) {
			arrayList.get(i);
		}
		long stop1 = System.currentTimeMillis();
		System.out.println("In arrayList, cost time: " + (stop1 - start1));
		long start2 = System.currentTimeMillis();
		for (int i = 0; i < TEST_TIMES; i++) {
			linkedList.get(i);
		}
		long stop2 = System.currentTimeMillis();
		System.out.println("In linkedList, cost time: " + (stop2 - start2));
	}

	public static void main(String[] args) {
		Demo demo = new Demo();
		demo.init();
		demo.testAdd();
//		demo.testFind();
	}

}
