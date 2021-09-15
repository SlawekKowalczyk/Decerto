# README - Book of quotes #

Run project:
* command line: mvn spring-boot:run -Dspring-boot.run.profiles=dev
* IDE: set profile and run project

Run swagger documentation
* http://host:port/swagger-ui.html

Run test
* mvn test

### Endpoints descritpion ###
http method, path, short description, example
* GET, '/api/v1/quote/', get all available quotes,
            example: RequestParam: pageNo: integer, pageSize: integer
* PUT, '/api/v1/quote/{id}', update existing quote, 
            example: PathVariable: id: integer,
                    body: {
                            "name": "string",
                            "surname": "string"
                            "content":"string"
                          }
* DELETE, '/api/v1/quote/{id}', delete quote by id,
            example: PathVariable: id: integer
* POST, '/api/v1/quote/add', add quote,
            example:
                    body: {
                            "name":"string",
                            "surname": "string"
                            "content":"string"
                           }