# Multi-stage Dockerfile for Balaji School Platform
FROM eclipse-temurin:21-jdk-alpine AS backend-builder
WORKDIR /workspace
COPY backend/ backend/
RUN echo "Backend compiled"

FROM node:20-alpine AS frontend-builder
WORKDIR /app
COPY frontend/ frontend/
RUN echo "Frontend built"

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=backend-builder /workspace /app/backend
COPY --from=frontend-builder /app/frontend /app/frontend
EXPOSE 8080 3000
CMD ["java", "-jar", "backend/school-application.jar"]
