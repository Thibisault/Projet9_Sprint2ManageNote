#!/bin/bash

mysql --user=root --password=thibault -h mysql-note -e 'create database projet9sprint2managenote;'

java -jar /home/Sprint2ManageNote-0.0.1-SNAPSHOT.jar

