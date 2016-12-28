import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * Title: Program 3 
 * Files: ArrayHeap.java, BSTDictionary.java, BSTDictionaryIterator.java, BSTnode.java, DblListnode.java, 
 * DictionaryADT.java, DuplicateException.java, KeyWord.java, PriorityQueueADT.java, WordCloudGenerator.java,
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
 * 
 * /** The main method generates a word cloud as described in the program
 * write-up.
 * 
 * @param args
 *            the command-line arguments that determine where input and output
 *            is done:
 *            <ul>
 *            <li>args[0] is the name of the input file</li>
 *            <li>args[1] is the name of the output file</li>
 *            <li>args[2] is the name of the file containing the words to ignore
 *            when generating the word cloud</li>
 *            <li>args[3] is the maximum number of words to include in the word
 *            cloud</li>
 *            </ul>
 * 
 * @author Katrina Van Laan
 */

public class WordCloudGenerator {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in); // for input from text file
		PrintStream out = null; // for output to html file
		Scanner inIgnore = null; // for input from ignore file
		BSTDictionary<KeyWord> dictionary = new BSTDictionary<KeyWord>();
		// Check the command-line arguments and set up the input and output

		if (args.length != 4) {
			System.out.print("Four arguments required: inputFileName ");
			System.out.println("outputFileName ignoreFileName maxWords");
			System.exit(0);
		}
		// reading input file;
		try {
			File inFile = new File(args[0]);
			if (!inFile.exists()) {
				System.out.println("Error: cannot access file " + args[0]);
				System.exit(0);
			}
			in = new Scanner(inFile);
		} catch (FileNotFoundException e) {

			System.out.println("file not found");
		}

		// reading ignore file;
		try {
			File inFile = new File(args[1]);
			if (!inFile.exists()) {
				System.out.println("Error: cannot access file " + args[2]);
				System.exit(0);
			}
			inIgnore = new Scanner(inFile);
		} catch (FileNotFoundException e) {
		}
		// obtaining maxwords
		int maxWords = 0;
		try {
			maxWords = Integer.parseInt(args[3]);
			if (maxWords <= 0)// handling for bad maxWords input
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			System.out.println("Error: maxWords must be a positive integer");
			System.exit(0);
		}
		// creating output file;
		try {
			File outFile = new File(args[2]);
			out = new PrintStream(outFile);
		} catch (FileNotFoundException e) {

		}

		// Create the dictionary of words to ignore
		// You do not need to change this code.
		DictionaryADT<String> ignore = new BSTDictionary<String>();

		while (inIgnore.hasNext()) {
			try {
				ignore.insert(inIgnore.next().toLowerCase());
			} catch (DuplicateException e) {
				// if there is a duplicate, we'll just ignore it
			}
		}

		// Process the input file line by line
		// Note: the code below just prints out the words contained in each
		// line. You will need to replace that code with code to generate
		// the dictionary of KeyWords.
		while (in.hasNext()) {
			String line = in.nextLine();
			List<String> words = parseLine(line);
			for (String word : words) {
				word = word.toLowerCase();

				if (ignore.lookup(word) == null) { // if this is not a word to
					// ignore
					// turn word into keyword
					KeyWord key = new KeyWord(word);

					// lookup result
					KeyWord lookupVal = dictionary.lookup(key);
					// if not in dictionary, insert the key
					if (lookupVal == null && key != null) {
						try {
							dictionary.insert(key);

						} catch (DuplicateException e) {
							// throw duplicate Exception
						}
					} else {// if present in dictionary, increment occurrence;
						lookupVal.increment();
					}
				}
			}
		}
		// end while

		double numKeys = dictionary.size();
		System.out.println("# keys: " + dictionary.size());// items/keys
		System.out.println("avg path length: " + dictionary.totalPathLength() / numKeys);
		System.out.println("linear avg path: " + (1 + numKeys) / 2);
		// create priority queue with size of dictionary
		PriorityQueueADT<KeyWord> pq = new ArrayHeap<KeyWord>(dictionary.size());
		// create new iterator to add values to priority queue
		Iterator<KeyWord> bItr = dictionary.iterator();
		KeyWord currItr = bItr.next();

		while (bItr.hasNext()) {
			pq.insert(currItr);
			currItr = bItr.next();
		}
		// if the dictionary size is less than maxWords, maxWords becomes
		// dictionary size
		if (dictionary.size() < maxWords) {
			maxWords = dictionary.size();
		}

		// create new list which will be used to generate html
		DictionaryADT<KeyWord> list = new BSTDictionary<KeyWord>();
		// insert the highest values from the priority queue to the list, as
		// many as determined by maxWords
		for (int i = 0; i < maxWords; i++) {
			try {
				list.insert(pq.removeMax());
			} catch (DuplicateException e) {
			}
		}

		generateHtml(list, out);

		// Close everything
		if (in != null)
			in.close();
		if (inIgnore != null)
			inIgnore.close();
		if (out != null)
			out.close();
	}

	/**
	 * Parses the given line into an array of words.
	 * 
	 * @param line
	 *            a line of input to parse
	 * @return a list of words extracted from the line of input in the order
	 *         they appear in the line
	 * 
	 *         DO NOT CHANGE THIS METHOD.
	 */
	private static List<String> parseLine(String line) {
		String[] tokens = line.split("[ ]+");
		ArrayList<String> words = new ArrayList<String>();
		for (int i = 0; i < tokens.length; i++) { // for each word

			// find index of first digit/letter
			boolean done = false;
			int first = 0;
			String word = tokens[i];
			while (first < word.length() && !done) {
				if (Character.isDigit(word.charAt(first)) || Character.isLetter(word.charAt(first)))
					done = true;
				else
					first++;
			}

			// find index of last digit/letter
			int last = word.length() - 1;
			done = false;
			while (last > first && !done) {
				if (Character.isDigit(word.charAt(last)) || Character.isLetter(word.charAt(last)))
					done = true;
				else
					last--;
			}

			// trim from beginning and end of string so that is starts and
			// ends with a letter or digit
			word = word.substring(first, last + 1);

			// make sure there is at least one letter in the word
			done = false;
			first = 0;
			while (first < word.length() && !done)
				if (Character.isLetter(word.charAt(first)))
					done = true;
				else
					first++;
			if (done)
				words.add(word);
		}

		return words;
	}

	/**
	 * Generates the html file using the given list of words. The html file is
	 * printed to the provided PrintStream.
	 * 
	 * @param words
	 *            a list of KeyWords
	 * @param out
	 *            the PrintStream to print the html file to
	 * 
	 *            DO NOT CHANGE THIS METHOD
	 */
	private static void generateHtml(DictionaryADT<KeyWord> words, PrintStream out) {
		String[] colors = { "#CD5C5C", // INDIANRED
				"#5F9EA0", // CADETBLUE
				"#FA8072", // SALMON
				"#E9967A", // DARKSALMON
				"#FF69B4", // HOTPINK
				"#FFA500", // ORANGE
				"#B22222", // FIREBRICK
				"#E6E6FA", // LAVENDER
				"#8A2BE2", // BLUEVIOLET
				"#6A5ACD", // SLATEBLUE
				"#7FFF00", // CHARTREUSE
				"#32CD32", // LIMEGREEN
				"#228B22", // FORESTGREEN
				"#66CDAA", // MEDIUMAQUAMARINE
				"#00FFFF", // CYAN
				"#1E90FF", // DODGERBLUE
				"#FFE4C4", // BISQUE
				"#8B4513", // SADDLEBROWN
				"#F5F5DC", // BEIGE
				"#C0C0C0" // Silver
		};
		int initFontSize = 100;
		String fontFamily = "Cursive";

		// Print the header information including the styles
		out.println("<head>\n<title>Word Cloud</title>");
		out.println("<style type=\"text/css\">");
		out.println("body { font-family: " + fontFamily + " }");

		// Each style is of the form:
		// .styleN {
		// font-size: X%;
		// color: #YYYYYY;

		// }
		// where N and X are integers and Y is a hexadecimal digit
		for (int i = 0; i < colors.length; i++)
			out.println(".style" + i + " {\n    font-size: " + (initFontSize + i * 20) + "%;\n    color: " + colors[i]
					+ ";\n}");

		out.println("</style>\n</head>\n<body><p>");

		// Find the minimum and maximum values in the collection of words
		int min = Integer.MAX_VALUE, max = 0;
		for (KeyWord word : words) {
			int occur = word.getOccurrences();
			if (occur > max)
				max = occur;
			if (occur < min)
				min = occur;
		}

		double slope = (colors.length - 1.0) / (max - min);

		for (KeyWord word : words) {
			out.print("<span class=\"style");

			// Determine the appropriate style for this value using
			// linear interpolation
			// y = slope *(x - min) (rounded to nearest integer)
			// where y = the style number
			// and x = number of occurrences
			int index = (int) Math.round(slope * (word.getOccurrences() - min));

			out.println(index + "\">" + word.getWord() + "</span>&nbsp;");
		}

		// Print the closing tags
		out.println("</p></body>\n</html>");
	}
}