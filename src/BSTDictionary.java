import java.util.Iterator;

/**
 * 
 * Title: Program 3 
 * Files: ArrayHeap.java, BSTDictionary.java, BSTDictionaryIterator.java, BSTnode.java, DictionaryADT.java, 
 * DuplicatedException.java, KeyWord.java, Prioritizable.java, PriorityQueueADT.java, WordCloudGenerator.java
 * 
 * Semester: Fall 2016 
 * 
 * Author: Katrina Van Laan 
 * Email:vanlaan@wisc.edu
 * Lecturer's Name: Charles Fischer
 *
 */
/**
 *
 * This is for creating, adding to, removing from and querying the
 * BSTdictionary;
 *
 * The BSTdictionary consists of a Binary Search tree of BSTnodes.
 * 
 * @author Katrina Van Laan
 */

public class BSTDictionary<K extends Comparable<K>> implements DictionaryADT<K> {
	private BSTnode<K> root; // the root node
	private int numItems; // the number of items in the dictionary
	private int sum = 0;// total path length sum

	/**
	 * Constructor for the BSTDictionary class
	 *
	 */
	BSTDictionary() {
		root = null;// default root to null;
	}

	/**
	 * Constructor for the BSTDictionary class
	 * 
	 * @param BSTnode<K>
	 *            node which will become the new root
	 *
	 */
	BSTDictionary(BSTnode<K> node) {
		root = node;// set root to node
	}

	/**
	 * Inserts the given key into the BSTdictionary if the key is not already in
	 * the Dictionary. If the key is already in the Dictionary, a
	 * DuplicatException is thrown.
	 * 
	 * @param key
	 *            the key to insert into the Dictionary
	 * @throws DuplicateException
	 *             if the key is already in the Dictionary
	 */
	@Override
	public void insert(K key) throws DuplicateException {
		if (key != null) {// bad key handling
			if (isEmpty()) {// if is empty, the root will be the inserted node
				root = insert(root, key);
			} else {
				insert(root, key);// if not empty just insert
			}
		}
	}

	/**
	 * Helper to the insert method, Inserts KeyWords into BSTDictionary
	 * 
	 * @param K
	 *            key which will become the new root if the tree is empty,
	 *            become added to the tree otherwise, or if the tree contains
	 *            the key, it will be incremented
	 * @param BSTnod<K>
	 *            n , root whose subtree will be the new node will be inserted
	 *            on
	 * 
	 * @throws DuplicateException
	 *             if the key is already in the Dictionary
	 * @return BSTnode<K> which is the newly inserted node.
	 */
	private BSTnode<K> insert(BSTnode<K> n, K key) throws DuplicateException {
		if (n == null) {// insert key as new node
			numItems++;
			n = new BSTnode<K>(key);
			return n;
		} else if (n.getKey().equals(key)) {
			throw new DuplicateException();// no duplicates
		} else if (key.compareTo(n.getKey()) < 0) {
			// add key to the left subtree
			n.setLeft(insert(n.getLeft(), key));
			return n;
		} else {
			// add key to the right subtree
			n.setRight(insert(n.getRight(), key));
			return n;
		}
	}

	/**
	 * Deletes key from the BSTDictionary
	 * 
	 * @param K
	 *            key, to be deleted
	 * @return boolean signifying whether the delete method has been performed
	 */
	@Override
	public boolean delete(K key) {
		root = actualDelete(root, key);
		return true;
	}

	/**
	 * Helper method for delete, Deletes key from the BSTDictionary
	 * 
	 * @param K
	 *            key, to be deleted
	 * @param BSTNode<K>
	 *            root
	 * @return boolean signifying whether the delete method has been performed
	 */

	private BSTnode<K> actualDelete(BSTnode<K> n, K key) {
		if (n == null) {
			return null;
		}

		if (key.equals(n.getKey())) {
			// n is the node to be removed
			if (n.getLeft() == null && n.getRight() == null) {
				return null;
			}
			if (n.getLeft() == null) {
				return n.getRight();
			}
			if (n.getRight() == null) {
				return n.getLeft();
			}

			// if we get here, then n has 2 children
			K smallVal = smallest(n.getRight());
			n.setKey(smallVal);
			n.setRight(actualDelete(n.getRight(), smallVal));
			return n;
		}

		else if (key.compareTo(n.getKey()) < 0) {
			n.setLeft(actualDelete(n.getLeft(), key));
			return n;
		}

		else {
			n.setRight(actualDelete(n.getRight(), key));
			return n;
		}
	}

	/**
	 * Helper method for delete, Deletes key from the BSTDictionary
	 * 
	 * @param BSTNode<K>
	 *            n, root whose subtree will be searched
	 * @return KeyWord, returns the smallest value in the subtree rooted at n
	 */
	private K smallest(BSTnode<K> n) {
		if (n.getLeft() == null) {
			return n.getKey();
		} else {
			return smallest(n.getLeft());
		}
	}

	/**
	 * lookup method, searches the BSTDictionary to see if it contains the given
	 * key
	 * 
	 * @param K
	 *            key, search query
	 * @return KeyWord, null if the root is null, the key if there is a match
	 */
	@Override
	public K lookup(K key) {
		return lookup(root, key);
	}

	/**
	 * Helper method for lookup method, searches the BSTDictionary to see if it
	 * contains the given key
	 * 
	 * @param BSTNode<K>
	 *            n, root whose subtree will be searched
	 * @param K
	 *            key, search query
	 * @return KeyWord, null if the root is null, the key if there is a match
	 */
	private K lookup(BSTnode<K> n, K key) {
		if (n == null) {// if key is not present
			return null;
		}
		if (key.equals(n.getKey())) {// if key is present, return the match to
										// be incremented
			return n.getKey();
		}
		if (key.compareTo(n.getKey()) < 0) {
			// key < this node's key; look in left subtree
			return lookup(n.getLeft(), key);
		}

		else {
			// key > this node's key; look in right subtree
			return lookup(n.getRight(), key);
		}
	}

	/**
	 * Tells whether or not the BSTDictionary is empty
	 * 
	 * @return boolean true if the root is null, false if not
	 */
	@Override
	public boolean isEmpty() {
		if (root == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Tells size of BSTDictionary
	 * 
	 * @return int numItems, number of items in BSTDictionary
	 */
	@Override
	public int size() {
		return numItems;// numItems represents size
	}

	/**
	 * Tells totalPathLength() of BSTDictionary
	 * 
	 * @return int pathLength;
	 */

	@Override
	public int totalPathLength() {
		return totalPathLength(root, sum);// returns total path lenght number
	}

	/**
	 * Helper function for totalPathLength() of BSTDictionary
	 * 
	 * @param BSTnode<K>
	 *            n signifying root
	 * 
	 * @param int
	 *            num signifying depth
	 * 
	 * @return int pathLength;
	 */
	public int totalPathLength(BSTnode<K> n, int num) {
		if (n == null) {
			return num;// return pathlength to that point
		}
		num++;// increment pathlength
		if (n.getLeft() == null && n.getRight() == null) {
			return num;// return pathlength to that point
		} else {
			// return totalPathLengths of right and left subtrees
			return totalPathLength(n.getLeft(), num) + totalPathLength(n.getRight(), num);
		}
	}

	/**
	 * Iterator of BSTDictionary
	 * 
	 * @return Iterator of BSTDictionary
	 */
	@Override
	public Iterator<K> iterator() {
		// create new Iterator passing in this root
		Iterator<K> iter = new BSTDictionaryIterator<K>(this.root);
		return iter;
	}

}