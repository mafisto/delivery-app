# Инструкция к проекту:

### задание, диаграмма классов и архитектура приложения находятся в папке info


### Используемые технологии:
* java, spring boot, spring data - разработка
* junit, mockito, testcontainers - тестирование
* kafka - брокер сообщений
* kafdrop - ui для просмотра очередей сообщений
* zookeeper - оркестратор сервисов( в данном случае для кафки)
* redis - кеширование
* mongodb - NoSql DB
* postgresql - Sql DB
* liquibase - развертывание скриптов на БД
* nginx - API gateway
* prometheus - сбор метрик с сервисов
* grafana - визуализация метрик
* elasticsearch - Nosql DB. хранилище логов
* graylog - сбор, обработка и управление логами от сервисов
* swagger - документация API
* jwt auth - авторизация с помощью JWT токена


Запускать следует поочередно, дожидаясь выполнения предыдущей команды.

## !!!! важно !!!!
В докере будет крутить около 18 сервисов. 
На машине с 16Гб оперативки всё лагало и отваливались сервисы, запускать на свой страх и риск.
Возможно проблема в самом докере


### -- сборка сервисов --
* docker-compose -f docker-compose-infra.yaml build --parallel
* docker-compose -f docker-compose-services.yaml build --parallel

### -- запуск сервисов--
* docker-compose -f docker-compose-infra.yaml up
* docker-compose -f docker-compose-services.yaml up

Сначала нужно запустить docker-compose-infra со всеми сервисами и дождаться, когда
всё будет развернуто, после чего запускать docker-compose-services.yaml.

Из-за большого количества контейнеров иногда случается глюк, что может в самом начале
отвалиться что-то и дальше не собираться. Поэтому в таком случае нужно просто перезапустить

### -- остановка сервисов --
docker-compose -f docker-compose-infra.yaml down
docker-compose -f docker-compose-services.yaml down

### -- сервисы --

#### Эти сервисы используюся для поставки API:
* http://localhost:8081   -   orders-service - сервис заказов
* http://localhost:8082   -   users-service - сервис пользователей
* http://localhost:8083   -   couriers-service - сервис курьеров
* http://localhost:8084   -   admin-service - сервис админов
* http://localhost:9500   -   auth server - сервис для аутентификации

#### Эти веб страницы для мониторинга за приложением:
* http://localhost:9100  -   kafdrop  - просмотр очередей kafka
* http://localhost:3001   -   grafana admin/admin - метрики сервисов: потребление памяти, цпу, скорость ответа и т.д.
* http://localhost:9000   -   graylog  - admin/admin - коллектор логов

### -- swagger --
* http://localhost:8082/swagger-ui/index.html  -   users-service -  user1/password
* http://localhost:8083/swagger-ui/index.html  -   couriers-service - courier1/password
* http://localhost:8084/swagger-ui/index.html  -   admin-service - admin1/password

### --- nginx ---
#### маппинг сервисов:
это сервисы API которых были спрятаны за API gateway (nginx).

* users-service = http://localhost:8082  -> http://localhost/users-service/
* couriers-service = http://localhost:8083  -> http://localhost/couriers-service/
* admin-service = http://localhost:8084 -> http://localhost/admin-service/

#### тестовые запросы:

Для проверки работоспособности проксирования через API gateway,
можно отправить эти запросы на проверку

* POST http://localhost/users-service/api/v1/auth/sign-in 

  ```
  {
  "username": "user1",
  "password": "password"
  }
  ```

* POST http://localhost/admin-service/api/v1/auth/sign-in

  ```
  {
  "username": "admin1",
  "password": "password"
  }
  ```

* POST http://localhost/couriers-service/api/v1/auth/sign-in

  ```
  {
    "username": "courier1", 
    "password": "password"
   }
  ```

