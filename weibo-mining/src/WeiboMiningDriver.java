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
		String weiboFileName = 
			"/home/forhappy/SCM-Repos/GIT/Weibo-mining/weibo-mining/src/weibo/weibo.xml";
		//分词处理之后的文件名
		String splittedWeiboFileName = 
			"/home/forhappy/SCM-Repos/GIT/Weibo-mining/weibo-mining/src/output/weibo-splitted.xml";
		//微博权重文件名
		String weightFileName = 
			"/home/forhappy/SCM-Repos/GIT/Weibo-mining/weibo-mining/src/output/weight-output.txt";
		
		
		XmlWeiboReader reader = new XmlWeiboReader(weiboFileName);
		XmlWeiboWriter writer = 
			new XmlWeiboWriter(splittedWeiboFileName);
		
		//需再次读入微博文件，进行权重计算的处理。
		XmlWeiboReader splittedWeiboReader = new XmlWeiboReader(splittedWeiboFileName);
		/**
		 * 用户词典测试
		 */
//		String input = "华中科技大学 爱国者 天敏 天国之门阿尔马格罗";
		
//		logger.info("\n" + input + " 分词后:\n" + weiboSplitter.weiboSplitProcessing(input));
		
		try {
			reader.read();
			while (reader.hasNext()) {
				WeiboXMLHandler.Weibo weibo = reader.next();
				String weiboText = weibo.getWeiboText();
				String weiboTextTrimmed = weiboSplitter.weiboTrim(weiboText);
				/**
				 * 分词处理
				 */
				String weiboTextSplitted = weiboSplitter.weiboSplitProcessing(weiboTextTrimmed);
				String weiboTextStopWordsRemoved = stopWords.remove(weiboTextSplitted);
				String[] terms = weiboTextStopWordsRemoved.split("\\s+");
				
				//微博分词后长度小于 3 的微博去除掉。
				if(terms.length <= 9) continue;
				
				//测试分词，去除停用词，同义词扩展
//				logger.info("分词前微博文本：\n" + weiboText);
//				logger.info("分词后微博文本：\n" + weiboTextSplitted);
//				logger.info("除去停用词后的微博文本：\n" + stopWords.remove(weiboTextSplitted));
				logger.info("同义词扩展后的微博文本：\n" + 
						synonymWords.extendSynonymWords(stopWords.remove(weiboTextSplitted)));
				
				WeiboXMLHandler.Weibo weiboSplitted = new WeiboXMLHandler.Weibo();
				
				weiboSplitted.setDocNo(weibo.getDocNo());
				weiboSplitted.setUser(weibo.getUser());
//				weiboSplitted.setWeiboText(weiboTextSplitted);
				weiboSplitted.setWeiboText(
						synonymWords.extendSynonymWords(weiboTextStopWordsRemoved));
				
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
			
			//特征词的总数
			System.out.println("特征词的总数: " + cs.getNumberOfUniqueTerms());
			
			//测试倒排索引
		/*	WeiboInvertedIndex weiboInvertedIndex = cs.getWeiboInvertedIndex();
			
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
			}*/
			
			
			//测试权重计算
			WeightCalculator wc = new WeightCalculator(cs, weightFileName);
			splittedWeiboReader.read();
			while (splittedWeiboReader.hasNext()) {
				WeiboXMLHandler.Weibo weibo = splittedWeiboReader.next();
				wc.weightInMemory(weibo);
			}
			wc.flush();
			
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
