package code.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;

@Slf4j
public final class CloneUtils {

    private CloneUtils() {}

    public static <T> T shallowClone(T obj, Class<T> clazz) {

        try {
            final T dest = clazz.newInstance();
            BeanCopier.create(clazz, clazz, false).copy(obj, dest, null);

            return dest;
        } catch (Exception e) {
            log.warn("Failed to clone object of class [{}].", clazz);
            return obj;
        }
    }
}