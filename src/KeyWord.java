/**
 * The KeyWord class consists of a word and an integer (representing the number
 * of occurrences of the word).
 * 
 * @author Katrina Van Laan,
 */
public class KeyWord implements Comparable<KeyWord>, Prioritizable {
	private String word;// field represents word in KeyWord
	private Integer occurrence;// field represents #occurrences

	/**
	 * Constructs a KeyWord with the given word
	 * 
	 * @param String
	 *            newWord the word for this KeyWord
	 */
	KeyWord(String newWord) throws IllegalArgumentException {
		if (newWord == null) {// handling for bad word args
			throw new IllegalArgumentException();
		}
		word = newWord;
		occurrence = 1;// initial occurrence = 1

	}

	/**
	 * Compares the current KeyWord with another given as a param by basis of
	 * alphabetical order.
	 * 
	 * @param KeyWord
	 *            o which the current KeyWord will be compared to
	 * 
	 * @return int 0 if equal, -1 if this KeyWord is less than the input, 1 if
	 *         this KeyWord is more than the input
	 */
	@Override
	public int compareTo(KeyWord o) {

		if (this.getWord().compareTo(o.getWord()) == 0) {
			return 0;
		}
		if (this.getWord().compareTo(o.getWord()) < 0) {
			return -1;
		} else {
			return 1;
		}

	}

	/**
	 * Compares the current KeyWord with another given as a param by basis of of
	 * priority.
	 * 
	 * @param KeyWord
	 *            o which the current KeyWord will be compared to
	 * 
	 * @return int 0 if equal, -1 if this KeyWord is less than the input, 1 if
	 *         this KeyWord is more than the input
	 */
	public int compareToTwo(KeyWord o) {

		if (this.getPriority() == o.getPriority()) {
			if (this.compareTo(o) > 0) {// if equals, compare via alphabet
				return 1;
			} else {
				return -1;
			}
		}
		if (this.getPriority() < o.getPriority()) {
			return -1;
		} else {
			return 1;
		}

	}

	/**
	 * Determines whether the two objects equal each other
	 * 
	 * @param KeyWord
	 *            o which the current KeyWord will be compared to
	 * 
	 * @return boolean true if the two objects equal each other, false if not;
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other.getClass() == KeyWord.class) {// handling for class
			if (word.equals(((KeyWord) other).getWord())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Returns number of occurrences of the KeyWord
	 * 
	 * 
	 * @return Integer representing the number of occurrences;
	 */
	public int getOccurrences() {
		return occurrence;// returns occurrence field
	}

	/**
	 * Returns number the priority, determined by number of occurrences of the
	 * KeyWord
	 * 
	 * 
	 * @return Integer representing the priority
	 */
	@Override
	public int getPriority() {
		return occurrence;// occurrence = priority
	}

	/**
	 * Returns the KeyWord's word
	 * 
	 * 
	 * @return word representing the word;
	 */
	public String getWord() {
		return this.word;// returns world field
	}

	/**
	 * Increments the KeyWord's occurrences;
	 * 
	 */
	public void increment() {
		occurrence++;// increments occurrence
	}

}
