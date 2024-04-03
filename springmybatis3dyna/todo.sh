# or /dynatemplate, /javamapper, /xmlmapper
curl -H "Content-type: application/json" localhost:8080/rest/v1/dynamapper/todo -d @todo1.json
curl localhost:8080/rest/v1/dynamapper/todo/
curl -X PUT -H "Content-type: application/json" localhost:8080/rest/v1/dynamapper/todo/1 -d @todo2.json
curl localhost:8080/rest/v1/dynamapper/todo/1
curl -X DELETE localhost:8080/rest/v1/dynamapper/todo/1