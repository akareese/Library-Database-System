package com.library;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.Member;
import com.library.service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LibraryService libraryService = new LibraryService();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewAllBooks();
                case 3 -> searchBooks();
                case 4 -> updateBook();
                case 5 -> deleteBook();
                case 6 -> addMember();
                case 7 -> viewAllMembers();
                case 8 -> issueBook();
                case 9 -> returnBook();
                case 10 -> viewAllLoans();
                case 11 -> viewOverdueLoans();
                case 0 -> {
                    running = false;
                    System.out.println("Exiting Library Management System.");
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== Library Management System =====");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Search Books");
        System.out.println("4. Update Book");
        System.out.println("5. Delete Book");
        System.out.println("6. Add Member");
        System.out.println("7. View All Members");
        System.out.println("8. Issue Book");
        System.out.println("9. Return Book");
        System.out.println("10. View All Loans");
        System.out.println("11. View Overdue Loans");
        System.out.println("0. Exit");
    }

    private static void addBook() {
        System.out.println("\n--- Add Book ---");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        int totalCopies = readInt("Total copies: ");

        Book book = new Book(title, author, genre, isbn, totalCopies, totalCopies);
        libraryService.addBook(book);
    }

    private static void viewAllBooks() {
        System.out.println("\n--- All Books ---");
        List<Book> books = libraryService.getAllBooks();
        printList(books);
    }

    private static void searchBooks() {
        System.out.println("\n--- Search Books ---");
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();
        List<Book> books = libraryService.searchBooks(keyword);
        printList(books);
    }

    private static void updateBook() {
        System.out.println("\n--- Update Book ---");
        int bookId = readInt("Book ID: ");
        System.out.print("New title: ");
        String title = scanner.nextLine();
        System.out.print("New author: ");
        String author = scanner.nextLine();
        System.out.print("New genre: ");
        String genre = scanner.nextLine();
        System.out.print("New ISBN: ");
        String isbn = scanner.nextLine();
        int totalCopies = readInt("New total copies: ");
        int availableCopies = readInt("New available copies: ");

        Book updatedBook = new Book(bookId, title, author, genre, isbn, totalCopies, availableCopies);
        libraryService.updateBook(updatedBook);
    }

    private static void deleteBook() {
        System.out.println("\n--- Delete Book ---");
        int bookId = readInt("Book ID: ");
        libraryService.deleteBook(bookId);
    }

    private static void addMember() {
        System.out.println("\n--- Add Member ---");
        System.out.print("Full name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        libraryService.addMember(new Member(name, email, phone));
    }

    private static void viewAllMembers() {
        System.out.println("\n--- All Members ---");
        List<Member> members = libraryService.getAllMembers();
        printList(members);
    }

    private static void issueBook() {
        System.out.println("\n--- Issue Book ---");
        int bookId = readInt("Book ID: ");
        int memberId = readInt("Member ID: ");
        int loanDays = readInt("Loan period (days): ");
        libraryService.issueBook(bookId, memberId, loanDays);
    }

    private static void returnBook() {
        System.out.println("\n--- Return Book ---");
        int loanId = readInt("Loan ID: ");
        libraryService.returnBook(loanId);
    }

    private static void viewAllLoans() {
        System.out.println("\n--- All Loans ---");
        List<Loan> loans = libraryService.getAllLoans();
        printList(loans);
    }

    private static void viewOverdueLoans() {
        System.out.println("\n--- Overdue Loans ---");
        List<Loan> overdueLoans = libraryService.getOverdueLoans();
        printList(overdueLoans);
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static <T> void printList(List<T> items) {
        if (items.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (T item : items) {
            System.out.println(item);
        }
    }
}