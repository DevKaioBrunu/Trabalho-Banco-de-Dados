FROM openjdk:11

WORKDIR /app

# Instalar ferramentas de desenvolvimento necessárias
RUN apt-get update && apt-get install -y \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Adicionar o conector JDBC do MySQL
RUN mkdir -p /app/lib
ADD https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.28/mysql-connector-java-8.0.28.jar /app/lib/mysql-connector-java-8.0.28.jar

# Definir o CLASSPATH para incluir o driver JDBC
ENV CLASSPATH=/app/lib/*:/app/target/*

# Criar diretório para o código-fonte
RUN mkdir -p /app/src /app/target /app/logs

# Script para compilar e executar o projeto
COPY run.sh /app/
RUN chmod +x /app/run.sh

# Definir o ponto de entrada como o script de execução
ENTRYPOINT ["/app/run.sh"]