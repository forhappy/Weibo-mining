import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopWords {
	/**
	 * logger.
	 */
	public static final Logger logger = 
		LoggerFactory.getLogger(StopWords.class);
	
	private String fileName = "/home/forhappy/SCM-Repos/GIT/Weibo-mining/weibo-mining/src/stopwords.txt";
	
	/**
	 * iterator of record.
	 */
	private Iterator iterStopWords = null;
	
	private Set<String> setStopWords = new HashSet<String>();

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

	public StopWords() {
		super();
		try {
			File file = new File(fileName);

			InputStreamReader isr = new InputStreamReader (new FileInputStream(file),"GBK");
			BufferedReader br=new BufferedReader(isr);
			String line; 
			while (br.ready()) {
				line = br.readLine();
				addStopWord(line);
			}
			br.close();
			isr.close();

		} catch (IOException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} finally {
			iterStopWords = setStopWords.iterator();
		}
	}

	public StopWords(String fileName) {
		super();
		this.fileName = fileName;
		try {
			File file = new File(fileName);
			InputStreamReader isr = new InputStreamReader (new FileInputStream(file),"GBK");
			BufferedReader br=new BufferedReader(isr);
			String line; 
			while (br.ready()) {
				line = br.readLine();
				addStopWord(line);
			}
			br.close();
			isr.close();

		} catch (IOException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} finally {
			iterStopWords = setStopWords.iterator();
		}
	}

	public void addStopWord(String word) {
		if (setStopWords.contains(word)) {}
		else 
			setStopWords.add(word);
	}
	
	public Boolean contains(String word) {
		return setStopWords.contains(word);
	}
	
	public Boolean hasNext() {
		return iterStopWords.hasNext();
	}
	
	public String next() {
		return (String) iterStopWords.next();
	}
	
	public String remove(String sentence) {
		String regex = "\\s+";
		String[] words = sentence.split(regex);
		StringBuilder stopWordRemoved = new StringBuilder();
		for (int i = 0; i < words.length; i++) {
			if (contains(words[i]));
			else { 
				stopWordRemoved.append(words[i]);
				stopWordRemoved.append(" ");
			}
		}
		return stopWordRemoved.toString();
	}
}
