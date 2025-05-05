# 1) Build de la app React
FROM node:18-alpine AS build
WORKDIR /app
COPY package.json yarn.lock ./
RUN yarn install --frozen-lockfile
COPY . .
RUN yarn build

# segunda etapa: nginx
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html

# Reconfigura Nginx para escuchar en el 3000
RUN sed -i 's/listen       80;/listen       3000;/' /etc/nginx/conf.d/default.conf

EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]
