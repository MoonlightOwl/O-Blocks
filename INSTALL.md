Установка
---------
1. Клонируем репозиторий: `git clone https://github.com/MoonlightOwl/O-Blocks`.
2. Переходим в директорию: `cd O-Blocks/`.
3. Создаём директорию выхода: `mkdir build/`.
4. Сохраняем пути к исходникам: `find -name "*.java" > sources`.
5. Компилируем: `javac -verbose -cp lib -d build @sources`.
6. Копируем ресурсы: `cp src/resources build`.
7. Вытаскиваем ресурсы в корень: `cp build/resources/* build`.
8. Распаковываем либу: `unzip lib/json-simple-1.1.1.jar -d build`.
9. Удаляем `META-INF`: `rm -r build/META-INF`.
10. Собираем JAR: `jar -cvfm o-blocks.jar src/META-INF/MANIFEST.MF -C build/ .`.
11. Запускаем: `java -jar o-blocks.jar`.
