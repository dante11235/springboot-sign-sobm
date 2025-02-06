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
cosign generate-key-pair
```

2. Create GITHUB secrets
```bash
gh secret set COSIGN_PUBLIC_KEY --body-file cosign.pub
gh secret set COSIGN_PRIVATE_KEY --body-file cosign.key
gh secret set COSIGN_PASSWORD --body-file cosign.password
```

3. Create a policy in Kyverno
```bash
kubectl apply -f kube/kyverno-policy.yaml
```

## Testing

```
cosign verify --key cosign.pub nexus.test-env.sk/repository/dante11235/springboot-sign-sobm:sha-5a5971640f85611927c7c12c56b3fd5649fbe982
cosign verify --key cosign.pub nexus.test-env.sk/repository/dante11235/springboot-sign-sobm:unsigned
```

create kubenetes pods
```
kubectl run app-unsigned --image=nexus.test-env.sk/repository/dante11235/springboot-sign-sobm:unsigned
kubectl run app-signed --image=nexus.test-env.sk/repository/dante11235/springboot-sign-sobm:sha-5a5971640f85611927c7c12c56b3fd5649fbe982 
```


## Continuous Integration
GitHub Actions automatically:
- Builds the application
- Generates SBOM
- Creates container image
- Signs the container using Cosign with our signing key
- Publishes to GitHub Container Registry