import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class WeiboXMLHandler extends DefaultHandler {
	public static class Weibo {
		/**
		 * 微博ID
		 */
		private String docNo = null;
		
		/**
		 * 用户名
		 */
		private String user = null;
		
		/**
		 * 微博内容
		 */
		private String weiboText = null;

		/**
		 * @return the docNo
		 */
		public String getDocNo() {
			return docNo;
		}

		/**
		 * @param docNo the docNo to set
		 */
		public void setDocNo(String docNo) {
			this.docNo = docNo;
		}

		/**
		 * @return the user
		 */
		public String getUser() {
			return user;
		}

		/**
		 * @param user the user to set
		 */
		public void setUser(String user) {
			this.user = user;
		}

		/**
		 * @return the weiboText
		 */
		public String getWeiboText() {
			return weiboText;
		}

		/**
		 * @param weiboText the weiboText to set
		 */
		public void setWeiboText(String weiboText) {
			this.weiboText = weiboText;
		}

		public Weibo() {
			super();
		}

		public Weibo(String docNo, String user, String weiboText) {
			super();
			this.docNo = docNo;
			this.user = user;
			this.weiboText = weiboText;
		}
	}
	
	private List<Weibo> listWeibo = new ArrayList<Weibo>();
	
	private Weibo currentWeibo;
	
	/**
	 * @return the listWeibo
	 */
	public List<Weibo> getListWeibo() {
		return listWeibo;
	}

	/**
	 * @param listWeibo the listWeibo to set
	 */
	public void setListWeibo(List<Weibo> listWeibo) {
		this.listWeibo = listWeibo;
	}

	public void startElement(String namespaceURI, String localName,
			String qualifiedName, Attributes attributes)
			throws SAXException {

		if (qualifiedName.equalsIgnoreCase("DOC")) {
			isDoc = true;
		}

		if (qualifiedName.equalsIgnoreCase("DOCNO")) {
			isDocNo = true;
		}
		if (qualifiedName.equalsIgnoreCase("USER")) {
			isUser = true;
		}

		if (qualifiedName.equalsIgnoreCase("TEXT")) {
			isText = true;
		}

	}

	public void endElement(String namespaceURI, String localName,
			String qualifiedName) throws SAXException {
		if (qualifiedName.equalsIgnoreCase("DOC")) {
			listWeibo.add(currentWeibo);
		}
	}

	public void characters(char ch[], int start, int length)
			throws SAXException {
		if (isDoc) {
			currentWeibo = new Weibo();
			isDoc = false;
		}

		if (isDocNo) {
			currentWeibo.setDocNo(new String(ch, start, length));
			isDocNo = false;
		}

		if (isUser) {
			currentWeibo.setUser(new String(ch, start, length));
			isUser = false;
		}

		if (isText) {
			currentWeibo.setWeiboText(new String(ch, start, length));
			isText = false;
		}
	}

	private Boolean isDoc = false;
	private Boolean isDocNo = false;
	private Boolean isUser = false;
	private Boolean isText = false;
}
