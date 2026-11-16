# Техническое задание: Parcel Delivery App (бэкенд сервиса доставки посылок)

Эталонная реализация: https://github.com/mafisto/delivery-app (коммит `8315bf2`).
ТЗ составлено по коду, конфигурации и README эталона, а также по исходному заданию `info/Tasks for candidates Java.pdf`.

Разделы 1–11 описывают систему так, как она устроена в эталоне: выполнив их, получаешь то же самое.
Раздел 12 — недочёты эталона, которые повторять не нужно; каждое исправление помечено как отступление от эталона.

---

## 1. Назначение

Бэкенд веб-приложения доставки посылок. Фронтенда нет: его пишет другая команда, взаимодействие — только через REST API.
Три роли: **пользователь** (заказывает доставку), **курьер** (везёт), **администратор** (управляет заказами и курьерами).

Обязательные свойства решения:

- микросервисная архитектура;
- обмен между сервисами через брокер сообщений;
- единая точка входа (API gateway);
- JWT-аутентификация;
- описание API в Swagger/OpenAPI;
- модульные и интеграционные тесты;
- запуск всего стенда через docker compose;
- подробная инструкция по развёртыванию и сценарий демонстрации.

## 2. Пользовательские сценарии

| Пользователь | Администратор | Курьер |
|---|---|---|
| Регистрируется и входит (JWT) | Входит (JWT) | Входит (JWT) |
| Создаёт заказ на доставку | Видит все заказы | Видит заказы, назначенные ему |
| Меняет пункт назначения заказа | Меняет статус любого заказа | Меняет статус своего заказа |
| Отменяет заказ | Назначает заказ курьеру | Видит детали своего заказа |
| Видит детали своего заказа | Создаёт учётную запись курьера | Передаёт текущие координаты заказа |
| Видит все свои заказы | Отслеживает заказ по координатам (текущая точка и весь путь) | |
| | Видит список курьеров с их статусами | |

Правила, которые задание оставляло на усмотрение исполнителя, в эталоне решены так:

- **Отмена заказа пользователем** — только пока заказ в статусе `Created`.
- **Смена пункта назначения** — без ограничений по статусу (см. п. 12.5).
- **Статусы курьера** — `Available`, `In Transit`, `Coffee Break`; новый курьер создаётся в `Available`.

## 3. Архитектура

### 3.1. Сервисы приложения

| Сервис | Порт | Роль |
|---|---|---|
| `auth-server` | 9500 | Сервер авторизации OAuth2: выдаёт JWT, публикует ключи (JWKS) |
| `orders-service` | 8081 | Единственный **писатель** данных заказов. REST API не имеет, только слушает Kafka |
| `users-service` | 8082 | REST API пользователя |
| `couriers-service` | 8083 | REST API курьера |
| `admin-service` | 8084 | REST API администратора |

### 3.2. Принцип разделения чтения и записи

- Сервисы `users`, `couriers`, `admin` **читают** PostgreSQL напрямую и отдают данные синхронно.
- Любое **изменение заказа** они не выполняют сами: проверяют предусловия, публикуют команду в Kafka и сразу отвечают клиенту.
- `orders-service` потребляет команды и в транзакции меняет БД.
- Следствие: изменения применяются асинхронно. Ответ `200/201` означает «команда принята», а не «изменение записано»; повторное чтение сразу после записи может вернуть старое состояние.
- Исключение: регистрация пользователя (`sign-up`) пишется в БД синхронно самим `users-service`.

### 3.3. Схема взаимодействия

```
клиент ─► nginx:80 ─┬─► users-service ───┐
                    ├─► couriers-service ├─(команды)─► Kafka ─► orders-service ─► PostgreSQL
                    └─► admin-service ───┘                                    └─► MongoDB (путь)
        users/couriers/admin ─(sign-in, Feign)─► auth-server ─► PostgreSQL (persons)
        users/couriers/admin ─(чтение)─► PostgreSQL;  admin ─► MongoDB (путь), Redis (кеш)
        все сервисы ─► Graylog (GELF UDP);  Prometheus ─► /actuator/prometheus ─► Grafana
```

### 3.4. Хранилища

