package com.acme.architecture.common.deserializer;

import java.io.Serializable;
import java.lang.reflect.TypeVariable;
import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomDeserializer <T extends Serializable> implements Deserializer<T> {
	
	private Class<T> tClass;
	
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@Override
	public T deserialize(String topic, byte[] data) {
		System.out.println("*** DESERIALIZE ***");
		ObjectMapper mapper = new ObjectMapper();

		/*
		TypeVariable[] tValue = this.getClass().getTypeParameters();
		System.out.println(tValue[0].getName()+" -> "+((Object) this.getClass().getGenericSuperclass())
			      .getActualTypeArguments()[0]);
		*/
		
		Class cl = this.getClass().getTypeParameters()[0].getGenericDeclaration().getClass();
		
		///TypeVariable typeVariable = this.getClass().getTypeParameters();
		
		System.out.println("cl ::"+ cl);

		//System.out.println(this.getClass().getGenericSuperclass().getActualTypeArguments()[0]);
		
//		try {
//			

//			//System.out.println("field ::"+ field.getGenericType());
//			System.out.println("type ::"+ typeOfT);
//			object = (T) mapper.readValue(data, typeOfT);
//		} catch (Exception exception) {
//			System.out.println("Error in deserializing bytes " + exception);
//		}
		
		return null;
		//return object;
	}

	@Override
	public void close() {
	}

}