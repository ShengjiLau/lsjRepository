FROM java:8-jre
ADD ./target/traffic-service.jar /app/
RUN echo "Asia/shanghai" > /etc/timezone;
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime
CMD ["java", "-Xmx200m","-jar", "/app/traffic-service.jar"]
EXPOSE 20881
EXPOSE 52413
