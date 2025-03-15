CREATE TABLE organizations (
    organization_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    address TEXT,
    contact_email VARCHAR(255),
    contact_phone VARCHAR(50),
    PRIMARY KEY (organization_id)
);

CREATE TABLE services (
    service_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    organization_id BIGINT NOT NULL,
    PRIMARY KEY (service_id),
    FOREIGN KEY (organization_id) REFERENCES organizations(organization_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE users (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(50),
    address TEXT,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Tenant', 'User') DEFAULT 'User',
    PRIMARY KEY (user_id)
);
CREATE TABLE tenants (
    tenant_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(50),
    organization_id BIGINT NOT NULL,
    role ENUM('Admin', 'Manager') DEFAULT 'Manager',
    PRIMARY KEY (tenant_id),
    FOREIGN KEY (organization_id) REFERENCES organizations(organization_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE user_services (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    premium_amount DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(service_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE transactions (
    transaction_id BIGINT NOT NULL AUTO_INCREMENT,
    user_service_id BIGINT NOT NULL,
    transaction_date DATE NOT NULL,
    amount_paid DECIMAL(10, 2) NOT NULL,
    status ENUM('Paid', 'Pending', 'Failed') NOT NULL,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (user_service_id) REFERENCES user_services(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

