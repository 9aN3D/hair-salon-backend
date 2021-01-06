FROM mongo:4.4

COPY init /docker-entrypoint-initdb.d
RUN chmod 775 /docker-entrypoint-initdb.d/initDb.js

RUN mongod --fork --logpath /var/log/mongodb.log \
    && sleep 1 \
    && mongo admin /docker-entrypoint-initdb.d/initDb.js

RUN ln -snf /usr/share/zoneinfo/Europe/Warsaw /etc/localtime && echo "Europe/Warsaw" > /etc/timezone
