FROM httpd:latest

#RUN a2enmod mod_proxy
#RUN a2enmod mod_proxy_http

RUN echo "" >> /usr/local/apache2/conf/apache2.conf 
RUN echo "IncludeOptional /usr/local/apache2/conf.d/*.conf" >> /usr/local/apache2/conf/apache2.conf

COPY proxy.conf /usr/local/apache2/conf.d/proxy.conf

