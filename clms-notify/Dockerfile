FROM java:8-jre

ADD ./target/clms-notify.jar /app/
CMD ["java", "-Xmx200m","-Ddubbo.registry.address=192.168.1.131:2181","-jar", "/app/clms-notify.jar"]

EXPOSE 20880