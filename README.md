# 🛒 Online Shopping Application

A **comprehensive e-commerce application** developed entirely in **Java** using a traditional layered architecture. This project caters to both **customers** and **sellers**, providing functionalities from user registration to order processing. Developed using **Apache NetBeans**, built with **Apache Ant**, and backed by a **MySQL** relational database.

---

## Table of Contents

| Section            | Description                                      |
|--------------------|------------------------------------------------|
| [Key Features](#key-features)         | Main functionalities and user capabilities     |
| [Technology Stack](#technology-stack) | Tools, libraries, and platforms used           |
| [Project Structure](#project-structure) | Code organization and important modules        |
| [Database Model](#database-model)      | Detailed database tables and their purposes     |
| [Getting Started](#getting-started)     | Setup, installation, and configuration guide    |
| [Usage](#usage)                      | How to use the application and user flows       |
| [Contributing](#contributing)           | Contribution guidelines and process              |
| [License](#license)                   | License information                              |

---

## Key Features

| Feature                          | Description                                                                                           |
|---------------------------------|-----------------------------------------------------------------------------------------------------|
| **User Registration & Authentication** | Secure sign-up and login system for customers and sellers, with password hashing and validation.    |
| **Product Management**              | Sellers can add new products, update existing listings (name, price, stock, details), and delete products. |
| **Shopping Cart**                  | Customers can add multiple products to their cart, modify quantities, and remove items before checkout. |
| **Order Processing & Payment**    | Customers place orders with payment info stored securely; orders record product prices at purchase time. |
| **Coupon & Discount System**      | Supports coupon codes for discounts linked to sellers or games, with expiration dates and validation. |
| **Search & Filtering**             | Customers can search products by name, category, and filter by price, color, size, or rating.        |
| **Favorites & Reviews**            | Customers can mark products as favorites and leave reviews with star ratings and comments.           |
| **Referral & Invitation System**  | Tracks customer referrals to encourage user base growth.                                             |
| **Game-related Features**          | Supports gaming-related product categories with reset hours and tracking of customer plays.          |
| **Shipping & Delivery Management**| Integration with shipping companies; sellers have shipment IDs for order dispatching.                |
| **Responsive & Intuitive UI**      | Simple, clean interface for desktop users developed with Java Swing (or similar UI libraries).        |

---

## Technology Stack

| Layer              | Technologies                          |
|--------------------|-------------------------------------|
| Programming Language| Java (JDK 8+)                       |
| IDE                | Apache NetBeans                     |
| Build Tool         | Apache Ant                         |
| Database           | MySQL                             |
| Database Access    | JDBC (Java Database Connectivity)  |
| Web Server         | (Optional) Apache Tomcat for servlet hosting |
| Libraries & Tools  | Java Swing (for GUI), Java Collections Framework |

---

## Project Structure


---

## Database Model

| Table Name       | Purpose                                                                                          |
|------------------|-------------------------------------------------------------------------------------------------|
| **address**          | Stores address details, including address name, city, district, street, and building number.     |
| **cart**             | Represents a customer's shopping cart.                                                          |
| **cart_item**        | Stores individual products and their quantities within a specific shopping cart.                 |
| **cartitem_product** | Links products to items in a shopping cart.                                                     |
| **coupon**           | Stores information about discount coupons, including code, discount amount, expiration date, and associated seller or game. |
| **customer**         | Manages customer accounts, including name, phone number, email, password, height, age, and referrer ID. |
| **customer_product** | Tracks products associated with a customer, including quantity.                                  |
| **favorites**        | Stores products that customers have marked as favorites.                                        |
| **game**             | Stores details about games, including game name, type, and reset hours.                         |
| **invites**          | Records customer invitations or referrals.                                                      |
| **order_product**    | Links products to specific orders and includes the price at the time of order.                  |
| **orders**           | Records completed customer orders, including order date, customer ID, and address name.         |
| **payment_info**     | Stores customer payment card details such as card number, expiration date, CVC, card owner, and card name. |
| **plays**            | Records instances of customers playing games, including play time.                              |
| **product**          | Contains details for all products available for sale, such as product name, price, color, size, material, stock, and associated seller. |
| **review**           | Stores customer reviews for products, including star rating, comment, and rating date.          |
| **seller**           | Stores seller information, including seller name, experience, rating, and associated shipment ID. |
| **shipping_company** | Stores details about shipping companies, including company name.                                |

---

## Getting Started

### Prerequisites

- Java JDK 8 or higher installed on your system
- Apache NetBeans IDE (recommended) for easy project management and development
- Apache Ant installed and configured for building the project
- MySQL Server installed and running locally or remotely
- Basic familiarity with Java development and SQL queries

### Installation Steps

1. **Clone the repository:**

    ```bash
    git clone https://github.com/IsmailTekin05/Online_Shopping_Application.git
    ```

2. **Import the project:**

    - Open Apache NetBeans.
    - Select *File > Open Project* and navigate to the cloned folder.

3. **Configure the database:**

    - Create a new MySQL database (e.g., `online_shopping`).
    - Run the `database.sql` script to create tables and insert initial seed data.
    - Edit the database connection settings in `src/util/DBConnection.java` to match your MySQL credentials.

4. **Build and run the project:**

    - Open a terminal in the project directory.
    - Execute the following commands to clean, build, and run:

    ```bash
    ant clean build run
    ```

---

## Usage

- **User Registration & Login:** New users can sign up as either customers or sellers and log in securely.
- **Seller Dashboard:** Sellers can manage product listings by adding, updating, or deleting products with detailed attributes like price, color, size, material, and stock.
- **Customer Shopping Experience:** Customers can browse products by category, search and filter by various criteria, add items to their cart, and modify cart contents before purchase.
- **Checkout & Payment:** Customers place orders by confirming cart contents and providing payment information, securely stored for future transactions.
- **Order Management:** Both customers and sellers can view order statuses. Admin functionality allows management of users and overall order tracking.
- **Coupons & Discounts:** Customers can apply valid coupon codes for discounts linked to specific sellers or games.
- **Favorites & Reviews:** Customers can save favorite products for quick access and submit product reviews with ratings and comments.
- **Referral System:** Customers can invite others, helping grow the user base.
- **Gaming Integration:** Supports products linked to games with special gameplay tracking features.
- **Shipping Integration:** Sellers associate with shipping companies to manage deliveries effectively.

---

## Contributing

We welcome contributions to improve this project! To contribute:

1. Fork the repository.
2. Create your feature branch:

    ```bash
    git checkout -b feature/your-feature-name
    ```

3. Commit your changes with clear messages:

    ```bash
    git commit -m "Add feature: description"
    ```

4. Push your branch:

    ```bash
    git push origin feature/your-feature-name
    ```

5. Open a Pull Request on GitHub explaining your changes.

Please adhere to the existing code style and include tests where applicable.

---

## License

This project is licensed under the **Creative Commons Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0)** License.

See the [LICENSE](LICENSE) file for full details.

---

**Thank you for checking out the Online Shopping Application! Happy coding!** 🚀