- **PostgreSQL** — одна общая база `farel` на все сервисы (учётные записи, заказы, справочники).
- **MongoDB** — коллекция `order_path`: полный пройденный путь заказа.
- **Redis** — кеш чтения заказов в `admin-service`.

## 4. Технологический стек

| Назначение | Технология (версия в эталоне) |
|---|---|
| Язык, сборка | Java 17 (образ JRE 21), Maven; отдельный `pom.xml` на сервис |
| Фреймворк | Spring Boot 3.2.2: Web, Data JPA, Security, Actuator |
| Авторизация | Spring Authorization Server; в сервисах — OAuth2 Resource Server |
| Межсервисный HTTP | Spring Cloud OpenFeign (только запрос токена) |
| Брокер | Apache Kafka + Zookeeper, spring-kafka; Kafdrop для просмотра |
| БД | PostgreSQL 16.1, MongoDB 7.0.5, Redis (клиент Jedis) |
| Миграции | Liquibase 4.25 отдельным контейнером, расширение liquibase-mongodb |
| Gateway | nginx |
| Документация API | springdoc-openapi (Swagger UI) |
| Метрики | Micrometer + Prometheus + Grafana |
| Логи | Logback + GELF-аппендер → Graylog 5.0 + Elasticsearch 7.10.1 |
| Тесты | JUnit 5, Mockito, Testcontainers (PostgreSQL, Kafka, MongoDB, Redis) |
| Прочее | Lombok, spring-retry |

## 5. Модель данных

### 5.1. PostgreSQL

| Таблица | Поля | Примечание |
|---|---|---|
| `roles` | `id`, `role` | Значения: `admin`, `user`, `courier` |
| `persons` | `id`, `username`, `password`, `email`, `active`, `mobile_number`, `role_id → roles` | Общая учётная запись для всех ролей |
| `admins` | `id → persons`, `superadmin` | Расширение `persons` |
| `users` | `id → persons`, `vip` | Расширение `persons` |
| `couriers` | `id → persons`, `courier_status_id → courier_statuses` | Расширение `persons` |
| `courier_statuses` | `id`, `status` | `Available`, `In Transit`, `Coffee Break` |
| `parcels` | `id`, `user_id → users`, `item_name` | Посылка; через неё заказ связан с владельцем |
| `order_statuses` | `id`, `status` | `Created`, `Processing`, `In Transit`, `Delivered`, `Cancelled` |
| `order_status_history` | `id`, `order_id`, `agent_id → persons`, `status_id → order_statuses`, `change_time` | Текущий статус заказа и кто его выставил |
| `order_location` | `id`, `start_location POINT`, `finish_location POINT`, `current_location POINT`, `coordinates POINT[]` | Координаты заказа |
| `orders` | `id`, `parcel_id`, `courier_id`, `order_status_history_id`, `order_location_id`, `created_time`, `start_time`, `finish_time` | Заказ |

- Наследование учётных записей — JPA `JOINED`: `admins`/`users`/`couriers` делят первичный ключ с `persons`.
- Тип `POINT` маппится пользовательским Hibernate-типом (`PGpoint`).
- Hibernate схему не создаёт (`ddl-auto: none`) — только Liquibase.

### 5.2. MongoDB, коллекция `order_path`

```json
{ "order_id": 1, "coordinates": [[1.0, 11.0], [2.0, 22.0]] }
```

- Создаётся Liquibase с JSON-schema-валидатором (`validationAction: warn`) и уникальным индексом по `order_id`.
- Каждая новая точка добавляется в конец массива (`$push`, upsert).

### 5.3. Redis (`admin-service`)

- Список всех заказов — одно значение; заказ по id — хеш.
- TTL обоих ключей — 15 секунд. Значения сериализуются в JSON.
- Логика: есть в кеше → вернуть; нет → прочитать из PostgreSQL и положить в кеш.

## 6. Аутентификация и авторизация

