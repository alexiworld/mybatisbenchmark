curl -H "Content-type: application/json" localhost:8080/rest/v1/todo -d @todo1.json
curl localhost:8080/rest/v1/todo/
curl -X PUT -H "Content-type: application/json" localhost:8080/rest/v1/todo/1 -d @todo2.json
curl localhost:8080/rest/v1/todo/1
curl -X DELETE localhost:8080/rest/v1/todo/1