# build environment
FROM node:12.2.0-alpine AS build
WORKDIR /app
COPY . .
RUN npm install
RUN npm install react-scripts@latest -g
RUN npm run build:prod

# production environment
FROM node:12.2.0-alpine
COPY --from=build /app/build /app
RUN npm i -g serve
EXPOSE 5000
CMD ["serve", "-l", "5000","-s","app"]