# Stage 1: Build the React app
FROM node:14 AS build
# Устанавливаем рабочую директорию
WORKDIR /app
# Копируем файлы package.json и package-lock.json
COPY package.json ./
COPY package-lock.json ./
# Устанавливаем зависимости
RUN npm install
# Копируем все остальные файлы приложения
COPY . ./
# Сборка приложения
RUN npm run build

# Stage 2: Serve the app with Nginx
FROM nginx:alpine
# Копируем собранные файлы из первого этапа в директорию Nginx
COPY --from=build /app/build /usr/share/nginx/html
# Копируем файл конфигурации Nginx (если у вас есть свой файл конфигурации)
# COPY nginx.conf /etc/nginx/conf.d/default.conf
# Открываем порт 80
EXPOSE 80
# Запускаем Nginx
CMD ["nginx", "-g", "daemon off;"]
