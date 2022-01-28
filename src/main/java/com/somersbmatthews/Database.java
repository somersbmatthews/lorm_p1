package com.somersbmatthews;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.somersbmatthews.sqlmakers.SqlMaker;
import com.somersbmatthews.sqlmakers.StandardSqlMaker;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Provides methods to access a database.
 */
public class Database {

	protected SqlMaker sqlMaker = new StandardSqlMaker();
	protected DataSource ds;

	protected String dataSourceClassName = System.getProperty("norm.dataSourceClassName");
	protected String driverClassName = System.getProperty("norm.driverClassName");
	protected String jdbcUrl = System.getProperty("norm.jdbcUrl");
	protected String serverName = System.getProperty("norm.serverName");
	protected String databaseName = System.getProperty("norm.databaseName");
	protected String user = System.getProperty("norm.user");
	protected String password = System.getProperty("norm.password");
	protected int maxPoolSize = 10;

	protected Map<String, String> dataSourceProperties = new HashMap<>();

	public void setSqlMaker(SqlMaker sqlMaker) {
		this.sqlMaker = sqlMaker;
	}

	public SqlMaker getSqlMaker() {
		return sqlMaker;
	}

	protected DataSource getDataSource() throws SQLException {
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(maxPoolSize);

		if (dataSourceClassName != null) {
			config.setDataSourceClassName(dataSourceClassName);
		}

		if (driverClassName != null) {
			config.setDriverClassName(driverClassName);
		}

		if (jdbcUrl != null) {
			config.setJdbcUrl(jdbcUrl);
		}

		addDataSourceProperty("serverName", serverName);
		addDataSourceProperty("databaseName", databaseName);
		addDataSourceProperty("user", user);
		addDataSourceProperty("password", password);

		for (Map.Entry<String, String> entry : dataSourceProperties.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (value != null) {
				config.addDataSourceProperty(key, value);
			}
		}

		config.setLeakDetectionThreshold(30000);

		return new HikariDataSource(config);
	}

	public void addDataSourceProperty(String name, String value) {
		dataSourceProperties.put(name, value);
	}

	public Query sql(String sql, Object... args) {
		return new Query(this).sql(sql, args);
	}

	public Query where(String where, Object... args) {
		return new Query(this).where(where, args);
	}

	public Query orderBy(String orderBy) {
		return new Query(this).orderBy(orderBy);
	}

	public Connection getConnection() {
		try {

			if (ds == null) {
				ds = getDataSource();
			}
			return ds.getConnection();

		} catch (Throwable t) {
			throw new DbException(t);
		}
	}

	public Query createTable(Class<?> clazz) {
		return new Query(this).createTable(clazz);
	}

	public Query insert(Object row) {
		return new Query(this).insert(row);
	}

	public Query generatedKeyReceiver(Object generatedKeyReceiver, String... generatedKeyNames) {
		return new Query(this).generatedKeyReceiver(generatedKeyReceiver, generatedKeyNames);
	}

	public Query delete(Object row) {
		return new Query(this).delete(row);
	}

	public <T> List<T> results(Class<T> clazz) {
		return new Query(this).results(clazz);
	}

	public <T> T first(Class<T> clazz) {
		return new Query(this).first(clazz);
	}

	public Query update(Object row) {
		return new Query(this).update(row);
	}

	public Query upsert(Object row) {
		return new Query(this).upsert(row);
	}

	public Query table(String table) {
		return new Query(this).table(table);
	}

	public Transaction startTransaction() {
		Transaction trans = new Transaction();
		trans.setConnection(getConnection());
		return trans;
	}

	public Query transaction(Transaction trans) {
		return new Query(this).transaction(trans);
	}

	public void close() {
		if (ds instanceof HikariDataSource) {
			((HikariDataSource) ds).close();
		}
	}

	public void setDataSourceClassName(String dataSourceClassName) {
		this.dataSourceClassName = dataSourceClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

}