# Демонастрация приложения
зайти в грейлог и создать инпут для просмотра логов, т.к. приложение подключено к нему, то если не будет инпута - будут сыпаться ошибки.
   - http://localhost:9000/
   - admin/admin
   - system-inputs (http://localhost:9000/system/inputs)
   - select input - выбрать gelf udp и создать его.
   - с этого момента логи можно будет видеть на странице http://localhost:9000/search
   - справа вверху нужно иногда включать воспроизведение логов


## Happy Flow:

Для демонстрации работы в базе уже находятся тестовые данные.
Тут будут описаны шаги для показа демо продукта.

### демонстрация работы APIGW
через постман выполнить тестовые запросы к апи

* users-service = http://localhost:8082  -> http://localhost/users-service/
* couriers-service = http://localhost:8083  -> http://localhost/couriers-service/
* admin-service = http://localhost:8084 -> http://localhost/admin-service/

### демонстрация работы kafdrop
показать созданные очереди, рассказать для чего нужно
http://localhost:9100 
 

### демонстрация работы Graylog
продемонстрировать логи в грейлог
http://localhost:9000/ 
 
 ### демонстрация работы Grafana
продемонстрировать логи в грейлог
http://localhost:3001 
 
### courier service
http://localhost:8083/swagger-ui/index.html
имеет следующие эндпоинты:
```
POST /sign-in
GET /orders
GET /orders/{id}
PUT /orders/{id}/location
PUT /orders/{id}/status
```
1. демонстрация возможности логина
   - идем на `POST /sign-in`
   - вводим любые данные для демонстрации ошибки
   - вводим `courier1/password`   
   - получаем в ответе jwt токен
   - авторуземся в сваггере
2. получаем список всех заказов для курьера
   - `GET /orders`
3. получаем конкретный ордер
    - `GET /orders{id}`
    - `id = 1`
4. еще раз пробуем получить заказ, но с неверным айди: `id = 1111`. получится ошибка
5. меняем локацию полученного заказа
   - `PUT /orders/{id}/location`
    - `id=1; x=444, y=888`
    - смотрим, что локация изменилась `GET /orders/{id}`
	- можно продемонстрировать работу kafdrop. `http://localhost:9100`
6. еще раз меняем локацию у ордера, но вводим `id = 1111`, чтобы получилась ошибка для показа в логах
7. меняем статус заказа
    - `PUT /orders/{id}/status`
    - `id=1; status=DELIVERED` 
    - смотрим, что статус изменился `GET /orders/{id}`
10. меняем статус заказа на невалидный. получаем ошибку во время смены закза:
   - `id=1; status=PROCESSING`

### Просмотр обновленной информации в грейлог и графане
* http://localhost:3001 
* http://localhost:9000 

### users service
http://localhost:8082/swagger-ui/index.html
```
POST /sign-in
POST /sign-up
GET /orders
POST /orders
GET /orders/{id}
PUT /orders/{id}/destination
POST /orders/{id}/cancel
```
1. демонстрация возможности логина
   - идем на `POST /sign-in`
   - вводим любые данные для демонстрации ошибки
   - вводим `user1/password`
   - получаем в ответе `jwt` токен
   - авторуземся в сваггере
2. регистрация нового пользователя/проверка на существующего
   - `POST /sign-up`
   - вводим `user1` - получаем ошибку, что пользователь есть
   - регистрируем `user3` - показываем ответ
   - логинимся
   - выполняем любой запрос, чтобы показать, что апи работает и мы авторизованны
   - логаут с `user3`
   - логин `user1`
3. получение списка всех заказов
   - `GET /orders`
4. добавление нового заказа
   - `POST /orders`
   - заполняем новыми данными
   - `itemName=Auto; destination = 111,222; startLocation= 222;333`
   - выполняем `GET /orders` и смотрим на созданный заказ 
5. получение конкретного заказа
   - `GET /orders/{id}`
   - `id = 1`
6. Обновление конечной точки доставки
   - показываем `finishLocation` у заказа
   - `PUT /orders/{id}/destination`
   - `id=1; x=123, y=321`
   - выполняем `GET /orders/{id}; id=1` 
   - показываем новое значение `finishLocation`
7. отмена заказа (только со статусов started)
   - `POST /orders/{id}/cancel`
   - получаем ошибку
   - `id=1`
   - вводим валидный ид
   - `id=4`
   


### admin service
http://localhost:8084/swagger-ui/index.html
```
POST /sign-in
GET /couriers
POST /couriers
GET /couriers/{id}
GET /orders
GET /orders/{id}
PUT /orders/{id}/status
PUT /orders/{id}/assign
GET /orders/{id}/track
GET /orders/{id}/path

```
1. демонстрация возможности логина
   - идем на `POST /sign-in`
   - вводим любые данные для демонстрации ошибки
   - вводим `admin1/password`
   - получаем в ответе `jwt` токен
   - авторуземся в сваггере
2. получаем список всех курьеров
   - `GET /couriers` 
3. создаём нового курьера
   - пытаемся создать уже созданного курьера и получаем ошибку
   - `username = courier4`
   - создаем валидного курьера
   - `username = courier5`
   - получаем список всех курьеров и демонстрируем созданного
   - `GET /couriers`
4. получаем данные о курьере
   - `GET /couriers/{id}`
   - `id = 3 `
5. пробуем получить несуществующего курьера = ошибка
   - `GET /couriers/{id}`
   - `id = 111`
6. получение списка всех заказов
   - `GET /orders`
7. получение конкретного заказа
   - `GET /orders/{id}`
   - `id = 1`
8. меняем статус заказа (на любой)
   - `PUT /orders/{id}/status`
   - `id = 1; status=CANCELLED`
   - получаем заказ и показываем, что статус изменился
   - `GET /orders/{id}`
   - `id = 1`
9. назначаем курьера заказу
   - `PUT /orders/{id}/assign`
   - `id = 1; courierId=4`
   - получение заказа дял показа измененного статуса
   - `GET /orders/{id}`
   - `id = 1`
10. получение последних координаты
   - `GET /orders/{id}/track`
11. получение списка из координат по перемещению заказа
   - `GET /orders/{id}/path`


