FROM java:8-jre
ADD ./target/pay-service.jar /app/
RUN echo "Asia/shanghai" > /etc/timezone;
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime
CMD ["java", "-Xmx200m","-jar", "/app/pay-service.jar"]
EXPOSE 20881
EXPOSE 52411