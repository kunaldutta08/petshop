../gradlew clean build
docker build -t spring-boot-docker:pet-shop .
docker run -p 8080:8080 spring-boot-docker:pet-shop .