# 📦 orders-applications

**orders-applications** es una API REST construida con Spring Boot para simular un sistema de procesamiento de pedidos de alta concurrencia. Esta aplicación puede manejar múltiples solicitudes concurrentes de manera eficiente, simulando operaciones reales como validación, cálculo y almacenamiento de pedidos.

---

## 🛠️ Tecnologías y herramientas utilizadas

| Tecnología        | Versión                                                 |
|------------------|---------------------------------------------------------|
| Java             | 17                                                      |
| Spring Boot      | 3.5+                                                    |
| Maven            | ✅                                                       |
| JUnit 5          | ✅                                                       |
| Mockito          | ✅                                                       |
| Awaitility       | ✅                                                       |
| Swagger (OpenAPI)| ✅ (`springdoc`)                                         |
| Spring Actuator  | ✅                                                       |
| Liquibase        | ✅ (opcional para BD)                                    |
| Git + GitHub     | https://github.com/japarejodiaz/orders-applications.git |

---

## 📋 Requisitos Previos

- JDK 17
- Maven 3.x
- (Opcional) MySQL 8.x si deseas persistir los datos

---

## 🚀 Ejecución del Proyecto

```bash
# Clonar el repositorio
git clone https://github.com/japarejodiaz/orders-applications.git
cd orders-applications

# Compilar y ejecutar
mvn clean spring-boot:run
```

Accede a:
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Actuator: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

---

## 📌 Endpoints principales

### POST `/api/orders/process`

Procesa un pedido de forma asíncrona.

#### JSON de entrada:

```json
{
  "orderId": "ORD-001",
  "customerId": "CUST-001",
  "orderAmount": 250.00,
  "orderItems": [
    {
      "itemId": "PROD-001",
      "quantity": 2,
      "price": 125.00
    }
  ]
}
```

#### Respuesta:
```json
{
  "message": "Order received and is being processed asynchronously"
}
```

---

## 🧪 Pruebas

### 📁 `src/test/java`

Contiene:

- `OrderServiceTest`: Prueba la lógica de procesamiento asíncrono con `CompletableFuture` y `Awaitility`.
- `OrderControllerTest`: Pruebas de integración con `@WebMvcTest` y `MockMvc`.

```bash
# Ejecutar las pruebas
mvn test
```

Las pruebas simulan múltiples pedidos concurrentes y verifican que todos sean almacenados correctamente sin condiciones de carrera.

---

## ⚙️ Concurrencia y rendimiento

- Se usa `ThreadPoolExecutor` configurado para manejar múltiples solicitudes.
- La lógica de negocio simula un retardo aleatorio entre 100ms y 500ms usando `Thread.sleep`.
- Los pedidos son almacenados en un `ConcurrentHashMap` para evitar problemas de concurrencia.
- El sistema fue validado con cargas de hasta 1000 solicitudes concurrentes usando pruebas con **JMeter**.

---

## 🔒 Seguridad

No se ha incluido seguridad por token en esta versión (puede añadirse con Spring Security y JWT si se requiere).

---

## 🗂️ Estructura del proyecto

```
orders-applications/
├── src/
│   ├── main/
│   │   ├── java/com/example/order/
│   │   │   ├── controller/
│   │   │   ├── dtos/
│   │   │   ├── model/
│   │   │   ├── service/
│   │   │       ├── impl/
│   │   └── resources/
│   │       ├── application.properties
│   │       
│   └── test/
│       └── java/com/org/orders/ordersapplications
│           ├── controller/
│           │   └── OrderControllerTest.java
│           └── service.impl/
│               └── OrderServiceTest.java
├── pom.xml
└── README.md
```

---

## 🧪 Simulación sin base de datos

Si no deseas usar base de datos:
- Todos los pedidos se almacenan en memoria (`ConcurrentHashMap`)
- Se puede acceder a ellos usando métodos internos de servicio
- Útil para pruebas de rendimiento puras y simulaciones

---

## 🧪 Simulación con JMeter

Si deseas probar 1000 solicitudes simultáneas:
- Puedes usar el archivo `orders-jmeter.jmx` incluido
- También puede generarse a pedido
- Mide tiempo promedio, throughput y tasa de error

---

## 🧾 Comandos Git usados

```bash
git init
git remote add origin https://github.com/japarejodiaz/orders-applications.git
git add .
git commit -m "Initial commit with base order processing API"
git push -u origin master
```