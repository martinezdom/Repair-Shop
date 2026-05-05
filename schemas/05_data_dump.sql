-- ==============================================
-- Repair-Shop-Backend sample data dump
-- Compatible con MySQL 8+
-- Requiere haber ejecutado previamente:
--   schema_users.sql
--   schema_customers.sql
--   schema_vehicles.sql
--   schema_repairs.sql
-- ==============================================

START TRANSACTION;

-- ----------------
-- USERS (4 mecanicos, incluye usuario demo)
-- ----------------
-- Usuario demo para login:
--   email: demo.mecanico@repairshop.local
--   password: example1234
-- Hash BCrypt valido para "example1234":
--   $2a$10$kmG0PQlIhPycJPTA4w1MPe5nMzbHCxWYmkYm8WQut2aZEhNhkV7Im
INSERT INTO users (username, email, password_hash, role) VALUES
('jlopez', 'j.lopez@repairshop.local', '$2a$10$wQvSxK1F7s4H8mN2rP0yOeL9zYtD3fG6hJ1kL2mN3pQ4rS5tU6vW.', 'MECHANIC'),
('agarcia', 'a.garcia@repairshop.local', '$2a$10$uXrD3kP9mN1bV6cQ2wE4rT8yY5uI7oP3aS6dF9gH2jK4lZ0xC1vB.', 'MECHANIC'),
('mmartin', 'm.martin@repairshop.local', '$2a$10$gH4jK7lP2sD9fQ1wE6rT3yU8iO5pA0zX2cV7bN4mL1kJ6hG9dF3s.', 'MECHANIC'),
('demo.mecanico', 'demo.mecanico@repairshop.local', '$2a$10$kmG0PQlIhPycJPTA4w1MPe5nMzbHCxWYmkYm8WQut2aZEhNhkV7Im', 'MECHANIC');

-- ----------------
-- CUSTOMERS (12)
-- ----------------
INSERT INTO customers (first_name, last_name, email, phone) VALUES
('Carlos', 'Mendoza', 'c.mendoza@mail.com', '600100101'),
('Laura', 'Santos', 'laura.santos@mail.com', '600100102'),
('Pablo', 'Ruiz', 'p.ruiz@mail.com', '600100103'),
('Marta', 'Vega', 'marta.vega@mail.com', '600100104'),
('Diego', 'Ortega', 'diego.ortega@mail.com', '600100105'),
('Ana', 'Navarro', 'ana.navarro@mail.com', '600100106'),
('Javier', 'Iglesias', 'j.iglesias@mail.com', '600100107'),
('Elena', 'Morales', 'elena.morales@mail.com', '600100108'),
('Sergio', 'Prieto', 'sergio.prieto@mail.com', '600100109'),
('Lucia', 'Fuentes', 'lucia.fuentes@mail.com', '600100110'),
('Ricardo', 'Blanco', 'ricardo.blanco@mail.com', '600100111'),
('Patricia', 'Gomez', 'patricia.gomez@mail.com', '600100112');

-- ----------------
-- VEHICLES (12)
-- ----------------
INSERT INTO vehicles (brand, model, license_plate, year, customer_id) VALUES
('Toyota', 'Corolla', '1111KBC', 2018, 1),
('Seat', 'Leon', '2222KBC', 2019, 2),
('Volkswagen', 'Golf', '3333KBC', 2017, 3),
('Renault', 'Clio', '4444KBC', 2016, 4),
('Peugeot', '308', '5555KBC', 2020, 5),
('Ford', 'Focus', '6666KBC', 2015, 6),
('Hyundai', 'i30', '7777KBC', 2021, 7),
('Kia', 'Ceed', '8888KBC', 2019, 8),
('Opel', 'Astra', '9999KBC', 2014, 9),
('Nissan', 'Qashqai', '1212LDF', 2022, 10),
('Skoda', 'Octavia', '2323LDF', 2018, 11),
('Mazda', '3', '3434LDF', 2020, 12);

