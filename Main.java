import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Book> books = new ArrayList<>();
    private static final ArrayList<Member> members = new ArrayList<>();
    
    private static final ArrayList<IssueRecord> issuedRecords = new ArrayList<>();

    private static int nextBookId = 1;
    private static int nextMemberId = 1;

   
    static class Book {
        private int id;
        private String title;
        private String author;
        private int quantity;

        public Book(int id, String title, String author, int quantity) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.quantity = quantity;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        @Override
        public String toString() {
            return String.format("%-5d | %-30s | %-20s | Qty: %d",
                    id, title, author, quantity);
        }
    }

   
    static class Member {
        private int id;
        private String name;

        public Member(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        @Override
        public String toString() {
            return String.format("%-5d | %s", id, name);
        }
    }

    static class IssueRecord {
        private final int bookId;
        private final int memberId;

        public IssueRecord(int bookId, int memberId) {
            this.bookId = bookId;
            this.memberId = memberId;
        }

        public int getBookId() { return bookId; }
        public int getMemberId() { return memberId; }
    }

   

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1  -> addBook();
                case 2  -> viewAllBooks();
                case 3  -> searchBook();
                case 4  -> updateBook();
                case 5  -> deleteBook();
                case 6  -> addMember();
                case 7  -> viewAllMembers();
                case 8  -> issueBook();
                case 9  -> returnBook();
                case 10 -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n========== LIBRARY MANAGEMENT SYSTEM ==========");
        System.out.println(" 1. Add Book");
        System.out.println(" 2. View All Books");
        System.out.println(" 3. Search Book (by ID or title)");
        System.out.println(" 4. Update Book");
        System.out.println(" 5. Delete Book");
        System.out.println(" 6. Add Member");
        System.out.println(" 7. View All Members");
        System.out.println(" 8. Issue Book to Member");
        System.out.println(" 9. Return Book");
        System.out.println("10. Exit");
        System.out.println("===============================================");
    }

    

    private static void addBook() {
        System.out.println("\n--- Add Book ---");
        String title = readNonEmptyLine("Enter title: ");
        String author = readNonEmptyLine("Enter author: ");
        int quantity = readIntInRange("Enter quantity: ", 1, Integer.MAX_VALUE);

        books.add(new Book(nextBookId++, title, author, quantity));
        System.out.println("Book added successfully with ID: " + (nextBookId - 1));
    }

    private static void viewAllBooks() {
        System.out.println("\n--- All Books ---");
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        printBookHeader();
        for (Book b : books) {
            System.out.println(b);
        }
    }

    private static void searchBook() {
        System.out.println("\n--- Search Book ---");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by title");
        int mode = readInt("Choose search mode: ");

        if (mode == 1) {
            int id = readInt("Enter book ID: ");
            Book b = findBookById(id);
            if (b == null) {
                System.out.println("Book not found.");
            } else {
                printBookHeader();
                System.out.println(b);
            }
        } else if (mode == 2) {
            String query = readNonEmptyLine("Enter title (partial match allowed): ");
            boolean found = false;
            printBookHeader();
            for (Book b : books) {
                // Case-insensitive partial match on title.
                if (b.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    System.out.println(b);
                    found = true;
                }
            }
            if (!found) System.out.println("No matching books found.");
        } else {
            System.out.println("Invalid search mode.");
        }
    }

    private static void updateBook() {
        System.out.println("\n--- Update Book ---");
        int id = readInt("Enter book ID to update: ");
        Book b = findBookById(id);
        if (b == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.println("Current: " + b);
        String title = readOptionalLine("New title (press Enter to keep): ");
        String author = readOptionalLine("New author (press Enter to keep): ");
        if (!title.isEmpty()) b.setTitle(title);
        if (!author.isEmpty()) b.setAuthor(author);

        String qtyInput = readOptionalLine("New quantity (press Enter to keep): ");
        if (!qtyInput.isEmpty()) {
            try {
                int qty = Integer.parseInt(qtyInput);
                if (qty >= 1) b.setQuantity(qty);
                else System.out.println("Quantity must be at least 1. Kept old value.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Kept old quantity.");
            }
        }
        System.out.println("Book updated: " + b);
    }

    private static void deleteBook() {
        System.out.println("\n--- Delete Book ---");
        int id = readInt("Enter book ID to delete: ");
        Book b = findBookById(id);
        if (b == null) {
            System.out.println("Book not found.");
            return;
        }
        // Remove any issue records tied to this book.
        issuedRecords.removeIf(r -> r.getBookId() == id);
        books.remove(b);
        System.out.println("Book deleted: " + b.getTitle());
    }

    // ========================== MEMBER OPERATIONS ==========================

    private static void addMember() {
        System.out.println("\n--- Add Member ---");
        String name = readNonEmptyLine("Enter member name: ");
        members.add(new Member(nextMemberId++, name));
        System.out.println("Member added successfully with ID: " + (nextMemberId - 1));
    }

    private static void viewAllMembers() {
        System.out.println("\n--- All Members ---");
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        System.out.println(String.format("%-5s | %s", "ID", "Name"));
        System.out.println("---------------------------------------");
        for (Member m : members) {
            System.out.println(m);
        }
    }

    

    private static void issueBook() {
        System.out.println("\n--- Issue Book ---");
        int bookId = readInt("Enter book ID: ");
        Book b = findBookById(bookId);
        if (b == null) {
            System.out.println("Book not found.");
            return;
        }

        int memberId = readInt("Enter member ID: ");
        Member m = findMemberById(memberId);
        if (m == null) {
            System.out.println("Member not found.");
            return;
        }

        // Block duplicate issue of the same book to the same member.
        for (IssueRecord r : issuedRecords) {
            if (r.getBookId() == bookId && r.getMemberId() == memberId) {
                System.out.println("This book is already issued to this member.");
                return;
            }
        }

        if (b.getQuantity() <= 0) {
            System.out.println("No copies of \"" + b.getTitle() + "\" available.");
            return;
        }

        b.setQuantity(b.getQuantity() - 1);
        issuedRecords.add(new IssueRecord(bookId, memberId));
        System.out.println("Issued \"" + b.getTitle() + "\" to " + m.getName()
                + ". Remaining copies: " + b.getQuantity());
    }

    private static void returnBook() {
        System.out.println("\n--- Return Book ---");
        int bookId = readInt("Enter book ID: ");
        int memberId = readInt("Enter member ID: ");

        IssueRecord found = null;
        for (IssueRecord r : issuedRecords) {
            if (r.getBookId() == bookId && r.getMemberId() == memberId) {
                found = r;
                break;
            }
        }
        if (found == null) {
            System.out.println("No matching issue record found.");
            return;
        }

        issuedRecords.remove(found);
        Book b = findBookById(bookId);
        if (b != null) {
            b.setQuantity(b.getQuantity() + 1);
            System.out.println("Returned \"" + b.getTitle()
                    + "\". Copies now available: " + b.getQuantity());
        } else {
            System.out.println("Record closed (book no longer exists in catalog).");
        }
    }

    
    private static Book findBookById(int id) {
        for (Book b : books) {
            if (String.valueOf(b.getId()).equalsIgnoreCase(String.valueOf(id))) {
                return b;
            }
        }
        return null;
    }

    private static Member findMemberById(int id) {
        for (Member m : members) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    private static void printBookHeader() {
        System.out.println(String.format("%-5s | %-30s | %-20s | %s",
                "ID", "Title", "Author", "Quantity"));
        System.out.println("---------------------------------------------------------------------");
    }

   
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    
    private static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) return value;
            System.out.println("Value must be between " + min + " and " + max + ".");
        }
    }

    
    private static String readNonEmptyLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) return line;
            System.out.println("This field cannot be empty.");
        }
    }

    
    private static String readOptionalLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}