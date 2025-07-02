#Use official tomcat 9 with java 21 pre installed
FROM tomcat:9.0.82-jdk21-temurin

# set maintainer label (optional but good to proctice)
LABEL maintainer="Rohit@example.com"

# remove default root app (optional, keep container clean)
RUN rm -rf /usr/local/tomcat/webapps/ROOT

#create a user for running the application
RUN useradd -m makemytrip

#copy your war file into the webapps directory
COPY ./target/makemytrip*.jar /usr/local/tomcat/webapps

# Expose the default Tomcat Port
Expose 8080

#Set the user to makemytrip for security
USER makemytrip

# Default command to run Tomcat
CMD ["catalina.sh", "run"]