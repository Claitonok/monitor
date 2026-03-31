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
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/*.jar application.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","application.jar"]