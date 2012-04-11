import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class XmlWeiboWriter {
	/**
	 * logger
	 */
	public static final Logger logger = 
		LoggerFactory.getLogger(XmlWeiboWriter.class);
	
	/**
	 * 保存分词后的微博的文件名
	 */
	private String fileName = "weibo-splitted.xml";
	OutputStream outputStream = null;
	XMLStreamWriter out = null;
	
	List<WeiboXMLHandler.Weibo> listWeibo = new ArrayList<WeiboXMLHandler.Weibo>();
	
	public XmlWeiboWriter () {
		try {
			outputStream = new FileOutputStream(new File(fileName));
			out = XMLOutputFactory.newInstance().createXMLStreamWriter(
			        new OutputStreamWriter(outputStream, "utf-8"));
		} catch (FileNotFoundException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (XMLStreamException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public XmlWeiboWriter(String fileName) {
		super();
		this.fileName = fileName;
		try {
			outputStream = new FileOutputStream(new File(this.fileName));
			out = XMLOutputFactory.newInstance().createXMLStreamWriter(
			        new OutputStreamWriter(outputStream, "utf-8"));
			
		} catch (FileNotFoundException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (XMLStreamException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	public void addWeibo(WeiboXMLHandler.Weibo weibo) {
		listWeibo.add(weibo);
	}
	
	public void removeWeibo(WeiboXMLHandler.Weibo weibo) {
		listWeibo.remove(weibo);
	}
	
	public void write() {
		try {
			out.writeStartDocument("UTF-8", "1.0");
			out.writeStartElement("Weibo");
			for (int i = 0; i < listWeibo.size(); i++) {
				WeiboXMLHandler.Weibo weibo = listWeibo.get(i);
				
				out.writeStartElement("DOC");
				/**
				 * DOCNO
				 */
				out.writeStartElement("DOCNO");
				out.writeCharacters(weibo.getDocNo());
				out.writeEndElement();
				/**
				 * USER
				 */
				out.writeStartElement("USER");
				out.writeCharacters(weibo.getDocNo());
				out.writeEndElement();
				/**
				 * TEXT
				 */	
				out.writeStartElement("TEXT");
				out.writeCharacters(weibo.getWeiboText());
				out.writeEndElement();
			
				out.writeEndElement();
			}
			out.writeEndElement();
			out.writeEndDocument();
			out.close();
		} catch (XMLStreamException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}
}
