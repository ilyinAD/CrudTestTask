Загрузить базу данных:\
pg_restore -d crud db.dump

Запустить приложение:\
./gradlew build &&
java -jar .\build\libs\crud-0.0.1-SNAPSHOT.jar

Примеры команд:\
POST http://localhost:8080/category/add?title=MacOs \
POST http://localhost:8080/product/add?title=windows32&article=120b&category_id=60887d11-3a09-4c61-a36b-5dbb144daa40
&price=1&quantity=0 \
PUT http://localhost:8080/product/update?id=c4a9b813-b46b-4cfe-bc80-b0e76104de9c?title=windows-86 \
POST http://localhost:8080/product/delete?id=7db490b0-d461-48fc-92c7-0bf0366bd69d \
GET http://localhost:8080/product/all \
GET http://localhost:8080/product/afb43322-2ea3-47f4-b1ca-4f8b12226964