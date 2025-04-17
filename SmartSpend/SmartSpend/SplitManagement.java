package Buffer_2025;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Transaction {
	// Transaction details
	Contact contact;
	LocalDate date;
	String time;
	boolean paymentDone; // defaults to false
	String purpose;
	double amount;
	String transactionType; // "group" or "personal"
	String groupName; // name of the group, or null if personal
	String direction; // "incoming" or "outgoing"

	// Constructor with default values for date, time, and paymentDone
	Transaction(Contact contact, double amount, String transactionType, String groupName, String direction,
			String purpose) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		this.contact = contact;
		this.date = LocalDate.now(); // Set current date
		this.time = LocalTime.now().format(timeFormatter); // Format time as hrs:mins:secs
		this.paymentDone = false; // Defaults to false
		this.purpose = purpose;
		this.amount = amount;
		this.transactionType = transactionType;
		this.groupName = transactionType.equals("group") ? groupName : null;
		this.direction = direction;
	}

	// Method to display the transaction details
	void displayTransactionDetails() {
		// Print the transaction details with improved alignment for better readability
		System.out.printf("%-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s%n",
				contact.name, date, time, (paymentDone == true ? "DONE" : "UNDONE"), purpose, amount, transactionType,
				(groupName != null ? groupName : "N/A"), direction);
		System.out.println(
				"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}

	// Method to mark the transaction as done
	void markAsDone() {
		this.paymentDone = true;
		System.out.println("Transaction marked as done.");
	}
}

class Contact {
	Contact left, right;
	long phoneNumber;
	String name;
	String email;

	Contact(String name, long phoneNumber, String email) {
		this.left = null;
		this.right = null;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	// Method to display contact details
	void display() {
		System.out.println("Name: " + name);
		System.out.println("Phone: " + phoneNumber);
		System.out.println("Email: " + email);
	}
}

// Class for the binary search tree to manage contacts
class ContactBST {
	Contact root; // Root node of the tree
	Scanner sc = new Scanner(System.in);

	// Constructor initializes an empty tree
	ContactBST() {
		root = null;
	}

	boolean insertContactDirect(String name, long phoneNumber, String email) {
		Contact newContact = new Contact(name, phoneNumber, email);
		if (root == null) {
			root = newContact; // First contact becomes root
			return true;
		} else {
			Contact current = root;
			while (true) {
				if (name.compareToIgnoreCase(current.name) < 0) {
					if (current.left == null) {
						current.left = newContact;
						return true;
					}
					current = current.left;
				} else if (name.compareToIgnoreCase(current.name) > 0) {
					if (current.right == null) {
						current.right = newContact;
						return true;
					}
					current = current.right;
				} else {
					return false;
				}
			}
		}
	}

	void insertContact() {
		boolean flag = true;
		while (flag) {
			System.out.print("Enter Contact Name: ");
			String name = sc.nextLine();
			System.out.print("Enter Phone Number: ");
			long phoneNumber = sc.nextLong();
			sc.nextLine();
			System.out.print("Enter Email: ");
			String email = sc.nextLine(); // Now asking for email instead of balance

			Contact newContact = new Contact(name, phoneNumber, email);
			if (root == null) {
				root = newContact; // First contact becomes root
				System.out.println("Contact inserted!");
			} else {
				Contact current = root;
				while (true) {
					if (name.compareToIgnoreCase(current.name) < 0) {
						if (current.left == null) {
							current.left = newContact;
							System.out.println("Contact inserted!");
							break;
						}
						current = current.left;
					} else if (name.compareToIgnoreCase(current.name) > 0) {
						if (current.right == null) {
							current.right = newContact;
							System.out.println("Contact inserted!");
							break;
						}
						current = current.right;
					} else {
						System.out.println("Contact name already exists in contacts!");
						break;
					}
				}
			}
			System.out.print("Enter more? 0:no or 1:yes? ");
			int t = sc.nextInt();
			if (t == 0) {
				flag = false;
			} else {
				sc.nextLine(); // clear buffer
				System.out.println();
			}
		}
	}

	Contact search(String name) {
		Contact current = root;

		while (current != null) {
			if (name.equalsIgnoreCase(current.name)) {
				return current;
			}
			// Traverse left if the name is lexicographically smaller, else traverse right
			current = name.compareToIgnoreCase(current.name) < 0 ? current.left : current.right;
		}
		return null;
	}

	// Method to delete a contact by name
	void deleteContact() {
		System.out.print("Enter Name to delete: ");
		String name = sc.nextLine();
		if (searchContactByName(name)) {
			root = deleteRec(root, name);
			System.out.println("Contact deleted successfully!");
		} else {
			System.out.println("Contact not found!");
		}
	}

	// Recursive function to delete a contact node by name
	Contact deleteRec(Contact root, String name) {
		if (root == null) {
			return null;
		}
		if (name.compareToIgnoreCase(root.name) < 0) {
			root.left = deleteRec(root.left, name);
		} else if (name.compareToIgnoreCase(root.name) > 0) {
			root.right = deleteRec(root.right, name);
		} else {
			if (root.left == null)
				return root.right;
			if (root.right == null)
				return root.left;
			Contact minNode = findMin(root.right);
			root.name = minNode.name;
			root.phoneNumber = minNode.phoneNumber;
			root.email = minNode.email;
			root.right = deleteRec(root.right, minNode.name);
		}
		return root;
	}

	// Helper function to find the minimum node
	Contact findMin(Contact node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	// Method to display all contacts in ascending order by name
	void displayAllContacts() {
		if (root == null) {
			System.out.println("No contacts in the directory.");
		} else {
			System.out.println("Contacts:");
			inOrderTraversal(root);
		}
	}

	// In-order traversal to display contacts in sorted order by name
	void inOrderTraversal(Contact node) {
		if (node != null) {
			inOrderTraversal(node.left);
			node.display();
			inOrderTraversal(node.right);
		}
	}

	// Check if a contact exists by name
	boolean searchContactByName(String name) {
		Contact current = root;
		while (current != null) {
			if (name.equalsIgnoreCase(current.name)) {
				return true;
			}
			current = name.compareToIgnoreCase(current.name) < 0 ? current.left : current.right;
		}
		return false;
	}

	// Method to update contact details by name
	void updateContact() {
		System.out.print("Enter Name to update: ");
		String name = sc.nextLine();
		Contact current = root;

		while (current != null) {
			if (name.equalsIgnoreCase(current.name)) {
				System.out.println("Contact found!");
				current.display();
				System.out.print("Enter new name: ");
				current.name = sc.nextLine();
				System.out.print("Enter new phone number: ");
				current.phoneNumber = sc.nextLong();
				System.out.print("Enter new email: ");
				current.email = sc.nextLine(); // Now asking for new email instead of balance
				System.out.println("Contact updated successfully!");
				return;
			}
			current = name.compareToIgnoreCase(current.name) < 0 ? current.left : current.right;
		}
		System.out.println("Contact not found!");
	}
}

class Group {
	String GroupName;
	Contact[] GroupContacts;
	int numContacts;
	Scanner sc = new Scanner(System.in);

	Group(String GroupName, ContactBST bst) {
		this.GroupName = GroupName;
		GroupContacts = addContactsToGroup(bst);
	}

	Contact[] addContactsToGroup(ContactBST bst) {
		System.out.print("Enter number of contacts to add to the group: ");
		numContacts = sc.nextInt(); // Get the number of contacts to add
		sc.nextLine(); // Consume the leftover newline

		// Assume we know the maximum number of contacts a group can hold, e.g.,
		// MAX_CONTACTS = 20
		final int MAX_CONTACTS = 20;
		Contact[] groupContacts = new Contact[Math.min(numContacts, MAX_CONTACTS)];

		for (int i = 0; i < numContacts; i++) {
			boolean validContactAdded = false; // Flag to check if a valid contact was added

			// Loop until a valid contact is added or user opts to skip adding the contact
			while (!validContactAdded) {
				System.out.print("Enter contact name to add: ");
				String contactName = sc.nextLine(); // Get the contact name

				Contact contact = bst.search(contactName); // Search for the contact in the BST

				if (contact == null) {
					System.out.println("Contact '" + contactName + "' not found.");
					System.out.print("Do you want to try again? (y/n): ");
					String tryAgain = sc.nextLine().trim().toLowerCase();

					if (!tryAgain.equals("y")) {
						validContactAdded = true; // Exit the loop if user doesn't want to try again
					}
				} else {
					groupContacts[i] = contact; // Add the contact node to the array
					System.out.println("Contact '" + contactName + "' added to the group.");
					validContactAdded = true; // Valid contact added, exit the loop
				}
			}
		}

		return groupContacts; // Return the array of contacts added to the group
	}
}

class GroupMap {
	Scanner sc = new Scanner(System.in);
	final int SIZE = 30;
	Group[] groups = new Group[SIZE];
	// List<Contact>[] groupContacts = new ArrayList[SIZE];

	private int hash(String key) {
		return Math.abs(key.hashCode() % SIZE);
	}

	void AddGroup(String groupName, ContactBST bst) {
		int hashVal = hash(groupName);
		int i = 1;

		while (groups[hashVal] != null && !groups[hashVal].equals(groupName)) {
			hashVal = (hashVal + i * i) % SIZE; // Quadratic probing
			i++;
		}

		if (groups[hashVal] == null) {
			groups[hashVal] = new Group(groupName, bst);
			// groupContacts[hashVal] = addContactsToGroup();
			System.out.println("Group '" + groupName + "' created.");
		} else {
			System.out.println("Group '" + groupName + "' already exists.");
		}
	}

	Group get(String groupName) {
		int hashVal = hash(groupName);
		int i = 1;

		while (groups[hashVal] != null) {
			if (groups[hashVal].GroupName.equals(groupName)) {
				return groups[hashVal];
			}
			hashVal = (hashVal + i * i) % SIZE; // Quadratic probing
			i++;
		}
		return null; // Group not found
	}

	boolean displayAllGroups() {
		boolean anyGroupFound = false;

		for (int i = 0; i < SIZE; i++) {
			if (groups[i] != null) {
				if (!anyGroupFound)
					System.out.println("Available groups: ");
				anyGroupFound = true;
				System.out.println("Group: " + groups[i].GroupName);
				System.out.println("Contacts in this group:");

				for (Contact contact : groups[i].GroupContacts) {
					if (contact != null) {
						System.out.println(" - " + contact.name); // Assuming Contact class has getName() method
					}
				}
				System.out.println();
			}
		}

		if (!anyGroupFound) {
			return false;
		}
		return true;
	}
}

// TRIP MANAGEMENT

class TripTransaction {
	String purpose;
	Contact payee; // "me" means the user
	double amount;
	List<Contact> involved;
	String splitType;

	TripTransaction(String purpose, Contact payee, double amount, List<Contact> involved, String splitType) {
		this.purpose = purpose;
		this.payee = payee;
		this.amount = amount;
		this.involved = involved;
		this.splitType = splitType;
	}
}

class TripGraph {
	Map<String, Integer> contactIndexMap; // Maps contact name to index
	Contact[] indexToContact; // Index to Contact
	double[][] matrix; // matrix[i][j] = how much j owes i
	int size;

	TripGraph(Collection<Contact> tripContacts) {
		this.size = tripContacts.size() + 1; // +1 for 'me'
		this.contactIndexMap = new HashMap<>();
		this.indexToContact = new Contact[size];
		this.matrix = new double[size][size];

		int i = 0;
		for (Contact c : tripContacts) {
			contactIndexMap.put(c.name.toLowerCase(), i);
			indexToContact[i] = c;
			i++;
		}

		// Add dummy contact for 'me'
		Contact user = new Contact("me", 0, "me@example.com");
		contactIndexMap.put("me", i);
		indexToContact[i] = user;
	}

	void addTransaction(String payer, String payee, double amount) {
		int i = contactIndexMap.get(payer.toLowerCase()); // Error here
		int j = contactIndexMap.get(payee.strip().toLowerCase());
		matrix[i][j] += amount;
	}

	double[][] getMatrix() {
		return matrix;
	}

	Contact getContact(int index) {
		return indexToContact[index];
	}

	int getSize() {
		return size;
	}
}

class Trip {
	String tripName;
	boolean isSettled;
	HashMap<String, Contact> tripContacts; // key: contact name
	List<TripTransaction> transactions;
	TripGraph graph;

	Trip(String tripName) {
		this.tripName = tripName;
		this.isSettled = false;
		this.tripContacts = new HashMap<>();
		this.transactions = new ArrayList<>();
		this.graph = new TripGraph(this.tripContacts.values());
	}

	void addContact(Contact contact) {
		if (!contact.name.equalsIgnoreCase("me")) {
			tripContacts.put(contact.name.toLowerCase(), contact);
		}
	}

	void removeContact(String contactName) {
		tripContacts.remove(contactName.toLowerCase());
	}

	boolean hasContact(String contactName) {
		return tripContacts.containsKey(contactName.toLowerCase());
	}

	Collection<Contact> getAllContacts() {
		return tripContacts.values();
	}
}

class TripManager {
	List<Trip> trips = new ArrayList<>();
	Scanner sc = new Scanner(System.in);

	void listAllTrips() {
		if (trips.isEmpty()) {
			System.out.println("No trips available.");
			return;
		}

		System.out.println("\n--- List of Trips ---");
		int i = 1;
		for (Trip trip : trips) {
			String status = trip.isSettled ? "Settled" : "Ongoing";
			System.out.println(i + ". " + trip.tripName + " [" + status + "]");
			i++;
		}
	}

	void createNewTrip(ContactBST bst) {
		System.out.print("Enter trip name: ");
		String name = sc.nextLine();
		Trip trip = new Trip(name);

		System.out.print("Enter number of participants(excluding you): ");
		int n = sc.nextInt();
		sc.nextLine(); // clear buffer

		for (int i = 0; i < n; i++) {
			while (true) {
				System.out.print("Enter contact name to add: ");
				String contactName = sc.nextLine();
				Contact contact = bst.search(contactName);
				if (contact == null) {
					System.out.println("Contact not found. Try again.");
				} else {
					trip.addContact(contact);
					System.out.println(contact.name + " added to trip.");
					break;
				}
			}
		}

		// ✅ Build graph AFTER adding contacts
		trip.graph = new TripGraph(trip.tripContacts.values());

		trips.add(trip);
		System.out.println("Trip '" + name + "' created successfully.");
	}

	Trip getTripByIndex(int index) {
		if (index >= 0 && index < trips.size()) {
			return trips.get(index);
		}
		return null;
	}

	int tripCount() {
		return trips.size();
	}
}

// DS_MINIPROJECT class to run the contact manager program
public class SplitManagement {
	static Scanner sc = new Scanner(System.in);
	static ContactBST bst = new ContactBST();
	static public ArrayList<Transaction> GlobalTransactionUndone = new ArrayList<>();
	static public ArrayList<Transaction> GlobalTransactionDone = new ArrayList<>();
	TripManager tripManager = new TripManager();
	// defining a user profile default for now
	Contact user = new Contact("me", 0, "me@example.com");

	GroupMap GMap = new GroupMap();
	static String password;

	public void splitBill(ContactBST bst) {
		Scanner scanner = new Scanner(System.in);

		// Step 1: Get the purpose for the split
		System.out.print("Enter the purpose for the split: ");
		String purpose = scanner.nextLine();

		// Step 2: Choose between splitting with a friend or a group
		System.out.print("Choose 'friend' or 'group' to split with: ");
		String splitType = scanner.nextLine().toLowerCase();

		if (splitType.equals("friend")) {
			// Split with a friend
			System.out.print("Enter the friend's name: ");
			String friendName = scanner.nextLine();

			// Search for the friend in contacts
			Contact friend = bst.search(friendName);

			if (friend == null) {
				// Friend not found in contacts, so offer to add a new contact
				System.out.print(friendName + " not found. Do you want to add a new contact? (yes/no): ");
				String addNew = scanner.nextLine().toLowerCase();

				if (addNew.equals("yes")) {
					bst.insertContact();
					// contact = contacts.search(friendName); // Re-search after adding
				} else {
					System.out.println("Cannot proceed without a valid contact.");
					return;
				}
			}

			// Get the amount and split method
			System.out.print("Enter the amount to split: ");
			double amount = scanner.nextDouble();

			System.out.print("Choose split option: 1) Equal 2) Custom Amount 3) Percentage\nYour Choice: ");
			int splitOption = scanner.nextInt();

			double userShare = 0;
			switch (splitOption) {
				case 1: // Equal split
					userShare = amount / 2;
					break;
				case 2: // Custom amount
					System.out.print("Enter the friend's share: ");
					while (true) {
						if (scanner.hasNextDouble()) {
							double friendShare = scanner.nextDouble();

							// Check if friend's share is greater than the total amount
							if (friendShare > amount) {
								System.out.println("Error: Friend's share cannot be greater than the total amount.");
								System.out.print("Please enter a valid share: ");
							} else {
								userShare = amount - friendShare; // Calculate the user's share
								break; // Exit the loop once a valid share is entered
							}
						} else {
							System.out.println("Error: Invalid input. Please enter a valid number for friend's share.");
							scanner.next(); // Consume the invalid input
						}
					}

					break;
				case 3: // Percentage split
					System.out.print("Enter the friend's share percentage: ");
					while (true) {
						if (scanner.hasNextDouble()) {
							double friendPercentage = scanner.nextDouble();

							// Check if the percentage is greater than 100
							if (friendPercentage > 100) {
								System.out.println(
										"Error: Percentage cannot be greater than 100. Please enter a valid percentage.");
								System.out.print("Enter the friend's share percentage: ");
							} else {
								userShare = amount * (1 - friendPercentage / 100); // Calculate the user's share
								break; // Exit the loop once a valid percentage is entered
							}
						} else {
							System.out.println("Error: Invalid input. Please enter a valid number for the percentage.");
							scanner.next(); // Consume the invalid input
						}
					}

					break;
				default:
					System.out.println("Invalid option.");
					return;
			}

			System.out.println("Your share: " + userShare);

			// Add transaction
			Transaction transaction = new Transaction(friend, amount, "personal", null, "incoming", purpose);
			GlobalTransactionUndone.add(transaction);

		} else if (splitType.equals("group")) {
			// Split with a group
			GMap.displayAllGroups();

			System.out.print("Enter the group name: ");
			String groupName = scanner.nextLine();

			Group grouptoSplitWith = GMap.get(groupName);
			if (grouptoSplitWith == null) {
				System.out.print("Group not found. Do you want to create a new group? (yes/no): ");
				String addNewGroup = scanner.nextLine().toLowerCase();

				if (addNewGroup.equals("yes")) {
					GMap.AddGroup(groupName, bst);
					grouptoSplitWith = GMap.get(groupName); // Re-fetch after creating the group
				} else {
					System.out.println("Cannot proceed without a valid group.");
					return;
				}
			}

			// Get the amount and split method
			System.out.print("Enter the amount to split: ");
			double amount = scanner.nextDouble();

			System.out.print("Choose split option: 1) Equal 2) Custom Amounts 3) Percentage\nYour Choice: ");
			int splitOption = scanner.nextInt();

			double userShare = 0;
			switch (splitOption) {
				case 1: // Equal split
					double perShare = amount / (grouptoSplitWith.numContacts + 1);
					System.out.println("Your share: " + perShare);

					// Add transactions for each group member
					for (int i = 0; i < grouptoSplitWith.numContacts; i++) {
						Contact member = grouptoSplitWith.GroupContacts[i];
						Transaction transaction = new Transaction(member, perShare, "group", grouptoSplitWith.GroupName,
								"incoming", purpose);
						GlobalTransactionUndone.add(transaction);
					}
					break;
				case 2: // Custom amounts
					double sumOfOthers = 0;
					double[] shares = new double[grouptoSplitWith.numContacts]; // Array to store each member's share

					for (int i = 0; i < grouptoSplitWith.numContacts; i++) {
						double share;

						while (true) {
							System.out.print("Enter share for member " + grouptoSplitWith.GroupContacts[i].name + ": ");
							share = scanner.nextDouble();

							// Check if adding this share would exceed the total amount
							if (sumOfOthers + share > amount) {
								System.out.println(
										"Error: The total share exceeds the amount. Please re-enter a valid share.");
							} else {
								break; // Valid share, exit the loop
							}
						}

						shares[i] = share; // Store the valid share in the array
						sumOfOthers += share; // Add to the running total
					}

					userShare = amount - sumOfOthers;
					System.out.println("Your share: " + userShare);

					// Add transactions for each group member
					for (int i = 0; i < grouptoSplitWith.numContacts; i++) {
						Contact member = grouptoSplitWith.GroupContacts[i];
						Transaction transaction = new Transaction(member, shares[i], "group",
								grouptoSplitWith.GroupName, "incoming", purpose);
						GlobalTransactionUndone.add(transaction);
					}
					break;

				case 3: // Percentage split
					// List to store the percentage share of each member
					double[] percentages = new double[grouptoSplitWith.numContacts];
					double totalPercentage;

					// Loop until valid percentages are entered
					while (true) {
						totalPercentage = 0; // Reset total percentage for each attempt

						// Loop through all members except the user to get their share percentage
						for (int i = 0; i < grouptoSplitWith.numContacts; i++) {
							System.out.print(
									"Enter percentage for member " + grouptoSplitWith.GroupContacts[i].name + ": ");
							double percentage = scanner.nextDouble();

							// Add the percentage to the list and to the total
							percentages[i] = percentage;
							totalPercentage += percentage;

							// Validate each entry to ensure totalPercentage doesn’t exceed 100
							if (totalPercentage > 100) {
								System.out.println("Total percentage exceeds 100%. Please re-enter the percentages.");
								break; // Exit the loop to restart percentage entry
							}
						}

						// If the totalPercentage is valid (<= 100), break out of the outer while loop
						if (totalPercentage <= 100) {
							break;
						}
					}

					// Calculate the reDS_MINIPROJECTing percentage for the user
					double userPercentage = 100 - totalPercentage;
					// percentages.add(userPercentage); // Add user's percentage share

					System.out.println("Your share: " + (amount * userPercentage / 100));

					// Add transactions for each group member
					for (int i = 0; i < grouptoSplitWith.numContacts; i++) {
						double share = amount * percentages[i] / 100;
						Contact member = grouptoSplitWith.GroupContacts[i];
						Transaction transaction = new Transaction(member, share, "group", grouptoSplitWith.GroupName,
								"incoming", purpose);
						GlobalTransactionUndone.add(transaction);
					}
					break;
				default:
					System.out.println("Invalid option.");
					return;
			}
		} else {
			System.out.println("Invalid Input! Try Again!");
		}
	}

	void addOwingTransaction() {
		Contact contact = null;

		// Loop until a valid contact is entered
		while (contact == null) {
			System.out.print("Enter contact name (or 0 to go back): ");
			sc.nextLine();
			String contactName = sc.nextLine().trim();

			if (contactName.equals("0")) {
				return;
			}

			contact = bst.search(contactName);

			if (contact == null) {
				System.out.println(
						"Contact not found. Please enter a valid contact name.\n(Or enter 0 to go back)");
			}
		}

		// Prompt for transaction amount
		System.out.print("Enter transaction amount: ");
		double amount = sc.nextDouble();
		sc.nextLine(); // Consume newline

		// Prompt for transaction type
		System.out.print("Enter transaction type (personal/group): ");
		String transactionType = sc.nextLine();

		// Prompt for direction
		String direction = "outgoing";

		// If it's a group transaction, prompt for group name
		String groupName = null;
		if (transactionType.equalsIgnoreCase("group")) {
			System.out.print("Enter group name: ");
			groupName = sc.nextLine();
		}

		// Prompt for transaction purpose
		System.out.print("Enter transaction purpose: ");
		String purpose = sc.nextLine();

		// Create and return a new Transaction object
		Transaction transaction = new Transaction(contact, amount, transactionType, groupName, direction, purpose);
		GlobalTransactionUndone.add(transaction);
	}

	void receiveForSettlements() {
		// Step 1: Filter only the incoming transactions from GlobalTransactionUndone
		ArrayList<Transaction> incomingTransactions = new ArrayList<>();
		for (int i = 0; i < GlobalTransactionUndone.size(); i++) {
			Transaction txn = GlobalTransactionUndone.get(i);
			if ("incoming".equals(txn.direction)) { // Only incoming transactions
				incomingTransactions.add(txn);
			}
		}

		// Step 2: Display the filtered incoming transactions with serial numbers
		// starting from 1
		System.out.println("Displaying all UNDONE incoming transactions:");
		// Define column headers with clearer alignment
		System.out.printf("%-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s%n",
				"Contact", "Date", "Time", "Payment Status", "Purpose", "Amount", "Transaction Type", "Group Name",
				"Direction");
		System.out.println(
				"==================================================================================================================================================================================================");

		for (int i = 0; i < incomingTransactions.size(); i++) {
			System.out.println("Serial Number: " + (i + 1));
			incomingTransactions.get(i).displayTransactionDetails();
		}

		// Step 3: Ask the user to enter the serial number of the transaction to be
		// marked as done
		int serialNumber;

		while (true) {
			System.out.print("Enter the serial number of the transaction to mark as done (0 to exit): ");
			sc.nextLine();

			try {
				serialNumber = Integer.parseInt(sc.nextLine().trim());

				if (serialNumber == 0) {
					System.out.println("Exiting...");
					break;
				}

				// You can add your logic here to check if the serial number is valid
				// For example:
				if (serialNumber > 0) {
					break; // remove this break if you want to ask again even after valid input
				} else {
					System.out.println("Please enter a positive serial number or 0 to exit.");
				}
				// Step 4: Validate the serial number input
				if (serialNumber < 1 || serialNumber > incomingTransactions.size()) {
					System.out.println("Invalid serial number.");
					return;
				}

			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid serial number or 0 to exit.");
			}
		}

		int attempts = 3; // Total attempts allowed
		sc.nextLine();
		while (attempts > 0) {
			System.out.print("Enter password: ");
			String inputPassword = sc.nextLine();

			if (inputPassword.equals(password)) {
				System.out.println("Access granted!");
				break;
			} else {
				attempts--;
				System.out.println("Incorrect password. Attempts left: " + attempts);
			}
		}
		if (attempts == 0) {
			System.out.println("Too many failed attempts. Access denied.");
			return;
		}

		// Step 5: Find the corresponding transaction from GlobalTransactionUndone using
		// the serial number
		incomingTransactions.get(serialNumber - 1).markAsDone();

		// Step 6: Remove from GlobalTransactionUndone and add to GlobalTransactionDone
		GlobalTransactionUndone.remove(incomingTransactions.get(serialNumber - 1));
		GlobalTransactionDone.add(incomingTransactions.get(serialNumber - 1));
	}

	void payForSettlements() {
		// Step 1: Filter only the outgoing transactions from GlobalTransactionUndone
		ArrayList<Transaction> outgoingTransactions = new ArrayList<>();
		for (int i = 0; i < GlobalTransactionUndone.size(); i++) {
			Transaction txn = GlobalTransactionUndone.get(i);
			if ("outgoing".equals(txn.direction)) { // Only outgoing transactions
				outgoingTransactions.add(txn);
			}
		}

		// Step 2: Display the filtered outgoing transactions with serial numbers
		// starting from 1
		System.out.println("Displaying all UNDONE outgoing transactions:");
		// Define column headers with clearer alignment
		System.out.printf("%-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s%n",
				"Contact", "Date", "Time", "Payment Status", "Purpose", "Amount", "Transaction Type", "Group Name",
				"Direction");
		System.out.println(
				"==================================================================================================================================================================================================");

		for (int i = 0; i < outgoingTransactions.size(); i++) {
			System.out.println("Serial Number: " + (i + 1));
			outgoingTransactions.get(i).displayTransactionDetails();
		}

		// Step 3: Ask the user to enter the serial number of the transaction to be
		// marked as done
		int serialNumber;

		while (true) {
			System.out.print("Enter the serial number of the transaction to mark as done (0 to exit): ");
			sc.nextLine();

			try {
				serialNumber = Integer.parseInt(sc.nextLine().trim());

				if (serialNumber == 0) {
					System.out.println("Exiting...");
					break;
				}

				// You can add your logic here to check if the serial number is valid
				// For example:
				if (serialNumber > 0) {
					System.out.println("Transaction " + serialNumber + " marked as done.");
					break; // remove this break if you want to ask again even after valid input
				} else {
					System.out.println("Please enter a positive serial number or 0 to exit.");
				}
				// Step 4: Validate the serial number input
				if (serialNumber < 1 || serialNumber > outgoingTransactions.size()) {
					System.out.println("Invalid serial number.");
					return;
				}

			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid serial number or 0 to exit.");
			}
		}

		int attempts = 3; // Total attempts allowed
		sc.nextLine();
		while (attempts > 0) {
			System.out.print("Enter password: ");
			String inputPassword = sc.nextLine();

			if (inputPassword.equals(password)) {
				System.out.println("Access granted!");
				break;
			} else {
				attempts--;
				System.out.println("Incorrect password. Attempts left: " + attempts);
			}
		}
		if (attempts == 0) {
			System.out.println("Too many failed attempts. Access denied.");
			return;
		}
		// Step 5: Find the corresponding transaction from GlobalTransactionUndone using
		// the serial number
		outgoingTransactions.get(serialNumber - 1).markAsDone();

		// Step 6: Remove from GlobalTransactionUndone and add to GlobalTransactionDone
		GlobalTransactionUndone.remove(outgoingTransactions.get(serialNumber - 1));
		GlobalTransactionDone.add(outgoingTransactions.get(serialNumber - 1));
	}

	void viewAllPastTransactions(String sortBy) {
		// Check if there are any transactions done or undone
		if (GlobalTransactionDone.isEmpty() && GlobalTransactionUndone.isEmpty()) {
			System.out.println("No transactions available.");
			return;
		}

		// Sort transactions based on the specified criteria
		switch (sortBy.toLowerCase()) {
			case "date":
				sortByDate(GlobalTransactionUndone);
				sortByDate(GlobalTransactionDone);
				break;
			case "amount":
				sortByAmount(GlobalTransactionUndone, 0, GlobalTransactionUndone.size() - 1);
				sortByAmount(GlobalTransactionUndone, 0, GlobalTransactionDone.size() - 1);
				break;
			case "time":
				sortByTime(GlobalTransactionUndone);
				sortByTime(GlobalTransactionDone);
				break;
			default:
				System.out.println("Invalid sort criteria. Sorting by default (date).");
				sortByDate(GlobalTransactionUndone);
				sortByDate(GlobalTransactionDone);
				break;
		}

		// Display the transactions as in the original method
		if (!GlobalTransactionUndone.isEmpty()) {
			System.out.println("Displaying all UNDONE transactions:");
			displayTransactions(GlobalTransactionUndone);
		}

		if (!GlobalTransactionDone.isEmpty()) {
			System.out.println("\nDisplaying all DONE transactions:");
			displayTransactions(GlobalTransactionDone);
		}
	}

	// Method to display transactions
	void displayTransactions(List<Transaction> transactions) {
		System.out.printf("%-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s %-22s%n",
				"Contact", "Date", "Time", "Payment Status", "Purpose", "Amount", "Transaction Type", "Group Name",
				"Direction");
		System.out.println(
				"==================================================================================================================================================================================================");

		for (int i = 0; i < transactions.size(); i++) {
			System.out.println("Transaction " + (i + 1) + ":");
			transactions.get(i).displayTransactionDetails();
		}
	}

	void sortByTime(List<Transaction> transactions) {
		for (int i = 0; i < transactions.size() - 1; i++) {
			for (int j = 0; j < transactions.size() - i - 1; j++) {
				if (transactions.get(j).time.compareTo(transactions.get(j + 1).time) > 0) {
					// Swap if the current time is later than the next time
					Transaction temp = transactions.get(j);
					transactions.set(j, transactions.get(j + 1));
					transactions.set(j + 1, temp);
				}
			}
		}
	}

	void sortByDate(List<Transaction> transactions) {
		for (int i = 0; i < transactions.size() - 1; i++) {
			for (int j = 0; j < transactions.size() - i - 1; j++) {
				if (transactions.get(j).date.isAfter(transactions.get(j + 1).date)) {
					// Swap if the current date is after the next date
					Transaction temp = transactions.get(j);
					transactions.set(j, transactions.get(j + 1));
					transactions.set(j + 1, temp);
				}
			}
		}
	}

	void sortByAmount(List<Transaction> transactions, int low, int high) {
		if (low < high) {
			// Partition the list and get the pivot index
			int pivotIndex = partition(transactions, low, high);

			// Recursively sort the elements before and after the partition
			sortByAmount(transactions, low, pivotIndex - 1);
			sortByAmount(transactions, pivotIndex + 1, high);
		}
	}

	// Partition method to place pivot at the correct position
	int partition(List<Transaction> transactions, int low, int high) {
		double pivot = transactions.get(high).amount; // Choose the last element as the pivot
		int i = low - 1; // Index of the smaller element

		for (int j = low; j < high; j++) {
			if (transactions.get(j).amount <= pivot) {
				i++;

				// Swap transactions[i] and transactions[j]
				Transaction temp = transactions.get(i);
				transactions.set(i, transactions.get(j));
				transactions.set(j, temp);
			}
		}

		// Swap transactions[i+1] and transactions[high] (pivot)
		Transaction temp = transactions.get(i + 1);
		transactions.set(i + 1, transactions.get(high));
		transactions.set(high, temp);

		return i + 1; // Return the pivot index
	}

	void manageContacts() {
		boolean back = false;

		while (!back) {
			System.out.println("\n--- Contact Management ---");
			System.out.println("1. Add Contact");
			System.out.println("2. Search Contact");
			System.out.println("3. Delete Contact");
			System.out.println("4. Update Contact");
			System.out.println("5. View All Contacts");
			System.out.println("6. Go Back");
			System.out.print("Choose an option: ");
			int option = sc.nextInt();
			sc.nextLine(); // clear buffer

			switch (option) {
				case 1:
					bst.insertContact();
					break;
				case 2:
					System.out.print("Enter name to search: ");
					String name = sc.nextLine();
					Contact found = bst.search(name);
					if (found != null) {
						found.display();
					} else {
						System.out.println("Contact not found.");
					}
					break;
				case 3:
					bst.deleteContact();
					break;
				case 4:
					bst.updateContact();
					break;
				case 5:
					bst.displayAllContacts();
					break;
				case 6:
					back = true;
					break;
				default:
					System.out.println("Invalid choice.");
			}
		}
	}

	// TRIP MANAGEMENT
	void tripMenu() {
		boolean back = false;

		while (!back) {
			System.out.println("\n--- Trip Management ---");
			System.out.println("1. View All Trips");
			System.out.println("2. Create New Trip");
			System.out.println("3. Manage a Trip");
			System.out.println("0. Go Back");
			System.out.print("Choose an option: ");
			int choice = sc.nextInt();

			switch (choice) {
				case 1:
					tripManager.listAllTrips();
					break;
				case 2:
					tripManager.createNewTrip(bst);
					break;
				case 3:
					if (tripManager.tripCount() == 0) {
						System.out.println("No trips available to manage.");
						break;
					}
					tripManager.listAllTrips();
					System.out.print("Enter trip number to manage: ");
					int idx = sc.nextInt();
					if (idx < 1 || idx > tripManager.tripCount()) {
						System.out.println("Invalid trip number.");
						break;
					}
					Trip trip = tripManager.getTripByIndex(idx - 1);
					manageTrip(trip); // we'll define this later
					break;
				case 0:
					back = true;
					break;
				default:
					System.out.println("Invalid choice.");
			}
		}
	}

	void manageTrip(Trip trip) {
		if (trip.isSettled) {
			System.out.println("This trip is already settled. No further modifications allowed.");
			return;
		}

		boolean back = false;

		while (!back) {
			System.out.println("\n--- Managing Trip: " + trip.tripName + " ---");
			System.out.println("1. View Trip Contacts");
			System.out.println("2. Add Contact to Trip");
			System.out.println("3. Remove Contact from Trip");
			System.out.println("4. Add Transaction");
			System.out.println("5. Settle Trip");
			System.out.println("0. Go Back");
			System.out.print("Choose an option: ");
			int choice = sc.nextInt();
			sc.nextLine(); // clear buffer

			switch (choice) {
				case 1:
					System.out.println("Trip Contacts:");
					for (Contact c : trip.getAllContacts()) {
						c.display();
						System.out.println("------------------------");
					}
					break;

				case 2:
					System.out.println("Enter contact name to add (or 0 to go back): ");
					String newName = sc.nextLine();
					if (newName.equals("0"))
						break;
					Contact newContact = bst.search(newName);
					if (newContact == null) {
						System.out.println("Contact not found.");
					} else if (trip.hasContact(newContact.name)) {
						System.out.println("Contact already in trip.");
					} else {
						trip.addContact(newContact);
						System.out.println("Contact added.");
					}
					break;

				case 3:
					System.out.println("Enter contact name to remove (or 0 to go back): ");
					String removeName = sc.nextLine();
					if (removeName.equals("0"))
						break;
					if (trip.hasContact(removeName)) {
						trip.removeContact(removeName);
						System.out.println("Contact removed.");
					} else {
						System.out.println("Contact not in trip.");
					}
					break;

				case 4:
					addTransactionToTrip(trip);
					break;

				case 5:
					settleTrip(trip);
					return;

				case 0:
					back = true;
					break;

				default:
					System.out.println("Invalid option.");
			}
		}
	}

	void addTransactionToTrip(Trip trip) {
		System.out.print("Enter purpose (or 0 to go back): ");
		String purpose = sc.nextLine();
		if (purpose.equals("0"))
			return;

		System.out.print("Enter payee name (enter 'me' if it's you, or 0 to go back): ");
		String payeeName = sc.nextLine();
		if (payeeName.equals("0"))
			return;

		Contact payee = null;
		if (!payeeName.equalsIgnoreCase("me")) {
			if (!trip.hasContact(payeeName)) {
				System.out.println("Payee not in trip.");
				return;
			}
			payee = trip.tripContacts.get(payeeName.toLowerCase());
		}

		System.out.print("Enter amount (or 0 to cancel): ");
		double amount = sc.nextDouble();
		if (amount == 0)
			return;
		sc.nextLine(); // clear buffer

		// Step: Display members and ask for participants
		List<Contact> allContacts = new ArrayList<>(trip.getAllContacts());
		System.out.println("Trip members:");
		int idx = 1;
		for (Contact c : allContacts) {
			System.out.println(idx + ". " + c.name);
			idx++;
		}
		System.out.println(
				"Enter names of participants (comma separated, include 'me' if you're part). Enter 0 to cancel:");
		String input = sc.nextLine();
		if (input.equals("0"))
			return;

		String[] names = input.split(",");
		List<Contact> participants = new ArrayList<>();
		boolean userIncluded = false;

		for (String name : names) {
			name = name.trim();
			if (name.equalsIgnoreCase("me")) {
				userIncluded = true;
			} else {
				Contact contact = trip.tripContacts.get(name.toLowerCase());
				if (contact == null) {
					System.out.println("Contact " + name + " not found in trip. Cancelling.");
					return;
				}
				participants.add(contact);
			}
		}

		int totalPeople = participants.size() + (userIncluded ? 1 : 0);
		if (totalPeople == 0) {
			System.out.println("No participants selected. Cancelling.");
			return;
		}

		System.out.println("Choose split type:");
		System.out.println("1. Equal");
		System.out.println("2. Custom Amounts");
		System.out.println("3. Percentages");
		System.out.print("Your choice (or 0 to cancel): ");
		int choice = sc.nextInt();
		if (choice == 0)
			return;

		List<Double> shares = new ArrayList<>();
		double userShare = 0;

		switch (choice) {
			case 1: // Equal
				double perPerson = amount / totalPeople;
				for (int i = 0; i < participants.size(); i++)
					shares.add(perPerson);
				if (userIncluded)
					userShare = perPerson;
				break;

			case 2: // Custom Amounts
				double total = 0;
				for (Contact p : participants) {
					System.out.print("Enter amount for " + p.name + " (or 0 to cancel): ");
					double share = sc.nextDouble();
					if (share == 0)
						return;
					shares.add(share);
					total += share;
				}
				if (userIncluded) {
					System.out.print("Enter your share (or 0 to cancel): ");
					userShare = sc.nextDouble();
					if (userShare == 0)
						return;
					total += userShare;
				}
				if (total > amount) {
					System.out.println("Error: Total shares exceed amount.");
					return;
				}
				break;

			case 3: // Percentages
				double totalPercent = 0;
				for (Contact p : participants) {
					System.out.print("Enter percentage for " + p.name + " (or 0 to cancel): ");
					double percent = sc.nextDouble();
					if (percent == 0)
						return;
					shares.add(amount * percent / 100);
					totalPercent += percent;
				}
				if (userIncluded) {
					System.out.print("Enter your percentage (or 0 to cancel): ");
					double userPercent = sc.nextDouble();
					if (userPercent == 0)
						return;
					userShare = amount * userPercent / 100;
					totalPercent += userPercent;
				}
				if (totalPercent > 100) {
					System.out.println("Error: Total percentage exceeds 100.");
					return;
				}
				break;

			default:
				System.out.println("Invalid split type.");
				return;
		}

		sc.nextLine(); // clear buffer
		System.out.printf("Your share of the transaction is: ₹%.2f%n", userShare);
		System.out.println("Transaction added successfully.");

		// Save in trip
		trip.transactions.add(new TripTransaction(purpose, payee, amount, participants, "trip:" + trip.tripName));

		// Add to graph and GlobalTransaction lists
		String payerKey = payeeName.equalsIgnoreCase("me") ? "me" : payee.name;

		if (userIncluded && !payerKey.equalsIgnoreCase("me")) {
			Transaction txn = new Transaction(payee, userShare, "group", "trip:" + trip.tripName, "outgoing", purpose);
			GlobalTransactionUndone.add(txn);
			trip.graph.addTransaction(payerKey, "me", userShare);
		}

		for (int i = 0; i < participants.size(); i++) {
			Contact participant = participants.get(i);
			double share = shares.get(i);
			String participantKey = participant.name;

			// Add to trip matrix
			trip.graph.addTransaction(payerKey, participantKey, share);

			// GLOBAL TRACKING:
			if (payerKey.equalsIgnoreCase("me")) {
				Transaction txn = new Transaction(participant, share, "group", "trip:" + trip.tripName, "incoming",
						purpose);
				GlobalTransactionUndone.add(txn);
			}
		}

		// Case 2: You paid and are included — mark self-paid share as done
		if (userIncluded && payerKey.equalsIgnoreCase("me")) {
			Transaction txn = new Transaction(user, userShare, "group", "trip:" + trip.tripName, "outgoing", purpose);
			txn.markAsDone();
			GlobalTransactionDone.add(txn);
		}

	}

	void settleTrip(Trip trip) {
		if (trip.isSettled) {
			System.out.println("Trip already settled.");
			return;
		}

		String tripGroupName = "trip:" + trip.tripName;

		// Mark all trip transactions as done
		List<Transaction> toRemove = new ArrayList<>();
		for (Transaction txn : GlobalTransactionUndone) {
			if (tripGroupName.equals(txn.groupName)) {
				toRemove.add(txn);
				txn.markAsDone();
				GlobalTransactionDone.add(txn);
			}
		}
		GlobalTransactionUndone.removeAll(toRemove);

		// Step 1: Calculate net balance for each contact
		TripGraph graph = trip.graph;
		int n = graph.getSize();
		double[] net = new double[n];

		printMatrix(graph.getMatrix(), graph);
		double[][] matrix = graph.getMatrix();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				net[i] += matrix[i][j] - matrix[j][i]; // total received - total paid
			}
		}

		// Step 2: Greedy settlement
		System.out.println("\n--- Final Reduced Settlements ---");

		while (true) {
			int maxCred = getMax(net); // person to receive
			int maxDebt = getMin(net); // person to pay

			if (net[maxCred] == 0 && net[maxDebt] == 0)
				break;

			double amount = Math.min(-net[maxDebt], net[maxCred]);

			net[maxCred] -= amount;
			net[maxDebt] += amount;

			Contact creditor = graph.getContact(maxCred);
			Contact debtor = graph.getContact(maxDebt);

			System.out.printf("%s owes ₹%.2f to %s%n", debtor.name, amount, creditor.name);
		}

		trip.isSettled = true;
		System.out.println("\nTrip '" + trip.tripName + "' settled successfully.");
	}

	void printMatrix(double[][] matrix, TripGraph graph) {
		System.out.println("\n--- Trip Debt Matrix (who owes whom) ---");
		int n = graph.getSize();

		// Print header row
		System.out.print(String.format("%-15s", ""));
		for (int j = 0; j < n; j++) {
			System.out.print(String.format("%-15s", graph.getContact(j).name));
		}
		System.out.println();

		// Print matrix rows
		for (int i = 0; i < n; i++) {
			System.out.print(String.format("%-15s", graph.getContact(i).name));
			for (int j = 0; j < n; j++) {
				System.out.print(String.format("%-15.2f", matrix[i][j]));
			}
			System.out.println();
		}
	}

	int getMax(double[] arr) {
		int index = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > arr[index])
				index = i;
		}
		return index;
	}

	int getMin(double[] arr) {
		int index = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < arr[index])
				index = i;
		}
		return index;
	}

	void main() {
		SplitManagement DS_MINIPROJECT = new SplitManagement();

		bst.insertContactDirect("Amit Sharma", 9876543210L, "amit.sharma@example.com");
		bst.insertContactDirect("Priya Gupta", 8765432109L, "priya.gupta@example.com");
		bst.insertContactDirect("Rohit Verma", 7654321098L, "rohit.verma@example.com");
		bst.insertContactDirect("Sneha Reddy", 6543210987L, "sneha.reddy@example.com");
		bst.insertContactDirect("Rahul Singh", 5432109876L, "rahul.singh@example.com");
		bst.insertContactDirect("Neha Bansal", 4321098765L, "neha.bansal@example.com");
		bst.insertContactDirect("Vikram Mehta", 3210987654L, "vikram.mehta@example.com");
		bst.insertContactDirect("Anjali Joshi", 2109876543, "anjali.joshi@example.com");
		bst.insertContactDirect("Karan Kapoor", 1098765432, "karan.kapoor@example.com");
		bst.insertContactDirect("Pooja Desai", 1987654321, "pooja.desai@example.com");
		bst.insertContactDirect("Suresh Nair", 9876123450L, "suresh.nair@example.com");
		bst.insertContactDirect("Rina Patel", 8765234567L, "rina.patel@example.com");
		bst.insertContactDirect("Manoj Kumar", 7654321987L, "manoj.kumar@example.com");
		bst.insertContactDirect("Deepika Choudhary", 6543212345L, "deepika.choudhary@example.com");
		bst.insertContactDirect("Arjun Raghav", 5432101234L, "arjun.raghav@example.com");
		bst.insertContactDirect("Nisha Mehta", 4321098760L, "nisha.mehta@example.com");
		bst.insertContactDirect("Kavita Singh", 3210987651L, "kavita.singh@example.com");
		bst.insertContactDirect("Ravi Kumar", 2109876542, "ravi.kumar@example.com");
		bst.insertContactDirect("Simran Kaur", 1098765433, "simran.kaur@example.com");
		bst.insertContactDirect("Tarun Bhatia", 1987654320, "tarun.bhatia@example.com");
		bst.insertContactDirect("Geeta Verma", 9876543211L, "geeta.verma@example.com");

		System.out.print("Enter password for transactions: ");
		password = sc.nextLine();

		boolean exit = false;
		do {
			System.out.println("\n--- Contact Manager ---");
			System.out.println("1. Manage Contacts");
			System.out.println("2. Split a Bill");
			System.out.println("3. Add Owing Payments");
			System.out.println("4. Pay and Settle Owing Payments");
			System.out.println("5. Lent Receivals");
			System.out.println("6. View All Transactions");
			System.out.println("7. Manage Expenses on a Trip");
			System.out.println("0. Exit");
			System.out.print("Choose an option: ");
			int choice = sc.nextInt();

			switch (choice) {
				case 1:
					DS_MINIPROJECT.manageContacts(); // <== New method for contact menu
					break;
				case 2:
					DS_MINIPROJECT.splitBill(bst);
					break;
				case 3:
					DS_MINIPROJECT.addOwingTransaction();
					break;
				case 4:
					DS_MINIPROJECT.payForSettlements();
					break;
				case 5:
					DS_MINIPROJECT.receiveForSettlements();
					break;
				case 6:
					String sortBy;

					// Repeat until valid input is received
					while (true) {
						System.out.print("Enter sorting criteria (date, amount, time): ");
						sc.nextLine();
						sortBy = sc.nextLine(); // take sorting input

						// Validate input
						if (sortBy.equals("date") || sortBy.equals("amount") || sortBy.equals("time")) {
							break; // valid input, exit loop
						} else {
							System.out.println("Invalid input. Please enter one of the following: date, amount, time.");
						}
					}

					// Call the method with the valid input
					DS_MINIPROJECT.viewAllPastTransactions(sortBy);
					break;
				case 7:
					DS_MINIPROJECT.tripMenu();
					break;
				case 0:
					exit = true;
					System.out.println("Goodbye!");
					break;
				default:
					System.out.println("Invalid choice. Please select a valid option.");
					break;
			}

		} while (!exit);

		sc.close();
	}
}