package main;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SkeletonHelper {
	private static Boolean isEnabled;
	
	public static void turnOn() {
		isEnabled = true;
	}
	
	public static void turnOff() {
		isEnabled = false;
	}
	
	@SuppressWarnings("rawtypes")
	public static void enterMethod() {
		if (!isEnabled)
			return;
		
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();

		StringBuffer indentString = new StringBuffer();
		int indent = elements.length - 4;
		for (int i = 0; i < indent; i++) {
			if (i < indent - 1)
				indentString.append("|   ");
			else
				indentString.append("| - ");
		}

		try {
			String className = elements[2].getClassName();
			String methodName = elements[2].getMethodName();
			Class cl = Class.forName(className);
			String simpleClassName = cl.getSimpleName();

			if (methodName == "<init>") {
				Constructor ctor = cl.getDeclaredConstructors()[0];
				String parameters = join(ctor.getParameterTypes());

				System.out.println(indentString.toString() + simpleClassName
						+ ".ctor(" + parameters + ")");
			} else {
				Method[] methods = cl.getDeclaredMethods();
				for (int i = 0; i < methods.length; i++) {
					Method method = methods[i];
					if (method.getName() == methodName) {
						Class returnType = method.getReturnType();
						String returnTypeString = returnType == null ? ""
								: returnType.getSimpleName() + " ";
						String parameters = join(method.getParameterTypes());

						System.out.println(indentString.toString()
								+ returnTypeString + simpleClassName + "."
								+ method.getName() + "(" + parameters + ")");
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void comment(String comment) {
		if (!isEnabled)
			return;
		
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();

		StringBuffer indentString = new StringBuffer();
		int indent = elements.length - 3;
		for (int i = 0; i < indent; i++) {
			indentString.append("|   ");
		}

		System.out.println(indentString.toString() + "// " + comment);
	}

	@SuppressWarnings("rawtypes")
	private static String join(Class[] types) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < types.length; i++) {
			if (i != 0) {
				buffer.append(", ");
			}

			buffer.append(types[i].getSimpleName());
		}
		return buffer.toString();
	}

	public static void space() {
		if (!isEnabled)
			return;
		
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();

		StringBuffer indentString = new StringBuffer();
		int indent = elements.length - 3;
		for (int i = 0; i < indent; i++) {
			indentString.append("|   ");
		}

		System.out.println(indentString.toString());
	}
}
