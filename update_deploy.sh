#!/bin/bash

docker push meranote/api-gateway
docker push meranote/authentication-service
docker push meranote/user-service
docker push meranote/post-service

gcloud container clusters get-credentials shenzhentagram-group-asia --zone asia-east1-b --project shenzhentagram

kubectl delete pods --all