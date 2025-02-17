# Используем официальный образ Maven как базовый
FROM maven:3.9.0-eclipse-temurin-17 AS builder

# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файл pom.xml и загружаем зависимости
COPY pom.xml .
COPY src ./src

# Устанавливаем зависимости и собираем jar-файл
RUN mvn clean package -DskipTests

# Начальной образ для выполнения приложения
FROM eclipse-temurin:17-jre

# Указываем рабочую директорию внутри итогового контейнера
WORKDIR /app

# Копируем jar-файл из образа сборки
COPY --from=builder /app/target/*.jar app.jar

# При запуске контейнера, приложение будет работать
ENTRYPOINT ["java", "-jar", "app.jar"]

# Опционально, если приложение использует определенный порт, вы можете объявить его
EXPOSE 8081
