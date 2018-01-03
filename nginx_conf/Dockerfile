FROM openresty/openresty:centos

ADD ./nginx.conf /usr/local/openresty/nginx/conf/
ADD ./request_access.lua /etc/nginx/lua/
RUN echo "Asia/shanghai" > /etc/timezone;
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai  /etc/localtime
RUN echo -e "127.0.0.1   login.datuodui.com " >> /etc/hosts
RUN echo -e "127.0.0.1   user.datuodui.com " >> /etc/hosts
RUN echo -e "127.0.0.1   customer.datuodui.com " >> /etc/hosts
RUN echo -e "127.0.0.1   items.datuodui.com " >> /etc/hosts
RUN echo -e "127.0.0.1   traffc.datuodui.com " >> /etc/hosts

EXPOSE 80