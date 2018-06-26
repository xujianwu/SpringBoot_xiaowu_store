package cn.stroe.xiaowu.utils;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class Pagination extends JdbcDaoSupport{
	//每页10条（常量）
	public static final int NUMBERS_PER_PAGE = 10;
	//每页条数
	private int numberPage;
	//总条数
	private int totalRows;
	//总页数
	private int totalPages;
	//当前页码
	private int currentPage;
	//起始行数
	private int startIndex;
	//结束行数
	private int lastIndex;
	//结果存放List
	private List resultList;
	//jdbcTemplate
	private JdbcTemplate jdbcTemplate;
	/**
	 * 每页显示10条记录的构造函数，使用该函数必须先给Pagination设置currentPage和jdbcTemplate的值
	 * @param sql
	 */
	public Pagination(String sql) {
		if(jdbcTemplate==null) {
			throw new IllegalArgumentException("cn.stroe.xiaowu.utils.jdbcTemplate is null");
		}else if("".equals(sql) || sql==null){
			throw new IllegalArgumentException("cn.stroe.xiaowu.utils.sql is empty");
		}else {
			new Pagination(sql,currentPage,NUMBERS_PER_PAGE,jdbcTemplate);
		}
	}
	/**
	 * 分页构造函数
	 * @param sql 根据传人的sql语句得到一些基本分页信息
	 * @param currentPage 当前页
	 * @param numberPage 每页记录数
	 * @param jdbcTemplate JdbcTemplate实例
	 */
	public Pagination(String sql,int currentPage,int numberPage,JdbcTemplate jdbcTemplate) {
		if(jdbcTemplate==null) {
			throw new IllegalArgumentException("cn.stroe.xiaowu.utils.jdbcTemplate is null");
		}else if("".equals(sql) || sql==null){
			throw new IllegalArgumentException("cn.stroe.xiaowu.utils.sql is empty");
		}else {
			//设置每页显示记录数
			setNumberPage(numberPage);
			//设置要显示的页数
			setCurrentPage(currentPage);
			//计算总条数
			StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
			totalSQL.append(sql);
			totalSQL.append(") as totalTable");
			//给jdbcTemplate赋值
			setJdbcTemplate(jdbcTemplate);
			//设置总条数
			setTotalRows(getJdbcTemplate().queryForObject(totalSQL.toString(), Integer.class));
			//设置总页数
			setTotalPages();
			//设置起始行数
			setStartIndex();
			//设置结束行数
			setLastIndex();
			
			//装入结果集
			setResultList(getJdbcTemplate().queryForList(getMysqlPageSQL(sql,startIndex,numberPage)));
		}
	}
	//构造Mysql数据分页SQL
	public String getMysqlPageSQL(String queryString,Integer startIndex,Integer pageSize) {
		String result = "";
		if(null != startIndex && null != pageSize) {
			result = queryString + " limit " + startIndex + "," + pageSize;
		}else if(null != startIndex && null == pageSize) {
			result = queryString + " limit " + startIndex;
		}else {
			result = queryString;
		}
		return result;
	}
	//设置结束时的索引
	public void setLastIndex() {
		if(totalRows<numberPage) {
			this.lastIndex = totalRows;
		}else if(totalRows % numberPage == 0) {
			this.lastIndex = totalRows * numberPage;
		}else if(totalRows % numberPage != 0 && currentPage == totalPages){
			this.lastIndex = totalRows;
		}
	}
	public int getNumberPage() {
		return numberPage;
	}
	public void setNumberPage(int numberPage) {
		this.numberPage = numberPage;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages() {
		if(totalRows % numberPage == 0) {
			this.totalPages = totalRows / numberPage;
		}else {
			this.totalPages = (totalRows / numberPage) + 1;
		}
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex() {
		this.startIndex = (currentPage-1)*numberPage;
	}
	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	public List getResultList() {
		return resultList;
	}
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	public JdbcTemplate getJTemplate() {
		return jdbcTemplate;
	}
	public void setJTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPage;
		result = prime * result + ((jdbcTemplate == null) ? 0 : jdbcTemplate.hashCode());
		result = prime * result + lastIndex;
		result = prime * result + numberPage;
		result = prime * result + ((resultList == null) ? 0 : resultList.hashCode());
		result = prime * result + startIndex;
		result = prime * result + totalPages;
		result = prime * result + totalRows;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagination other = (Pagination) obj;
		if (currentPage != other.currentPage)
			return false;
		if (jdbcTemplate == null) {
			if (other.jdbcTemplate != null)
				return false;
		} else if (!jdbcTemplate.equals(other.jdbcTemplate))
			return false;
		if (lastIndex != other.lastIndex)
			return false;
		if (numberPage != other.numberPage)
			return false;
		if (resultList == null) {
			if (other.resultList != null)
				return false;
		} else if (!resultList.equals(other.resultList))
			return false;
		if (startIndex != other.startIndex)
			return false;
		if (totalPages != other.totalPages)
			return false;
		if (totalRows != other.totalRows)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Pagination [numberPage=" + numberPage + ", totalRows=" + totalRows + ", totalPages=" + totalPages
				+ ", currentPage=" + currentPage + ", startIndex=" + startIndex + ", lastIndex=" + lastIndex
				+ ", resultList=" + resultList + ", jdbcTemplate=" + jdbcTemplate + "]";
	}
	
	
}