1. Клиент вызывает `POST /api/v1/auth/sign-in` своего сервиса с `username`/`password`.
2. Сервис проверяет, что учётная запись с таким именем есть **в его роли** (пользователь не войдёт через `admin-service`); иначе `401`.
3. Сервис через Feign запрашивает токен у `auth-server`: `POST {issuer}/oauth2/token`, `application/x-www-form-urlencoded`, `grant_type=client_credentials`, `client_id=username`, `client_secret=password`.
4. `auth-server` находит клиента в таблице `persons` по `username` (своя реализация `RegisteredClientRepository`), scope = роль. Ошибка Feign → `401`.
5. Ответ клиенту: `{ "access_token", "token_type": "Bearer", "expires_in" }`. Время жизни access-токена — 60 минут. Подпись — RSA 2048, ключ генерируется при старте `auth-server`.
6. Защищённые запросы идут с `Authorization: Bearer <token>`. Сервис проверяет подпись по `issuer-uri`, берёт `sub`, загружает учётную запись из БД, выдаёт authority = имя роли и кладёт id учётной записи в контекст запроса.
7. Доступ:
   - без токена: `/api/v1/auth/**`, `/swagger-ui/**`, `/v3/api-docs/**`, `/actuator/**`;
   - всё остальное — только с ролью сервиса (`user` / `courier` / `admin`).
8. `orders-service` и `auth-server` защиты на HTTP-уровне не имеют (внутренние).

## 7. REST API

Базовый префикс всех сервисов — `/api/v1`. Координата — объект `{ "x": double, "y": double }`.

**Объект заказа (`OrderDto`)**: `id`, `createdTime`, `startTime`, `finishTime`, `courierName`, `courierMobileNumber`, `status`, `startLocation`, `finishLocation`, `currentLocation`, `parcelName`.

### 7.1. users-service (роль `user`)

| Метод и путь | Тело | Поведение | Ответ |
|---|---|---|---|
| `POST /auth/sign-up` | `username` (5–50), `email`, `password` (≤255), `mobilenumber` (≤15) | Создаёт пользователя: роль `user`, `active=true`, `vip=false` | `201`; имя занято — ошибка |
| `POST /auth/sign-in` | `username` (3–50), `password` (5–255) | Выдаёт токен | `200` + токен; `401` |
| `GET /orders` | — | Заказы текущего пользователя | `200` + список |
| `GET /orders/{id}` | — | Заказ, только свой | `200`; `404` |
| `POST /orders` | `itemName` (2–50), `startLocation`, `destination` | Команда «создать заказ» | `201` + текст |
| `PUT /orders/{id}/destination` | координата | Команда «сменить пункт назначения» | `200` + текст |
| `POST /orders/{id}/cancel` | — | Заказ свой и в статусе `Created` → команда «отменить» | `200`; `404` — не найден; `400` — статус не `Created` |

### 7.2. couriers-service (роль `courier`)

| Метод и путь | Тело | Поведение | Ответ |
|---|---|---|---|
| `POST /auth/sign-in` | как выше | Выдаёт токен | `200`; `401` |
| `GET /orders` | — | Заказы, назначенные текущему курьеру | `200` |
| `GET /orders/{id}` | — | Заказ, только назначенный ему | `200`; `404` |
| `PUT /orders/{id}/location` | координата | Заказ назначен ему → команда «сменить текущую точку» | `200`; `404` |
| `PUT /orders/{id}/status` | `{ "status": "DELIVERED" }` | Текущий статус — `Created`, `Processing` или `In Transit` → команда «сменить статус» | `200`; `404`; `400` — заказ уже `Delivered`/`Cancelled` |

### 7.3. admin-service (роль `admin`)

| Метод и путь | Тело | Поведение | Ответ |
|---|---|---|---|
| `POST /auth/sign-in` | как выше | Выдаёт токен | `200`; `401` |
| `GET /couriers` | — | Все курьеры: `userName`, `email`, `mobileNumber`, `active`, `status` | `200` |
| `GET /couriers/{id}` | — | Один курьер | `200`; `404` |
| `POST /couriers` | `username` (5–50), `email`, `password`, `mobilenumber`, `active` | Имя свободно → команда «создать курьера» | `201`; имя занято — ошибка |
| `GET /orders` | — | Все заказы (через кеш Redis) | `200` |
| `GET /orders/{id}` | — | Любой заказ (через кеш Redis) | `200`; `404` |
| `PUT /orders/{id}/status` | `{ "status": "CANCELLED" }` | Команда «сменить статус» на любой, без ограничений | `200`; `404` |
| `PUT /orders/{id}/assign` | `{ "courierId": 4 }` | Заказ и курьер существуют → команда «назначить курьера» | `200`; `400` |
| `GET /orders/{id}/track` | — | `id`, `startLocation`, `finishLocation`, `currentLocation` из PostgreSQL | `200`; `404` |
| `GET /orders/{id}/path` | — | `{ orderId, coordinates: [[x,y],…] }` из MongoDB | `200` |

