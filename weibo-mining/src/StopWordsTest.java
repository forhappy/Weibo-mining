/**
 * 
 */

public class StopWordsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StopWords stopWords = new StopWords();
		while (stopWords.hasNext()) {
			String stopWord = stopWords.next();
			System.out.println(stopWord);
		}
	}

}
