/* Creamos la base de datos */
CREATE DATABASE vuelos;
USE vuelos;

/* Creacion de las tablas para entidades */
CREATE TABLE modelos_avion (
  modelo VARCHAR(45),
  fabricante VARCHAR(45) NOT NULL,
  cabinas INT UNSIGNED NOT NULL,
  cant_asientos INT UNSIGNED NOT NULL,
  
  PRIMARY KEY (modelo)
) ENGINE=InnoDB;

CREATE TABLE clases (
  nombre VARCHAR(45),
  porcentaje DECIMAL(2, 2) UNSIGNED NOT NULL,
  
  PRIMARY KEY (nombre)
) ENGINE=InnoDB;

CREATE TABLE comodidades (
  codigo INT UNSIGNED,
  descripcion TEXT NOT NULL,
  
  PRIMARY KEY (codigo)
) ENGINE=InnoDB;

CREATE TABLE ubicaciones (
  pais VARCHAR(45) NOT NULL,
  estado VARCHAR(45) NOT NULL,
  ciudad VARCHAR(45) NOT NULL,
  huso INT NOT NULL,

  PRIMARY KEY (pais, estado, ciudad)
) ENGINE=InnoDB;

CREATE TABLE aeropuertos (
  nombre VARCHAR(45) NOT NULL,
  codigo VARCHAR(10),
  telefono VARCHAR(45) NOT NULL,
  direccion VARCHAR(45) NOT NULL,
  pais VARCHAR(45) NOT NULL,
  estado VARCHAR(45) NOT NULL,
  ciudad VARCHAR(45) NOT NULL,
  
  PRIMARY KEY (codigo),
  
  FOREIGN KEY (pais,estado, ciudad) REFERENCES ubicaciones(pais,estado, ciudad)
) ENGINE=InnoDB;

CREATE TABLE vuelos_programados (
  numero VARCHAR(10),
  aeropuerto_salida VARCHAR(10) NOT NULL,
  aeropuerto_llegada VARCHAR(10) NOT NULL,
  
  PRIMARY KEY (numero),
  
  FOREIGN KEY (aeropuerto_salida) REFERENCES aeropuertos(codigo),
  FOREIGN KEY (aeropuerto_llegada) REFERENCES aeropuertos(codigo)
) ENGINE=InnoDB;

CREATE TABLE salidas (
  vuelo VARCHAR(10) NOT NULL,
  dia ENUM('do','lu','ma','mi','ju','vi','sa') NOT NULL,
  hora_sale TIME NOT NULL,
  hora_llega TIME NOT NULL,
  modelo_avion VARCHAR(45) NOT NULL,
  
  PRIMARY KEY (vuelo, dia),

  FOREIGN KEY (modelo_avion) REFERENCES modelos_avion(modelo),
  FOREIGN KEY (vuelo) REFERENCES vuelos_programados(numero)
) ENGINE=InnoDB;

CREATE TABLE instancias_vuelo (
  vuelo VARCHAR(10) NOT NULL,
  fecha DATE NOT NULL,
  dia ENUM('do','lu','ma','mi','ju','vi','sa') NOT NULL,
  estado VARCHAR(45),
  
  PRIMARY KEY (vuelo, fecha),
  
  FOREIGN KEY (vuelo, dia) REFERENCES salidas(vuelo, dia)
) ENGINE=InnoDB;

CREATE TABLE pasajeros (
  doc_tipo VARCHAR(3) NOT NULL,
  doc_nro INT UNSIGNED NOT NULL,
  apellido VARCHAR(45) NOT NULL,
  nombre VARCHAR(45) NOT NULL,
  direccion VARCHAR(45) NOT NULL,
  telefono VARCHAR(45) NOT NULL,
  nacionalidad VARCHAR(45) NOT NULL,
  
  PRIMARY KEY (doc_tipo, doc_nro)
) ENGINE=InnoDB;

CREATE TABLE empleados (
  legajo INT UNSIGNED,
  password CHAR(32) NOT NULL,
  doc_tipo VARCHAR(3) NOT NULL,
  doc_nro INT UNSIGNED NOT NULL,
  apellido VARCHAR(45) NOT NULL,
  nombre VARCHAR(45) NOT NULL,
  direccion VARCHAR(45) NOT NULL,
  telefono VARCHAR(45) NOT NULL,
  
  PRIMARY KEY (legajo)
) ENGINE=InnoDB;

