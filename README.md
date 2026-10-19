# Сайт-визитка — Александр Долгошеев

Spring Boot 3 + Java 21. Бэкенд отдаёт профиль через REST и принимает сообщения из формы обратной связи,
фронтенд — статические HTML/CSS/JS без сборки.

## Запуск

Нужен JDK 21. Maven ставить не обязательно — есть wrapper.

```bash
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```

Откройте http://localhost:8080. Порт меняется переменной `PORT`.

Сборка jar и тесты:

```bash
./mvnw test
./mvnw package && java -jar target/business-card-0.1.0.jar
```

## Как поменять содержимое

Все тексты лежат в [`src/main/resources/profile.yml`](src/main/resources/profile.yml):
имя, «обо мне», контакты, стек, проекты, обучение. Пустые поля (`location`, `period`) на странице скрываются.
Иконки контактов есть для `email`, `github`, `telegram`, `linkedin`, для остальных — общая «ссылка».

## API

| Метод | Путь | Что делает |
|-------|------|------------|
| GET | `/api/profile` | данные визитки |
| POST | `/api/contact` | сохранить сообщение `{name, email, message}`; ошибки валидации — `400` в формате ProblemDetail с полем `errors` |
| GET | `/actuator/health` | проверка живости |

## Сообщения из формы

Сохраняются в файловую H2 (`./data/`) и пишутся в лог. Посмотреть: http://localhost:8080/h2-console,
JDBC URL `jdbc:h2:file:./data/business-card`, пользователь `sa`, пароль пустой:

```sql
SELECT * FROM contact_messages ORDER BY created_at DESC;
```

Перед выкладкой в интернет консоль H2 нужно выключить (`H2_CONSOLE=false`) — она не защищена паролем.

## Структура

```
src/main/java/ru/dolgosheev/card
├── profile/   Profile (record из profile.yml) и ProfileController
├── contact/   сущность, репозиторий, сервис и контроллер формы
└── common/    GlobalExceptionHandler → ProblemDetail
src/main/resources
├── application.yml
├── profile.yml
└── static/    index.html, styles.css, app.js
```
