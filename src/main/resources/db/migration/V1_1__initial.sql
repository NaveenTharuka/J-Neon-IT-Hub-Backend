-- V1__init_schema.sql
-- PostgreSQL version of your schema (IDs switched to UUID)

-- Enable UUID generation (Postgres 13+)
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- 1. USERS TABLE
CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       name VARCHAR(120) NOT NULL,
                       email VARCHAR(190) UNIQUE NOT NULL,
                       oauth_provider VARCHAR(50) NOT NULL,
                       oauth_provider_id VARCHAR(190) NOT NULL,
                       role VARCHAR(30) DEFAULT 'editor',
                       status VARCHAR(20) DEFAULT 'active',
                       last_login_at TIMESTAMP NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_role ON users(role);
CREATE INDEX idx_status ON users(status);

-- 2. SERVICES TABLE
CREATE TABLE services (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          title VARCHAR(120) NOT NULL,
                          slug VARCHAR(140) UNIQUE NOT NULL,
                          short_description VARCHAR(255),
                          icon VARCHAR(120),
                          is_active BOOLEAN DEFAULT TRUE,
                          sort_order INT DEFAULT 0,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_active_sort ON services(is_active, sort_order);

-- 3. SERVICE_PLANS TABLE
CREATE TABLE service_plans (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               service_id UUID NOT NULL,
                               plan_name VARCHAR(80) NOT NULL,
                               price NUMERIC(10,2),
                               price_type VARCHAR(30) DEFAULT 'fixed',
                               currency CHAR(3) DEFAULT 'USD',
                               billing_period VARCHAR(20),
                               description VARCHAR(255),
                               features JSONB,
                               is_featured BOOLEAN DEFAULT FALSE,
                               sort_order INT DEFAULT 0,
                               is_active BOOLEAN DEFAULT TRUE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               CONSTRAINT fk_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);

CREATE INDEX idx_service_active_sort ON service_plans(service_id, is_active, sort_order);
CREATE INDEX idx_featured ON service_plans(is_featured);

-- 7. MEDIA TABLE
CREATE TABLE media (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       file_name VARCHAR(200) NOT NULL,
                       file_url VARCHAR(500) NOT NULL,
                       mime_type VARCHAR(100),
                       file_size_bytes BIGINT,
                       width INT,
                       height INT,
                       alt_text VARCHAR(255),
                       caption VARCHAR(255),
                       uploaded_by UUID NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_uploaded_by FOREIGN KEY (uploaded_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX idx_mime_type ON media(mime_type);
CREATE INDEX idx_uploaded_by ON media(uploaded_by);

-- 4. PAGES TABLE
CREATE TABLE pages (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       title VARCHAR(160) NOT NULL,
                       slug VARCHAR(160) UNIQUE NOT NULL,
                       page_type VARCHAR(30) DEFAULT 'custom',
                       service_id UUID NULL,
                       hero_title VARCHAR(180),
                       hero_subtitle VARCHAR(255),
                       content TEXT,
                       seo_title VARCHAR(160),
                       seo_description VARCHAR(255),
                       is_published BOOLEAN DEFAULT TRUE,
                       updated_by UUID NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_page_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE SET NULL,
                       CONSTRAINT fk_page_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX idx_page_type_published ON pages(page_type, is_published);
CREATE INDEX idx_pages_service ON pages(service_id);

-- 5. PAGE_SECTIONS TABLE
CREATE TABLE page_sections (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               page_id UUID NOT NULL,
                               section_type VARCHAR(40) NOT NULL,
                               title VARCHAR(160),
                               subtitle VARCHAR(255),
                               content TEXT,
                               data JSONB,
                               background_media_id UUID NULL,
                               sort_order INT DEFAULT 0,
                               is_active BOOLEAN DEFAULT TRUE,
                               updated_by UUID NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               CONSTRAINT fk_page_section_page FOREIGN KEY (page_id) REFERENCES pages(id) ON DELETE CASCADE,
                               CONSTRAINT fk_page_section_media FOREIGN KEY (background_media_id) REFERENCES media(id) ON DELETE SET NULL,
                               CONSTRAINT fk_page_section_updated_by FOREIGN KEY (updated_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE INDEX idx_page_active_sort ON page_sections(page_id, is_active, sort_order);
CREATE INDEX idx_section_type ON page_sections(section_type);

-- 6. PORTFOLIO_ITEMS TABLE
CREATE TABLE portfolio_items (
                                 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                 title VARCHAR(160) NOT NULL,
                                 slug VARCHAR(170) UNIQUE NOT NULL,
                                 client_name VARCHAR(160),
                                 summary VARCHAR(255),
                                 description TEXT,
                                 service_id UUID NULL,
                                 project_date DATE,
                                 project_url VARCHAR(255),
                                 is_published BOOLEAN DEFAULT TRUE,
                                 sort_order INT DEFAULT 0,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 CONSTRAINT fk_portfolio_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE SET NULL
);

CREATE INDEX idx_published_sort ON portfolio_items(is_published, sort_order);
CREATE INDEX idx_portfolio_service ON portfolio_items(service_id);

-- 8. PORTFOLIO_IMAGES TABLE
CREATE TABLE portfolio_images (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  portfolio_item_id UUID NOT NULL,
                                  media_id UUID NOT NULL,
                                  is_cover BOOLEAN DEFAULT FALSE,
                                  sort_order INT DEFAULT 0,
                                  alt_text_override VARCHAR(255),
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  CONSTRAINT fk_portfolio_image_item FOREIGN KEY (portfolio_item_id) REFERENCES portfolio_items(id) ON DELETE CASCADE,
                                  CONSTRAINT fk_portfolio_image_media FOREIGN KEY (media_id) REFERENCES media(id) ON DELETE CASCADE
);

CREATE INDEX idx_portfolio_sort ON portfolio_images(portfolio_item_id, sort_order);
CREATE INDEX idx_media ON portfolio_images(media_id);
CREATE INDEX idx_portfolio_cover ON portfolio_images(portfolio_item_id, is_cover);

-- 9. CONTACT_MESSAGES TABLE
CREATE TABLE contact_messages (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                  name VARCHAR(120) NOT NULL,
                                  email VARCHAR(190) NOT NULL,
                                  phone VARCHAR(40),
                                  subject VARCHAR(160),
                                  message TEXT NOT NULL,
                                  service_id UUID NULL,
                                  status VARCHAR(20) DEFAULT 'new',
                                  source_page VARCHAR(160),
                                  ip_address VARCHAR(45),
                                  user_agent VARCHAR(255),
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  CONSTRAINT fk_contact_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE SET NULL
);

CREATE INDEX idx_status_created ON contact_messages(status, created_at);
CREATE INDEX idx_contact_service ON contact_messages(service_id);
CREATE INDEX idx_email ON contact_messages(email);