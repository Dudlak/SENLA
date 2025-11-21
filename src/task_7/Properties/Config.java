package task_7.Properties;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class Config {
    private final Properties properties;

    public Config() {
        properties = new Properties();
    }

    // Загрузка конфигурации из файла
    public void loadConfig() {
        loadConfig("config.properties");
    }

    public void loadConfig(String file) {
        file = "src/task_7/Properties/" + file;
        try (InputStream input = new FileInputStream(file)) {
            properties.load(input);
            System.out.println("Конфигурация загружена из " + file);
            for (Map.Entry<Object, Object> set : properties.entrySet()) {
                System.out.println("\t" + set.getKey() + "=" + set.getValue());
            }
        } catch (IOException e) {
            System.out.println("Файл конфигурации не найден, используются значения по умолчанию");
            loadDefaultProperties();
        }
    }

    // Установка значений по умолчанию
    public void loadDefaultProperties() {
        loadConfig("default.properties");
    }

    // Получение строкового значения
    public String getString(String key) {
        return properties.getProperty(key);
    }

    // Получение числового значения
    public int getInt(String key) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Получение boolean значения
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    // Установка значения
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