Значения поля `status` в запросах: `CREATED`, `PROCESSING`, `IN_TRANSIT`, `DELIVERED`, `CANCELLED`.

### 7.4. Формат ошибки

```json
{ "code": "ADMIN-SERVICE-E004", "title": "Заказ не найден", "message": "Такого заказа не существует" }
```

- Код — `<СЕРВИС>-E<номер>`; перечень ошибок хранится в одном enum на сервис (код, заголовок, шаблон сообщения с параметрами).
- Все прикладные исключения наследуются от общего базового, несущего этот enum и аргументы.
- Соответствие: неверные учётные данные / нет учётной записи — `401`; не найдено — `404`; нарушено бизнес-правило — `400`; не удалось отправить сообщение в брокер — `503`.

### 7.5. Swagger

- У каждого из трёх API-сервисов: `/swagger-ui/index.html` и `/v3/api-docs`.
- Описаны схемы всех DTO с примерами, операции сгруппированы тегами, настроена авторизация Bearer-токеном прямо из UI.

## 8. Сообщения Kafka

Формат — JSON-строка (ключ и значение — `String`), сериализация вручную через `ObjectMapper`. Топики создаются автоматически. Потребитель один: `orders-service`, группа `order-1`, `auto-offset-reset: earliest`. У продюсера `acks=1`, повтор отправки — 3 попытки с паузой 100 мс.

| Топик | Отправитель | Сообщение | Действие `orders-service` |
|---|---|---|---|
| `t.order.create` | users | `{ userId, data: { itemName, destination, startLocation } }` | Создаёт `parcels`, `order_location` (текущая точка = начальная), запись статуса `Created` (агент — пользователь), `orders` с `created_time = now` |
| `t.order.change.destination` | users | `{ orderId, data: {x,y} }` | Обновляет `finish_location` |
| `t.order.cancel` | users | `{ userId, orderId }` | Проверяет, что пользователь — владелец; статус `Cancelled`, `finish_time = now` |
| `t.order.change.status` | couriers, admin | `{ personId, orderId, status }` | Отклоняет, если агент имеет роль `user`; `Processing` → `start_time = now`; `Delivered` → `finish_time = now`; записывает статус, агента и время |
| `t.order.change.location` | couriers | `{ userId, orderId, data: {x,y} }` | Обновляет `current_location` в PostgreSQL и дописывает точку в `order_path` в MongoDB |
| `t.order.assign.courier` | admin | `{ agentId, courierId, orderId }` | Проверяет, что агент — `admin`; проставляет `orders.courier_id` |
| `t.order.create.courier` | admin | `{ data: { username, email, password, mobilenumber, active } }` | Создаёт курьера в статусе `Available` |

Все обработчики транзакционные. Имена топиков задаются переменными окружения.

## 9. Инфраструктура и развёртывание

### 9.1. Два compose-файла

**`docker-compose-infra.yaml`** — запускается первым:

| Контейнер | Порт на хосте | Назначение |
|---|---|---|
| postgresql | 5432 | БД `farel` |
| mongodb | 27017 | БД `farel` + отдельная БД и пользователь для Graylog (init-скрипт) |
| redis | 6379 | Кеш |
| liquibase | — | Одноразовый: накатывает миграции MongoDB, затем PostgreSQL, и завершается |
| zookeeper, kafka | 2181; 9092, 29092 | Брокер |
| kafdrop | 9100 | UI очередей |
| prometheus | 9090 | Сбор метрик |
| grafana | 3001 | Дашборды (`admin/admin`) |
| nginx | 80 | Gateway |
| elasticsearch | — | Хранилище логов |
| graylog | 9000, 12201/udp | Логи (`admin/admin`) |

