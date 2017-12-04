FROM java:8-jre
ADD ./target/user-service.jar /app/
CMD ["java", "-Xmx200m","-jar", "/app/user-service.jar"]
EXPOSE 20881
EXPOSE 52411