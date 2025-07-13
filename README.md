🛒 Online Shopping Application
A complete e-commerce application built entirely in Java, designed for both customers and sellers. Developed with Apache NetBeans, powered by Apache Ant, and backed by a MySQL database.

📌 Table of Contents
✨ Key Features

🧰 Technology Stack

📁 Project Structure

🗃️ Database Model

🚀 Getting Started

📄 License

🤝 Contributors

📌 Conclusions

✨ Key Features
The application supports two distinct user roles: Customer and Seller.

👤 Customer Features
✅ Register & login

🛍️ Browse and view products

🛒 Add/remove/update items in cart

💳 Place orders and checkout

📜 View order history

🛒 Seller Features
🔐 Secure login

📦 Manage product inventory (add/edit/delete/view)

👥 View customer list

📦 View all and pending orders

📊 Dashboard showing total products, orders, and customers

🗂️ Feature Summary Table
Feature	Description	Role
Authentication	Secure login/logout & registration	Customer, Seller
Product Browsing	View products & details	Customer
Cart Operations	Manage cart items, quantities, and total cost	Customer
Order Management	Place and view orders	Customer
Product Management	Full control over inventory	Seller
Customer Management	View all registered customers	Seller
Order Oversight	View and manage order statuses	Seller
System Dashboard	Overview of key system metrics	Seller

🧰 Technology Stack
Component	Technology
Language	Java (100%)
IDE	Apache NetBeans
Build Tool	Apache Ant
Database	MySQL

This project uses traditional Java tooling (NetBeans + Ant), differing from modern stacks that typically use Spring Boot or Maven. Understanding this helps contributors align with the intended development flow.

📁 Project Structure
bash
Copy
Edit
Online_Shopping_Application/
├── build/             # Compiled .class files and JARs
├── nbproject/         # NetBeans project metadata
├── src/               # Source code (organized by MVC or layered structure)
├── schema.sql         # Database schema
├── build.xml          # Apache Ant build script
└── LICENSE            # CC BY-ND 4.0 License
NetBeans and Ant integration is tightly coupled here. Contributors are advised to use NetBeans IDE for optimal project compatibility.

🗃️ Database Model
A relational schema defined in schema.sql. Below are the key tables:

Table	Purpose
customer	Customer account data
seller	Seller info (name, experience, rating)
product	Product details (price, size, color, stock)
cart	Customer cart reference
cart_item	Products inside the cart
orders	Placed orders and metadata
order_product	Product details associated with an order
favorites	Products marked as favorite
payment_info	Customer payment card details
review	Customer reviews and star ratings
coupon	Discount codes and rules
shipping_company	Shipping company details
address	Shipping address details
invites	Customer referral tracking
plays, game	Bonus: Game and engagement features

🚀 Getting Started
✅ Prerequisites
Java Development Kit (JDK)

Apache NetBeans (v8.2+ recommended)

MySQL (v5.5+)

📥 Installation Steps
bash
Copy
Edit
git clone https://github.com/IsmailTekin05/Online_Shopping_Application.git
cd Online_Shopping_Application
Create Database

Create a new MySQL database (e.g., online_shopping_db)

Import schema.sql using:

bash
Copy
Edit
mysql -u your_user -p your_database < schema.sql
Open in NetBeans

File → Open Project...

Select the cloned folder

NetBeans will detect the Ant-based Java project

Configure Database Connection

Locate configuration (e.g., DBConnection.java)

Update JDBC URL, username, and password

▶️ How to Run
Build: Right-click the project → Clean and Build

Run: Right-click again → Run Project

If it's a GUI app: a window will appear
If it's a web app: it may launch in a browser

📄 License
Creative Commons Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0)

You are free to:

✅ Share — copy and redistribute the material
But you must:

✅ Attribute — give proper credit

🚫 NoDerivatives — you may not distribute modified versions

This license encourages sharing, but restricts distribution of derivative works. Contributions via documentation, bug fixes, and suggestions are welcome.

🤝 Contributors
Developed by a team of four collaborators.
Thanks to each contributor for building this educational and functional project together.

📌 Conclusions
This application is a complete e-commerce prototype showcasing:

Solid Java fundamentals

Clear role separation (Customer vs Seller)

Traditional stack (NetBeans + Ant + MySQL)

Real-world schema design

Despite some file access limitations (e.g., full src/ tree or build.xml internals), the documentation and structure provide all the necessary context to understand and run the project.
It is ideal for:

Java learners

Academic demos

Prototypes based on desktop or lightweight web app structures

If you'd like to fork, learn from, or build on this project (within license limits) — welcome aboard! ⭐
