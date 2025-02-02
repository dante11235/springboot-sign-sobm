# Spring Boot Application

## Features
- REST API with Spring Boot
- OpenAPI/Swagger Documentation
- Docker Support
- Software Bill of Materials (SBOM) using CycloneDX
- Container Signing with Cosign

## Build

```bash
docker build -t springboot-demo .
```

## Run

```bash
docker run -p 8080:8080 springboot-demo
```

## Generate SBOM
To generate the Software Bill of Materials:

```bash
./gradlew cyclonedxBom
```

The SBOM will be generated in JSON format at: `build/reports/bom.json`

## Access Points
- REST API: http://localhost:8080/hello
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Docs: http://localhost:8080/api-docs

## Container Security
This container is automatically built and signed using Cosign in GitHub Actions. 

### Key Preparation
1. Generate a key pair:
```bash
cosign generate-key-pair github://$REPO_OWNER/$REPO_NAME
```

To verify the SBOM:
```bash
cosign verify-attestation --key cosign.pub ghcr.io/dante11235/springboot-sign-sobm:main
```

## Continuous Integration
GitHub Actions automatically:
- Builds the application
- Generates SBOM
- Creates container image
- Signs the container using Cosign with our signing key
- Publishes to GitHub Container Registry