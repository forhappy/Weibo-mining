import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/**
 * 微博统计类, 统计项包括: 
 * 1) 微博总数目
 * 2) 平均微博长度
 * 3) 所有出现的词的总数(重复计算)
 * 4) 所有出现的词的总数(不考虑重复计算)
 * 5) 词的倒排索引
 * @author forhappy
 *
 */
public class CollectionStatistic {
	/**
	 * 倒排索引内存的结构.
	 */
	private WeiboInvertedIndex weiboInvertedIndex = new WeiboInvertedIndex();
	
	/**
	 * 微博总记录数
	 */
	private long numberOfWeibos = 0L;
	
	/**
	 * 所有出现的词的总数(不考虑重复计算)
	 */
	private long numberOfUniqueTerms = 0L;
	
	/**
	 * 微博中出现的所有的词,只计算一次
	 */
	private Set<String> setUniqueTerms = new TreeSet<String>();
	
	/**
	 * 所有出现的词的总数(重复计算)
	 */
	private long numberOfTokens = 0L;
	
	/**
	 * 平均微博长度
	 */
	private double averageWeiboLength = 0;

	/**
	 * @return the numberOfWeibos
	 */
	public long getNumberOfWeibos() {
		return numberOfWeibos;
	}

	/**
	 * @param numberOfWeibos the numberOfWeibos to set
	 */
	public void setNumberOfWeibos(long numberOfWeibos) {
		this.numberOfWeibos = numberOfWeibos;
	}

	/**
	 * @return the numberOfUniqueTerms
	 */
	public long getNumberOfUniqueTerms() {
		return numberOfUniqueTerms;
	}

	/**
	 * @param numberOfUniqueTerms the numberOfUniqueTerms to set
	 */
	public void setNumberOfUniqueTerms(long numberOfUniqueTerms) {
		this.numberOfUniqueTerms = numberOfUniqueTerms;
	}

	/**
	 * @return the setUniqueTerms
	 */
	public Set<String> getSetUniqueTerms() {
		return setUniqueTerms;
	}

	/**
	 * @param setUniqueTerms the setUniqueTerms to set
	 */
	public void setSetUniqueTerms(Set<String> setUniqueTerms) {
		this.setUniqueTerms = setUniqueTerms;
	}

	/**
	 * @return the numberOfTokens
	 */
	public long getNumberOfTokens() {
		return numberOfTokens;
	}

	/**
	 * @param numberOfTokens the numberOfTokens to set
	 */
	public void setNumberOfTokens(long numberOfTokens) {
		this.numberOfTokens = numberOfTokens;
	}

	/**
	 * @return the averageWeiboLength
	 */
	public double getAverageWeiboLength() {
		return averageWeiboLength;
	}

	/**
	 * @param averageWeiboLength the averageWeiboLength to set
	 */
	public void setAverageWeiboLength(double averageWeiboLength) {
		this.averageWeiboLength = averageWeiboLength;
	}

	/**
	 * @return the weiboInvertedIndex
	 */
	public WeiboInvertedIndex getWeiboInvertedIndex() {
		return weiboInvertedIndex;
	}

	public void addWeiboForStatistic(WeiboXMLHandler.Weibo weibo) {
		//加入一条新的预处理后(分词, 同义词扩展等)的微博
		weiboInvertedIndex.process(weibo);
		numberOfWeibos++;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void analyze() {
		Iterator iter = weiboInvertedIndex.iterator();
		while (iter.hasNext()) {
			Entry entryInvertedIndex = (Entry) iter.next();
			List<Map<String, Integer>> list = (List<Map<String, Integer>>) entryInvertedIndex.getValue();
			for (Map<String, Integer> m : list) {
				// 某一词的<微博:次数>对
				Iterator iterPair = m.entrySet().iterator();
				while(iterPair.hasNext()) {
					Entry entryPair = (Entry) iterPair.next();
					//微博中的一个词.
					String term = (String) entryInvertedIndex.getKey();
					if (!setUniqueTerms.contains(term)) {
						//加入到单一词集合中
						setUniqueTerms.add(term);
						//微博中出现的所有的词,只计算一次
						numberOfUniqueTerms++;
					}
					// 计算所有微博中出现词的总数, 包括重复出现的词
					numberOfTokens = numberOfTokens
							+ (new Long(entryPair.getValue().toString()))
									.longValue();
		
				}
			}
		}
		
		//微博的平均长度
		averageWeiboLength = (double) numberOfTokens / numberOfWeibos;
	}
}