**`docker-compose-services.yaml`** — запускается после полной готовности инфраструктуры: `auth-server` → `orders-service` → `users-service` → `couriers-service` → `admin-service`.

Требования:

- порядок старта задаётся через `depends_on` с условиями `service_healthy` / `service_completed_successfully`;
- у каждого сервиса healthcheck по `/actuator/health`;
- вся конфигурация сервисов — переменными окружения (порт, URL БД, адрес Kafka, имена топиков, issuer JWT, MongoDB, Redis, адрес Graylog);
- образ сервиса — двухэтапная сборка: Maven-сборка без тестов, затем запуск jar на JRE alpine.

Команды:

```
docker-compose -f docker-compose-infra.yaml build --parallel
docker-compose -f docker-compose-services.yaml build --parallel
docker-compose -f docker-compose-infra.yaml up        # дождаться полного старта
docker-compose -f docker-compose-services.yaml up
```

Всего около 18 контейнеров; на машине с 16 ГБ ОЗУ стенд работает на пределе.

### 9.2. Миграции

- Отдельный образ на базе `liquibase/liquibase` с драйверами MongoDB.
- Два changelog'а: `postgres` и `mongo`, каждый с версией `v1.0.0` из двух файлов — `init` (структура) и `data` (демо-данные).

### 9.3. Gateway (nginx)

| Внешний путь | Куда |
|---|---|
| `/users-service/…` | `users-service:8082/…` |
| `/couriers-service/…` | `couriers-service:8083/…` |
| `/admin-service/…` | `admin-service:8084/…` |

Префикс сервиса срезается. `orders-service` и `auth-server` наружу через gateway не публикуются.

### 9.4. Наблюдаемость

- **Метрики**: у каждого сервиса открыты `health` и `prometheus` в Actuator, включены гистограммы `http.server.requests`. Prometheus опрашивает все пять сервисов раз в 5 с. В Grafana автоматически подключаются источник данных и два дашборда: JVM (Micrometer) и пропускная способность Spring.
- **Логи**: Logback с переключаемыми аппендерами (`stdout`, `file`, `roll_file`, `gelf`), набор задаётся переменной окружения. В Graylog сообщения уходят по GELF UDP с именем сервиса и окружением. Для приёма в Graylog вручную создаётся input «GELF UDP».
- **Правило логирования**: каждое сообщение имеет код вида `[ORDER-SERVICE-I002]`, тексты вынесены в класс констант. Входящие команды и ошибки — уровень INFO, детали и стектрейсы — DEBUG.

## 10. Тестирование

- Каждый сервис покрыт тестами уровня сервисов: `AuthService`, `OrdersService`, `UsersService`, `CourierService`.
- Тесты поднимают контекст Spring (`@SpringBootTest`, профиль `test`) на Testcontainers: PostgreSQL с init-скриптом схемы и данных, Kafka, MongoDB, Redis — там, где они нужны.
- Профиль `test` отключает конфигурацию безопасности; текущий пользователь подставляется в контекст вспомогательным классом.
- Клиент `auth-server` в тестах заменяется моком.
- Обязательные проверки: выдача списка и одного заказа совпадает с ожидаемыми данными; чужой или несуществующий заказ — исключение; отмена разрешена в `Created` и запрещена в остальных статусах; смена статуса курьером запрещена для завершённых заказов; повторное создание учётной записи — исключение.

## 11. Демо-данные и сценарий приёмки

**Данные** (пароль у всех — `password`): `admin1`; `user1`; курьеры `courier1`…`courier4` в разных статусах, часть неактивна; три посылки и три заказа пользователя `user1` в статусах `Created`, `Processing`, `In Transit`; три записи пути в MongoDB.

**Приёмка** — пройти через Swagger или Postman:

