# Stage 1: Build the application using Node.js
FROM node:20 AS build

# Set the working directory for the build stage
WORKDIR /app

# Define build arguments
ARG REACT_APP_API_KEY

# Set environment variables
ENV REACT_APP_API_KEY=$REACT_APP_API_KEY

# Copy the package.json and package-lock.json (if available) and install dependencies
COPY package.json package-lock.json ./
RUN npm install --silent

# Copy the rest of your app's source code from your host to your image filesystem.
COPY . .

# Build the application
RUN npm run build 

# Stage 2: Serve the application using Nginx
FROM nginx:stable-alpine as production-stage

# Create a directory for Jenkins logs.
RUN mkdir -p /var/log/nginx/jenkins

# Set permissions if necessary (This step may not be necessary depending on your usage)
RUN chmod -R 755 /var/log/nginx

# Remove the default Nginx configuration
COPY ./default.conf /etc/nginx/conf.d

# Copy the built application from the build stage to the Nginx HTML directory
COPY --from=build /app/build /usr/share/nginx/html

# Ensure logging statements go to the standard output (for easier debugging and logging)
RUN ln -sf /dev/stdout /var/log/nginx/access.log \
    && ln -sf /dev/stderr /var/log/nginx/error.log

# Expose port 80 and 443 (HTTPS)
EXPOSE 80

# Start Nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]
