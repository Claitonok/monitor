# Antes de criar a imagem docker!!

# RODAR ESSE COMANDO NA RAIS DO SEU PROJETO 
# No powershell

# .\mvnw.cmd package -DskipTests
# Esse comando cria a Arquivo jar do seu projeto ou atualiza!!

# http://localhost:8080/swagger-ui/index.html



# ------------------------------------------------#
# CRIAR IMAGEM DO PROJETO
# docker build -t project-monitor .  
# ------------------------------------------------#
# RODAR O CONTEINER DO PROJETO
# docker run -d -p 8080:80 --name conteiner-monitor project-monitor
# ------------------------------------------------#

# Runtime
# FROM eclipse-temurin:21-jre-alpine

# WORKDIR /app

# COPY target/*.jar application.jar
# EXPOSE 8080

# ENTRYPOINT ["java","-jar","application.jar"]


# # Etapa 1 — Build do projeto
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2 — Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar application.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","application.jar"]