
public class WeightCalculatorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CollectionStatistic cs = new CollectionStatistic();
		WeiboXMLHandler.Weibo weibo = new WeiboXMLHandler.Weibo();
		String docNo = "123456789";
		String user = "258347328";
		String weiboText = "普 外 第一 天 做 做 准备 准备 准备 安排 值班 值班 消化道 穿孔 也许 晚上 手术室 泪";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		cs.addWeiboForStatistic(weibo);
		
		docNo = "123456790";
		user = "258347328";
		weiboText = "晚上 LP 神秘兮兮 说 老公 送 西天 反应 过来 问 干 取经 说 完 上马 赶路 ";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		cs.addWeiboForStatistic(weibo);
		
		docNo = "123456740";
		user = "258347328";
		weiboText = "每次 睇 翻 黄金 梅 里 号 \n \n\n\n最后 \t 一刻 灰 感动 " +
				"打动 想 哭 ";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		cs.addWeiboForStatistic(weibo);
		
		docNo = "123456720";
		user = "258347328";
		weiboText = "炎炎 夏日 夏季 推荐 保举 款 清爽 清新 	开胃 风味 	鸡 适合 	合适 汗 之后 乏 胃 广东 风味 鸡 可谓 式 多样 经典 数 盐 鸡 沙 姜 鸡 做法 配料 相同 沟通 口味 口胃 各有千秋 各有所长 相信 信任 总 款 适合 合适 深井 大叔 售卖 价格 价钱 旺 角 盐 鸡 24 元 例 古 法 沙 姜 鸡 26 元 例 清远 贵妃 鸡 26 元 例 机会 机遇 品尝";
		weibo.setDocNo(docNo);
		weibo.setUser(user);
		weibo.setWeiboText(weiboText);
		cs.addWeiboForStatistic(weibo);
		
		docNo = "123456710";
		user = "258347328";
		weiboText = "新浪 微 博 腾讯 微 博 两 条   平行线 永远 交集";
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
		
		//特征词的总数
		System.out.println("特征词的总数: " + cs.getNumberOfUniqueTerms());
		System.out.println("特征词集合: " + cs.getSetUniqueTerms());
		//权重测试类
		
		WeightCalculator wc = new WeightCalculator(cs, "weight-output.txt");
		wc.weightInMemory(weibo);
		wc.flush();
		
		System.out.println(wc.weight(weibo));

	}

}
