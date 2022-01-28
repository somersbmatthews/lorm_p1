package com.somersbmatthews.serialize;

public interface DbSerializable {

	public String serialize(Object in);

	public Object deserialize(String in, Class<?> targetClass);

}
