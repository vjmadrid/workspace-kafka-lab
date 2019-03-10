package com.acme.architecture.common.deserializer;

import java.io.Serializable;
import java.lang.reflect.TypeVariable;
import java.util.Map;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomDeserializer <T extends Serializable> implements Deserializer<T> {
	
	private Class<T> inferedClass;
	
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}
	
	private Class<T> getGenericTypeClass() {
	    try {
	        String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
	        Class<?> clazz = Class.forName(className);
	        return (Class<T>) clazz;
	    } catch (Exception e) {
	        throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
	    }
	} 

	@Override
	public T deserialize(String topic, byte[] data) {
		
		Type sooper = getClass().getGenericSuperclass();
		inferedClass = getGenericTypeClass();
		
		
		TypeVariable[] tValue = this.getClass().getTypeParameters();
		
		
		Type typeValue = ((ParameterizedType)sooper).getActualTypeArguments()[ 0 ];

		ObjectMapper mapper = new ObjectMapper();
		T object = null;
		try {
			object = (T) mapper.readValue(data,inferedClass);
		} catch (Exception exception) {
			System.out.println("Error in deserializing bytes " + exception);
		}
		return object;
		
		

	/*	
		System.out.println(tValue[0].getName()+" -> "+((Object) this.getClass().getGenericSuperclass())
			      .getActualTypeArguments()[0]);
	
		
		Class cl = this.getClass().getTypeParameters()[0].getGenericDeclaration().getClass();
		*/
		///TypeVariable typeVariable = this.getClass().getTypeParameters();
		
		//System.out.println("cl ::"+ cl);

		//System.out.println(this.getClass().getGenericSuperclass().getActualTypeArguments()[0]);
		
//		try {
//			

//			//System.out.println("field ::"+ field.getGenericType());
//			System.out.println("type ::"+ typeOfT);
//			object = (T) mapper.readValue(data, typeOfT);
//		} catch (Exception exception) {
//			System.out.println("Error in deserializing bytes " + exception);
//		}

	}

	@Override
	public void close() {
	}

	

}