FROM java:8-jre
ADD ./target/clms-manage-service.jar /app/
RUN echo "Asia/shanghai" > /etc/timezone;
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime
CMD ["java", "-Xmx200m","-jar", "/app/clms-manage-service.jar"]
EXPOSE 20890
EXPOSE 52420