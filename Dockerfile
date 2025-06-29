# Use imagem oficial OpenJDK 21
FROM eclipse-temurin:21-jdk-jammy

# Cria diretório para a aplicação
WORKDIR /app

# Copia o jar gerado para dentro do container
COPY target/zenith-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
