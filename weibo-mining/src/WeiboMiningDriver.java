import ICTCLAS.I3S.AC.ICTCLAS50;
import java.util.*;
import java.io.*;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WeiboMiningDriver {
	
	public static void main(String[] args) {
		WeiboSplitter weiboSplitter = new WeiboSplitter();
		
		StopWords stopWords = new StopWords();
		SynonymWords synonymWords = new SynonymWords();
		
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
				weiboSplitted.setWeiboText(weiboTextSplitted);
				
				/**
				 * 将分词后微博加入到writer中。
				 */
				writer.addWeibo(weiboSplitted);		
			}
			
			writer.write();
			
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
