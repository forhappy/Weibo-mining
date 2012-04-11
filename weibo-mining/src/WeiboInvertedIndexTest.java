import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class WeiboInvertedIndexTest {

	/**
	 * @param args
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		WeiboInvertedIndex weiboInvertedIndex = new WeiboInvertedIndex();
		WeiboXMLHandler.Weibo weibo = new WeiboXMLHandler.Weibo();
		String docNo = "123456789";
		String user = "258347328";
		String weiboText = "普 外 第一 天 做 做 准备 准备 准备 安排 值班 值班 消化道 穿孔 也许 晚上 手术室 泪 ";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		weiboInvertedIndex.process(weibo);
		
		docNo = "123456790";
		user = "258347328";
		weiboText = "普 外 第一 天 做 做 准备 准备 准备 安排 值班 消化道 穿孔 也许 晚上 手术室 泪 ";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		weiboInvertedIndex.process(weibo);
		
		docNo = "123456740";
		user = "258347328";
		weiboText = "普 外 第一 天 做 做 准备 准备 安排 值班 值班 消化道 穿孔 也许 晚上 手术室 泪 ";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		weiboInvertedIndex.process(weibo);
		
		docNo = "123456720";
		user = "258347328";
		weiboText = "普 外 第一 天 做 做 准备 准备 准备 安排 值班 值班 消化道 穿孔 也许 晚上 手术室 泪 ";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		weiboInvertedIndex.process(weibo);
		
		docNo = "123456710";
		user = "258347328";
		weiboText = "普 外 第一 天 做 做 准备 准备 准备 安排 值班 值班 消化道 穿孔 也许 晚上 手术室 泪 ";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		weiboInvertedIndex.process(weibo);
		
		// 测试 wordCount 函数.
/*		Map<String, Integer> map = weiboInvertedIndex.wordCount(weiboText);
		
		Iterator iterMap = map.entrySet().iterator();
		while (iterMap.hasNext()) {
			Entry entry = (Entry) iterMap.next();
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}*/
		
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
