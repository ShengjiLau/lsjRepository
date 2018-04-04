FROM java:8-jre
ADD ./target/customer-service.jar /app/
CMD ["java", "-Xmx200m","-jar", "/app/customer-service.jar"]
EXPOSE 20881
EXPOSE 52411