package com.itheima.newsclient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class XmlParserUtils {

	// ����xml��ҵ�񷽷�
	public static List<News> parserXml(InputStream in) throws Exception {

		List<News> newsLists =  null;
		News news = null;
		// [1]��ȡxml�Ľ�����
		XmlPullParser parser = Xml.newPullParser();
		// [2]���ý����� Ҫ����������
		parser.setInput(in, "utf-8");
		// [3]��ȡ�������¼�����
		int type = parser.getEventType();
		// [4]��ͣ�����½���
		while (type != XmlPullParser.END_DOCUMENT) {
			// [5]�����ж�һ�½������ǿ�ʼ�ڵ� ���ǽ����ڵ�
			switch (type) {
			case XmlPullParser.START_TAG: // ������ʼ�ڵ�

				//[6]�����ж�һ�½��������ĸ���ʼ��ǩ 
				if("channel".equals(parser.getName())){
					//����һ��list����
					newsLists = new ArrayList<News>();
				}else if ("item".equals(parser.getName())) {
					news = new News();
					
				}else if ("title".equals(parser.getName())) {
					news.setTitle(parser.nextText());
					
				}else if ("description".equals(parser.getName())) {
					news.setDescription(parser.nextText());
					
				}else if ("image".equals(parser.getName())) {
					news.setImage(parser.nextText());
					
				}else if ("type".equals(parser.getName())) {
					news.setType(parser.nextText());
					
				}else if ("comment".equals(parser.getName())) {
					news.setComment(parser.nextText());
					
				}
				
				break;

			case XmlPullParser.END_TAG: // ����������ǩ

				if ("item".equals(parser.getName())) {
					//��javabean��ӵ� ���� 
					newsLists.add(news);
					
				}
				break;
			}

			// ��ͣ�����½���
			type = parser.next();
		}

		return newsLists;
	}
}
