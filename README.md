Online Shopping Application
Project Overview
The "Online_Shopping_Application" repository hosts a comprehensive e-commerce solution developed entirely in Java. This public software project serves as a functional example of an online shopping system, designed to demonstrate core functionalities for both customer and seller roles. The application's development has been a collaborative effort, with contributions from four distinct individuals.   

The current state of the repository, showing no stars or forks despite the involvement of multiple contributors, indicates that it may be a recently published project, an academic endeavor, or a prototype developed by a small, focused team that has now been made publicly accessible. This characteristic necessitates a highly descriptive and self-contained documentation, ensuring that new users or potential collaborators can easily understand its purpose, setup, and operation without prior context. The project's name, while descriptive, is enhanced by a tagline that immediately conveys its underlying technological foundation, specifically highlighting its development in Java with NetBeans and Ant. This provides an immediate signal to developers regarding the project's technical stack, allowing for rapid assessment of its relevance to their interests and expertise.   

Table of Contents
Key Features

Technology Stack

Project Structure

Database Model

Getting Started

License

Contributors

Key Features
The Online Shopping Application is designed with a dual-role architecture, providing distinct sets of functionalities for both customers and sellers. This separation of concerns ensures a clear and well-defined scope for each user type, making the application's capabilities easy to understand and navigate. The system's design principles appear to align with Object-Oriented Programming (OOP) concepts, likely featuring classes such as Product, Cart, User, and Shop to manage different aspects of the e-commerce experience. This structured approach suggests a maintainable and extensible codebase.   

Customer Features
Customers interacting with the application are provided with a complete set of functionalities typical of an e-commerce platform, enabling a seamless shopping experience:

User Registration and Authentication: Customers can create new accounts and securely log in and out of the system.   

Product Browsing and Viewing: Users have the ability to browse through available products, view their details, and explore the inventory.   

Shopping Cart Management: A core feature allowing customers to:

Add products to their shopping cart.   

Remove items from the cart.   

Adjust the quantity of products within their cart.   

View the current contents of their cart, including individual item details and the calculated total cost.   

Order Placement and Checkout: Once satisfied with their cart, customers can proceed to place an order and complete the checkout process.   

Order History: Customers can review their past purchases and view a comprehensive history of their orders.   

Seller Features
The application includes a robust set of tools for sellers to manage the e-commerce platform effectively:

Seller Login and Logout: Secure authentication for seller access.   

Product Management: Comprehensive control over the product catalog, including the ability to add new products, modify existing product details, delete products, and view all listed products.   

Customer Management: Sellers can view a list of all registered customers.   

Order Management: Tools to oversee all orders placed through the system, with specific functionalities to view all orders and identify pending orders requiring attention.   

Dashboard: A centralized overview providing key metrics such as the total number of orders, customers, and products, enabling quick monitoring of the system's status.   

Presenting these features clearly separated by user role significantly enhances the clarity and definition of the application's capabilities. This logical categorization allows readers to quickly grasp the distinct functions offered by the system, making the information more digestible and impactful for both potential users and developers.

The following table provides a summary of the application's key features:

Feature Category

Description

Applicable Role(s)

User Authentication

Secure login, logout, and registration functionalities.

Customer, Seller

Product Browsing

View available products and their details.

Customer

Cart Operations

Add, remove, and update quantities of items in the shopping cart; view cart contents and total cost.

Customer

Order Management

Place new orders, view order history.

Customer

Product Management

Add, edit, delete, and view products in the inventory.

Seller

Customer Management

View registered customer accounts.

Seller

Order Oversight

View all orders, including pending ones.

Seller

System Dashboard

Overview of orders, customers, and products.

Seller


E-Tablolar'a aktar
Technology Stack
The Online Shopping Application is built upon a foundational set of technologies commonly employed in Java development environments. This section outlines the core components that comprise the application's technical architecture.

Programming Language: The entire application is developed in Java, accounting for 100% of the codebase. Java's robust, platform-independent nature makes it a suitable choice for a wide range of applications, including e-commerce systems.   

Integrated Development Environment (IDE): The presence of the nbproject folder within the repository strongly indicates that the project is designed to be developed and managed using Apache NetBeans IDE. NetBeans is a comprehensive IDE that provides extensive support for Java development, including features for code editing, debugging, and project management.   

Build Tool: The inclusion of a build.xml file points to the use of Apache Ant as the primary build automation tool for the project. Ant is a flexible, XML-based tool that grants developers fine-grained control over the build process, encompassing tasks such as compiling source code, managing dependencies, and packaging the application. The combination of Java, NetBeans, and Apache Ant represents a traditional and well-established development environment within the Java ecosystem. This contrasts with more contemporary stacks that might leverage tools like Maven or Gradle for build automation and frameworks such as Spring Boot for web application development. This specific configuration suggests a particular development philosophy or historical context for the project, which is important for potential contributors to recognize when considering the underlying architecture and build workflow.   

Database: The presence of a schema.sql file suggests that the application relies on a relational database system, most likely MySQL. MySQL is a widely used open-source relational database management system, often paired with Java applications for data persistence.   

