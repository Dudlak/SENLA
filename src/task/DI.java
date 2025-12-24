package task;

import task.Annotations.Inject;

import java.lang.reflect.Field;

class DI {
    public static void injectDependencies(Object target) throws Exception {
        // Получаем все поля класса
        Field[] fields = target.getClass().getDeclaredFields();

        for (Field field : fields) {
            // Проверяем, есть ли аннотация @Inject
            if (field.isAnnotationPresent(Inject.class)) {
                // Разрешаем доступ к приватному полю
                field.setAccessible(true);

                // Создаем экземпляр зависимости
                Class<?> fieldType = field.getType();
                Object dependency = fieldType.getDeclaredConstructor().newInstance();

                // Внедряем зависимость в поле
                field.set(target, dependency);
            }
        }
    }
}
