# Estágio 1: Build - Usa uma imagem com Maven e JDK para COMPILAR o projeto
# O nome "build" é um apelido para este estágio, que usaremos depois.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Este comando RODA O MAVEN dentro do Docker para criar o arquivo .jar
RUN mvn clean package -DskipTests

# Estágio 2: Run - Usa uma imagem Java leve para RODAR a aplicação
FROM eclipse-temurin:21-jre
WORKDIR /app
# Copia apenas o .jar gerado no Estágio 1 para a imagem final
COPY --from=build /app/target/*.jar app.jar
# Expõe a porta que a aplicação usa
EXPOSE 8080
# Comando para iniciar a aplicação quando o contêiner rodar
ENTRYPOINT ["java", "-jar", "app.jar"]