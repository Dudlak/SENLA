package task.Properties;

import task.Annotations.ConfigProperty;
import task.Annotations.PropertyType;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;


public class Config {
    private final String PATH = "task/Properties/";

    public void loadConfiguration(Object configObject) {
        if (configObject == null) {
            throw new IllegalArgumentException("Config object cannot be null");
        }

        Class<?> clazz = configObject.getClass();
        System.out.println("Загрузка конфигурации для: " + clazz.getSimpleName());

        // Обрабатываем все поля класса
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ConfigProperty.class)) {
                processField(configObject, field);
            }
        }
    }

    public void loadDefaultConfiguration(Object configObject) {
        if (configObject == null) {
            throw new IllegalArgumentException("Config object cannot be null");
        }

        Class<?> clazz = configObject.getClass();
        System.out.println("Загрузка конфигурации для: " + clazz.getSimpleName());

        // Обрабатываем все поля класса
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ConfigProperty.class)) {
                processFieldFromDefault(configObject, field);
            }
        }
    }

    // Обрабатывает одно аннотированное поле
    private void processField(Object configObject, Field field) {
        try {
            ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);

            String configFile = PATH + annotation.configFileName();
            String propertyName = getPropertyName(annotation, configObject.getClass(), field);
            Properties properties = loadProperties(configFile);

            // Получаем значение из свойств
            String stringValue = properties.getProperty(propertyName);

            if (stringValue != null) {
                // Преобразуем значение в нужный тип
                Object value = convertValue(stringValue, field.getType(), annotation.type());

                // Устанавливаем значение в поле
                field.setAccessible(true);
                field.set(configObject, value);

                System.out.printf("\tЗагружено: %s = %s%n", propertyName, value);
            } else {
                System.out.printf("\tСвойство не найдено: %s%n", propertyName);
            }

        } catch (Exception e) {
            System.err.printf("\tОшибка обработки поля %s: %s%n",
                    field.getName(), e.getMessage());
        }
    }

    private void processFieldFromDefault(Object configObject, Field field) {
        try {
            ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);

            String configFile = PATH + "default.properties";
            String propertyName = getPropertyName(annotation, configObject.getClass(), field);
            Properties properties = loadProperties(configFile);

            // Получаем значение из свойств
            String stringValue = properties.getProperty(propertyName);

            if (stringValue != null) {
                // Преобразуем значение в нужный тип
                Object value = convertValue(stringValue, field.getType(), annotation.type());

                // Устанавливаем значение в поле
                field.setAccessible(true);
                field.set(configObject, value);

                System.out.printf("\tЗагружено: %s = %s%n", propertyName, value);
            } else {
                System.out.printf("\tСвойство не найдено: %s%n", propertyName);
            }

        } catch (Exception e) {
            System.err.printf("\tОшибка обработки поля %s: %s%n",
                    field.getName(), e.getMessage());
        }
    }

    // Определяет имя свойства для поиска в конфиг-файле
    private String getPropertyName(ConfigProperty annotation, Class<?> clazz, Field field) {
        String propertyName = annotation.propertyName();

        // Если имя не указано, используем ИМЯ_КЛАССА.ИМЯ_ПОЛЯ
        if (propertyName.isEmpty()) {
            propertyName = clazz.getSimpleName().toUpperCase() + "." + field.getName().toUpperCase();
        }

        return propertyName;
    }

    // Преобразует строковое значение в нужный тип
    private Object convertValue(String stringValue, Class<?> targetType, PropertyType annotationType) {
        // Если указан конкретный тип в аннотации
        if (annotationType != PropertyType.AUTO) {
            return convertByAnnotationType(stringValue, annotationType);
        }

        // Автоматическое определение типа поля
        return convertByFieldType(stringValue, targetType);
    }

    // Преобразование по типу из аннотации
    private Object convertByAnnotationType(String value, PropertyType type) {
        return switch (type) {
            case INTEGER -> Integer.parseInt(value);
            case LONG -> Long.parseLong(value);
            case DOUBLE -> Double.parseDouble(value);
            case BOOLEAN -> parseBoolean(value);
            case STRING_ARRAY -> value.split("\\s*,\\s*");
            case LIST -> Arrays.stream(value.split("\\s*,\\s*"))
                    .collect(Collectors.toList());
            case SET -> Arrays.stream(value.split("\\s*,\\s*"))
                    .collect(Collectors.toSet());
            default -> value;
        };
    }

    // Преобразование по типу поля
    private Object convertByFieldType(String value, Class<?> fieldType) {
        // Проверяем примитивные типы и их обертки
        if (fieldType == String.class) {
            return value;
        }
        if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(value);
        }
        if (fieldType == long.class || fieldType == Long.class) {
            return Long.parseLong(value);
        }
        if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(value);
        }
        if (fieldType == float.class || fieldType == Float.class) {
            return Float.parseFloat(value);
        }
        if (fieldType == boolean.class || fieldType == Boolean.class) {
            return parseBoolean(value);
        }
        if (fieldType == String[].class) {
            return value.split("\\s*,\\s*");
        }
        if (fieldType == List.class) {
            return Arrays.stream(value.split("\\s*,\\s*"))
                    .collect(Collectors.toList());
        }
        if (fieldType == Set.class) {
            return Arrays.stream(value.split("\\s*,\\s*"))
                    .collect(Collectors.toSet());
        }

        // Если тип неизвестен, возвращаем строку
        return value;
    }

    // Парсинг булевых значений с поддержкой разных форматов
    private boolean parseBoolean(String value) {
        if (value == null) return false;

        String lowerValue = value.toLowerCase().trim();
        return lowerValue.equals("true") ||
                lowerValue.equals("yes") ||
                lowerValue.equals("1") ||
                lowerValue.equals("on");
    }

    // Загружает свойства из файла
    private Properties loadProperties(String filename) throws IOException {
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                File file = new File(filename);
                if (file.exists()) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        properties.load(fis);
                    }
                } else {
                    System.err.println("Файл конфигурации не найден: " + filename);
                }
            } else {
                properties.load(input);
            }
        }

        return properties;
    }
}