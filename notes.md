# Todo API Notes
- **TodoApiApplication.java**: Main class, starts Spring Boot with @SpringBootApplication.
- **Todo.java**: Entity class, maps to database table with @Entity, @Id.
- **TodoRepository.java**: Interface for database CRUD, extends JpaRepository.
- **TodoController.java**: REST endpoints, uses @RestController, @Autowired for repository.
- **pom.xml**: Maven config, lists dependencies (spring-boot-starter-web, h2).
- **application.properties**: Configures H2 database (jdbc:h2:mem:tododb).