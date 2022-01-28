package com.somersbmatthews.sqlmakers;

public interface PojoInfo {

	public Object getValue(Object pojo, String name);

	public void putValue(Object pojo, String name, Object value);

	public void putValue(Object pojo, String name, Object value, boolean ignoreIfMissing);

	public String[] getGeneratedColumnNames();

	public Property getProperty(String name);

}
