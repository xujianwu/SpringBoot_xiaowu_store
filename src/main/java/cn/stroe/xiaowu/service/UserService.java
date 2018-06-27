package cn.stroe.xiaowu.service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.stroe.xiaowu.dao.UserDao;
import cn.stroe.xiaowu.entity.Token;
import cn.stroe.xiaowu.entity.User;
import cn.stroe.xiaowu.utils.HttpUtil;
import cn.stroe.xiaowu.utils.Pagination;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	public static LinkedHashMap<String,String> phoneAndCode = new LinkedHashMap<String,String>();
	/**
	 * 用户注册
	 * @param user
	 */
	public void register(User user) {
		userDao.register(user);
	}
	/**
	 * 分页查询所有用户注册信息
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String,Object> queryAllUser(int currentPage,int pageSize) {
		Pagination page = null;
		Map<String,Object> map = new HashMap<String,Object>();
		page = userDao.queryAllUser(currentPage, pageSize);
		map.put("list", page.getResultList());
		map.put("totalRows", page.getTotalRows());
		map.put("currentPage", page.getCurrentPage());
		map.put("totalPages",page.getTotalPages());
		return map;
	}
	/**
	 * 修改用户信息
	 * @param user
	 */
	public void updateUser(User user) {
		userDao.updateUser(user);
	}
	/**
	 * 删除用户信息
	 * @param id
	 */
	public void deleteUser(String id) {
		userDao.deleteUser(id);
	}  
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public Map<String,Object> userLogin(String username,String password) {
		boolean flag = false;
		String tokenId = "";
		String message = "";
		Map<String,Object> map = new HashMap<String,Object>();
		List<User> list = userDao.userLogin(username, password);
		if(list != null && list.size()>0) {
			tokenId = UUID.randomUUID().toString().replace("-", "");
			int t = userDao.insertToken(username, tokenId);
			if(t == 1) {
				flag = true;
			}	
		}else {
			message = "用户名或密码错误";
		}
		map.put("result", flag);
		map.put("message", message);
		map.put("token", username+"_"+tokenId);
		return map;
	}
	/**
	 * 手机号登入
	 * 生成验证码，发送至手机
	 */
	public void createCode(String phone) {
//		boolean result = true;
		int code = (int)(Math.random()*(9999-1000+1))+1000;
		phoneAndCode.put(phone, code+"");
		String message = "验证码："+code;
		try {
			this.Message_url(message,phone);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 手机发送验证码
	 * @param message
	 * @param phone
	 * @throws Exception
	 */
	private void Message_url(String message, String phone) throws Exception {
		message = URLEncoder.encode(message, "utf-8");
		String url = "短信接口url?"+message+"&address="+phone;
		HttpPost httpPost = new HttpPost(url);
		HttpUtil.doGet(url);
	}
	/**
	 * 手机号登入
	 * 判断验证码是否正确
	 */
	public Map<String, Object> checkCode(String phone, String code) {
		boolean flag = false;
		String token = "";
		String message = "";
		Map<String,Object> map = new HashMap<String,Object>();
		String writeCode = phoneAndCode.get(phone);
		if(code != null && code.equals(writeCode)) {
			token = UUID.randomUUID().toString().replace("-", "");
			int t = userDao.insertToken(phone, token);
			if(t == 1) {
				flag = true;
			}
		}else {
			message = "手机号或验证码错误";
		}
		map.put("result", flag);
		map.put("message", message);
		map.put("token", phone+"_"+token);
		return map;
	}
	/**
	 * 根据token检查用户登录状态
	 * 
	 * 
	 */
	public boolean checkLogin(String username, String tokenId) {
		boolean flag = false;
		List<Token> list = userDao.checkLogin(username,tokenId);
		if(list != null && list.size()>0) {
			flag = true;
			//延长token过期时间
			int t = userDao.extendToken(username,tokenId);
			if(t>0) {
				System.out.println("修改token成功");
			}else {
				System.out.println("修改token失败");
			}
		}
		return flag;
	}
	/**
	 * 注销用户
	 * @param username
	 * @param tokenId
	 * @return
	 */
	public Map<String,Object> cancellLogin(String username, String tokenId) {
		boolean flag = false;
		String message = "";
		Map<String,Object> map = new HashMap<String,Object>();
		int t = userDao.cancellLogin(username,tokenId);
		if(t == 1) {
			flag = true;
			message = "注销成功";
		}else {
			message = "注销失败";
		}
		map.put("result", flag);
		map.put("message", message);
		return map;
	}
}
