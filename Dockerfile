# Étape 1 : image de base Java
FROM openjdk:21-jdk

# Définir le répertoire de travail
WORKDIR /app

# Copier le jar dans l'image
COPY target/*.jar app.jar

# Exposer le port que ton app utilise
EXPOSE 8080

# Commande pour lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
