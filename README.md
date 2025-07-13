# 🛒 Online Shopping Application

A **complete e-commerce application** developed entirely in **Java**, designed to serve both **customers** and **sellers**. Built with **Apache NetBeans**, managed by **Apache Ant**, and backed by a robust **MySQL** database.

---

## Table of Contents

| Section            | Description                                      |
|--------------------|------------------------------------------------|
| [Key Features](#key-features)         | Main functionalities of the application        |
| [Technology Stack](#technology-stack) | Tools and frameworks used                       |
| [Project Structure](#project-structure) | Overview of code organization                   |
| [Database Model](#database-model)      | Database schema and relationships               |
| [Getting Started](#getting-started)     | How to set up and run the project                |
| [Usage](#usage)                      | Instructions for using the application          |
| [Contributing](#contributing)           | Guidelines for contributing to the project       |
| [License](#license)                   | Licensing information                            |

---

## Key Features

| Feature                          | Description                                              |
|---------------------------------|----------------------------------------------------------|
| User Registration & Authentication | Secure sign-up/login for customers and sellers          |
| Product Management              | Sellers can add, update, and delete products             |
| Shopping Cart                  | Customers can add/remove items and proceed to checkout    |
| Order Processing               | Manage orders, payments, and shipping                     |
| Admin Panel                   | Overview of users, products, and orders                   |
| Search & Filtering             | Search products by categories, price, and ratings        |
| Responsive UI                 | Clean and user-friendly interface                          |

---

## Technology Stack

| Layer           | Technologies                          |
|-----------------|-------------------------------------|
| Programming Language | Java                             |
| IDE             | Apache NetBeans                      |
| Build Tool      | Apache Ant                          |
| Database        | MySQL                              |
| Libraries       | JDBC (Java Database Connectivity)   |
| Web Server      | Apache Tomcat (optional)            |

---

## Project Structure


Online_Shopping_Application/

├── src/                         # Java source files

│   ├── model/                   # Entity classes

│   ├── dao/                     # Data Access Objects for DB operations

│   ├── service/                 # Business logic

│   ├── controller/              # Handles user requests

│   └── util/                    # Utility classes (DB connections, helpers)

├── build.xml                    # Apache Ant build configuration

├── lib/                        # External libraries (if any)

├── README.md                   # Project documentation

└── database.sql                # SQL scripts to create and populate DB


## Database Model

| Table Name       | Purpose                                                                                          |
|------------------|-------------------------------------------------------------------------------------------------|
| address          | Stores address details, including address name, city, district, street, and building number.     |
| cart             | Represents a customer's shopping cart.                                                          |
| cart_item        | Stores individual products and their quantities within a specific shopping cart.                 |
| cartitem_product | Links products to items in a shopping cart.                                                     |
| coupon           | Stores information about discount coupons, including code, discount amount, expiration date, and associated seller or game. |
| customer         | Manages customer accounts, including name, phone number, email, password, height, age, and referrer ID. |
| customer_product | Tracks products associated with a customer, including quantity.                                  |
| favorites        | Stores products that customers have marked as favorites.                                        |
| game             | Stores details about games, including game name, type, and reset hours.                         |
| invites          | Records customer invitations or referrals.                                                      |
| order_product    | Links products to specific orders and includes the price at the time of order.                  |
| orders           | Records completed customer orders, including order date, customer ID, and address name.         |
| payment_info     | Stores customer payment card details such as card number, expiration date, CVC, card owner, and card name. |
| plays            | Records instances of customers playing games, including play time.                              |
| product          | Contains details for all products available for sale, such as product name, price, color, size, material, stock, and associated seller. |
| review           | Stores customer reviews for products, including star rating, comment, and rating date.          |
| seller           | Stores seller information, including seller name, experience, rating, and associated shipment ID. |
| shipping_company | Stores details about shipping companies, including company name.     
---

## Getting Started

### Prerequisites

- Java JDK 8+ installed
- Apache NetBeans IDE (recommended)
- Apache Ant installed and configured
- MySQL Server installed and running
- Basic knowledge of running Java projects and SQL

### Installation Steps

1. Clone the repository:

    ```bash
    git clone https://github.com/IsmailTekin05/Online_Shopping_Application.git
    ```

2. Import the project into Apache NetBeans.

3. Configure database:

    - Create a MySQL database.
    - Run `database.sql` script to create tables and insert initial data.
    - Update database credentials in the configuration file (`DBConnection.java` or equivalent).

4. Build and run the project using Apache Ant:

    ```bash
    ant clean build run
    ```

---

## Usage

- Register as a customer or seller.
- Sellers can add and manage products.
- Customers browse products, add items to cart, and place orders.
- Admins manage users and oversee orders.

---

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a Pull Request describing your changes.

Please ensure your code follows the existing style and includes necessary tests.

---

## License

This project is licensed under the **Creative Commons Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0)** License.

See the [LICENSE](LICENSE) file for details.

