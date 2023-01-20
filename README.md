# shopping-mall
Mono repository for the shopping mall application

It has  mainly following components:  

**service-gateway**: This service acts as a single entry point for all the other microservices. Any external client cann access the services in this application only through this gateway, Apart from providing access control, this service can be extended to provide support for rate-limiting, load-balancing, authentication etc.

**service-registry**: This service acts as a regitry to all other microservices so that service discovery becomes easier and do not require to remeber uri for all other services except them.  

**product-warehouse-service**: This services is responsible for any inventory action e.g,.adding//getting/updating any clothes/food items.  

**order-service**: Takes care of managing orders.  


# App Launching Instructions:  
1. Launch mysql instance or use docker image https://hub.docker.com/_/mysql. 
2. Run the following sqls to create tables: db/tables.sql
3. Run the following sqls to insert data: db/food_item_202301191005.sql,  
db/food_item_sale_country_202301191006.sql, db/clothes_item_202301191005.sql,  
db/food_item_sale_country_202301191006.sql.   
4. Update application.properties in **order-service** and **product-warehouse-service** and add mysql connection, and db details 
4. Run the services in the following order:   
service-registry -> launch all other microservices. 

## All services can be run by the below command. 
./gradlew bootrun
