.PHONY: all build test run docker-up docker-down

all: build test

build:
	@echo "Building Balaji High School Java backend and React frontend..."
	@mvn -f backend/pom.xml clean install -DskipTests
	@npm --prefix frontend run build

test:
	@echo "Executing JUnit 5 and component test suites..."
	@mvn -f backend/pom.xml test

run:
	@echo "Launching Balaji High School Digital Ecosystem..."
	@node server.js

docker-up:
	@docker-compose up -d

docker-down:
	@docker-compose down
