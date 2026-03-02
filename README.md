# IT Agency Website – Backend

Scalable REST API powering the public website, Admin CMS, AI knowledge system, and analytics services.

---

## Overview

This backend application provides all core business logic, authentication, data management, AI knowledge processing, and analytics aggregation for the agency platform.

It is responsible for:

* Secure admin authentication (OAuth 2.0)
* CMS content management
* Contact inquiry handling
* AI-powered PDF knowledge retrieval (RAG)
* Media storage management
* Analytics tracking & aggregation

This repository contains **only the backend implementation**.
Frontend is handled separately.

---

# Project Scope (Based on Product Backlog)

---

## EPIC 1: Public Website Experience Support

### Backend Responsibilities

* Provide public APIs for:

  * Services listing & details
  * Portfolio listing & details
  * Website page content & sections
* Track page views
* Track portfolio item views

---

## EPIC 2: Contact & Inquiry Management

### Features

* Store contact form submissions
* Validate input data
* Provide admin endpoint to retrieve inquiries

---

## EPIC 3: Admin Authentication & Account Management

### Features

* OAuth 2.0 integration
* JWT token generation & validation
* Role-based access control (RBAC)
* Secure password storage (if local accounts supported)
* Admin account lifecycle management

### Security Responsibilities

* Protect admin routes with middleware
* Validate JWT tokens
* Restrict access to authorized roles only

---

## EPIC 4: Content & Media Management (CMS)

### Services Management

* Create service
* Update service
* Delete service
* Attach media references

### Pages Management

* Create pages
* Add / edit / reorder sections
* Delete sections

### Portfolio Management

* CRUD operations for portfolio items

### Media Management

* Upload image files
* Store metadata in database
* Delete unused images
* Prevent orphaned file storage

---

## EPIC 5: AI Knowledge System (Multimodal RAG with PDFs)

### Features

* Upload PDF documents
* Extract text from PDFs
* Chunk & embed document content
* Store embeddings in vector database
* Retrieve relevant chunks during user queries
* Generate responses strictly from indexed documents

### Backend Responsibilities

* PDF parsing
* Embedding generation
* Vector storage
* Retrieval logic
* Prompt construction for AI model
* Ensure responses are grounded (no hallucination)

---

## EPIC 6: Analytics & System Insights

### Features

* Track page visits
* Track portfolio views
* Aggregate metrics daily
* Provide dashboard analytics endpoints

### Backend Responsibilities

* Event logging
* Scheduled aggregation (daily batch job)
* Optimized analytics queries

---

# Tech Stack

### Core

* Spring Boot
* RESTful API architecture

### Database

* PostgreSQL / MySQL
* Relational schema for:

    * Services
    * Pages & Sections
    * Portfolio
    * Admins
    * Inquiries
    * Media
    * Analytics

### AI & Vector Processing

* PDF parsing library
* Embedding model
* Vector database

### Security

* OAuth 2.0
* JWT authentication
* Role-based access control
* Input validation & sanitization

---

# Installation

```bash
git clone <backend-repo-url>
cd backend
```

If Node.js:

```bash
npm install
npm run dev
```

If Spring Boot:

```bash
mvn clean install
mvn spring-boot:run
```

---

# Environment Variables

Create a `.env` file:

```
DATABASE_URL=
JWT_SECRET=
OAUTH_CLIENT_ID=
OAUTH_CLIENT_SECRET=
AI_API_KEY=
VECTOR_DB_URL=
```

---

# Database Responsibilities

The backend maintains:

* Relational integrity
* Cascade deletion rules
* Foreign key constraints
* Optimized indexing for analytics & search