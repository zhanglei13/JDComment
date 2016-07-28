package com.jd.comment.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;

public class ReflectUtils {
    
    private static final String GET = "get";
    private static final String SET = "set";
    
    /**
     * 获得超类的参数类型，取第一个参数类型
     * @param <T> 类型参数
     * @param clazz 超类类型
     */
    public static <T> Class<T> getClassGenricType(final Class clazz) {
        return getClassGenricType(clazz, 0);
    }
    public static void main(String[] args) {
        ArrayList<String> list=new ArrayList<String>();
        System.out.println(getClassGenricType(list.getClass()));
    }
    /**
     * 根据索引获得超类的参数类型
     * @param clazz 超类类型
     * @param index 索引
     */
    public static Class getClassGenricType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
    
    /**
     * 获取一个类的某个一个泛型参数
     * 
     * @param klass
     *            类
     * @param index
     *            参数下标 （从 0 开始）
     * @return 泛型参数类型
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getTypeParam(Class<?> klass, int index) {
        Type[] types = getTypeParams(klass);
        if (index >= 0 && index < types.length) {
            Type t = types[index];
            Class<T> clazz = (Class<T>) getTypeClass(t);
            if (clazz == null)
                throw new RuntimeException(String.format("Type '%s' is not a Class", t.toString()));
            return clazz;
        }
        throw new RuntimeException(String.format("Class type param out of range %d/%d", index, types.length));
    }
    /**
     * 获取一个类的泛型参数数组，如果这个类没有泛型参数，返回 null
     */
    public static Type[] getTypeParams(Class<?> klass) {
        if (klass == null || "java.lang.Object".equals(klass.getName()))
            return null;
        // 看看父类
        Type superclass = klass.getGenericSuperclass();
        if (null != superclass && superclass instanceof ParameterizedType)
            return ((ParameterizedType) superclass).getActualTypeArguments();

        // 看看接口
        Type[] interfaces = klass.getGenericInterfaces();
        for (Type inf : interfaces) {
            if (inf instanceof ParameterizedType) {
                return ((ParameterizedType) inf).getActualTypeArguments();
            }
        }
        return getTypeParams(klass.getSuperclass());
    }
    /**
     * 获取一个Type类型实际对应的Class
     */
    @SuppressWarnings("rawtypes")
    public static Class<?> getTypeClass(Type type) {
        Class<?> clazz = null;
        if (type instanceof Class<?>) {
            clazz = (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            clazz = (Class<?>) pt.getRawType();
        } else if (type instanceof GenericArrayType) {
            GenericArrayType gat = (GenericArrayType) type;
            Class<?> typeClass = getTypeClass(gat.getGenericComponentType());
            return Array.newInstance(typeClass, 0).getClass();
        } else if (type instanceof TypeVariable) {
            TypeVariable tv = (TypeVariable) type;
            Type[] ts = tv.getBounds();
            if (ts != null && ts.length > 0)
                return getTypeClass(ts[0]);
        } else if (type instanceof WildcardType) {
            WildcardType wt = (WildcardType) type;
            Type[] t_low = wt.getLowerBounds();// 取其下界
            if (t_low.length > 0)
                return getTypeClass(t_low[0]);
            Type[] t_up = wt.getUpperBounds(); // 没有下界?取其上界
            return getTypeClass(t_up[0]);// 最起码有Object作为上界
        }
        return clazz;
    }
    /** 
     * @Author: Charles
     * @Description: 获取类所有字段名称
     * @param clazz
     * @return String[]: 名称数组
     */
    public static String[] getDeclaredFieldName(final Class<?> clazz){
        return getDeclaredFieldNameByPrefix(clazz, null);
    }
    
    /** 
     * @Author: Charles
     * @Description: 获取类所有字段并且加上前缀名称
     * @param clazz
     * @param prefix    在字段名加上的前缀
     * @return String[]:    名称数组
     */
    public static String[] getDeclaredFieldNameByPrefix(final Class clazz, String prefix){
        Field[] fields = clazz.getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for(int i = 0; i < fields.length; i++){
            String field = fields[i].getName();
            if(prefix != null && !"".equals(prefix)){
                field = field.substring(0, 1).toUpperCase() + field.substring(1, field.length());   //把首字母改大写
                fieldNames[i] = prefix + field;
            }else{
                fieldNames[i] = field;
            }
        }
        return fieldNames;
    }
    /** 
     * @Author: Charles
     * @Description: 获取所有字段的GET方法名称
     * @param clazz
     * @return String[]: 
     */
    public static String[] getDeclaredFieldNameByGet(final Class clazz){
        return getDeclaredFieldNameByPrefix(clazz, GET);
    }
    /** 
     * @Author: Charles
     * @Description: 获取所有字段的SET方法名称
     * @param clazz
     * @return String[]: 
     */
    public static String[] getDeclaredFieldNameBySet(final Class clazz){
        return getDeclaredFieldNameByPrefix(clazz, SET);
    }
    /** 
     * @Author: Charles
     * @Description: 获取指定名称的方法
     * @param clazz
     * @param methodName    方法名称    
     * @param parameterTypes    参数Class数组
     * @return Method: 
     */
    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes)
    {
        Method m=null;
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass())
        {
            try {
                m=superClass.getDeclaredMethod(methodName, parameterTypes);
                 if(m!=null)
                    return m;
            }catch (NoSuchMethodException e) {
//                e.printStackTrace();找不到不需要理会
            }
        }
        return null;
    }
    /** 
     * @Author: Charles
     * @Description: 获取类所有方法名称
     * @param clazz
     * @return String[]: 
     */
    public static String[] getDeclaredMethodName(final Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        String[] methodNames = new String[methods.length];
        for(int i = 0; i < methods.length; i++){
            methodNames[i] = methods[i].getName();
        }
        return methodNames;
    }
    
    
    
    
    /** 
     * @Author: Charles
     * @Description: 调用某个类的某个指定方法
     * @param clazz
     * @param methodName    需要调用的方法名称
     * @param parameterTypes    参数Class类型
     * @param parameters        参数数组
     * @return
     * @throws InvocationTargetException Object: 
     */
    public static Object invokeMethod(Object target, String methodName, Class<?>[] parameterTypes, Object[] parameters){
        Method method = getDeclaredMethod(target.getClass(), methodName, parameterTypes);
       
        if (method == null){
            throw new IllegalArgumentException("Could not find method ["
                    + methodName + "] on target [" + target.getClass().getName() + "]");
        }
        method.setAccessible(true);
        try {
            return method.invoke(target, parameters);
        } catch (IllegalAccessException e) {
           e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
       
        return null;
    }
    /** 
     * @Author: fjz
     * @Description: 调用某个类的某个指定方法
     * @param methodName    需要调用的方法名称
     * @param parameters        参数数组
     * @return
     * @throws InvocationTargetException Object: 
     */
    public static Object invokeMethod(Object target, String methodName, Object... parameters){
        Class<?>[] parameterTypes=null;
        if(parameters!=null){
            parameterTypes=new Class<?>[parameters.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                parameterTypes[i]=parameters[i].getClass();
            }
        }
        return invokeMethod(target, methodName, parameterTypes, parameters);
    }
    
    
    /** 
     * @Author: Charles
     * @Description: 设置该字段是否可访问
     * @param field void: 
     */
    private static void makeAccessible(Field field)
    {
        if (!Modifier.isPublic(field.getModifiers()))
        {
            field.setAccessible(true);
        }
    }
    
    /** 
     * @Author: Charles
     * @Description: 获取某个字段的值
     * @param clazz
     * @param fieldName     字段名称
     * @return Object: 
     */
    public static Object getFieldValue(Object target, String fieldName)
    {
        Field field = null;
        try {
            field = target.getClass().getDeclaredField(fieldName);
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        }
        
        makeAccessible(field);
       
        Object result = null;
        try {
            result = field.get(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /** 
     * @Author: Charles
     * @Description: 设置某个字段的值
     * @param object
     * @param fieldName 字段名称
     * @param value void:   
     */
    public static void setFieldValue(Object target, String fieldName, Object value)
    {
        Field field = null;
        try {
            field = target.getClass().getDeclaredField(fieldName);
        } catch (SecurityException e1) {
            e1.printStackTrace();
        } catch (NoSuchFieldException e1) {
            e1.printStackTrace();
        }
        
        makeAccessible(field);
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}