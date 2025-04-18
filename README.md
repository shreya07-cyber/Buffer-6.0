SmartSpend — CLI-Based Expense & Budget Manager (DSA-Driven)

SmartSpend is a command-line application for managing shared group expenses, personal budgeting, and trip settlements. Built around efficient Data Structures and Algorithms (DSA), it focuses on performance, real-world usability, and clarity in financial tracking. Designed without databases or GUI, it processes all data in-memory, ideal for both quick usage and educational demonstrations.


->Overview

SmartSpend is split into two core modules:

- Budgeting Module – For managing individual budgets, forecasting expenses, and analyzing spending behavior
- Split and Trip Module – For managing group expenses, tracking dues, and handling trip settlements


->Budgeting Module

This module supports personal financial planning, category-based spending analysis, and budget forecasting.

Key Features:

- Transaction Storage: Uses a double-ended queue (Deque) to store transaction history. This supports fast insertions and retrievals from both the front and back of the list — useful for recent and historical views.

- Transaction Categorization: Utilizes a Trie to auto-categorize transactions based on description keywords, allowing space-efficient storage and prefix-based matching.

- Category-Wise Budgeting: A HashMap links each spending category with its allocated budget and total expenditure, allowing for real-time updates and comparisons.

- Zero-Based Budget Allocation: Implements a HashMap to allocate every unit of income to specific categories, promoting disciplined and intentional budgeting.

- Spending Prioritization: A Max Heap (priority queue) ranks categories by their spending levels, ensuring instant visibility into where money is most consumed.

- Monthly Budget Forecasting: A combination of TreeMap and a sliding window algorithm is used to analyze historical expenses and predict upcoming trends based on the last three months of data.

- Real-Time Tracking: A Segment Tree enables efficient computation of total expenses within any specified date range, making daily or weekly financial checks possible in logarithmic time.

----------------------------------------------------------------------------------------------------------------------------


->Split and Trip Module

Key Features:

- Contact Management: Contacts are stored using a Binary Search Tree (BST), allowing efficient insertion, deletion, and lookup by name while maintaining sorted order.

- Group Expense Storage: Each group is tracked using a HashMap, where each group stores an array of contacts. This allows for quick access of group during split payments.

- Transaction Logging: Every financial transaction is recorded sequentially using an ArrayList, supporting fast appending and iteration.

- Direction-Based Splits: Each transaction includes a flag indicating the direction of money flow — either “incoming” or “outgoing” — making it simple to track who owes whom and how much.

- Trip Management: A directed graph implemented via an adjacency matrix represents the debt relationships among group members.

- Trip Settlement: Graph reduction techniques are applied to minimize the number of required transactions for settling debts. This is implemented using Greedy Approach for minimization of cash flow.


----------------------------------------------------------------------------------------------------------------------------


VIDEO LINK-https://drive.google.com/drive/folders/1sWHfIlcKI_ceJPgkRvkyd4-uFY7yl24I?usp=drive_link
