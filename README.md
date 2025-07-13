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

```plaintext
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
Database Model
Table Name	Description
users	Stores user information (customers, sellers, admins)
products	Contains product details
categories	Product categories
orders	Customer orders
order_items	Items within each order
payments	Payment transaction records

Relationships Overview
Relationship	Description
users (1) ↔ (N) orders	One user can have multiple orders
orders (1) ↔ (N) order_items	Each order contains multiple items
products (N) ↔ (1) categories	Each product belongs to a category
users (1) ↔ (N) products	Sellers can have multiple products listed

Getting Started
Prerequisites
Java JDK 8+ installed

Apache NetBeans IDE (recommended)

Apache Ant installed and configured

MySQL Server installed and running

Basic knowledge of running Java projects and SQL

Installation Steps
Clone the repository:

bash
Copy
Edit
git clone https://github.com/IsmailTekin05/Online_Shopping_Application.git
Import the project into Apache NetBeans.

Configure database:

Create a MySQL database.

Run database.sql script to create tables and insert initial data.

Update database credentials in the configuration file (DBConnection.java or equivalent).

Build and run the project using Apache Ant:

bash
Copy
Edit
ant clean build run
Usage
Register as a customer or seller.

Sellers can add and manage products.

Customers browse products, add items to cart, and place orders.

Admins manage users and oversee orders.

Contributing
Contributions are welcome! Please follow these steps:

Fork the repository.

Create your feature branch (git checkout -b feature/your-feature).

Commit your changes (git commit -m 'Add some feature').

Push to the branch (git push origin feature/your-feature).

Open a Pull Request describing your changes.

Please ensure your code follows the existing style and includes necessary tests.

License
This project is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0) License.

See the LICENSE file for details.
