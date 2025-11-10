# Étape 1 : image de base Java (version stable)
FROM openjdk:17-jdk‑slim

# Définir le répertoire de travail dans le container
WORKDIR /app

# Copier le fichier jar généré par Maven dans le container
COPY target/*.jar app.jar

# Exposer le port sur lequel l’application écoute (modifie si différent)
EXPOSE 8080

# Lancer l’application
ENTRYPOINT ["java", "-jar", "app.jar"]
