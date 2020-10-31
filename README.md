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
