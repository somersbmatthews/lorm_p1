package com.somersbmatthews;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;

public class Transaction implements Closeable {
	private Connection con;

	void setConnection(Connection con) {
		this.con = con;
		try {
			con.setAutoCommit(false);
		} catch (Throwable t) {
			throw new DbException(t);
		}
	}

	public void commit() {
		try {
			con.commit();
		} catch (Throwable t) {
			throw new DbException(t);
		} finally {
			try {
				con.close();
			} catch (Throwable t) {
				throw new DbException(t);
			}
		}
	}

	public void rollback() {
		try {
			con.rollback();
		} catch (Throwable t) {
			throw new DbException(t);
		} finally {
			try {
				con.close();
			} catch (Throwable t) {
				throw new DbException(t);
			}
		}
	}

	public Connection getConnection() {
		return con;
	}

	@Override
	public void close() throws IOException {
		commit();
	}

}
