package tool.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import tool.PropertiesTool;


/**
 * 数据库管理工具类
 *
 * @author GaoHuanjie
 */
public class DBLink {
	
	private Logger logger = Logger.getLogger(DBLink.class);
	
	/**
	 * 获取数据库连接
	 *
	 * @author GaoHuanjie
	 */
	private Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");//加载驱动
			String userName = PropertiesTool.getValue("db.username");
			String password = PropertiesTool.getValue("db.password");
			String url = PropertiesTool.getValue("db.url");
			return DriverManager.getConnection(url, userName, password);//获取连接
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 判断SQL语句是否能查出数据
	 *
	 * @author GaoHuanjie
	 */
	public boolean exist(String sql) {
		Connection connection = null;
		Statement statement =null;
		ResultSet resultSet=null;
		try {
			connection = getConnection();//获取连接
			statement = connection.createStatement();
			resultSet= statement.executeQuery(sql);//执行sql,将查询的数据存到ResultSet类型的变量中
			return resultSet.next();
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}finally {
			close(resultSet,statement,connection);
		}
		return false;
	}
	
	/**
	 * 判断SQL语句是否能查出数据
	 *
	 * @author GaoHuanjie
	 */
	public boolean exist(String sql, Object ...params) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet=null;
		try {
			connection = getConnection();//获取连接
			preparedStatement = connection.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				preparedStatement.setObject(i+1, params[i]);
			}
			resultSet= preparedStatement.executeQuery();//执行sql,将查询的数据存到ResultSet类型的变量中
			return resultSet.next();
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}finally {
			close(resultSet,preparedStatement,connection);
		}
		return false;
	}
	
	/**
	 * 查询数据
	 *
	 * @author GaoHuanjie
	 */
	public void select(String sql,IRowMapper rowMapper) {//接口无法创建对象，所以rowMapper参数一定指向IRowMapper接口实现类对象
		Connection connection = null;
		Statement statement =null;
		ResultSet resultSet=null;
		try {
			connection = getConnection();//获取连接
			statement = connection.createStatement();
			resultSet= statement.executeQuery(sql);//执行sql,将查询的数据存到ResultSet类型的变量中
			rowMapper.rowMapper(resultSet);//因为rowMapper参数指向IRowMapper接口实现类对象，所以此处将调用接口实现类中所实现的rowMapper方法  多态
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}finally {//释放资源
			close(resultSet,statement,connection);
		}
	}
	public Object getValue(String sql,String columName) {
		Connection connection = null;
		Statement statement =null;
		ResultSet resultSet=null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			resultSet= statement.executeQuery(sql);
			if(resultSet.next()) {
				return resultSet.getObject(columName);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}finally {//释放资源
			close(resultSet,statement,connection);
		}
		return null;
	}
	
	/**
	 * 查询数据
	 *
	 * @author GaoHuanjie
	 */
	public void select(String sql,IRowMapper rowMapper,Object ...params) {
		Connection connection = null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet=null;
		try {
			connection = getConnection();//获取连接
			preparedStatement= connection.prepareStatement(sql);//含有？号占位符的sql
			for (int i = 0; i < params.length; i++) {
				preparedStatement.setObject(i+1, params[i]);//为？号赋值
			}
			resultSet= preparedStatement.executeQuery();//执行sql
			rowMapper.rowMapper(resultSet);//因为rowMapper参数指向IRowMapper接口实现类对象，所以此处将调用接口实现类中所实现的rowMapper方法  多态
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}finally {//释放资源
			close(resultSet,preparedStatement,connection);
		}
	}

	/**
	 * 修改（insert、update和delete）数据
	 *
	 * @author GaoHuanjie
	 */
	public boolean update(String sql) {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();//获取数据库连接对象，一个对象表示一次数据库连接
			statement = connection.createStatement();//获取Statement对象
			int result = statement.executeUpdate(sql);//执行sql语句，返回受影响的行数，仅限于数据insert、update和delete
			return result>0;//处理结果
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}finally {//即便有异常也会执行代码
			close(statement,connection);
		}
		return false;
	}
	
	/**
	 * 修改（insert、update和delete）数据
	 *
	 * @author GaoHuanjie
	 */
	public boolean update(String sql,Object ...params) {
		Connection connection= null;
		PreparedStatement preparedStatement= null;
		try {
			connection= getConnection();
			preparedStatement= connection.prepareStatement(sql);//含有？号占位符的sql
			for (int i = 0; i < params.length; i++) {
				preparedStatement.setObject(i+1, params[i]);//为？号赋值
			}
			return preparedStatement.executeUpdate()>0;
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}finally {
			close(preparedStatement,connection);
		}
		return false;
	}
	
	/**
	 * 释放资源
	 *
	 * @author GaoHuanjie
	 */
	private void close(Statement statement,Connection connection) {
		try {
			if(statement!=null) {//有可能由于异常导致statement没有赋值，比如url出错
				statement.close();
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage(), e);
		}
		try {
			if(connection!=null) {
				connection.close();
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage(), e);
		}
	}
	
	/**
	 * 释放资源
	 *
	 * @author GaoHuanjie
	 */
	private void close(ResultSet resultSet,Statement statement,Connection connection) {//重载
		try {
			if(resultSet!=null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage(), e);
		}
		close(statement,connection);
	}
}
