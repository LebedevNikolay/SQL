FROM mysql:latest

ENV MYSQL_RANDOM_ROOT_PASSWORD=yes

ENV MYSQL_DATABASE=deadline

ENV MYSQL_USER=mrtotalsecurity

ENV MYSQL_PASSWORD=CzmGtmRjc3cLGV7KXza294520qCMYXuF

ADD schema.sql /docker-entrypoint-initdb.d/

EXPOSE 3306