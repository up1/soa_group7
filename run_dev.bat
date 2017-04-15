
cd ./post-service
./gradlew build buildDocker

cd ./user-service
mvn package docker:build

cd ./authentication-service
mvn package docker:build

cd ./api-gateway
mvn package docker:build

docker-compose up --build -d

PAUSE 