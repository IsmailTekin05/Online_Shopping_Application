# 🛒 Online Shopping Application

A complete e-commerce application built entirely in **Java**, designed for both **customers** and **sellers**. Developed with **Apache NetBeans**, powered by **Apache Ant**, and backed by a **MySQL** database.

# Table of Contents

- [Key Features](#key-features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Database Model](#database-model)
- [Getting Started](#getting-started)
- [License](#license)
- [Contributors](#contributors)
- [Conclusions](#conclusions)

# Key Features

The application supports two distinct user roles: **Customer** and **Seller**.

## Customer Features

- Register & login
- Browse and view products
- Add/remove/update items in cart
- Place orders and checkout
- View order history

## Seller Features

- Secure login
- Manage product inventory (add/edit/delete/view)
- View customer list
- View all and pending orders
- Dashboard showing total products, orders, and customers

## Feature Summary Table

| Feature            | Description                          | Role             |
| ------------------ | ---------------------------------- | ---------------- |
| Authentication     | Secure login/logout & registration | Customer, Seller |
| Product Browsing   | View products & details             | Customer         |
| Cart Operations    | Manage cart items and total cost   | Customer         |
| Order Management   | Place and view orders               | Customer         |
| Product Management | Manage product inventory            | Seller           |
| Customer Management| View all registered customers       | Seller           |
| Order Oversight    | View and manage orders              | Seller           |
| System Dashboard   | Overview of system metrics          | Seller           |

# Technology Stack

| Component   | Technology      |
| ----------- | --------------- |
| Language    | Java            |
| IDE         | Apache NetBeans |
| Build Tool  | Apache Ant      |
| Database    | MySQL           |

This project uses traditional Java tooling (NetBeans + Ant), unlike modern stacks with Spring Boot or Maven.

# Project Structure

Online_Shopping_Application/

├── build/ # Compiled files and JARs

├── nbproject/ # NetBeans project metadata

├── src/ # Source code

├── schema.sql # Database schema

├── build.xml # Ant build script

└── LICENSE # License file

less
Copy
Edit

# Database Model

The main tables include:

| Table             | Purpose                           |
| ----------------- | -------------------------------- |
| customer          | Customer account data             |
| seller            | Seller info                      |
| product           | Product details                  |
| cart              | Customer cart reference          |
| cart_item         | Products inside the cart         |
| orders            | Placed orders                   |
| order_product     | Products associated with an order|
| favorites         | Customer favorites               |
| payment_info      | Payment card details             |
| review            | Customer reviews                 |
| coupon            | Discount codes                  |
| shipping_company  | Shipping company details         |
| address           | Shipping address details         |

# Getting Started

## Prerequisites

- Java Development Kit (JDK)
- Apache NetBeans IDE (8.2 or newer)
- MySQL database (5.5 or newer)

## Installation

bash
git clone https://github.com/IsmailTekin05/Online_Shopping_Application.git
cd Online_Shopping_Application

Create a new MySQL database, e.g., online_shopping_db.

Import the schema:

bash
Copy
Edit
mysql -u your_user -p your_database < schema.sql
Open the project in NetBeans (File > Open Project), select the cloned folder.

Configure database connection parameters (e.g., in DBConnection.java).

Running the Project
Build: Right-click project → Clean and Build.

Run: Right-click project → Run.

License
This project is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0) license.

You may share (copy and redistribute) the material.

You must provide attribution.

You may not distribute modified versions.

Contributors
Developed collaboratively by four contributors.

Conclusions
This project is a traditional Java e-commerce prototype demonstrating:

Role-based features for customers and sellers.

Use of NetBeans and Ant for build and development.

MySQL relational database integration.
