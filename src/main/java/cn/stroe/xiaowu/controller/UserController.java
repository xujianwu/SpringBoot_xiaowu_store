package cn.stroe.xiaowu.controller;


import java.util.HashMap; 
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.stroe.xiaowu.entity.User;
import cn.stroe.xiaowu.service.UserService;
import cn.stroe.xiaowu.utils.ResponseResult;

@RestController
@RequestMapping("/user") 
public class UserController {
	@Autowired
	private UserService userService;
	//注册用户
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public ResponseResult<Void> register(User user) {
		ResponseResult<Void> rr = new ResponseResult<Void>();
		userService.register(user);
		rr.setStatus(1);
		rr.setMessage("注册成功");
		return rr;
	}
	/**
	 * 分页查询所有用户注册信息
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/queryAllUser",method=RequestMethod.GET)
	public ResponseResult<Map<String,Object>> queryAllUser(int currentPage,int pageSize){
		Map<String,Object> map = new HashMap<String,Object>();
		ResponseResult<Map<String,Object>> rr = new ResponseResult<Map<String,Object>>();
		map = userService.queryAllUser(currentPage, pageSize);
		rr.setStatus(1);
		rr.setMessage("查询成功");
		rr.setData(map);
		return rr;
	}
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/updateUser",method=RequestMethod.GET)
	public ResponseResult<Void> updateUser(User user) {
		ResponseResult<Void> rr = new ResponseResult<Void>();
		userService.updateUser(user);
		rr.setStatus(1);
		rr.setMessage("修改成功");
		return rr;
	}
	/**
	 * 修改用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deleteUser",method=RequestMethod.GET)
	public ResponseResult<Void> deleteUser(String id) {
		ResponseResult<Void> rr = new ResponseResult<Void>();
		userService.deleteUser(id);
		rr.setStatus(1);
		rr.setMessage("删除成功");
		return rr;
	}
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/userLogin",method=RequestMethod.GET)
	public ResponseResult<Map<String,Object>> userLogin(String username,String password){
		ResponseResult<Map<String,Object>> rr = new ResponseResult<Map<String,Object>>();
		Map<String,Object> map = userService.userLogin(username, password);
		rr.setStatus(1);
		rr.setMessage("登录成功");
		rr.setData(map);
		return rr;
	}  
}
