package com.somersbmatthews;

@SuppressWarnings("serial")

public class DbException extends RuntimeException {

	private String sql;

	public DbException() {
	}

	public DbException(String msg) {
		super(msg);
	}

	public DbException(Throwable t) {
		super(t);
	}

	public DbException(String msg, Throwable t) {
		super(msg, t);
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

}
