FROM java:8-jre
ADD ./target/user-service.jar /app/
RUN echo "Asia/shanghai" > /etc/timezone;
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime
CMD ["java", "-Xmx200m","-jar", "/app/user-service.jar"]
EXPOSE 20881
EXPOSE 52411