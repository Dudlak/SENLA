@echo off
set DB_USER=postgres
set DB_PASS=0000
set SQL_FILE=create.sql

echo Выполняю %SQL_FILE% в MySQL...
mysql -u %DB_USER% -p%DB_PASS% < %SQL_FILE%

if errorlevel 0 (
    echo Успешно! Таблицы созданы.
) else (
    echo Ошибка!
)

pause