FROM postgres

COPY init-sedi-db.sh /docker-entrypoint-initdb.d/
COPY version.txt /init-scripts/
COPY setup_db.sql /init-scripts/
COPY scripts /init-scripts/scripts/


