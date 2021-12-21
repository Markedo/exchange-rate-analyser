#### Описание
Создать сервис, который обращается к сервису курсов валют, и отображает gif:
<ul>
<li> если курс по отношению к USD за сегодня стал выше вчерашнего, то отдаем рандомную отсюда https://giphy.com/search/rich
<li> если ниже - отсюда https://giphy.com/search/broke
</ul>
<br>

#### Ссылки
<ul>
<li> REST API курсов валют - https://docs.openexchangerates.org/
<li> REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide
</ul>
<br>

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
<br>

####Nice to Have
<ul>
• Сборка и запуск Docker контейнера с этим сервисом
</ul>
<br>