package task.DAO;

import task.Hotel.BaseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class DAO<T extends BaseEntity<Long>> {
    private final Class<T> entityClass;
    private final String tableName;


    @SuppressWarnings("unchecked")
    public DAO() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        this.tableName = getTableName();
        System.out.println("✅ Создан DAO для " + entityClass.getSimpleName());
    }

    private String getTableName() {
        String className = entityClass.getSimpleName();
        return className.toLowerCase() + "s"; // users, products
    }

    // Save метод для полей String и Integer/int
    protected boolean save(T entity) throws SQLException {
        // Если есть ID - обновляем
        if (entity.getId() != null) {
            update(entity);
        }

        // Получаем только поля String и Integer
        List<FieldInfo> fields = getSimpleFields();
        List<Object> params = new ArrayList<>();

        // Собираем значения полей
        for (FieldInfo fieldInfo : fields) {
            try {
                Object value = fieldInfo.field.get(entity);
                params.add(value);
            } catch (IllegalAccessException e) {
                throw new SQLException("Ошибка доступа к полю: " + fieldInfo.field.getName(), e);
            }
        }

        // Генерируем SQL
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
        StringBuilder placeholders = new StringBuilder(" VALUES (");

        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) {
                sql.append(", ");
                placeholders.append(", ");
            }
            sql.append(fields.get(i).columnName);
            placeholders.append("?");
        }

        sql.append(")").append(placeholders).append(")");

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {

            // Устанавливаем параметры с правильными типами
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                setSimpleParameter(stmt, i + 1, param);
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Создание записи не удалось");
            }

            // Получаем сгенерированный ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = (Long) generatedKeys.getObject(1);
                    entity.setId(generatedId);
                    System.out.println("✅ Создана запись с ID: " + generatedId);
                    return true;
                } else {
                    throw new SQLException("Не удалось получить сгенерированный ID");
                }
            }
        }
    }

    //сохранение через транзакцию
    public boolean saveTransaction(T entity) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {

            conn.setAutoCommit(false);

            if (save(entity)) {

                conn.commit();
                System.out.println("Транзакция успешно завершена");
                return true;
            } else {
                conn.rollback();
                System.out.println("Транзакция откачена");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Получаем только простые поля (String, Integer, int)
    private List<FieldInfo> getSimpleFields() {
        List<FieldInfo> fields = new ArrayList<>();
        Field[] allFields = entityClass.getDeclaredFields();

        for (Field field : allFields) {
            String fieldName = field.getName();

            // Пропускаем поле id
            if (fieldName.equals("id")) {
                continue;
            }

            Class<?> fieldType = field.getType();

            // Проверяем тип поля
            boolean isSimpleType =
                    fieldType == String.class ||
                            fieldType == Integer.class ||
                            fieldType == int.class ||
                            fieldType == long.class ||
                            fieldType == Boolean.class ||
                            fieldType == boolean.class;

            if (isSimpleType) {
                field.setAccessible(true);
                String columnName = camelToSnake(fieldName);
                fields.add(new FieldInfo(field, columnName));
            }
        }

        return fields;
    }

    // Устанавливаем простой параметр (только String и Integer)
    private void setSimpleParameter(PreparedStatement stmt, int index, Object value) throws SQLException {
        if (value == null) {
            stmt.setNull(index, Types.NULL);
        } else if (value instanceof String) {
            stmt.setString(index, (String) value);
        } else if (value instanceof Integer) {
            stmt.setInt(index, (Integer) value);
        } else if (value instanceof Boolean) {
            stmt.setBoolean(index, (Boolean) value);
        } else if (value instanceof Byte) {
            stmt.setByte(index, (Byte) value);
        } else if (value instanceof Short) {
            stmt.setShort(index, (Short) value);
        } else if (value instanceof Float) {
            stmt.setFloat(index, (Float) value);
        } else if (value instanceof Double) {
            stmt.setDouble(index, (Double) value);
        }
    }

    // Update метод
    public T update(T entity) throws SQLException {
        List<FieldInfo> fields = getSimpleFields();
        List<Object> params = new ArrayList<>();

        // Собираем параметры
        for (FieldInfo fieldInfo : fields) {
            try {
                Object value = fieldInfo.field.get(entity);
                params.add(value);
            } catch (IllegalAccessException e) {
                throw new SQLException("Ошибка доступа к полю: " + fieldInfo.field.getName(), e);
            }
        }

        // Добавляем ID
        params.add(entity.getId());

        // Генерируем SQL
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(fields.get(i).columnName).append(" = ?");
        }
        sql.append(" WHERE id = ?");

        System.out.println("✏️ Выполняю UPDATE: " + sql);

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                setSimpleParameter(stmt, i + 1, params.get(i));
            }

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Обновление не удалось, запись не найдена");
            }

            System.out.println("✅ Обновлено записей: " + affectedRows);
            return entity;
        }
    }

    // Read (по ID)
    public Optional<T> findById(long id) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEntity(rs));
                }
                return Optional.empty();
            }
        }
    }

    // Read (все записи)
    public List<T> findAll() throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        List<T> entities = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs));
            }
        }
        return entities;
    }

    // Delete
    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Clear
    public boolean clearAll() throws SQLException {
        String sql = "TRUNCATE TABLE guests, rooms, services;";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            return stmt.executeUpdate() > 0;
        }
    }

    // Подсчет записей
    public long count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        }
    }

    // Маппинг ResultSet в сущность (только простые поля)
    protected T mapResultSetToEntity(ResultSet rs) throws SQLException {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            Field[] fields = entityClass.getDeclaredFields();

            for (Field field : fields) {
                String fieldName = field.getName();
                String columnName = camelToSnake(fieldName);

                try {
                    Object value = rs.getObject(columnName);
                    field.setAccessible(true);

                    // Устанавливаем значение только если оно не null
                    if (value != null) {
                        field.set(entity, value);
                    }

                } catch (SQLException e) {
                    // Колонка может отсутствовать в таблице
                    System.out.println("⚠️  Колонка '" + columnName + "' не найдена в таблице");
                }
            }

            return entity;
        } catch (Exception e) {
            throw new SQLException("Ошибка маппинга ResultSet в сущность", e);
        }
    }

    // Вспомогательные методы

    private String camelToSnake(String str) {
        return str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    // Вспомогательный класс для информации о поле
    private static class FieldInfo {
        Field field;
        String columnName;

        FieldInfo(Field field, String columnName) {
            this.field = field;
            this.columnName = columnName;
        }
    }
}