CREATE TABLE reservas (
  numero INT UNSIGNED AUTO_INCREMENT NOT NULL,
  fecha DATE NOT NULL,
  vencimiento DATE NOT NULL,
  estado VARCHAR(45) NOT NULL,
  doc_tipo VARCHAR(3) NOT NULL,
  doc_nro INT UNSIGNED NOT NULL,
  legajo INT UNSIGNED NOT NULL,
  
  PRIMARY KEY (numero),
  
  FOREIGN KEY (doc_tipo, doc_nro) REFERENCES pasajeros(doc_tipo, doc_nro),
  FOREIGN KEY (legajo) REFERENCES empleados(legajo)
) ENGINE=InnoDB;

/* Creacion de las tablas para relaciones */
CREATE TABLE brinda (
  vuelo VARCHAR(10) NOT NULL,
  dia ENUM('do','lu','ma','mi','ju','vi','sa') NOT NULL,
  clase VARCHAR(45) NOT NULL,
  precio DECIMAL(7, 2) UNSIGNED NOT NULL,
  cant_asientos INT UNSIGNED NOT NULL,
  
  PRIMARY KEY (vuelo, dia, clase),
  
  FOREIGN KEY (vuelo, dia) REFERENCES salidas(vuelo, dia),
  FOREIGN KEY (clase) REFERENCES clases(nombre)
) ENGINE=InnoDB;

CREATE TABLE posee (
  clase VARCHAR(45) NOT NULL,
  comodidad INT UNSIGNED NOT NULL,
  
  PRIMARY KEY (clase, comodidad),
  
  FOREIGN KEY (clase) REFERENCES clases(nombre),
  FOREIGN KEY (comodidad) REFERENCES comodidades(codigo)
) ENGINE=InnoDB;

CREATE TABLE reserva_vuelo_clase (
  numero INT UNSIGNED NOT NULL,
  vuelo VARCHAR(10) NOT NULL,
  fecha_vuelo DATE NOT NULL,
  clase VARCHAR(45) NOT NULL,
  
  PRIMARY KEY (numero, vuelo, fecha_vuelo),
  
  FOREIGN KEY (numero) REFERENCES reservas(numero),
  FOREIGN KEY (vuelo, fecha_vuelo) REFERENCES instancias_vuelo(vuelo, fecha),
  FOREIGN KEY (clase) REFERENCES clases(nombre)
) ENGINE=InnoDB;

/* Creamos los usuarios correspondientes y les asignamos sus permisos
   Los drop y flush previos a la creacion de los 
   usuarios estan por un bug existente en mysql, donde
   por momentos no permite crear usuarios, incluso si 
   estos aun no existen en la BD */
DROP USER ''@localhost;

DROP USER admin@localhost;
FLUSH PRIVILEGES;
CREATE USER admin@localhost IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON vuelos.* to admin@localhost;

DROP USER empleado@'%';
FLUSH PRIVILEGES;
CREATE USER empleado@'%' IDENTIFIED BY 'empleado';
GRANT SELECT ON vuelos.* to empleado@'%';
GRANT ALL PRIVILEGES ON vuelos.reservas to empleado@'%';
GRANT ALL PRIVILEGES ON vuelos.pasajeros to empleado@'%';
GRANT ALL PRIVILEGES ON vuelos.reserva_vuelo_clase to empleado@'%';

DROP USER cliente@'%';
FLUSH PRIVILEGES;
CREATE USER cliente@'%' IDENTIFIED BY 'cliente';

SELECT	salidas.vuelo, salidas.modelo_avion, salidas.hora_sale, salidas.hora_llega,
		iv.fecha,
		a_salida.codigo as codigo_salida, a_salida.nombre as nombre_salida, a_salida.pais as pais_salida, a_salida.estado as estado_salida, a_salida.ciudad as ciudad_salida,
		a_llegada.codigo as codigo_salida, a_llegada.nombre as nombre_salida, a_llegada.pais as pais_salida, a_llegada.estado as estado_salida, a_llegada.ciudad as ciudad_salida,
		b.cant_asientos + (b.cant_asientos * c.porcentaje)  as asientos_disponibles

FROM instancias_vuelo as iv JOIN salidas 					on iv.vuelo = salidas.vuelo
							JOIN vuelos_programados as vp 	on vp.numero = salidas.vuelo
							JOIN aeropuertos as a_salida	on a_salida.codigo = vp.aeropuerto_salida
							JOIN aeropuertos as a_llegada 	on a_llegada.codigo = vp.aeropuerto_llegada
							JOIN brinda as b on (b.vuelo = salidas.vuelo AND b.dia = salidas.dia)
							JOIN reserva_vuelo_clase as rvc on (rvc.vuelo = salidas.vuelo AND rvc.fecha_vuelo = iv.fecha)
							JOIN clases as c on c.nombre = (select clase 
															from brinda
															where (vuelo = salidas.vuelo AND dia = salidas.dia));

GRANT SELECT ON vuelos_disponibles to cliente@'%';