package com.demo.commonutil.utilits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import net.logstash.logback.encoder.org.apache.commons.lang.Validate;

import java.lang.reflect.*;

@SuppressWarnings("rawtypes")
public class ReflectionUtils {
	
	private static final String SETTER_PREFIX = "set";

	private static final String GETTER_PREFIX = "get";

	private static final String CGLIB_CLASS_SEPARATOR = "$$";
	
	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	/**
	 * 璋冪敤Getter鏂规硶.
	 * 鏀寔澶氱骇锛屽锛氬璞″悕.瀵硅薄鍚�.鏂规硶
	 */
	public static Object invokeGetter(Object obj, String propertyName) {
		Object object = obj;
		for (String name : StringUtils.split(propertyName, ".")){
			String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
			object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
		}
		return object;
	}

	/**
	 * 璋冪敤Setter鏂规硶, 浠呭尮閰嶆柟娉曞悕銆�
	 * 鏀寔澶氱骇锛屽锛氬璞″悕.瀵硅薄鍚�.鏂规硶
	 */
	public static void invokeSetter(Object obj, String propertyName, Object value) {
		Object object = obj;
		String[] names = StringUtils.split(propertyName, ".");
		for (int i=0; i<names.length; i++){
			if(i<names.length-1){
				String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
				object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
			}else{
				String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
				invokeMethodByName(object, setterMethodName, new Object[] { value });
			}
		}
	}

