- Для запуска необходимо настроить файлы settings.proprties и mainSetttings.properties. Если файлов нет, то при первом запуске программа автоматически создаст их и заполнит данными по-умолчанию
- В файле settings.proprties нужно указать способ подключения к базе данных
- В файле mainSetttings.properties нужно указать свободные порты для взаимодейтствия сервера и клиента, а так же указать путь видео-файлу, который будет анализироваться. Видео-файл должен быть в формате mp4

- Запуск сервера java -cp <путь_к_компилированным_классам> ru.boost.network.common.businesslogic.controller.MainController
- Запуск клиента java -cp <путь_к_компилированным_классам> ru.boost.network.client.controller.ClientController
