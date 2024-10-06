-- Tabla: Ciudadano
CREATE TABLE Ciudadano (
    idCiudadano INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(45) NOT NULL,
    apellido VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    contrasena VARCHAR(45) NOT NULL
);

-- Tabla: Servicio
CREATE TABLE Servicio (
    idServicio INT AUTO_INCREMENT PRIMARY KEY,
    tipoServicio VARCHAR(45) NOT NULL,
    costo DECIMAL(10, 2) NOT NULL
);

-- Tabla: Administrador
CREATE TABLE Administrador (
    idAdm INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(45) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Tabla: Solicitud
CREATE TABLE Solicitud (
    idSolicitud INT AUTO_INCREMENT PRIMARY KEY,
    fechaRegistro DATE NOT NULL,
    estado ENUM('pendiente', 'en_proceso', 'completada', 'cancelada') NOT NULL,
    descripcion MEDIUMTEXT,
    idCiudadano INT,
    idServicio INT,
    FOREIGN KEY (idCiudadano) REFERENCES Ciudadano(idCiudadano),
    FOREIGN KEY (idServicio) REFERENCES Servicio(idServicio)
);

-- Tabla: Recurso
CREATE TABLE Recurso (
    idRecurso INT AUTO_INCREMENT PRIMARY KEY,
    tipoRecurso ENUM('humano', 'vehículo', 'herramienta') NOT NULL,
    disponibilidad ENUM('disponible', 'ocupado') NOT NULL,
    idSolicitud INT,
    idAdm INT,
    FOREIGN KEY (idSolicitud) REFERENCES Solicitud(idSolicitud),
    FOREIGN KEY (idAdm) REFERENCES Administrador(idAdm)
);

-- Tabla: Solicitud_Recurso
CREATE TABLE Solicitud_Recurso (
    idSolicitud INT,
    idRecurso INT,
    PRIMARY KEY (idSolicitud, idRecurso),
    FOREIGN KEY (idSolicitud) REFERENCES Solicitud(idSolicitud),
    FOREIGN KEY (idRecurso) REFERENCES Recurso(idRecurso)
);

-- Tabla: Notificación
CREATE TABLE Notificacion (
    idNotificacion INT AUTO_INCREMENT PRIMARY KEY,
    mensaje LONGTEXT NOT NULL,
    fechaEnvio DATE NOT NULL,
    idCiudadano INT,
    idSolicitud INT,
    FOREIGN KEY (idCiudadano) REFERENCES Ciudadano(idCiudadano),
    FOREIGN KEY (idSolicitud) REFERENCES Solicitud(idSolicitud)
);

-- Tabla: Feedback 
CREATE TABLE Feedback (
    idFeedback INT AUTO_INCREMENT PRIMARY KEY,
    calificacion TINYINT NOT NULL,
    comentarios MEDIUMTEXT,
    idSolicitud INT,
    FOREIGN KEY (idSolicitud) REFERENCES Solicitud(idSolicitud)
);

-- datos en la tabla Ciudadano
INSERT INTO Ciudadano (nombre, apellido, email, contrasena) VALUES
('Juan', 'Pérez', 'juan.perez@gmail.com', 'juan123'),
('Nicolas', 'García', 'Nico.garcia@gmail.com', 'nico123'),
('Marta', 'Martínez', 'Marta.martinez@gmail.com', 'marta123'),
('Angie', ' Lopez', 'Angie.lopez@gmail.com' , 'angie123');

-- datos en la tabla Servicio
INSERT INTO Servicio (tipoServicio, costo) VALUES
('Recolección de basura', 00),
('Recoleccion de ramas', 00),
('Iluminación pública', 00),
('Reciclado' , 00);

-- datos en la tabla Administrador
INSERT INTO Administrador (nombre, email) VALUES
('Carlos', 'carlos@gmail.com'),
('María', 'maria@gmail.com');

-- datos en la tabla Solicitud
INSERT INTO Solicitud (fechaRegistro, estado, descripcion, idCiudadano, idServicio) VALUES
('2024-10-01', 'pendiente', 'Solicitud de recolección de basura', 1, 1),
('2024-10-02', 'en_proceso', 'Solicitud de poda de árboles', 2, 2),
('2024-10-03', 'completada', 'Solicitud de iluminación pública', 3, 3);

-- datos en la tabla Recurso
INSERT INTO Recurso (tipoRecurso, disponibilidad, idSolicitud, idAdm) VALUES
('Humano', 'disponible', 1, 1),
('Vehículo', 'ocupado', 2, 1),
('Herramienta', 'disponible', 3, 2);

-- datos en la tabla Solicitud_Recurso
INSERT INTO Solicitud_Recurso (idSolicitud, idRecurso) VALUES
(1, 1),
(2, 2),
(3, 3);

-- datos en la tabla Notificacion
INSERT INTO Notificacion (mensaje, fechaEnvio, idCiudadano, idSolicitud) VALUES
('Su solicitud ha sido registrada.', '2024-10-01', 1, 1),
('Su solicitud está en proceso.', '2024-10-02', 2, 2),
('Su solicitud ha sido completada.', '2024-10-03', 3, 3);

-- datos en la tabla Feedback
INSERT INTO Feedback (calificacion, comentarios, idSolicitud) VALUES
(5, 'Excelente servicio.', 1),
(4, 'Muy buen trabajo.', 2),
(3, 'Servicio aceptable.', 3);

-- Consultas 

SELECT * FROM Ciudadano;
SELECT * FROM Servicio;
SELECT * FROM Solicitud;
SELECT * FROM Notificacion;
SELECT * FROM Feedback;
SELECT * FROM Recurso WHERE disponibilidad = 'disponible';

-- Borrado de datos

-- Borrar registros de Feedback
DELETE FROM Feedback;

-- Borrar registros de Notificacion
DELETE FROM Notificacion;

-- Borrar registros de Solicitud_Recurso
DELETE FROM Solicitud_Recurso;

-- Borrar registros de Recurso
DELETE FROM Recurso;

-- Borrar registros de Solicitud
DELETE FROM Solicitud;

-- Borrar registros de Administrador
DELETE FROM Administrador;

-- Borrar registros de Servicio
DELETE FROM Servicio;

-- Borrar registros de Ciudadano
DELETE FROM Ciudadano;

