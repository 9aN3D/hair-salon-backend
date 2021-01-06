FROM mongo:4.4

COPY init /docker-entrypoint-initdb.d
RUN chmod 775 /docker-entrypoint-initdb.d/initDb.js

RUN mongod --fork --logpath /var/log/mongodb.log \
    && sleep 1 \
    && mongo admin /docker-entrypoint-initdb.d/initDb.js

RUN ln -snf /usr/share/zoneinfo/Europe/Warsaw /etc/localtime && echo "Europe/Warsaw" > /etc/timezone

RUN echo "rs.initiate({'_id':'rs0','members':[{'_id':0,'host':'127.0.0.1:27017'}]});" > /docker-entrypoint-initdb.d/replica-init.js
RUN cat /docker-entrypoint-initdb.d/replica-init.js
CMD [ "--bind_ip_all", "--replSet", "rs0" ]
