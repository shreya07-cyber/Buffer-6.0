**SmartSpend**
**Overview**
SmartSpend is a modular Java-based expense management system designed to help users manage personal and group expenses efficiently. It combines contact and transaction management with budget tracking, summaries, and analytics. The project is divided into multiple modules to ensure clarity, scalability, and ease of maintenance.

This README describes two main components of the system:

Contact and Group Transaction Manager ()

Transaction Service Module (Analytical and Budgeting Module)

**Module 1: Contact and Group Transaction Manager**
Purpose
Handles contact management and enables users to perform bill splitting, manage pending payments, and record financial interactions between individuals or within groups.

Key Functionalities

Contact management using Binary Search Tree (add, update, delete, display)

Group creation and management using Hash Map with quadratic probing

Bill splitting with friends or groups (equal, custom, percentage-based)

Adding transactions for pending settlements

Password-protected payment settlements

Viewing past transactions with sorting options (by date, time, or amount)

Data Structures Used

Binary Search Tree for contact storage

Array-based hash map for group handling

ArrayLists for transaction records

Pending Work

Trip expense management module (menu option 7)

**Module 2: Transaction Service Module**
Purpose
Provides insights, budget control, and transaction summaries for smarter expense handling. Designed to be integrated with analytical tools and user-facing dashboards.

**Key Functionalities**:

Add transactions with description, category, amount, and date

Show recent transactions in tabular format

Category-wise summary using bar chart-style visualization

Set and track a user-defined monthly budget

Predict future expenses using a sliding window approach (based on past 3 months)

Efficient range queries using Segment Tree

Data Structures Used

Deque (for maintaining the latest 50 transactions)

HashMap (for category totals)

TreeMap (for date-based tracking and prediction)

Segment Tree (for future analytics)

**Integration Points**

Works with modules like User Management, AI Budget Advisor, Group Splitter, and Dashboard UI

**Usage**
The modules can be run as independent services or integrated into a CLI or GUI.

The transaction module can be extended to include file/database-based persistence.

Interactions are currently handled via terminal inputs, and the system is designed for future GUI or web-based control panels.

**License**
This project is open for academic and educational use. Contributions and extensions are welcome.



**Contact, Group, and Trip Expense Manager**
**Overview**
This module serves as the core of SmartSpend's expense interaction system. It manages contacts, facilitates group and individual bill splits, and introduces a comprehensive trip expense management feature. Designed for both personal and collaborative expense tracking, this module offers intuitive CLI-based operations and is structured for future integration with graphical interfaces and analytical tools.

**Core Functionalities**
**Contact Management**
Efficiently manage contact information using a Binary Search Tree. Supports operations such as:

Add, update, delete, and search contacts

In-order display of contact records

**Group Management**
Organize contacts into named groups using a custom hash map with quadratic probing. Features include:

Group creation and contact assignment

Group-level bill splitting and transaction tracking

Bill Splitting
Supports expense splitting between individuals or within groups through:

Equal distribution

Custom amounts

Percentage-based splits

Transaction Recording
Maintains a global ledger of financial interactions with:

Classification as personal or group transactions

Directional tagging: incoming (receivable) or outgoing (payable)

Password-protected settlement marking

Historical transaction display with sort options (date, time, or amount)

Trip Expense Management
A specialized feature for handling travel-related expenses involving multiple participants:

Create trips and assign contacts

Log detailed expense transactions per trip

Internally builds a weighted graph (adjacency matrix) to represent who owes whom

Calculates and displays minimized settlements using a greedy algorithm

Visual representation of the debt matrix and reduced payment paths

Technical Details
Data Structures Used

Binary Search Tree (BST) – For storing and retrieving contacts efficiently

Hash Map with Quadratic Probing – For group mapping and collision handling

ArrayLists – For dynamic transaction storage

2D Matrix and Hash Maps – For trip-level graph representation and lookups

Custom Segment Tree (in complementary modules) – For analytical range queries

Security

Password authentication is required for confirming and settling transactions.

Execution & Integration
Can be executed directly via terminal or integrated into CLI-based workflows.

Serves as a foundational backend service for group and trip expense functionalities.

Future-ready for integration with GUI dashboards, persistence layers, and analytical modules.

**Future Enhancements**
Integration with file-based or database persistence mechanisms

Enhanced user interface for improved accessibility

Automated notification and reminder systems for unsettled transactions

Real-time syncing with other SmartSpend modules (e.g., analytics, budgeting)
  **Video link**-https://drive.google.com/drive/folders/1sWHfIlcKI_ceJPgkRvkyd4-uFY7yl24I?usp=drive_link
