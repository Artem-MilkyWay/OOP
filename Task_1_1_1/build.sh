#!/bin/bash

# папка для компиляции классов
mkdir -p out

# Компилируем все исходники (Main.java и MainTest.java)
javac -d out -sourcepath src src/Main.java src/MainTest.java

# Генерируем Java-документацию
javadoc -d out/docs -sourcepath src Main

# Создаем JAR-файл
jar cvfe out/Main.jar Main -C out Main.class

# Запускаем JAR-файл
java -jar out/Main.jar
