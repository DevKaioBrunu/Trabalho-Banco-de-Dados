#!/bin/bash

# Verificar a estrutura de diretórios
echo "Verificando estrutura de diretórios..."
ls -la /app
ls -la /app/lib
ls -la /app/src

# Compilar o código Java
echo "Compilando o código Java..."
find /app/src -name "*.java" > sources.txt
echo "/app/Main.java" >> sources.txt
mkdir -p /app/target
javac -d /app/target -cp "$CLASSPATH" @sources.txt

# Executar a aplicação
echo "Executando a aplicação..."
java -cp "$CLASSPATH:/app/target" Main

# Manter o contêiner em execução
# tail -f /dev/null
