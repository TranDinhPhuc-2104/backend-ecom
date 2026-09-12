CREATE DATABASE IF NOT EXISTS projectHK DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE projectHK;

-- --------------------------------------------------------
-- Table structure for account
-- --------------------------------------------------------
CREATE TABLE account (
    idAccount INT AUTO_INCREMENT NOT NULL,
    gmail VARCHAR(255) NULL,
    password VARCHAR(100) NULL,
    role VARCHAR(10) NULL,
    PRIMARY KEY (idAccount)
);

-- --------------------------------------------------------
-- Table structure for cart
-- --------------------------------------------------------
CREATE TABLE cart (
    idCart INT AUTO_INCREMENT NOT NULL,
    idAccount INT NULL,
    PRIMARY KEY (idCart),
    UNIQUE KEY uk_idAccount (idAccount)
);

-- --------------------------------------------------------
-- Table structure for cart_items
-- --------------------------------------------------------
CREATE TABLE cart_items (
    idCI INT AUTO_INCREMENT NOT NULL,
    idCart INT NULL,
    idProduct INT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price FLOAT NULL,
    PRIMARY KEY (idCI)
);

-- --------------------------------------------------------
-- Table structure for category
-- --------------------------------------------------------
CREATE TABLE category (
    idCate INT AUTO_INCREMENT NOT NULL,
    nameCate VARCHAR(55) NULL,
    PRIMARY KEY (idCate)
);

-- --------------------------------------------------------
-- Table structure for invalidated_token
-- --------------------------------------------------------
CREATE TABLE invalidated_token (
    id VARCHAR(255) NOT NULL,
    expiry_time DATETIME NULL,
    PRIMARY KEY (id)
);

-- --------------------------------------------------------
-- Table structure for order_details
-- --------------------------------------------------------
CREATE TABLE order_details (
    idOD INT AUTO_INCREMENT NOT NULL,
    idOrder INT NOT NULL,
    idProduct INT NOT NULL,
    quantity INT NOT NULL,
    priceAtOrder DECIMAL(18, 2) NOT NULL,
    PRIMARY KEY (idOD)
);

-- --------------------------------------------------------
-- Table structure for orders
-- --------------------------------------------------------
CREATE TABLE orders (
    idOrder INT AUTO_INCREMENT NOT NULL,
    idAccount INT NOT NULL,
    fullName VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(500) NOT NULL,
    payment VARCHAR(50) NOT NULL,
    totalPrice DECIMAL(18, 2) NOT NULL,
    orderDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'Chờ xử lý',
    PRIMARY KEY (idOrder)
);

-- --------------------------------------------------------
-- Table structure for products
-- --------------------------------------------------------
CREATE TABLE products (
    id INT AUTO_INCREMENT NOT NULL,
    namePro VARCHAR(55) NULL,
    quantity INT NULL,
    idCate INT NULL,
    description VARCHAR(255) NULL,
    image LONGTEXT NULL,
    price DECIMAL(10, 2) NULL,
    nameCate VARCHAR(50) NULL,
    PRIMARY KEY (id)
);

-- --------------------------------------------------------
-- Table structure for ui
-- --------------------------------------------------------
CREATE TABLE ui (
    id INT AUTO_INCREMENT NOT NULL,
    firstName VARCHAR(50) NULL,
    lastName VARCHAR(50) NULL,
    dob DATE NULL,
    address VARCHAR(100) NULL,
    idAccount INT NULL,
    phone CHAR(10) NULL,
    PRIMARY KEY (id)
);

-- --------------------------------------------------------
-- Constraints (Foreign Keys)
-- --------------------------------------------------------
ALTER TABLE cart 
    ADD CONSTRAINT fk_cart_login FOREIGN KEY (idAccount) REFERENCES account (idAccount);

ALTER TABLE cart_items 
    ADD CONSTRAINT fk_ci_cart FOREIGN KEY (idCart) REFERENCES cart (idCart),
    ADD CONSTRAINT fk_ci_product FOREIGN KEY (idProduct) REFERENCES products (id);

ALTER TABLE order_details 
    ADD CONSTRAINT FK_OrderDetails_Orders FOREIGN KEY (idOrder) REFERENCES orders (idOrder) ON DELETE CASCADE,
    ADD CONSTRAINT FK_OrderDetails_Products FOREIGN KEY (idProduct) REFERENCES products (id);

ALTER TABLE orders 
    ADD CONSTRAINT FK_Orders_Account FOREIGN KEY (idAccount) REFERENCES account (idAccount);

ALTER TABLE products 
    ADD CONSTRAINT FK_category FOREIGN KEY (idCate) REFERENCES category (idCate);

ALTER TABLE ui 
    ADD CONSTRAINT ui_Fkey FOREIGN KEY (idAccount) REFERENCES account (idAccount);
