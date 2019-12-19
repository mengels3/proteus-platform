#!/bin/bash
echo "Running SEDI DB Init"

db_name="sedi"

check_db_exists=$(psql template1 -U postgres -c "SELECT 1 as result FROM pg_database
WHERE datname='sedi'";)

if [[ $check_db_exists == *"1"* ]]; 
  then
    echo "Database already there - running update"
    echo "Database update is done"
  else
    psql template1 -U postgres -c "CREATE DATABASE \"sedi\";"
    psql template1 -U postgres -c "CREATE USER admin WITH PASSWORD 'sedi@uni1';"
    psql template1 -U postgres -c "CREATE USER read WITH PASSWORD 'grafana';"
    psql -U postgres -d sedi -a -f /init-scripts/setup_db.sql
    psql -d sedi -U postgres -c "GRANT SELECT ON ALL TABLES IN SCHEMA public to read;"
    psql -d sedi -U postgres -c "GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public to admin;"

    echo "Database setup is done"
fi
