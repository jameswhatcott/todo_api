#!/bin/bash

# Todo API Docker Deployment Script

set -e

echo "🐳 Todo API Docker Deployment Script"
echo "====================================="

# Function to display usage
usage() {
    echo "Usage: $0 [build|run|stop|clean|logs]"
    echo ""
    echo "Commands:"
    echo "  build   - Build the Docker image"
    echo "  run     - Run the application using docker-compose"
    echo "  stop    - Stop the running containers"
    echo "  clean   - Remove containers and images"
    echo "  logs    - Show container logs"
    echo ""
    echo "Examples:"
    echo "  $0 build && $0 run"
    echo "  $0 logs"
}

# Function to build the image
build() {
    echo "🔨 Building Docker image..."
    docker build -t todo-api:latest .
    echo "✅ Build completed successfully!"
}

# Function to run the application
run() {
    echo "🚀 Starting Todo API..."
    docker-compose up -d
    echo "✅ Application is starting up..."
    echo "📊 Health check will be available at: http://localhost:8080/actuator/health"
    echo "🌐 API will be available at: http://localhost:8080"
    echo "💾 H2 Console will be available at: http://localhost:8080/h2-console"
}

# Function to stop the application
stop() {
    echo "🛑 Stopping Todo API..."
    docker-compose down
    echo "✅ Application stopped!"
}

# Function to clean up
clean() {
    echo "🧹 Cleaning up Docker resources..."
    docker-compose down --rmi all --volumes --remove-orphans
    docker system prune -f
    echo "✅ Cleanup completed!"
}

# Function to show logs
logs() {
    echo "📋 Showing container logs..."
    docker-compose logs -f
}

# Main script logic
case "${1:-}" in
    build)
        build
        ;;
    run)
        run
        ;;
    stop)
        stop
        ;;
    clean)
        clean
        ;;
    logs)
        logs
        ;;
    *)
        usage
        exit 1
        ;;
esac 