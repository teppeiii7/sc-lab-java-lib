package tokyo.scratchcrew.sclab.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * リフレクションのUtilsです。
 * 
 * @author teppeiii7
 *
 */
public class ReflectionUtils {

    private ReflectionUtils() {
    };

    /**
     * ジェネリック型で宣言したクラスの型パラメータを特定して返却します。
     * 
     * @param clazz
     * @return
     */
    public static <T> Class<T> getGenericType(Class<?> clazz) {
        Type t = clazz;
        while (t instanceof Class<?>) {
            t = ((Class<?>) t).getGenericSuperclass();
        }
        if (t instanceof ParameterizedType) {
            for (Type param : ((ParameterizedType) t).getActualTypeArguments()) {
                if (param instanceof Class<?>) {
                    Class<T> cls = determineClass(param);
                    if (cls != null) {
                        return cls;
                    }
                } else if (param instanceof TypeVariable) {
                    for (Type paramBound : ((TypeVariable<?>) param).getBounds()) {
                        if (paramBound instanceof Class<?>) {
                            Class<T> cls = determineClass(paramBound);
                            if (cls != null) {
                                return cls;
                            }
                        }
                    }
                }
            }
        }
        throw new IllegalStateException("Cannot figure out type parameterization for " + clazz.getName());
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> determineClass(Type candidate) {
        if (candidate instanceof Class<?>) {
            final Class<?> cls = (Class<?>) candidate;
            if (Object.class.isAssignableFrom(cls)) {
                return (Class<T>) cls;
            }
        }
        return null;
    }

}
