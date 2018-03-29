FROM java:8-jre
ADD ./target/contract-service.jar /app/
RUN echo "Asia/shanghai" > /etc/timezone;
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime
CMD ["java", "-Xmx200m","-jar", "/app/contract-service.jar"]
EXPOSE 20881
EXPOSE 52411