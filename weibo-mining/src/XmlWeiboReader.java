import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;

public class XmlWeiboReader implements WeiboReadable {
	/**
	 * Weibo TREC file name.
	 */
	private String fileName = null;
	
	private List<WeiboXMLHandler.Weibo> listWeibo;
	
	@SuppressWarnings("rawtypes")
	private Iterator iterListWeibo;

	/**
	 * logger.
	 */
	public static final Logger logger = LoggerFactory
			.getLogger(XmlWeiboReader.class);

	public XmlWeiboReader(String fileName) {
		super();
		this.fileName = fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	@Override
	public void read() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			File file = new File(this.fileName);
			
			WeiboXMLHandler handler = new WeiboXMLHandler();

			InputStream inputStream = null;
			Reader reader = null;
			try {
				
				inputStream = new FileInputStream(file);
				reader = new InputStreamReader(inputStream, "UTF-8");
				InputSource is = new InputSource(reader);
				is.setEncoding("UTF-8");
				saxParser.parse(is, handler);
				
				listWeibo = handler.getListWeibo();
				
				iterListWeibo = listWeibo.iterator();
				
			} catch (FileNotFoundException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
			
		} catch (ParserConfigurationException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (SAXException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Boolean hasNext() {
		return iterListWeibo.hasNext();
	}
	
	public WeiboXMLHandler.Weibo next() {
		return (WeiboXMLHandler.Weibo) iterListWeibo.next();
	}

}
