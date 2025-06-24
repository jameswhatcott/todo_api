# Docker Deployment Guide

This guide explains how to containerize and deploy the Todo API using Docker.

## 🐳 Quick Start

### Prerequisites
- Docker installed on your system
- Docker Compose installed
- At least 2GB of available memory

### Quick Deployment
```bash
# Make the deployment script executable (if not already done)
chmod +x deploy.sh

# Build and run the application
./deploy.sh build && ./deploy.sh run

# Check the logs
./deploy.sh logs
```

## 📁 Docker Files Overview

### `Dockerfile`
- **Multi-stage build** for optimized image size
- **Build stage**: Uses Maven to compile the application
- **Production stage**: Uses lightweight JRE image
- **Security**: Runs as non-root user
- **Health checks**: Built-in health monitoring

### `docker-compose.yml`
- **Service orchestration**: Defines the todo-api service
- **Environment configuration**: H2 database settings
- **Port mapping**: Exposes port 8080
- **Health checks**: Container health monitoring
- **Restart policy**: Automatic restart on failure

### `.dockerignore`
- **Optimized builds**: Excludes unnecessary files
- **Faster builds**: Reduces build context size
- **Security**: Prevents sensitive files from being included

## 🚀 Deployment Options

### Option 1: Using the Deployment Script (Recommended)
```bash
# Build the Docker image
./deploy.sh build

# Run the application
./deploy.sh run

# Stop the application
./deploy.sh stop

# View logs
./deploy.sh logs

# Clean up everything
./deploy.sh clean
```

### Option 2: Using Docker Compose Directly
```bash
# Build and start
docker-compose up --build -d

# View logs
docker-compose logs -f

# Stop
docker-compose down

# Clean up
docker-compose down --rmi all --volumes
```

### Option 3: Using Docker Commands Directly
```bash
# Build the image
docker build -t todo-api:latest .

# Run the container
docker run -d -p 8080:8080 --name todo-api todo-api:latest

# View logs
docker logs -f todo-api

# Stop and remove
docker stop todo-api && docker rm todo-api
```

## 🌐 Access Points

Once deployed, the application will be available at:

- **API Base URL**: http://localhost:8080
- **Health Check**: http://localhost:8080/actuator/health
- **H2 Database Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

## 🔧 Configuration

### Environment Variables
The application can be configured using environment variables:

```yaml
# Database Configuration
SPRING_DATASOURCE_URL: jdbc:h2:mem:testdb
SPRING_DATASOURCE_USERNAME: sa
SPRING_DATASOURCE_PASSWORD: password
SPRING_H2_CONSOLE_ENABLED: true

# JPA Configuration
SPRING_JPA_HIBERNATE_DDL_AUTO: create-drop

# Profile
SPRING_PROFILES_ACTIVE: docker
```

### Custom Configuration
To use a different database (e.g., PostgreSQL), uncomment the postgres service in `docker-compose.yml` and update the environment variables accordingly.

## 📊 Monitoring

### Health Checks
The container includes built-in health checks that monitor:
- Application startup
- HTTP endpoint availability
- Database connectivity

### Logs
Access application logs using:
```bash
# Using deployment script
./deploy.sh logs

# Using docker-compose
docker-compose logs -f

# Using docker directly
docker logs -f todo-api
```

## 🔒 Security Features

- **Non-root user**: Application runs as `appuser` (UID 1001)
- **Minimal base image**: Uses Alpine Linux for smaller attack surface
- **Health checks**: Monitors application health
- **Resource limits**: Can be configured in docker-compose.yml

## 🚀 Production Deployment

### For Production Environments

1. **Use a Production Database**:
   ```yaml
   # Uncomment and configure in docker-compose.yml
   postgres:
     image: postgres:15-alpine
     environment:
       POSTGRES_DB: todo_db
       POSTGRES_USER: todo_user
       POSTGRES_PASSWORD: your_secure_password
   ```

2. **Add Resource Limits**:
   ```yaml
   services:
     todo-api:
       # ... existing config ...
       deploy:
         resources:
           limits:
             memory: 512M
             cpus: '0.5'
           reservations:
             memory: 256M
             cpus: '0.25'
   ```

3. **Use Environment Files**:
   ```bash
   # Create .env file
   SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/todo_db
   SPRING_DATASOURCE_USERNAME=todo_user
   SPRING_DATASOURCE_PASSWORD=your_secure_password
   ```

4. **Add Reverse Proxy** (e.g., Nginx):
   ```yaml
   nginx:
     image: nginx:alpine
     ports:
       - "80:80"
       - "443:443"
     volumes:
       - ./nginx.conf:/etc/nginx/nginx.conf
     depends_on:
       - todo-api
   ```

## 🐛 Troubleshooting

### Common Issues

1. **Port Already in Use**:
   ```bash
   # Check what's using port 8080
   lsof -i :8080
   
   # Change port in docker-compose.yml
   ports:
     - "8081:8080"  # Use port 8081 instead
   ```

2. **Build Failures**:
   ```bash
   # Clean Docker cache
   docker system prune -a
   
   # Rebuild without cache
   docker build --no-cache -t todo-api:latest .
   ```

3. **Application Won't Start**:
   ```bash
   # Check logs
   ./deploy.sh logs
   
   # Check container status
   docker ps -a
   ```

4. **Memory Issues**:
   ```bash
   # Increase Docker memory limit in Docker Desktop
   # Or add memory limits to docker-compose.yml
   ```

### Debug Mode
To run in debug mode with more verbose logging:
```bash
docker run -d -p 8080:8080 -e SPRING_PROFILES_ACTIVE=debug todo-api:latest
```

## 📈 Performance Optimization

### Image Size Optimization
- Multi-stage build reduces final image size
- Alpine Linux base image is lightweight
- Only necessary dependencies are included

### Build Optimization
- `.dockerignore` excludes unnecessary files
- Maven dependency caching in build stage
- Layer caching for faster rebuilds

### Runtime Optimization
- JRE instead of full JDK
- Non-root user for security
- Health checks for monitoring

## 🔄 CI/CD Integration

### GitHub Actions Example
```yaml
name: Build and Deploy
on:
  push:
    branches: [ main ]

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Build Docker image
        run: docker build -t todo-api:${{ github.sha }} .
      - name: Deploy to production
        run: |
          # Add your deployment commands here
          docker-compose up -d
```

## 📚 Additional Resources

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)
- [Alpine Linux](https://alpinelinux.org/)

## 🤝 Contributing

When contributing to the Docker setup:
1. Test your changes locally
2. Update this documentation
3. Ensure backward compatibility
4. Follow Docker best practices 