import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class CollectionStatisticTest {

	/**
	 * @param args
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CollectionStatistic cs = new CollectionStatistic();
		WeiboXMLHandler.Weibo weibo = new WeiboXMLHandler.Weibo();
		String docNo = "123456789";
		String user = "258347328";
		String weiboText = "普 外 第一 天 做 做 准备 准备 准备 安排 值班 值班 消化道 穿孔 也许 晚上 手术室 泪 ";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		cs.addWeiboForStatistic(weibo);
		
		docNo = "123456790";
		user = "258347328";
		weiboText = "普 外 第一 天 做 做 准备 准备 准备 安排 值班 消化道 穿孔 也许 晚上 手术室 泪 ";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		cs.addWeiboForStatistic(weibo);
		
		docNo = "123456740";
		user = "258347328";
		weiboText = "普 外 第一 天 做 做 准备 准备 安排 值班 值班 消化道 穿孔 也许 晚上 手术室 泪 ";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		cs.addWeiboForStatistic(weibo);
		
		docNo = "123456720";
		user = "258347328";
		weiboText = "普 外 第一 天 做 做 准备 准备 准备 安排 消化道 穿孔 也许 晚上 手术室 泪 ";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		cs.addWeiboForStatistic(weibo);
		
		docNo = "123456710";
		user = "258347328";
		weiboText = "普 外 第一 天 做 做 准备 准备 准备 安排 值班 值班 消化道 穿孔 也许 晚上 手术室 泪 ";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		cs.addWeiboForStatistic(weibo);
		
		cs.analyze();
		
		//总的微博数
		System.out.println("总的微博数: " + cs.getNumberOfWeibos());
		
		// 平均微博长度
		System.out.println("平均微博长度: " + cs.getAverageWeiboLength());
		
		//总词数
		System.out.println("总词数: " + cs.getNumberOfTokens());
		
		//Unique 词的总数
		System.out.println("Unique 词的总数: " + cs.getNumberOfUniqueTerms());
		
		WeiboInvertedIndex weiboInvertedIndex = cs.getWeiboInvertedIndex();
		
		Iterator iter = weiboInvertedIndex.iterator();
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			System.out.println();
			List<Map<String, Integer>> list = (List<Map<String, Integer>>) entry.getValue();
			for (Map<String, Integer> m : list) {
				Iterator iterM = m.entrySet().iterator();
				while(iterM.hasNext()) {
					Entry entryM = (Entry) iterM.next();
					System.out.println("词: " + entry.getKey() +
							" 在微博ID: " + entryM.getKey() +
							" 出现了 " + entryM.getValue() + " 次");
				}
			}
		}
		
	}

}
