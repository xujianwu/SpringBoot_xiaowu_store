package cn.stroe.xiaowu.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.stroe.xiaowu.entity.Token;
import cn.stroe.xiaowu.entity.User;
import cn.stroe.xiaowu.utils.Pagination;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 注册用户信息
	 * @param user
	 * @return
	 */
	public void register(User user) {
		String sql = "insert into user values(?,?,?,?,?,?)";
		jdbcTemplate.update(sql,user.getId(),user.getUsername(),
		user.getPassword(),user.getTel(),user.getRoleId(),user.getDepartment());
	}
	/**
	 * 分页查询所有用户注册信息
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Pagination queryAllUser(int currentPage,int pageSize) {
		String sql = "select * from user";
		Pagination page = new Pagination(sql,currentPage,pageSize,jdbcTemplate);
		return page;
	}
	/**
	 * 修改用户信息
	 * @param user
	 */
	public void updateUser(User user) {
		String sql = "update user set username=?,password=?,tel=?,roleId=?,department=? where id=?";
		jdbcTemplate.update(sql,user.getUsername(),user.getPassword(),
		user.getTel(),user.getRoleId(),user.getDepartment(),user.getId());
	}
	/**
	 * 删除用户信息
	 * @param id
	 */
	public void deleteUser(String id) {
		String sql = "delete from user where id=?";
		jdbcTemplate.update(sql,id);
	}
	/**
	 * 用户登入验证
	 * @param username
	 * @param password
	 * @return
	 */
	public List<User> userLogin(String username,String password){
		List<User> list = new ArrayList<User>();
		String sql = "select * from user where username=? and password=?";
		list= jdbcTemplate.query(sql, new Object[] {username,password},new BeanPropertyRowMapper(User.class));
		return list;
	}
	/** 
	 * 添加token码
	 * @param username
	 * @param tokenId
	 * @return
	 */
	public int insertToken(String username,String tokenId) {
		String sql = "insert into token values(?,?,now(),now(),date_add(now(),interval 30 minute),'1')";
		int t = jdbcTemplate.update(sql,username,tokenId);
		return t;
	}
	/**
	 * 根据username,token检查用户登录状态
	 * @param username
	 * @param tokenId
	 * @return
	 */
	public List<Token> checkLogin(String username, String tokenId) {
		List<Token> list = new ArrayList<Token>();
		String sql = "select username,tokenId from token where isActive='1' and now() < failureTime and username = ? and tokenId = ?";
		list= jdbcTemplate.query(sql, new Object[] {username,tokenId},new BeanPropertyRowMapper(Token.class));
		return list;
	}
	/**
	 * 根据username,token延长token过期时间
	 * @param username
	 * @param tokenId
	 * @return
	 */
	public int extendToken(String username, String tokenId) {
		String sql = "update token set activeTime = now() and failureTime = date_add(now(),interval 30 minute) where username = ? and tokenId = ?";
		int t = jdbcTemplate.update(sql,username,tokenId);
		return t;
	}
	/**
	 * 注销用户，删除token
	 * @param username
	 * @param tokenId
	 * @return
	 */
	public int cancellLogin(String username, String tokenId) {
		String sql = "update token set activeTime = '0' where username = ? and tokenId = ?";
		int t = jdbcTemplate.update(sql,username,tokenId);
		return t;
	}
	
}
