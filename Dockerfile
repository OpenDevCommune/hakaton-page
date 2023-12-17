# Используем образ, содержащий и Maven, и JDK
FROM maven:3.8.4-openjdk-17 AS build


# Копируем исходный код приложения и файлы Maven
COPY src /usr/src/hakaton.page/src
COPY pom.xml /usr/src/hakaton.page
COPY .mvn /usr/src/hakaton.page/.mvn
COPY mvnw /usr/src/hakaton.page/mvnw
COPY mvnw.cmd /usr/src/hakaton.page/mvnw.cmd

# Даем права на выполнение для mvnw
RUN chmod +x /usr/src/hakaton.page/mvnw

# Запускаем сборку приложения
WORKDIR /usr/src/hakaton.page
RUN mvn clean package spring-boot:repackage

# Используем базовый образ Java для запуска приложения
FROM openjdk:17-slim

# Копируем собранный jar-файл из предыдущего шага
COPY --from=build /usr/src/hakaton.page/target/*.jar /usr/app/hakaton.page-0.0.1-SNAPSHOT.jar

# Открываем порт 8080
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java","-jar","/usr/app/hakaton.page-0.0.1-SNAPSHOT.jar"]
