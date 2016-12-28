public class ArrayHeap<E extends Prioritizable> implements PriorityQueueADT<E> {

	// default number of items the heap can hold before expanding
	private static final int INIT_SIZE = 100;

	private E[] heap;// priority queue
	private int numItems;// number of items in heap
	private int pos = 0;// pos number to insert elements
	private int modSize = 0;// represents specified dictionary size

	/**
	 * Constructor for the ArrayHeap class
	 *
	 */
	public ArrayHeap() {
		heap = (E[]) (new Prioritizable[INIT_SIZE]);
		numItems = 0;// init numItems to 0
	}

	/**
	 * Constructor for the ArrayHeap class
	 *
	 * @param Integer
	 *            size
	 */
	public ArrayHeap(Integer size) {
		modSize = size;// to be used later for arrayCopy
		heap = (E[]) (new Prioritizable[size]);
		numItems = 0;// init numItems to 0
	}

	/**
	 * Tells whether or not the ArrayHeap is empty
	 * 
	 * @return boolean true if the root is null, false if not
	 */
	@Override
	public boolean isEmpty() {
		if (numItems == 0) {// if numItems is 0, heap is empty
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Inserts E items into the heap
	 * 
	 * @param E
	 *            item which will added to the Heap
	 */
	@Override
	public void insert(E item) {
		if (item != null) {// handling for bad input
			heap[pos] = item;// insert item at current pos
			pos++;// increment pos
		}
	}

	/**
	 * Helper function to sort Bubble Sort
	 * 
	 * @param E[]
	 *            array to be sorted
	 */
	private void bubbleSort(E[] array) {
		boolean swapped = true;// init to true, triggers iteration shrink
		int j = 0;
		E tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < array.length - j; i++) {
				if (array[i] != null && array[i + 1] != null) {
					// if priority of preceding element is less than following,
					// then swap
					if (((KeyWord) array[i]).compareToTwo((KeyWord) array[i + 1]) < 0) {
						tmp = array[i];
						array[i] = array[i + 1];
						array[i + 1] = tmp;
						swapped = true;// switch swapped back to true
					}
				}
			}
		}
	}

	/**
	 * Removes E item of maximum priority from the heap
	 * 
	 * @return E item of maximum priority
	 */
	@Override
	public E removeMax() {
		bubbleSort(heap);// sort the heap before obtaining max value
		// obtain max value
		E temp = heap[0];
		// delete value
		int n = heap.length - 1;
		E[] newArray = (E[]) (new Prioritizable[modSize]);
		System.arraycopy(heap, 1, newArray, 0, n);
		// set heap to new modified array
		heap = newArray;
		// return max value
		return temp;
	}

	/**
	 * Return E item of maximum priority from the heap
	 * 
	 * @return E item of maximum priority
	 */
	@Override
	public E getMax() {
		bubbleSort(heap);// sort the heap before obtaining max value
		return heap[0];// obtain max value
	}

	/**
	 * Returns number of items in the heap
	 * 
	 * @return return Integer number of items in the heap
	 */
	@Override
	public int size() {
		return numItems;// numItems is size
	}

}