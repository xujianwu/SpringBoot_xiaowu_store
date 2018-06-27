package cn.stroe.xiaowu.utils;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtil {
	public static String doGet(String url) {
		String responseMsg = "";
		//1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		//2.构造GetMethod的实例
		GetMethod getMethod = new GetMethod();
		//使用系统默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		
		try {
			//3.执行getMethod，调用HTTP接口
			httpClient.executeMethod(getMethod);
			//4.读取内容
			byte[] responseBody = getMethod.getResponseBody();
			//5。处理返回的内容
			responseMsg = new String(responseBody,"utf-8");
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//6.释放连接
			getMethod.releaseConnection();
		}
		return responseMsg;
	}
}
