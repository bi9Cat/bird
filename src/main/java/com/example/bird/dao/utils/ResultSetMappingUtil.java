package com.example.bird.dao.utils;

import com.example.bird.model.enums.EnumInterface;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

@Slf4j
public class ResultSetMappingUtil {

    /**
     * 将 ResultSet 中的数据映射到指定类型的对象列表中
     *
     * @param resultSet 结果集
     * @param clazz     目标对象的类
     * @param <T>       目标对象的类型
     * @return 映射后的对象列表
     */
    public static <T> T mapResultSetToObject(ResultSet resultSet, Class<T> clazz) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);

            T object = constructor.newInstance();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);
                setFieldValue(object, columnName, value);
            }
            return object;
        } catch (Exception e) {
            LOG.error("ResultSetMappingUtil.mapResultSetToObject error.", e);
            return null;
        }
    }

    /**
     * 设置对象的属性值
     *
     * @param object    目标对象
     * @param fieldName 属性名
     * @param value     属性值
     */
    private static void setFieldValue(Object object, String fieldName, Object value) {
        Class<?> clazz = object.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Class<?> fieldType = field.getType();

            if (Enum.class.isAssignableFrom(fieldType) && EnumInterface.class.isAssignableFrom(fieldType)) {
                // 如果是实现了 IEnum 接口的枚举类型
                java.lang.reflect.Method valueOfMethod = fieldType.getMethod("valueOfCode", String.class);
                Object enumValue = valueOfMethod.invoke(null, String.valueOf(value));
                field.set(object, enumValue);
            } else {
                field.set(object, value);
            }
        } catch (Exception e) {
            LOG.error("ResultSetMappingUtil.setFieldValue error.", e);
        }
    }
}