1. Вход каждой ролью через gateway: `POST http://localhost/{users|couriers|admin}-service/api/v1/auth/sign-in`; неверные данные дают `401`.
2. Пользователь: регистрация нового и отказ при занятом имени; список заказов; создание заказа и его появление в списке; смена пункта назначения видна в `finishLocation`; отмена заказа в `Created` проходит, в другом статусе — `400`.
3. Курьер: список и детали своих заказов; несуществующий id — `404`; смена координат видна в `currentLocation`; смена статуса на `DELIVERED`; повторная смена статуса завершённого заказа — `400`.
4. Администратор: список курьеров; создание курьера и отказ при занятом имени; несуществующий курьер — `404`; все заказы; смена статуса на любой; назначение курьера; `track` и `path` возвращают координаты.
5. Каждое изменение видно сообщением в соответствующем топике Kafdrop.
6. Логи запросов и ошибок видны в Graylog, метрики сервисов — в Grafana.

**Состав поставки**: исходный код, оба compose-файла, миграции, конфигурация nginx/Prometheus/Grafana, README с инструкцией и сценарием демо, диаграмма архитектуры и диаграмма классов.

## 12. Недочёты эталона — не повторять

Всё ниже найдено чтением кода; стенд не запускался.

| № | Что в эталоне | Что требовать вместо этого |
|---|---|---|
| 12.1 | Пароли хранятся и сравниваются открытым текстом (`{noop}`) | Хеш BCrypt при регистрации и создании курьера |
| 12.2 | Вход сделан грантом `client_credentials`: логин и пароль человека выступают как id и секрет OAuth-клиента | Допустимо для учебного стенда; для реального использования — отдельная проверка пароля и выдача токена пользователю |
| 12.3 | RSA-ключ `auth-server` создаётся при старте: после перезапуска все выданные токены недействительны | Ключ из конфигурации или хранилища |
| 12.4 | `order_status_history` хранит одну строку на заказ, которая перезаписывается; поле `order_id` не заполняется — истории нет | Новая строка на каждую смену статуса |
| 12.5 | Смена пункта назначения не проверяет ни владельца заказа, ни статус: любой пользователь может изменить любой заказ | Проверка владельца и разрешённых статусов (например, до `In Transit`) |
| 12.6 | Занятое имя при регистрации и создании курьера возвращает `404` | `409 Conflict` |
| 12.7 | `@NotBlank` стоит на полях типов `double`, `Boolean`, enum и вложенных объектах — эта аннотация применима только к строкам | `@NotNull` для нестроковых полей |
| 12.8 | Сообщения в текстах валидации расходятся с ограничениями (например, «от 5 до 50» при `min = 3`) | Привести в соответствие |
| 12.9 | Ошибки обработчиков Kafka никуда не возвращаются: клиент получил `200`, команда отклонена, узнать об этом нельзя; нет DLQ | Топик ошибок/DLQ и статус обработки команды |
| 12.10 | Назначение курьера не учитывает его статус и флаг `active`; статус курьера никогда не меняется | Назначать только активных в `Available`, менять статус при взятии и завершении заказа |
| 12.11 | Администратор может перевести заказ в любой статус из любого, включая завершённые | Таблица допустимых переходов |
| 12.12 | Кеш Redis не сбрасывается при изменениях — до 15 с администратор видит устаревшие данные | Сброс ключа при обработке команды |
| 12.13 | Все сервисы работают с одной БД и дублируют одни и те же JPA-сущности | Минимум — общий модуль сущностей; в идеале — данные заказов только у `orders-service` |
| 12.14 | В `prometheus.yml` метки сервисов не соответствуют портам (8081 подписан `users-service` и т. д.) | Исправить метки |
| 12.15 | Валидатор коллекции `order_path` описывает `order_id` как строку, а пишется число; индекс включает несуществующее поле `type` | Привести схему к фактическим данным |
| 12.16 | Колонка `order_location.coordinates` не используется (путь хранится в MongoDB) | Удалить |
| 12.17 | `orders-service` и `auth-server` опубликованы на хост без защиты; учётные данные БД и Graylog — в compose-файлах | Не публиковать порты внутренних сервисов, секреты — в `.env` |
| 12.18 | Input Graylog создаётся вручную, без него сервисы сыплют ошибками | Создавать input автоматически (content pack или API при старте) |
