FROM openresty/openresty:centos

COPY ./nginx_release.conf /usr/local/openresty/nginx/conf/nginx.conf
ADD ./request_access_release.lua /etc/nginx/lua/request_access.lua
COPY ./clms_datuodui.key /usr/local/openresty/nginx/conf/
COPY ./star.datuodui.com_nginx.crt /usr/local/openresty/nginx/conf/
RUN echo "Asia/shanghai" > /etc/timezone;
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime


EXPOSE 80