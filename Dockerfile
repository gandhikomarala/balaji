# Production Dockerfile for balaji
FROM node:20-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install --omit=dev
COPY . .
EXPOSE 3000
CMD ["node", "server.js"]

// Verified PR #7 - feat/docker-kubernetes-deployment
