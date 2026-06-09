# manufactory-knowledge-management-system
This system is made to solve business issue for one manufactory. It is also has intent behind it to scale up to a product to fulfill needs of organisation which shares the same issue. 

# Deployment

Production only
```
$ docker compose --profile prod up --build web-prod server database
```

Production and dev environment:
```
$ docker compose --profile dev --profile prod up -d
```

Web server API: http://localhost:8080/swagger-ui/index.html
Web client: localhost: 3000

## Known issues

Under VPN docker dependencies and npm packages works unstable 

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

## Generate OpenAPI skeleton for APIs
```
$ docker-compose run --rm openapi-generator-backend
$ docker-compose run --rm openapi-generator-web 

```

## Cleanup
```
$ sudo rm -R /backend/postgres-data
$ docker system prune -a --volumes -f
$ docker-compose down -v # stops containers AND remove volumes

$ docker compose --profile dev --profile prod down -v --remove-orphans
$ docker network prune -f
$ docker compose --profile dev --profile prod up -d --build
```
