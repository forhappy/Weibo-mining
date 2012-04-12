import ICTCLAS.I3S.AC.ICTCLAS50;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WeiboMiningDriver {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		WeiboSplitter weiboSplitter = new WeiboSplitter();
		
		StopWords stopWords = new StopWords();
		SynonymWords synonymWords = new SynonymWords();
		
		// 微博统计类
		CollectionStatistic cs = new CollectionStatistic();
		
		// 微博文件名
		String fileName = "/home/forhappy/SCM-Repos/GIT/Weibo-mining/weibo-mining/src/weibo/weibo-example.xml";

		XmlWeiboReader reader = new XmlWeiboReader(fileName);
		XmlWeiboWriter writer = new XmlWeiboWriter();
		
		/**
		 * 用户词典测试
		 */
		String input = "华中科技大学 爱国者 天敏 天国之门阿尔马格罗";
		
		logger.info("\n" + input + " 分词后:\n" + weiboSplitter.weiboSplitProcessing(input));
		
		try {
			reader.read();
			while (reader.hasNext()) {
				WeiboXMLHandler.Weibo weibo = reader.next();
				String weiboText = weibo.getWeiboText();
				/**
				 * 分词处理
				 */
				String weiboTextSplitted = weiboSplitter.weiboSplitProcessing(weiboText);
				
				
				logger.info("分词前微博文本：\n" + weiboText);
				logger.info("分词后微博文本：\n" + weiboTextSplitted);
				logger.info("除去停用词后的微博文本：\n" + stopWords.remove(weiboTextSplitted));
				logger.info("同义词扩展后的微博文本：\n" + 
						synonymWords.extendSynonymWords(stopWords.remove(weiboTextSplitted)));
				
				WeiboXMLHandler.Weibo weiboSplitted = new WeiboXMLHandler.Weibo();
				
				weiboSplitted.setDocNo(weibo.getDocNo());
				weiboSplitted.setUser(weibo.getUser());
//				weiboSplitted.setWeiboText(weiboTextSplitted);
				weiboSplitted.setWeiboText(
						synonymWords.extendSynonymWords(stopWords.remove(weiboTextSplitted)));
				
				cs.addWeiboForStatistic(weiboSplitted);
				/**
				 * 将分词后微博加入到writer中。
				 */
				writer.addWeibo(weiboSplitted);		
			}
			
			writer.write();
			cs.analyze();
			
			//微博统计类测试
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
			
		} catch (Exception ex) {
			logger.debug(ex.getMessage());
		} finally {
			weiboSplitter.finalize();
		}
		
	}
	/**
	 * logger.
	 */
	public static final Logger logger = 
		LoggerFactory.getLogger(WeiboMiningDriver.class);

}
