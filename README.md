Для запуска программы необходимо передать в среду разработки аргументы в следующем формате:
id-quantity discountCard=xxxx balanceDebitCard=xxxx pathToFile=xxxx saveToFile=xxxx (pathToDiscountCardsFile=xxxx - optional)

Либо из консоли:
java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity discountCard=xxxx balanceDebitCard=xxxx pathToFile=xxxx saveToFile=xxxx (pathToDiscountCardsFile=xxxx - optional)

Где: 
* id-quantity — это пары id-quantity товаров (1-2 : id=1, quantitry=2)
* discountCard=XXXX — номер дисконтной карты (4 цифры)
* balanceDebitCard=XXXX — баланс клиента
* pathToFile=XXXX — путь к файлу с товарами
* saveToFile=XXXX — путь к файлу с выводом
* pathToDiscountCardsFile=XXXX — путь к файлу с дисконтными картами (опционально)

Реализовано два логгера которые выводят сообщения о работе приложения в консоль и так же ошибки в файл. Реализованы обработчики ошибок и проверка вводимых данных.
Реализованы абстракции для чтения данных благодаря которым можно менять источник данных (файл, фэйковые данные, база данных и т.д.)
Для вывода ошибок и результатов используются пути из пользовательского ввода (./)
