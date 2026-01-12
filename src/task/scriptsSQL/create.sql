CREATE DATABASE IF NOT EXISTS hotel_db;
USE hotel_db;

-- Создание таблицы rooms
CREATE TABLE IF NOT EXISTS rooms (
    id INTEGER PRIMARY KEY,
    number INTEGER,
    cost INTEGER,
    capacity INTEGER,
    stars INTEGER,
    status TEXT,
    update_day INTEGER ,
);

-- Создание таблицы guests
CREATE TABLE IF NOT EXISTS guests (
    name TEXT ,
    move_in_day INTEGER,
    move_out_day INTEGER,
    pay INTEGER,
    room_pay INTEGER,
    id INTEGER,
    room_number INTEGER,

    FOREIGN KEY (id) REFERENCES rooms(id) ON DELETE CASCADE,
);

-- Создание таблицы services
CREATE TABLE IF NOT EXISTS services (
    name TEXT PRIMARY KEY,
    cost INTEGER NOT NULL,
);