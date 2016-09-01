package com.github.developframework.scanner;

import com.github.developframework.scanner.annotation.ScanProperties;
import com.github.developframework.scanner.annotation.ScanProperty;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;

@Slf4j
public class PropertiesScanner {

    public PropertiesBox scan(Class<?>... classes) {
        final PropertiesBox box = new PropertiesBox();
        Arrays.stream(classes).filter(clazz -> clazz.isAnnotationPresent(ScanProperties.class)).forEach(clazz -> {
            ScanProperties scanPropertiesAnnotation = clazz.getAnnotation(ScanProperties.class);
            Object properties = generatePropeties(clazz, scanPropertiesAnnotation);
            box.push(clazz, properties);
        });
        return box;
    }

    private <T> T generatePropeties(Class<T> clazz, ScanProperties scanPropertiesAnnotation) {
        final Properties utilProperties = getUtilProperties(clazz, scanPropertiesAnnotation);
        try {
            final T t = clazz.newInstance();
            Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
                boolean hasScanPropertyAnnotation = field.isAnnotationPresent(ScanProperty.class);
                String name = null;
                if (hasScanPropertyAnnotation) {
                    ScanProperty scanProperty = field.getAnnotation(ScanProperty.class);
                    name = scanPropertiesAnnotation.prefix() + "." + scanProperty.alias();
                } else {
                    name = scanPropertiesAnnotation.prefix() + "." + field.getName();
                }
                String value = utilProperties.get(name).toString();
                if (value != null && !value.isEmpty()) {
                    setValue(field, t, value);
                } else if(hasScanPropertyAnnotation){
                    ScanProperty scanProperty = field.getAnnotation(ScanProperty.class);
                    setValue(field, t, scanProperty.ifMissingOfValue());
                }
            });
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            log.error("PropertiesScanner have an error when new Class instance for {}, because {}.", clazz.getName(), e.getMessage());
            return null;
        }
    }

    private Properties getUtilProperties(Class<?> clazz, ScanProperties scanPropertiesAnnotation) {
        Properties utilProperties = new Properties();
        try {
            String filename = "/" + scanPropertiesAnnotation.location() + ".properties";
            utilProperties.load(PropertiesScanner.class.getResourceAsStream(filename));
            log.info("PropertiesScanner loaded Class {} for properties file: {}", clazz.getName(), filename);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("PropertiesScanner have a IO error", e);
        }
        return utilProperties;
    }

    private void setValue(Field field, Object object, String value) {
        field.setAccessible(true);
        Class<?> clazz = field.getType();
        try {
            if (clazz.isAssignableFrom(int.class)) {
                field.setInt(object, Integer.valueOf(value));
            } else if (clazz.isAssignableFrom(long.class)) {
                field.setLong(object, Long.valueOf(value));
            } else if (clazz.isAssignableFrom(short.class)) {
                field.setShort(object, Short.valueOf(value));
            } else if (clazz.isAssignableFrom(boolean.class)) {
                field.setBoolean(object, Boolean.valueOf(value));
            } else if (clazz.isAssignableFrom(float.class)) {
                field.setFloat(object, Float.valueOf(value));
            } else if (clazz.isAssignableFrom(double.class)) {
                field.setDouble(object, Double.valueOf(value));
            } else {
                field.set(object, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