	/**
	 * 鐩存帴璇诲彇瀵硅薄灞炴�у��, 鏃犺private/protected淇グ绗�, 涓嶇粡杩噂etter鍑芥暟.
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("涓嶅彲鑳芥姏鍑虹殑寮傚父{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 鐩存帴璁剧疆瀵硅薄灞炴�у��, 鏃犺private/protected淇グ绗�, 涓嶇粡杩噑etter鍑芥暟.
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			logger.error("Could not find field [" + fieldName + "] on target [" + obj + "]");
			return;
			//throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}
		try {
			field.set(obj, convert(value, field.getType()));
		} catch (IllegalAccessException e) {
			logger.error("涓嶅彲鑳芥姏鍑虹殑寮傚父:{}", e.getMessage());
		}
	}
	
	public static Object convert(Object object, Class<?> type) {
	    if (object instanceof Number) {
	        Number number = (Number) object;
	        if (type.equals(byte.class) || type.equals(Byte.class)) {
	            return number.byteValue();
	        }
	        if (type.equals(short.class) || type.equals(Short.class)) {
	            return number.shortValue();
	        }
	        if (type.equals(int.class) || type.equals(Integer.class)) {
	            return number.intValue();
	        }
	        if (type.equals(long.class) || type.equals(Long.class)) {
	            return number.longValue();
	        }
	        if (type.equals(float.class) || type.equals(Float.class)) {
	            return number.floatValue();
	        }
	        if (type.equals(double.class) || type.equals(Double.class)) {
	            return number.doubleValue();
	        }
	    }
	    if(type.equals(String.class)){
	    	return object==null?"":object.toString();
	    }
	    return object;
	}

	/**
	 * 鐩存帴璋冪敤瀵硅薄鏂规硶, 鏃犺private/protected淇グ绗�.
	 * 鐢ㄤ簬涓�娆℃�ц皟鐢ㄧ殑鎯呭喌锛屽惁鍒欏簲浣跨敤getAccessibleMethod()鍑芥暟鑾峰緱Method鍚庡弽澶嶈皟鐢�.
	 * 鍚屾椂鍖归厤鏂规硶鍚�+鍙傛暟绫诲瀷锛�
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 鐩存帴璋冪敤瀵硅薄鏂规硶, 鏃犺private/protected淇グ绗︼紝
	 * 鐢ㄤ簬涓�娆℃�ц皟鐢ㄧ殑鎯呭喌锛屽惁鍒欏簲浣跨敤getAccessibleMethodByName()鍑芥暟鑾峰緱Method鍚庡弽澶嶈皟鐢�.
	 * 鍙尮閰嶅嚱鏁板悕锛屽鏋滄湁澶氫釜鍚屽悕鍑芥暟璋冪敤绗竴涓��
	 */
	public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
		Method method = getAccessibleMethodByName(obj, methodName);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 寰幆鍚戜笂杞瀷, 鑾峰彇瀵硅薄鐨凞eclaredField, 骞跺己鍒惰缃负鍙闂�.
	 * 
	 * 濡傚悜涓婅浆鍨嬪埌Object浠嶆棤娉曟壘鍒�, 杩斿洖null.
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		Validate.notNull(obj, "object can't be null");
		Validate.notEmpty(fieldName, "fieldName can't be blank");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			} catch (NoSuchFieldException e) {//NOSONAR
				// Field涓嶅湪褰撳墠绫诲畾涔�,缁х画鍚戜笂杞瀷
				continue;// new add
			}
		}
		return null;
	}

	/**
	 * 寰幆鍚戜笂杞瀷, 鑾峰彇瀵硅薄鐨凞eclaredMethod,骞跺己鍒惰缃负鍙闂�.
	 * 濡傚悜涓婅浆鍨嬪埌Object浠嶆棤娉曟壘鍒�, 杩斿洖null.
	 * 鍖归厤鍑芥暟鍚�+鍙傛暟绫诲瀷銆�
	 * 
	 * 鐢ㄤ簬鏂规硶闇�瑕佽澶氭璋冪敤鐨勬儏鍐�. 鍏堜娇鐢ㄦ湰鍑芥暟鍏堝彇寰桵ethod,鐒跺悗璋冪敤Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {
		Validate.notNull(obj, "object can't be null");
		Validate.notEmpty(methodName, "methodName can't be blank");

		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
			try {
				Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
				makeAccessible(method);
				return method;
			} catch (NoSuchMethodException e) {
				// Method涓嶅湪褰撳墠绫诲畾涔�,缁х画鍚戜笂杞瀷
				continue;// new add
			}
		}
		return null;
	}

	/**
	 * 寰幆鍚戜笂杞瀷, 鑾峰彇瀵硅薄鐨凞eclaredMethod,骞跺己鍒惰缃负鍙闂�.
	 * 濡傚悜涓婅浆鍨嬪埌Object浠嶆棤娉曟壘鍒�, 杩斿洖null.
	 * 鍙尮閰嶅嚱鏁板悕銆�
	 * 
	 * 鐢ㄤ簬鏂规硶闇�瑕佽澶氭璋冪敤鐨勬儏鍐�. 鍏堜娇鐢ㄦ湰鍑芥暟鍏堝彇寰桵ethod,鐒跺悗璋冪敤Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethodByName(final Object obj, final String methodName) {
		Validate.notNull(obj, "object can't be null");
		Validate.notEmpty(methodName, "methodName can't be blank");

		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
			Method[] methods = searchType.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					makeAccessible(method);
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 鏀瑰彉private/protected鐨勬柟娉曚负public锛屽敖閲忎笉璋冪敤瀹為檯鏀瑰姩鐨勮鍙ワ紝閬垮厤JDK鐨凷ecurityManager鎶辨�ㄣ��
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * 鏀瑰彉private/protected鐨勬垚鍛樺彉閲忎负public锛屽敖閲忎笉璋冪敤瀹為檯鏀瑰姩鐨勮鍙ワ紝閬垮厤JDK鐨凷ecurityManager鎶辨�ㄣ��
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) || Modifier
				.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * 閫氳繃鍙嶅皠, 鑾峰緱Class瀹氫箟涓０鏄庣殑娉涘瀷鍙傛暟鐨勭被鍨�, 娉ㄦ剰娉涘瀷蹇呴』瀹氫箟鍦ㄧ埗绫诲
	 * 濡傛棤娉曟壘鍒�, 杩斿洖Object.class.
	 * eg.
	 * public UserDao extends HibernateDao<User>
	 *
	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClassGenricType(final Class clazz) {
		return getClassGenricType(clazz, 0);
	}

	/**
	 * 閫氳繃鍙嶅皠, 鑾峰緱Class瀹氫箟涓０鏄庣殑鐖剁被鐨勬硾鍨嬪弬鏁扮殑绫诲瀷.
	 * 濡傛棤娉曟壘鍒�, 杩斿洖Object.class.
	 * 
	 * 濡俻ublic UserDao extends HibernateDao<User,Long>
	 *
	 * @param clazz clazz The class to introspect
	 * @param index the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	public static Class getClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}
	
	public static Class<?> getUserClass(Object instance) {
		Assert.notNull(instance, "Instance must not be null");
		Class clazz = instance.getClass();
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;

	}
	
	/**
	 * 灏嗗弽灏勬椂鐨刢hecked exception杞崲涓簎nchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException(e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException(((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
	/**
	 * 鍒ゆ柇鏌愪釜瀵硅薄鏄惁鎷ユ湁鏌愪釜灞炴��
	 * 
	 * @param obj 瀵硅薄
	 * @param fieldName 灞炴�у悕
	 * @return 鏈夊睘鎬ц繑鍥瀟rue
	 * 		        鏃犲睘鎬ц繑鍥瀎alse
	 */
	public static boolean hasField(final Object obj, final String fieldName){
		Field field = getAccessibleField(obj, fieldName);
		if (field == null) {
			return false;
		}
		return true;

	}
}
