package es.dinersclub.architecture.testing.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Assert;

public class JUnitTestBuilder {

	public static void superficialEnumCodeCoverage(Class<? extends Enum<?>> enumClass) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		for (Object o : (Object[])enumClass.getMethod("values").invoke(null)) {
			enumClass.getMethod("valueOf", String.class).invoke(null, o.toString());
		}
	}

	public static void assertUtilityClassWellDefined(final Class<?> clazz) throws NoSuchMethodException,
			InvocationTargetException, InstantiationException, IllegalAccessException {

		Assert.assertTrue("class must be final", Modifier.isFinal(clazz.getModifiers()));
		Assert.assertEquals("There must be only one constructor", 1, clazz.getDeclaredConstructors().length);
		final Constructor<?> constructor = clazz.getDeclaredConstructor();
		if (constructor.isAccessible() || !Modifier.isPrivate(constructor.getModifiers())) {
			Assert.fail("constructor is not private");
		}
		constructor.setAccessible(true);
		constructor.newInstance();
		constructor.setAccessible(false);
		for (final Method method : clazz.getMethods()) {
			if (!Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass().equals(clazz)) {
				Assert.fail("there exists a non-static method:" + method);
			}
		}
	}

}
