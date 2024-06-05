FROM bellsoft/liberica-openjdk-alpine:17
EXPOSE 8080
MAINTAINER kunal.dutta
COPY build/libs/petshop-1.0.0.jar /app/opt/petshop-1.0.0.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","opt/petshop-1.0.0.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]