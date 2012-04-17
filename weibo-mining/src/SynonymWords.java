import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SynonymWords {
	/**
	 * logger.
	 */
	public static final Logger logger = 
		LoggerFactory.getLogger(SynonymWords.class);
	
	private String fileName = "/home/forhappy/SCM-Repos/GIT/Weibo-mining/weibo-mining/src/synonym-words-utf8.txt";
	
	/**
	 * iterator of record.
	 */
	@SuppressWarnings("rawtypes")
	private Iterator iterSynonymWords = null;
	
	private Map<String, List<String>> mapSynonymWords = new HashMap<String, List<String>>();

	private void addSynonymWord(String word, List<String> list) {
		mapSynonymWords.put(word, list);
	}
	
	private Boolean contains(String word) {
		return mapSynonymWords.containsKey(word);
	}
	
	private String synonymWords(String word) {
		List<String> list = mapSynonymWords.get(word);
		StringBuilder synonumWords = new StringBuilder();
		for (String synonumWord : list) {
			synonumWords.append(synonumWord);
			synonumWords.append(" ");
		}
		return synonumWords.toString();
	}
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public SynonymWords() {
		super();
		try {
			File file = new File(fileName);

			InputStreamReader isr = new InputStreamReader (new FileInputStream(file),"UTF-8");
			BufferedReader br=new BufferedReader(isr);
			String line; 
			while (br.ready()) {
				line = br.readLine();
				String words[] = line.split("\\s");
				if (words.length > 1) {
					String word = words[0];
					if (!mapSynonymWords.containsKey(word)) {
						List<String> synonyms = new ArrayList<String>(); 
						for (int i = 1; i < words.length; i++) {
							synonyms.add(words[i]);
						}
						mapSynonymWords.put(word, synonyms);
					}
				}
			}
			br.close();
			isr.close();

		} catch (IOException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} finally {
			iterSynonymWords = mapSynonymWords.entrySet().iterator();
		}
	}

	public SynonymWords(String fileName) {
		super();
		this.fileName = fileName;
		try {
			File file = new File(fileName);
			InputStreamReader isr = new InputStreamReader (new FileInputStream(file),"GBK");
			BufferedReader br=new BufferedReader(isr);
			String line; 
			while (br.ready()) {
				line = br.readLine();
				String words[] = line.split(" ");
				String word = words[0];
				List<String> synonyms = new ArrayList<String>(); 
				for (int i = 1; i < words.length; i++) {
					synonyms.add(words[i]);
				}
				addSynonymWord(word, synonyms);
			}
			br.close();
			isr.close();

		} catch (IOException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} finally {
			iterSynonymWords = mapSynonymWords.entrySet().iterator();
		}
	}
	
	public Boolean hasNext() {
		return iterSynonymWords.hasNext();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> next() {
		@SuppressWarnings("rawtypes")
		Entry entry = (Entry) iterSynonymWords.next();
		return (List<String>) entry.getValue();
	}
	
	public String extendSynonymWords(String sentence) {
		String regex = "\\s+";
		String[] words = sentence.split(regex);
		StringBuilder synonymWordsAdded = new StringBuilder();
		for (int i = 0; i < words.length; i++) {
			if (contains(words[i])) {
				/**
				 * 加入原始词
				 */
				synonymWordsAdded.append(words[i]);
				synonymWordsAdded.append(" ");
				
				/**
				 * 加入同义词
				 */
				synonymWordsAdded.append(synonymWords(words[i]));
			} else {
				/**
				 * 加入原始词
				 */
				synonymWordsAdded.append(words[i]);
				synonymWordsAdded.append(" ");
			}
		}
		return synonymWordsAdded.toString();
	}
	
	/**
	 * 深度优先扩展同义词，如扩展深度为2，则在第一次同义词扩展的基础上再次进行同义词扩展
	 * 缺点：某些特征词的权重会发生偏移。
	 * @param sentence 原始句子
	 * @param depth 扩展深度
	 * @return 同义词扩展后的词组
	 */
	public String extendSynonymWords(String sentence, int depth) {
		if (depth <= 0) return sentence;
		else {
			return extendSynonymWords(extendSynonymWords(sentence, depth - 1));
		}
	}
}
