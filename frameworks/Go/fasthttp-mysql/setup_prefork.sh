#!/bin/bash

sed -i 's|tcp(.*:3306)|tcp('"${DBHOST}"':3306)|g' src/hello/hello.go

fw_depends go

go get -u github.com/go-sql-driver/mysql
go get -u github.com/valyala/fasthttp

rm -f ./hello
go build src/hello/hello.go
./hello -prefork &
