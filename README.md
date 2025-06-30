Spring Boot 3.x service to track configuration changes with REST API, in-memory storage, and observability features.

	Made witt Java 21 and Spring Boot 3.x

	Create / list / get config changes via REST

	In-memory persistence (no DB)

	Input validation with error messages

	Simulated external notification for critical changes

	Retry logic with fallback (library Resilience4j)

	Correlation ID tracing

	Health + metrics via Spring Actuator

	Unit + integration tests


End-points:

	Changes: http://localhost:8080/changes
 
	And with ID: http://localhost:8080/changes/{id}
 
	Health check: http://localhost:8080/actuator/health
 
	Metrics: http://localhost:8080/actuator/metrics
	
ID is generated via UUID.randomUUID() as 128-bit unique random value and then turn to String.
This will remove the need for central counter with very low collision chance
