FROM java:8-jre
ADD ./target/sso-service.jar /app/
ENV login.callback http://user.datuodui.com:52411
CMD ["java", "-Xmx200m","-Ddubbo.protocol.port=20883" ,"-jar", "/app/sso-service.jar"]
EXPOSE 52412
EXPOSE 20883