FROM adoptopenjdk/openjdk11:alpine-jre  
ARG JAR_FILE=build/libs/tlv-converter-1.0.0.jar
WORKDIR /opt/app  
COPY ${JAR_FILE} tlv-converter.jar
ENTRYPOINT ["java","-jar","tlv-converter.jar"]
EXPOSE 8280