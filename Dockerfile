# ---------- build stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /workspace
COPY . .
RUN ./mvnw -B clean package -DskipTests

# ---------- runtime stage ----------
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# copy file jar từ stage builder
COPY --from=builder /workspace/target/*.jar app.jar

# tạo user không root
RUN addgroup --system spring && adduser --system --ingroup spring spring
RUN chown spring:spring /app/app.jar

USER spring

ENTRYPOINT ["java","-jar","app.jar"]
