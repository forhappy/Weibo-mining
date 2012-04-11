import java.util.List;

/**
 * 
 */

/**
 * @author forhappy
 *
 */
public class SynonymWordsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SynonymWords synonymWords = new SynonymWords();
//		while (synonymWords.hasNext()) {
//			List<String> synonyms = synonymWords.next();
//			for (String synonym : synonyms) {
//				System.out.println(synonym);
//			}
//		}
		String sentence = "今天 老公 吃 自助餐 ";
		
		System.out.println("同义词扩展前："+ sentence);
		System.out.println("同义词扩展后，一次扩展："+ synonymWords.extendSynonymWords(sentence));
		System.out.println("同义词扩展后，一次扩展："+ synonymWords.extendSynonymWords(sentence, 1));
		System.out.println("同义词扩展后，3次扩展："+ synonymWords.extendSynonymWords(sentence, 3));

	}

}
