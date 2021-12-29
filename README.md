# exchange-rate-analyser

Данное приложение создано исключительно в некоммерческих целях. 
В рамках создания приложения применялись исключительно библиотеки из стандартной поставки Spring Boot, а также библиотеки для поддержки OpenFeign.
Приложение осуществляет подробное логирование, а также содержит комментарии на английском языке.

## Список URL HTTP-методов

### POST /api/v1/rates

Данная команда оценивает изменение курса валюты по отношению к доллару США и выводит одно из GIF-изображений в зависимости от результата.
Интересующая для сравнения валюта передаётся в параметре "currency" в виде трёхзначного кода валюты.

Примеры запросов:
* /api/v1/rates?currency=EUR — произойдёт оценка курса валюты евро
* /api/v1/rates?currency=RUB — произойдёт оценка курса валюты российский рубль
* /api/v1/rates?currency= — приложение сообщит, что валюта не указана
* /api/v1/rates?currency=XYZ — приложение сообщит, что предоставлена некорректная валюта

### POST /api/v1/check

Метод без параметров. Проверяет работоспособность программы как таковой - на случай если один или несколько сторонних сервисов будут недоступны.

## Настройки приложения
Для корректной работы приложения потребуется установить в качестве переменных окружения, либо указать в файле настройки(например, в: /src/main/java/resources/application.properties) следующие переменные
OpenExchangeRateURL=https://openexchangerates.org //ссылка на основной сайт openexchangerates.org
OpenExchangeRateBASE=USD //определяет базовую валюту для сравнения
OpenExchangeRateID=*ключ* //индвивидуаьный ключ для доступа к API сайта openexchangerates 
GiphyURL=https://api.giphy.com //ссылка на основной сайт giphy
GiphyID=*ключ* //индвивидуаьный ключ для доступа к API сайта giphy
logging.level.com.alpha.exchangerateanalyser=DEBUG // (опционально) устанавливает уровень логирования приложения. В настоящий момент используется только уровень "debug"

## Тесты
К приложению составлен набор тестов, состоящий из:
* 1) тесты самого приложения в целом
* 2) тесты сервиса, отвечающего за взаимодействие с OpenExchangeRates
* 3) тесты сервиса, отвечающего за взаимодействие с Giphy

## Docker
Возможно создание docker-образа, для этого необходимо:
1. В корневой директории (где находится файл "Dockerfile) набрать команду "docker build . --tag exchange-rate-analyser" для создания образа
2. Запустить образ командой "docker run exchange-rate-analyser"

## JAR
Возможен запус приложения без компиляции и иных методов посредством запуска файла exchange-rate-analyser-0.0.1.jar при наличии необходимой среды исполнения.


Ниже приведено описание задания

#### Описание
Создать сервис, который обращается к сервису курсов валют, и отображает gif:
<ul>
<li> если курс по отношению к USD за сегодня стал выше вчерашнего, то отдаем рандомную отсюда https://giphy.com/search/rich
<li> если ниже - отсюда https://giphy.com/search/broke
</ul>

#### Ссылки
<ul>
<li> REST API курсов валют - https://docs.openexchangerates.org/
<li> REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide
</ul>

#### Must Have
<ul>
<li> Сервис на Spring Boot 2 + Java / Kotlin
<li> Запросы приходят на HTTP endpoint (должен быть написан в соответствии с rest conventions), туда передается код валюты по отношению с которой сравнивается USD
<li> Для взаимодействия с внешними сервисами используется Feign 
<li> Все параметры (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в настройки
<li> На сервис написаны тесты (для мока внешних сервисов можно использовать @mockbean или WireMock)
<li> Для сборки должен использоваться Gradle
<li> Результатом выполнения должен быть репо на GitHub с инструкцией по запуску
</ul>

#### Nice to Have
<ul>
• Сборка и запуск Docker контейнера с этим сервисом
</ul>

