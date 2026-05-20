-- =============================================
-- DATOS DE PRUEBA - Futbol JPA Spring
-- =============================================

-- ASOCIACIONES
INSERT INTO asociaciones (nombre, pais, presidente) VALUES
('FCF - Federacion Colombiana de Futbol', 'Colombia', 'Ramon Jesurun'),
('FIFA', 'Suiza', 'Gianni Infantino'),
('CONMEBOL', 'Paraguay', 'Alejandro Dominguez');

-- ENTRENADORES
INSERT INTO entrenadores (nombre, apellido, edad, nacionalidad) VALUES
('Néstor', 'Lorenzo', 58, 'Argentina'),
('Julio', 'Comesana', 72, 'Uruguay'),
('Jorge', 'Almirón', 55, 'Argentina'),
('Alexandre', 'Guimaraes', 62, 'Costa Rica'),
('Hernán', 'Darío Gómez', 66, 'Colombia');

-- CLUBES
INSERT INTO clubes (nombre, ciudad, anio_fundacion, asociacion_id, entrenador_id) VALUES
('Millonarios FC', 'Bogota', 1946, 1, 1),
('Atletico Nacional', 'Medellin', 1947, 1, 2),
('America de Cali', 'Cali', 1927, 1, 3),
('Junior FC', 'Barranquilla', 1924, 1, 4);

-- JUGADORES
INSERT INTO jugadores (nombre, apellido, numero, posicion, club_id) VALUES
('David', 'Mackalister Silva', 10, 'Centrocampista', 1),
('Juan', 'Moreno', 1, 'Portero', 1),
('Andrés', 'Llinás', 4, 'Defensa', 1),
('Leonardo', 'Castro', 9, 'Delantero', 1),
('Jefferson', 'Duque', 11, 'Delantero', 2),
('Andrés', 'Andrade', 8, 'Centrocampista', 2),
('Jhon', 'Mosquera', 3, 'Defensa', 2),
('Joel', 'Graterol', 1, 'Portero', 3),
('Carlos', 'Sierra', 7, 'Delantero', 3),
('Miguel', 'Angel Borja', 9, 'Delantero', 4),
('Didier', 'Moreno', 5, 'Centrocampista', 4);

-- COMPETICIONES
INSERT INTO competiciones (nombre, monto_premio, fecha_inicio, fecha_fin) VALUES
('Liga BetPlay Dimayor', 500000, '2025-01-20', '2025-06-15'),
('Copa Colombia', 200000, '2025-02-10', '2025-08-30'),
('Copa Libertadores', 5000000, '2025-02-04', '2025-11-29'),
('Copa Sudamericana', 2000000, '2025-02-19', '2025-11-22');

-- CLUBES - COMPETICIONES (ManyToMany)
INSERT INTO clubes_competiciones (club_id, competicion_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 1), (2, 2), (2, 3),
(3, 1), (3, 2), (3, 4),
(4, 1), (4, 2), (4, 4);
