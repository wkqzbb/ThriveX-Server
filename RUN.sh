#!/usr/bin/env bash
set -e
# 检查端口是否设置标准端口(整数且大于或等于80)
if [ -z "${PORT}" ]; then
  echo "ERROR: Please set the PORT environment variable."
  exit 1
fi
if [ -z "${DB_INFO}" ]; then
  echo "ERROR: Please set the DB_INFO environment variable."
  exit 1
fi
if [ -z "${DB_USERNAME}" ]; then
  echo "ERROR: Please set the DB_USERNAME environment variable."
  exit 1
fi
if [ -z "${DB_PASSWORD}" ]; then
  echo "ERROR: Please set the DB_PASSWORD environment variable."
  exit 1
fi
# 信息打印
echo "PORT: ${PORT}"
echo "DB_INFO: ${DB_INFO}"
echo "DB_USERNAME: ${DB_USERNAME}"
#echo "DB_PASSWORD: ${DB_PASSWORD}"
echo "EMAIL_HOST: ${EMAIL_HOST}"
echo "EMAIL_PORT: ${EMAIL_PORT}"
echo "EMAIL_USERNAME: ${EMAIL_USERNAME}"
#echo "EMAIL_PASSWORD: ${EMAIL_PASSWORD}"
## JAVA版本
java -version
## 程序版本
echo "VERSION: ${VERSION}"
cmd="java -jar /server/app.jar --PORT=${PORT} --DB_INFO=${DB_INFO} --DB_USERNAME=${DB_USERNAME} --DB_PASSWORD=${DB_PASSWORD} --EMAIL_HOST=${EMAIL_HOST} --EMAIL_PORT=${EMAIL_PORT} --EMAIL_USERNAME=${EMAIL_USERNAME} --EMAIL_PASSWORD=${EMAIL_PASSWORD}"
echo "Running: $cmd"
eval $cmd