-- ----------------
-- REPAIRS (28)
-- Estados validos: PENDIENTE, TERMINADO, EN_PROGRESO
-- ----------------
INSERT INTO repairs (description, status, entry_date, exit_date, cost, vehicle_id, mechanic_id) VALUES
('Cambio de aceite y filtro', 'TERMINADO', '2026-03-01 09:10:00', '2026-03-01 12:30:00', 89.90, 1, 1),
('Revision general pre-ITV', 'TERMINADO', '2026-03-02 10:00:00', '2026-03-02 13:45:00', 120.00, 2, 2),
('Sustitucion de pastillas de freno', 'TERMINADO', '2026-03-03 08:40:00', '2026-03-03 14:00:00', 215.50, 3, 1),
('Diagnostico de fallo electrico', 'PENDIENTE', '2026-03-04 11:15:00', NULL, NULL, 4, 4),
('Cambio de bateria', 'TERMINADO', '2026-03-05 09:30:00', '2026-03-05 11:00:00', 145.00, 5, 2),
('Alineacion y equilibrado', 'TERMINADO', '2026-03-06 12:00:00', '2026-03-06 15:00:00', 95.00, 6, 1),
('Sustitucion de embrague', 'PENDIENTE', '2026-03-07 08:20:00', NULL, NULL, 7, 4),
('Cambio de correa de distribucion', 'TERMINADO', '2026-03-08 09:00:00', '2026-03-08 18:30:00', 640.00, 8, 2),
('Limpieza de inyectores', 'TERMINADO', '2026-03-09 10:20:00', '2026-03-09 13:10:00', 180.00, 9, 1),
('Reparacion de aire acondicionado', 'PENDIENTE', '2026-03-10 09:45:00', NULL, NULL, 10, 4),
('Sustitucion de alternador', 'TERMINADO', '2026-03-11 08:50:00', '2026-03-11 16:20:00', 390.00, 11, 2),
('Cambio de amortiguadores delanteros', 'TERMINADO', '2026-03-12 10:10:00', '2026-03-12 17:10:00', 470.00, 12, 1),
('Revision de sistema de frenos', 'TERMINADO', '2026-03-13 09:30:00', '2026-03-13 12:40:00', 130.00, 1, 2),
('Sustitucion de bujias', 'TERMINADO', '2026-03-14 08:10:00', '2026-03-14 10:05:00', 95.00, 2, 4),
('Cambio de neumaticos (4)', 'TERMINADO', '2026-03-15 11:00:00', '2026-03-15 14:20:00', 520.00, 3, 1),
('Reparacion de elevalunas', 'PENDIENTE', '2026-03-16 10:40:00', NULL, NULL, 4, 2),
('Mantenimiento de caja automatica', 'TERMINADO', '2026-03-17 09:00:00', '2026-03-17 17:45:00', 760.00, 5, 3),
('Sustitucion de termostato', 'TERMINADO', '2026-03-18 08:35:00', '2026-03-18 12:00:00', 170.00, 6, 1),
('Reparacion de fuga de aceite', 'TERMINADO', '2026-03-19 10:25:00', '2026-03-19 16:30:00', 310.00, 7, 2),
('Cambio de sensor ABS', 'PENDIENTE', '2026-03-20 11:10:00', NULL, NULL, 8, 4),
('Pulido de faros', 'TERMINADO', '2026-03-21 09:20:00', '2026-03-21 10:50:00', 60.00, 9, 1),
('Revision de suspension trasera', 'TERMINADO', '2026-03-22 12:15:00', '2026-03-22 16:40:00', 280.00, 10, 2),
('Cambio de filtro de combustible', 'TERMINADO', '2026-03-23 08:45:00', '2026-03-23 11:05:00', 110.00, 11, 4),
('Reprogramacion centralita', 'EN_PROGRESO', '2026-03-24 09:30:00', NULL, NULL, 12, 1),
('Sustitucion de radiador', 'PENDIENTE', '2026-03-25 10:55:00', NULL, NULL, 1, 2),
('Cambio de liquido de frenos', 'TERMINADO', '2026-03-26 09:40:00', '2026-03-26 11:15:00', 75.00, 2, 3),
('Comprobacion de ruidos en direccion', 'PENDIENTE', '2026-03-27 11:20:00', NULL, NULL, 3, 1),
('Sustitucion de motor de arranque', 'TERMINADO', '2026-03-28 08:30:00', '2026-03-28 15:10:00', 420.00, 4, 2);

COMMIT;
