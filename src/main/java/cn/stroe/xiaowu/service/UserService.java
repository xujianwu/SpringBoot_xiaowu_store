package cn.stroe.xiaowu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.stroe.xiaowu.dao.UserDao;
import cn.stroe.xiaowu.entity.User;
import cn.stroe.xiaowu.utils.Pagination;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
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
		map.put("token", username+"..."+tokenId);
		return map;
	}
}
