package task_7.Properties;

import java.io.*;
import java.util.Properties;

public class Config {
    private Properties properties;
    private String configFile = "src/task_7/Properties/config.properties";

    public Config() {
        properties = new Properties();
        loadDefaultProperties();
    }

    // Загрузка конфигурации из файла
    public void loadConfig() {
        try (InputStream input = new FileInputStream(configFile)) {
            properties.load(input);
            System.out.println("Конфигурация загружена из " + configFile);
        } catch (IOException e) {
            System.out.println("Файл конфигурации не найден, используются значения по умолчанию");
            loadDefaultProperties();
        }
    }

    // Установка значений по умолчанию
    public void loadDefaultProperties() {
        properties.setProperty("db.guestsHistoryLong", "3");
        properties.setProperty("db.autoLoadSave", "true");
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
