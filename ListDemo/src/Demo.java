import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Demo {
	private final static int MAX_SIZE = 10000;
	private final static int TEST_TIMES = 10000;
	private final static Integer DEFAULT_ITEM = 0;
	private List<Integer> arrayList;
	private List<Integer> linkedList;

	public void init() {
		arrayList = new ArrayList<>();
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
			arrayList.add(i, DEFAULT_ITEM);
			stop = System.nanoTime();
			totalAddArrayList += (stop - start);

			start = System.nanoTime();
			arrayList.remove(i);
			stop = System.nanoTime();
			totalRemoveArrayList += (stop - start);
		}
		System.out.println("In arrayList, add cost time: " + totalAddArrayList + " ns, remove cost time: "
				+ totalRemoveArrayList + " ns");

		for (int i = 0; i < TEST_TIMES; i++) {
			start = System.nanoTime();
			linkedList.add(i, DEFAULT_ITEM);
			stop = System.nanoTime();
			totalAddLinkedList += (stop - start);

			start = System.nanoTime();
			linkedList.remove(i);
			stop = System.nanoTime();
			totalRemoveLinkedList += (stop - start);
		}
		System.out.println("In linkedList, add cost time: " + totalAddLinkedList + " ns, remove cost time: "
				+ totalRemoveLinkedList + " ns");
	}

	public void testFind() {
		System.out.println("\ntest find:");
		long start1 = System.nanoTime();
		for (int i = 0; i < TEST_TIMES; i++) {
			arrayList.get(i);
		}
		long stop1 = System.nanoTime();
		System.out.println("In arrayList, cost time: " + (stop1 - start1) + " ns");

		long start2 = System.nanoTime();
		for (int i = 0; i < TEST_TIMES; i++) {
			linkedList.get(i);
		}
		long stop2 = System.nanoTime();
		System.out.println("In linkedList, cost time: " + (stop2 - start2) + " ns");
	}

	public void testIterator() {
		System.out.println("\ntest Iterator:");
		Iterator<Integer> iterator1 = arrayList.iterator();
		long start1 = System.nanoTime();
		while (iterator1.hasNext()) {
			iterator1.next();
		}
		long stop1 = System.nanoTime();
		System.out.println("In arrayList, cost time: " + (stop1 - start1) + " ns");

		Iterator<Integer> iterator2 = linkedList.iterator();
		long start2 = System.nanoTime();
		while (iterator2.hasNext()) {
			iterator2.next();
		}
		long stop2 = System.nanoTime();
		System.out.println("In linkedList, cost time: " + (stop2 - start2) + " ns");
	}

	public static void main(String[] args) {
		Demo demo = new Demo();
		demo.init();
		demo.testAdd();
		demo.testFind();
		demo.testIterator();
	}
}