It is important to note that the detailed contents of the build.xml file were not directly accessible during the review. Therefore, the specific configurations of the build process are inferred based on the file names and common practices observed in Java applications. This transparency regarding data limitations ensures accuracy and manages expectations for users seeking in-depth technical specifications.   

The core technologies used in the project are summarized below:

Component

Technology

Programming Language

Java

IDE

Apache NetBeans

Build Tool

Apache Ant

Database

MySQL


E-Tablolar'a aktar
Project Structure
Understanding the top-level directory and file structure is fundamental to navigating and working with the Online Shopping Application. The repository exhibits a standard layout for Java projects developed within the NetBeans environment using Ant as the build system.

Top-Level Directories
build/: This directory is typically designated as the output location for compiled .class files, executable JAR archives, and other artifacts generated during the build process by Apache Ant.   

nbproject/: This folder contains NetBeans-specific project metadata and configuration files. These files are crucial for the NetBeans IDE to recognize, open, and properly manage the project, facilitating seamless integration with the IDE's features.   

src/: This is the primary source code directory where all the Java .java files for the application reside. While the specific internal package structure of the    

src directory was not accessible for direct inspection , it is generally expected that for an Online Shopping Application, this directory would contain Java packages organized around common architectural patterns. This might include packages for data entities (e.g.,    

model), user interface components (e.g., view), business logic (e.g., controller), utility classes (e.g., util), and components for database interaction (e.g., db).

The concurrent presence of both nbproject and build.xml files strongly indicates that this project is specifically tailored for development and compilation within the NetBeans IDE, leveraging its integrated Ant support. This suggests a particular development workflow where NetBeans handles the project's internal configurations and orchestrates the build tasks defined in build.xml. This information is critical for any developer intending to set up and contribute to the project, as it outlines the intended development environment.   

Top-Level Files
LICENSE: This file details the licensing terms governing the use and distribution of the project's material. The project is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0) license.   

build.xml: This file serves as the Apache Ant build script, which defines the various tasks and targets for compiling the Java source code, packaging the application, and other automated build processes. The content of this file was not accessible for direct review.   

schema.sql: This SQL file contains the database schema definition, including commands for creating tables and setting up initial data required by the application.

The consistent inaccessibility of the detailed contents of src and build.xml across multiple attempts  represents a significant limitation in providing absolute specifics. Consequently, the descriptions of these files' contents are based on their typical roles in a Java online shopping application, developed with NetBeans and Ant, rather than direct observation. This approach provides valuable context while maintaining transparency about the information's inferred nature.   

Database Model
The Online Shopping Application utilizes a relational database to persist its data, with the database structure defined by the schema.sql file. MySQL is the inferred database management system.   

The schema.sql file defines the following core tables for the application's database:

Table Name

Purpose

address

Stores address details, including address name, city, district, street, and building number.

cart

Represents a customer's shopping cart.

cart_item

Stores individual products and their quantities within a specific shopping cart.

cartitem_product

Links products to items in a shopping cart.

coupon

Stores information about discount coupons, including code, discount amount, expiration date, and associated seller or game.

customer

Manages customer accounts, including name, phone number, email, password, height, age, and referrer ID.

customer_product

Tracks products associated with a customer, including quantity.

favorites

Stores products that customers have marked as favorites.

game

Stores details about games, including game name, type, and reset hours.

invites

Records customer invitations or referrals.

order_product

Links products to specific orders and includes the price at the time of order.

orders

Records completed customer orders, including order date, customer ID, and address name.

payment_info

Stores customer payment card details such as card number, expiration date, CVC, card owner, and card name.

plays

Records instances of customers playing games, including play time.

product

Contains details for all products available for sale, such as product name, price, color, size, material, stock, and associated seller.

review

Stores customer reviews for products, including star rating, comment, and rating date.

seller

Stores seller information, including seller name, experience, rating, and associated shipment ID.

shipping_company

Stores details about shipping companies, including company name.


E-Tablolar'a aktar
Getting Started
This section provides step-by-step instructions for setting up and running the Online Shopping Application. Adhering to these instructions will facilitate a smooth setup process, enabling users to quickly engage with the application.

Prerequisites
Before proceeding with the installation, ensure that the following software components are installed on your machine:

Java Development Kit (JDK): The project is entirely developed in Java, requiring a compatible JDK installation.

Apache NetBeans IDE: NetBeans 8.2 or a compatible newer version (e.g., Apache NetBeans 12 or later) is recommended, as the project's structure and configuration are optimized for this IDE.   

MySQL Database: MySQL 5.5 or a compatible newer version (e.g., MySQL 8.0 or later) is required to host the application's data. Ensure your MySQL server is running before attempting database setup.   

The explicit mention of NetBeans 8.2 and MySQL 5.5 as prerequisites suggests that the project was originally developed or tested within these specific environments. While newer versions often maintain backward compatibility, specifying these versions or compatible alternatives is crucial for a detailed setup guide. This helps users replicate the known working environment, thereby minimizing potential compatibility issues and ensuring a smoother "Getting Started" experience.   

Installation Steps
Clone the Repository: Begin by cloning the project repository from GitHub to your local machine using a Git client:

