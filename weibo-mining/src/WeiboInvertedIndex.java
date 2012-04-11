import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 倒排索引类
 * @author forhappy
 *
 */
@SuppressWarnings("rawtypes")
public class WeiboInvertedIndex implements Iterable{
	/**
	 * 记录倒排表的内存结构：
	 * +--------------------------------------------------+
	 * |词项1 <微博1， 次数>，<微博2， 次数>， <微博3， 次数>... |
	 * |词项2 <微博1， 次数>，<微博2， 次数>， <微博3， 次数>... |
	 * |词项3 <微博1， 次数>，<微博2， 次数>， <微博3， 次数>... |
	 * |...  ...   ...   ...   ...   ...   ...   ...   ...|
	 * |...  ...   ...   ...   ...   ...   ...   ...   ...|
	 * |...  ...   ...   ...   ...   ...   ...   ...   ...|
	 * |词项n <微博1， 次数>，<微博2， 次数>， <微博3， 次数>... |
	 * +--------------------------------------------------+
	 */
	private Map<String, List<Map<String, Integer>>> mapInvertedIndex = 
		new HashMap<String, List<Map<String, Integer>>>();
	
	private List<Map<String, Integer>> getInvertedIndex(String word) {
		return mapInvertedIndex.get(word);
	}
	
	public Map<String, Integer> wordCount(String sentence) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Set<String> tmpWordSet = new HashSet<String>();
		String[] words = sentence.split(" ");
		for (int i = 0; i < words.length; i++) {
			if (!tmpWordSet.contains(words[i]))
			{
				tmpWordSet.add(words[i]);
				int counter = 0;
				for (int j = i; j < words.length; j++) {
					if (words[i].equals(words[j])) {
						counter++;
					}
				}
				map.put(words[i], new Integer(counter));
			}
		}
		return map;
	}
	
	public void process(WeiboXMLHandler.Weibo weibo) {

		//用户名
		String user = weibo.getUser();

		//该微博文本标识号
		String docNo = weibo.getDocNo();
		
		//预处理（分词，除去停用词，同义词扩展）后的微博文本内容
		String weiboText = weibo.getWeiboText();
		
		//计算一条微博中的词频
		Map<String, Integer> mapWordCount = wordCount(weiboText);
		
		//微博切分
		String[] words = weiboText.split(" ");
		
		//临时集合，记录微博文本中已经处理的词
		Set<String> tmpWordSet = new HashSet<String>();
		
		//计算每个词在本微博中出现的次数
		for (String word : words) {
			if (!tmpWordSet.contains(word)) {
				tmpWordSet.add(word);
				List<Map<String, Integer>> invertedIndex = getInvertedIndex(word);
				if (invertedIndex == null)
					invertedIndex = new ArrayList<Map<String, Integer>>();
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put(docNo, new Integer(mapWordCount.get(word)));
				invertedIndex.add(map);
				mapInvertedIndex.put(word, invertedIndex);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Iterator iterator(){
		return mapInvertedIndex.entrySet().iterator();
	}
}
