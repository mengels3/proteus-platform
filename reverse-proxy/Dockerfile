FROM httpd:latest

#RUN a2enmod mod_proxy
#RUN a2enmod mod_proxy_http

RUN echo "" >> /usr/local/apache2/conf/httpd.conf 
#enable mod_proxy
RUN echo "LoadModule proxy_module modules/mod_proxy.so" >> /usr/local/apache2/conf/httpd.conf
RUN echo "LoadModule proxy_connect_module modules/mod_proxy_connect.so" >> /usr/local/apache2/conf/httpd.conf
RUN echo "LoadModule proxy_http_module modules/mod_proxy_http.so" >> /usr/local/apache2/conf/httpd.conf

RUN echo "IncludeOptional /usr/local/apache2/conf.d/*.conf" >> /usr/local/apache2/conf/httpd.conf

COPY proxy.conf /usr/local/apache2/conf.d/proxy.conf

