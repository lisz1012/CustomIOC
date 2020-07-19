import annotation.Component;
import annotation.Inject;
import dao.StudentDao;
import service.StudentService;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceStarter {
	private static Map<String, Object> map = new HashMap<String, Object>();

	static {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void init() throws Exception {
		String daoPath = "/Users/shuzheng/IdeaProjects/CustomIOC/src/main/java/dao";
		String daoPackage = "dao";
		File daoDir = new File(daoPath);
		File files[] = daoDir.listFiles();
		List<Object> daoInstances = new ArrayList<>();
		//System.out.println("The below classes are included");

		for (File file : files) {
			String className = daoPackage + "." + file.getName().replace(".java", "");
			//System.out.println(className);
			Class clazz = Class.forName(className);
			if (!clazz.isInterface() && clazz.isAnnotationPresent(Component.class)) {
				Object daoInstance = clazz.newInstance();
				map.put(className, daoInstance);
				daoInstances.add(daoInstance);
			}
		}

		String servicePath = "/Users/shuzheng/IdeaProjects/CustomIOC/src/main/java/service";
		String servicePackage = "service";
		File serviceDir = new File(servicePath);
		files = serviceDir.listFiles();
		List<Object> serviceInstances = new ArrayList<>();
 		for (File file : files) {
			String className = servicePackage + "." + file.getName().replace(".java", "");
			//System.out.println(className);
			Class clazz = Class.forName(className);
			if (!clazz.isInterface()) {
				Object serviceInstance = clazz.newInstance();
				map.put(className, serviceInstance);
				serviceInstances.add(serviceInstance);
			}


		}

		/*System.out.println(map);
		daoInstances.forEach(System.out::println);
		serviceInstances.forEach(System.out::println);*/

		serviceInstances.forEach(s -> {
			Class clazz = s.getClass();
			Field fields[] = clazz.getDeclaredFields();
			for (Field f : fields) {
				if (f.isAnnotationPresent(Inject.class)) {
					String className = f.getType().getName();
					f.setAccessible(true);
					try {
						Object instance = map.get(className);
						if (instance == null) {
							for (Object daoInstance : daoInstances) {
								/*if (daoInstance.getClass() == f.getType()) {
									instance = map.get(daoInstance.getClass().getName());
									break;
								}*/
								Class interfaces[] = daoInstance.getClass().getInterfaces();
								for (Class infc : interfaces) {
									if (infc == f.getType()) {
										instance = map.get(daoInstance.getClass().getName());
										break;
									}
								}
							}
						}
						f.set(s, instance);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private static <T> T get(Class<T> clazz) {
		return (T)map.get(clazz.getName());
	}


	public static void main(String[] args) throws Exception {
		StudentService studentService = get(StudentService.class);
		studentService.save();
		studentService.run();
	}
}
