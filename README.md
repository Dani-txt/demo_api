#  :convenience_store: Demo E-Commerce API

API RESTful para plataforma de e-commerce desarrollada con fines académicos.

Enfocado en la aplicación de buenas prácticas, metodologías de trabajo y herramientas modernas de desarrollo.

Stack 100% gratuito - Herramientas y servicios utilizados son free source y/o tienen planes gratuitos

---

## :clipboard: Alcance del Proyecto

Sistema de e-commerce básico:
- **Gestión de productos** (CRUD, categorías, stock, etc)
- **Gestión de usuarios** (login y roles: ADMIN y CLIENTE)
- **Autenticación y autorización** (JWT)
- **Documentación interactiva** (Swagger/OpenAPI)

&gt; El objetivo principal es reconocer y aplicar el flujo de trabajo profesional: Git Flow, CI/CD, contenedores y deploy automatizado.

---

## :hammer: Arquitectura

| Capa | Tecnología | Hosting |
|------|-----------|---------|
| **Database** | PostgreSQL | [Neon](https://neon.tech) |
| **Backend** | Java 21 + Spring Boot 4 [Spring](https://start.spring.io/)| [Render](https://render.com) (Docker) |
| **Frontend** | React + Vite (JavaScript) | [Vercel](https://vercel.com) |

---

## :rocket: Deploys

| Entorno | URL | Rama |
|---------|-----|------|
| Producción | `https://demo-api-2lre.onrender.com` | `main` |
| Documentación | `/doc/swagger-ui.html` | - |

---

## :satellite: Stack Tecnológico

### Backend
- **Java 21** (LTS)
- **Spring Boot 4.x**
  - Spring Data JPA
  - Spring Security (JWT)
  - Spring Validation
  - SpringDoc OpenAPI
- **Maven** (build)
- **Docker** (contenedor)
- **PostgreSQL** (base de datos)

### DevOps & Tools
- **Git Flow** (main / develop / release / feature)
- **GitHub Actions** (CI/CD opcional)
- **Docker** + Render (deploy automatizado)
- **UpTimeRobot** (keep-alive gratuito)

---

### Roadmap previsto 

Alcances ajustables según calendario y carga academica

| Fase | Estado | Entregable |
|------|--------|------------|
| **Fase 1: Infraestructura** | :+1: Completo | API documentada y deployada en Render |
| **Fase 2: Core Backend** | 🏗️ En progreso | CRUD Productos + Usuarios + JWT auth |
| **Fase 3: Integración** | ⏳ Pendiente | Frontend React + deploy Vercel |



