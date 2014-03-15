CodigoAndino
============

Este repositorio contiene el documento de diseño de aplicación (SAD), código para el servidor Java EE que corre sobre Glassfish y el simulador de 
aplicación móvil, escrito en Java usando Swing.

## Tag System
Las versiones vX.5 son entregas del SAD, donde X es la entrega menos 1. Ej: v0.5 es el SAD de la entrega 1.

Las versiones vX.0 son entregas del experimento, donde X es el numero de la entrega. Ej: v1.0 es la entrega del primer experimento.

## Integrantes

* Miguel Caldas
* David Mesa
* Juan Sebastian Rolon
* Cristian Sierra

## Experimento 1

El objetivo de este experimento es validar las decisiones de diseño que su equipo de trabajo ha tomado con respecto a los siguientes requerimientos funcionales, no funcionales y restricciones:

### Requerimientos funcionales

* Avance del paciente
* Recordatorios
* Manejo de emergencias

### Requerimientos no funcionales

#### Latencia

* Procesamiento de la información: Se desea que la información de presión arterial e ICM enviada por un paciente sea procesada y presentada en el centro de control en menos de 3 segundos.
* Atención a Emergencias: Si se recibe una medición de presión arterial o ICM anormal se debe estar en capacidad de determinar la gravedad del asunto y enviar al paciente y/o personal médico una alarma en menos de 1.5 segundos.

#### Escalabilidad

El sistema central debe estar en capacidad de registrar hasta 600 notificaciones provenientes de los pacientes en menos 4 segundos hasta por periodos de 30 segundos.

El objetivo de este experimento es determinar la escalabilidad del sistema. Para ello usted deberá utilizar una herramienta para análisis de desempeño y escalabilidad como LoadUI, JUnitPerf, Selenium, JMeter, etc. para simular la ocurrencia de múltiples eventos concurrentemente.

### Restricciones

* Servidor central en JEE
* Dispositivos móviles pueden ser simulados con aplicaciones Java standalone.

### Protocolo sobre REST

La aplicación móvil puede acceder a los métodos de autenticación, envío de información de tensión y envío de IMC a través de la siguiente URL:

`/cliente/movil/nombre_del_metodo`

La respuesta del servidor es siempre un objeto JSON.

#### Envío de info de autenticación

**Nombre del método:** auth

**Parámetros**

* id de Usuario : String
* password de Usuario : String

**Respuesta**

```JSON
	{
	"token" : "token"
	}
```

#### Envío de info de IMC

**Nombre del método:** imc

**Parámetros**

* token de Autenticación : String
* peso del Paciente : double : 0 < peso <= 1000
* (Opcional) altura del Paciente : int : altura = -1 ó 0 < altura <= 300

**Respuesta**

```JSON
	{
	"status" : "ok"
	}
```

ó

```JSON
	{
	"status" : "alert",
	 "consejo" : [linea_1, linea_2, ...]
	}
```

ó

```JSON
	{
	"status" : "error",
	 "mensaje" : "mensaje"
	}
```

#### Envío de info de Tensión Arterial

**Nombre del método:** tension

**Parámetros**

* token : String
* diastole : int : 0 < diastole < 200
* sistole : int : 0 < sistole < 200
* pulso : int : 0 < pulso < 350

**Respuesta**

```JSON
	{
	"status" : "ok"
	}
```

ó

```JSON
	{
	"status" : "alert",
	 "alerta" : "alerta"
	}
```

ó

```JSON
	{
	"status" : "error",
	 "mensaje" : "mensaje"
	}
```

### Snippet útiles

**Cómo acceder a los beans de sesión desde una app. cliente Java?**

Usando JNDI y la interfaz remota que implementa el bean de sesión como sigue:

```java
	try
	{
		Properties env = new Properties();
		env.put("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
		env.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
		env.put("org.omg.CORBA.ORBInitialPort", "3700");
		InitialContext contexto;
		contexto = new InitialContext(env);
		servicio = (IServicioVendedoresMockRemote) contexto.lookup("com.losalpes.servicios.IServicioVendedoresMockRemote");
	} 
	catch (Exception e)
	{
		throw new Exception(e.getMessage());
	}
```

Adicionalmente, en el classpath de su app. cliente Java debe referenciar el jar “gf-client.jar” que se encuentra en su instalación de glassfish (i.e. ${GLASSFISH_HOME}/glassfish/lib/gf-client.jar) en lugar de copiarlo en su proyecto cliente.

Si al invocar un método del bean de sesión, este devuelve un objeto (o una colección de objetos), asegúrese que la clase del objeto implementa Serializable.
