package com.itheima.newsclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private List<News> newsLists;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// [1]�ҵ����ǹ��ĵĿؼ�
		
		lv = (ListView) findViewById(R.id.lv);
		// [2] ׼��listview Ҫ��ʾ������ ȥ������ȡ���� ���з�װ
		initListData();

	}

	// ׼��listview������
	private void initListData() {
		new Thread() {

			public void run() {

				try {
					// [2]ȥ������ȡ���� http://192.168.11.86:8080/news.xml
					String path = "http://139.129.30.213/androidNews/news.xml";
					// [2.2]����URL ����ָ������Ҫ���ʵ� ��ַ(·��)
					URL url = new URL(path);

					// [2.3]�õ�httpurlconnection���� ���ڷ��ͻ��߽�������
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					// [2.4]���÷���get����
					conn.setRequestMethod("GET");// getҪ���д Ĭ�Ͼ���get����
					// [2.5]��������ʱʱ��
					conn.setConnectTimeout(5000);
					// [2.6]��ȡ���������ص�״̬��
					int code = conn.getResponseCode();
					// [2.7]���code == 200 ˵������ɹ�
					if (code == 200) {
						// [2.8]��ȡ���������ص����� ����������ʽ���ص�
						InputStream in = conn.getInputStream();
						// [2.9]����xml ���һ��ҵ�񷽷�
						newsLists = XmlParserUtils.parserXml(in);
						System.out.println("newsLists:"+newsLists.size());
						
						runOnUiThread(new  Runnable() {
							public void run() {
								//[3]����ui  ������չʾ��listview�� 
								lv.setAdapter(new MyAdapter());
								
							}
						});
						

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();

	}

	//��������������
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return newsLists.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(), R.layout.item,
						null);
			} else {
				view = convertView;

			}
			// [1]�ҵ��ؼ� ��ʾ�������������
			SmartImageView iv_icon = (SmartImageView) view.findViewById(R.id.iv_icon);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
			TextView tv_type = (TextView) view.findViewById(R.id.tv_type);

			//[1.1]չʾͼƬ������ 
			String imageUrl = newsLists.get(position).getImage();
			
			//չʾͼƬ
//			iv_icon.setImageUrl(imageUrl);
			iv_icon.setImageUrl(imageUrl, R.drawable.bg);
			
			// [2]��ʾ����
			tv_title.setText(newsLists.get(position).getTitle());
			tv_desc.setText(newsLists.get(position).getDescription());
			String typee = newsLists.get(position).getType();
			String comment = newsLists.get(position).getComment();
			int type = Integer.parseInt(typee);
			switch (type) {
			case 1:

				tv_type.setText(comment+"����");
				break;
			case 2:
				tv_type.setText("����");
				break;
			case 3:
				tv_type.setText("����");
				break;

			default:
				break;
			}

			return view;
		}

	}

}
