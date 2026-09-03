.PHONY: all build test run

all: build test

build:
	@echo "Building balaji..."
	@npm run build

test:
	@echo "Running test suites for balaji..."
	@npm test

run:
	@echo "Starting balaji..."
	@node server.js