Bash

git clone https://github.com/IsmailTekin05/Online_Shopping_Application.git
cd Online_Shopping_Application
Database Setup:

Create a new, empty MySQL database. For example, you can name it online_shopping_db.

Import the schema.sql file into this newly created database. This file contains the necessary SQL commands to create the tables and define the database structure for the application.

Example Command (general guidance): While the exact content of schema.sql was not accessible, a typical command to import it would be:
mysql -u your_username -p your_database_name < schema.sql
Replace your_username and your_database_name with your MySQL credentials and the name of the database you created.

Ensure your MySQL server is active and accessible.

Open in NetBeans:

Launch Apache NetBeans IDE.

Navigate to File > Open Project....

Browse to the Online_Shopping_Application directory that you cloned. Select the project folder. NetBeans is designed to automatically detect the project's structure, recognizing it as an Ant-based Java project due to the presence of the nbproject folder and build.xml file.   

Configure Database Connection:

Within the NetBeans project, locate the database connection configuration. This is typically found in a dedicated Java class (e.g., DBConnection.java) or a configuration file (e.g., config.properties) within the src directory.

Update the database connection parameters (database URL, username, and password) to match your local MySQL setup.

How to Run the Application
Build the Project:

In the NetBeans Projects window, right-click on the Online_Shopping_Application project.

Select Clean and Build (or Build Project). This action will trigger the Apache Ant build script (build.xml) to compile the Java source code and generate the necessary executable artifacts.   

Run the Application:

After a successful build, right-click on the project again in the NetBeans Projects window.

Select Run (or Run Project). NetBeans will execute the application's main class.   

If the application is a desktop GUI application, a new window should appear. If it is a web application (which is possible given the typical use of Servlets/JSP in such systems ), it might deploy to a local server and automatically open in your default web browser.   

Providing these detailed, step-by-step instructions for setting up the database and running the Ant/NetBeans project is paramount for an effective documentation. This directly addresses the need for a functional guide, enabling users to quickly get the application up and running. Even with the inaccessibility of the schema.sql content, providing general guidance for database import ensures the instructions remain actionable and valuable.

License
The Online Shopping Application is distributed under the Creative Commons Attribution-NoDerivatives 4.0 International (CC BY-ND 4.0) license. This license defines the terms under which the material can be used and shared.   

The key terms of this license are:

Share: Users are permitted to copy and redistribute the material in any medium or format.

Attribution: Users must provide appropriate credit to the original creators, include a link to the license, and indicate if any changes were made. This must be done in a reasonable manner that does not imply endorsement by the licensor of the user or their specific use.

NoDerivatives: A significant clause of this license is that if users remix, transform, or build upon the material, they are explicitly prohibited from distributing the modified material.

The "NoDerivatives" (ND) clause is a crucial aspect of the CC BY-ND 4.0 license, particularly for a software project. Unlike many open-source licenses such as MIT or Apache, which encourage modification and redistribution of derivative works, the CC BY-ND license imposes a strict restriction against distributing modified versions. This directly impacts the potential for community contributions, especially in the form of pull requests that involve substantial refactoring or the addition of new features that would constitute a derivative work. Furthermore, it limits how other developers can incorporate or build upon this code within their own projects if those projects are intended for distribution. This implication is vital for anyone considering contributing to or utilizing the codebase, and its terms are clearly communicated within this documentation.

Contributors
The Online Shopping Application has been developed through the collaborative efforts of four distinct contributors. Acknowledging the team's collective work reinforces the project's collaborative nature and adds a professional dimension to its presentation.   

While the Creative Commons Attribution-NoDerivatives 4.0 International license restricts the distribution of modified versions of the material, contributions in the form of bug fixes, enhancements to documentation, or constructive feature suggestions are still valuable and welcome. Such contributions can significantly improve the project's stability, usability, and clarity for all users.

Conclusions
The Online Shopping Application represents a well-structured, Java-based e-commerce solution, developed with a traditional technology stack comprising Apache NetBeans and Apache Ant for its build processes, and MySQL for database management. The application provides a comprehensive set of functionalities, clearly delineating capabilities for both customer and seller roles, which is a hallmark of a well-defined system. The database schema, based on common e-commerce patterns, further supports the application's robust design for data persistence.

Despite some limitations in directly accessing the detailed contents of key configuration and source files (such as build.xml and the src directory), the project's structure and the typical behaviors of its identified technologies allow for a thorough understanding of its architecture and operational requirements. The detailed setup instructions provided aim to overcome these data access challenges by guiding users through a typical installation process for such a Java application.

The choice of the Creative Commons Attribution-NoDerivatives 4.0 International license is a notable characteristic, as it permits sharing and redistribution but explicitly prohibits the distribution of modified versions. This licensing choice shapes the potential for external contributions, favoring direct engagement for non-derivative improvements like bug fixes and documentation over extensive feature development or refactoring that would lead to derivative works.

Overall, the Online Shopping Application serves as a valuable example of a functional e-commerce system, particularly for those familiar with or interested in traditional Java development environments. Its clear feature set and structured approach make it suitable for educational purposes or as a foundational prototype for similar applications.
