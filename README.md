## Summary
A web service to work with widgets via HTTP REST API.
The service stores only widgets, assuming all clients work with the same board.

## Glossary
A Widget is an object on a plane in a Cartesian coordinate system with specific attributes:
coordinates (X, Y), Z-index, width, height, last modification date, and a unique identifier.
Z-index is a widget's attribute determining its order among other widgets along the axis perpendicular to the plane.

- X, Y, Z-index are integers.
- WIDTH and HEIGHT are positive integers.
- all attributes are not nullable.

## Project basic routines

#### Code coverage
- start tests: `> mvn clean test`
- explore details in `target/site/jacoco/index.html`

#### Build artifact and run it
- `> mvn clean package`
- `> cd target`
- `> java -jar widget-web-service.jar`

#### Run application in place (via maven plugin)
 
###### default:
`> mvn clean spring-boot:run`
###### recommended for local development:
`> mvn clean spring-boot:run -D spring-boot.run.profiles=developer`


## Available endpoints

###### all endpoints respect REST API conventions
> body format for mutation methods (POST, PUT, PATCH)\
`{"x": <int>, "y": <int>, "zIndex": <int>, "width": <positive int>, "height":<positive int>}`\
> POST, PUT require all properties\
> PATCH allows omitting unchanged properties


- GET /widgets 
  > `get array of all available widgets sorted by zIndex`
- POST /widgets
  > `create one widget`
- PUT /widgets/{id}
  > `replace one widget`
- PATCH /widgets/{id}
  > `change properties of one widget`
- DELETE /widgets/{id}
  > `remove one widget`
