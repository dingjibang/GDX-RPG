package com.rpsg.rpg.utils.xml;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Eval {
	private static boolean hasFirstInit = false;

	public static String getClassCode(String initParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("public class ETEvaler {").append("public Object eval() {")
				.append(initParam).append("}").append("}");
		return sb.toString();
	}

	public static Object eval(String sourceCode) throws SecurityException,
			NoSuchMethodException, IOException, ClassNotFoundException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		URLClassLoader classLoader = new URLClassLoader(new URL[] { new File(
				System.getProperty("java.io.tmpdir")).toURI().toURL() });
		Class clazz = null;
		boolean hasInit = true;
		try {
			clazz = classLoader.loadClass("ETEvaler");
		} catch (ClassNotFoundException e) {
			hasInit = false;
		}
		if (!hasInit || !hasFirstInit) {
			hasFirstInit = true;
			String classCode = getClassCode(sourceCode);
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			if (compiler == null)
				throw new IllegalArgumentException(
						"系统java编译器无法找到");
			DiagnosticCollector diagnostics = new DiagnosticCollector();
			StandardJavaFileManager fileManager = compiler
					.getStandardFileManager(diagnostics, null, null);
			String fileName = "ETEvaler.java";
			File file = new File(System.getProperty("java.io.tmpdir"), fileName);
			PrintWriter pw = new PrintWriter(file);
			pw.println(classCode);
			pw.close();
			Iterable compilationUnits = fileManager
					.getJavaFileObjectsFromStrings(Arrays.asList(file
							.getAbsolutePath()));
			JavaCompiler.CompilationTask task = compiler.getTask(null,
					fileManager, diagnostics, null, null, compilationUnits);
			boolean success = task.call();
			fileManager.close();
		}

		classLoader = new URLClassLoader(new URL[] { new File(
				System.getProperty("java.io.tmpdir")).toURI().toURL() });
		clazz = classLoader.loadClass("ETEvaler");
		Method method = clazz.getDeclaredMethod("eval");
		Object value = method.invoke(clazz.newInstance());
		return value;

	}
	
}
