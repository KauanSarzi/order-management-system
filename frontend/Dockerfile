# Stage 1 — copy static files
FROM alpine:3.19 AS builder
WORKDIR /app
COPY src/ .

# Stage 2 — serve with Nginx
FROM nginx:1.27-alpine
COPY --from=builder /app /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
