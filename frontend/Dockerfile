FROM alpine:3.19 AS builder
WORKDIR /app
COPY src/ .

FROM nginx:1.27-alpine
COPY --from=builder /app /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
