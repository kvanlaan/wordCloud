import java.util.Iterator;
import java.util.Stack;

/**
 * BSTDictionaryIterator implements an iterator for a binary search tree (BST)
 * implementation of a Dictionary. The iterator iterates over the tree in order
 * of the key values (from smallest to largest).
 * 
 * @author Katrina Van Laan,
 */
public class BSTDictionaryIterator<K> implements Iterator<K> {
	// stack to be used for iteration
	Stack<BSTnode<K>> stack;

	/**
	 * Constructor for the BSTDictionary Iterator
	 *
	 * @param BSTnode<K>
	 *            root
	 */
	public BSTDictionaryIterator(BSTnode<K> root) {
		stack = new Stack<BSTnode<K>>();
		while (root != null) {
			stack.push(root);
			root = root.getLeft();
		}
	}

	/**
	 * Returns true if the iteration has more elements, false if not.
	 * 
	 * @return true if the iterator has more elements.
	 */
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();// if stack is Empty, no nodes left to iterate
	}

	/**
	 * Returns the data for next element in the iteration.
	 * 
	 * @return the data for the next element in the iteration.
	 */
	@Override
	public K next() {
		BSTnode<K> node = stack.pop();// next node in tree
		K result = node.getKey();// KeyWord to be returned
		if (node.getRight() != null) {
			node = node.getRight();
			while (node != null) {
				stack.push(node);
				node = node.getLeft();
			}
		}
		return result;
	}

	@Override
	public void remove() {
		// DO NOT CHANGE: you do not need to implement this method
		throw new UnsupportedOperationException();
	}
}