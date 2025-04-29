# manufactory-knowledge-management-system
This system is made to solve business issue for one manufactory. It is also has intent behind it to scale up to a product to fulfill needs of organisation which shares the same issue. 

# Deployment
```
$ docker-compose up --build
```

Generate OpenAPI skeleton for APIs
```
$ docker-compose run --rm openapi-generator-backend
$ docker-compose run --rm openapi-generator-web 

```

Start web client:
```
$ npm start
```

# Development

## Web client

### Setup
```
$ sudo apt install nodejs
$ sudo apt install npm
$ node -v
$ npm -v
$ npm install
```

## Common

### Setup
```
```

## Database

Manual operations with database:
```
$ docker exec -it postgres-db /bin/sh
$ psql -U myuser -d mydb
$ \d
```

## Cleanup
```
docker system prune -a --volumes -f
```
