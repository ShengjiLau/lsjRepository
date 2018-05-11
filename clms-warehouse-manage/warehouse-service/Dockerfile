FROM java:8-jre
ADD ./target/warehouse-service.jar /app/
RUN echo "Asia/shanghai" > /etc/timezone;
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime
CMD ["java", "-Xmx200m","-jar", "/app/warehouse-service.jar"]
EXPOSE 20881
EXPOSE 52411