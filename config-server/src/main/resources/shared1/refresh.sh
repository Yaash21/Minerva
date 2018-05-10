#!/bin/bash

cd
cd /home/nirjhar/dockerized/config-server/src/main/resources/shared
sudo git add .
sudo git commit -m "1st"
echo "hello"
sudo docker cp . config-server:/usr/project/shared
