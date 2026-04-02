package com.library;

import com.library.model.Book;
import com.library.model.Member;
import com.library.service.LibraryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class LibraryUI extends JFrame {
    private final LibraryService libraryService;

    private JTextArea outputArea;

    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField isbnField;
    private JTextField copiesField;
    private JTextField searchField;

    private JTextField memberNameField;
    private JTextField memberEmailField;
    private JTextField memberPhoneField;

    private JTextField issueBookIdField;
    private JTextField issueMemberIdField;
    private JTextField issueDaysField;

    private JTextField returnLoanIdField;

    public LibraryUI() {
        libraryService = new LibraryService();

        setTitle("Library Management System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Books", createBooksPanel());
        tabbedPane.addTab("Members", createMembersPanel());
        tabbedPane.addTab("Loans", createLoansPanel());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setBorder(BorderFactory.createTitledBorder("Output"));
        outputScrollPane.setPreferredSize(new Dimension(900, 250));

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        add(outputScrollPane, BorderLayout.SOUTH);
    }

    private JPanel createBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        titleField = new JTextField();
        authorField = new JTextField();
        genreField = new JTextField();
        isbnField = new JTextField();
        copiesField = new JTextField();
        searchField = new JTextField();

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);

        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);

        formPanel.add(new JLabel("Genre:"));
        formPanel.add(genreField);

        formPanel.add(new JLabel("ISBN:"));
        formPanel.add(isbnField);

        formPanel.add(new JLabel("Total Copies:"));
        formPanel.add(copiesField);

        formPanel.add(new JLabel("Search Keyword:"));
        formPanel.add(searchField);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JButton addBookButton = new JButton("Add Book");
        JButton viewBooksButton = new JButton("View All Books");
        JButton searchBooksButton = new JButton("Search Books");
        JButton deleteBookButton = new JButton("Delete Book");
        JButton clearBookFieldsButton = new JButton("Clear Fields");

        addBookButton.addActionListener(e -> addBook());
        viewBooksButton.addActionListener(e -> viewAllBooks());
        searchBooksButton.addActionListener(e -> searchBooks());
        deleteBookButton.addActionListener(e -> deleteBook());
        clearBookFieldsButton.addActionListener(e -> clearBookFields());

        buttonPanel.add(addBookButton);
        buttonPanel.add(viewBooksButton);
        buttonPanel.add(searchBooksButton);
        buttonPanel.add(deleteBookButton);
        buttonPanel.add(clearBookFieldsButton);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMembersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        memberNameField = new JTextField();
        memberEmailField = new JTextField();
        memberPhoneField = new JTextField();

        formPanel.add(new JLabel("Full Name:"));
        formPanel.add(memberNameField);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(memberEmailField);

        formPanel.add(new JLabel("Phone:"));
        formPanel.add(memberPhoneField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        JButton addMemberButton = new JButton("Add Member");
        JButton viewMembersButton = new JButton("View All Members");
        JButton clearMemberFieldsButton = new JButton("Clear Fields");

        addMemberButton.addActionListener(e -> addMember());
        viewMembersButton.addActionListener(e -> viewAllMembers());
        clearMemberFieldsButton.addActionListener(e -> clearMemberFields());

        buttonPanel.add(addMemberButton);
        buttonPanel.add(viewMembersButton);
        buttonPanel.add(clearMemberFieldsButton);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createLoansPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel issuePanel = new JPanel(new GridLayout(4, 2, 10, 10));
        issuePanel.setBorder(BorderFactory.createTitledBorder("Issue Book"));

        issueBookIdField = new JTextField();
        issueMemberIdField = new JTextField();
        issueDaysField = new JTextField();

        issuePanel.add(new JLabel("Book ID:"));
        issuePanel.add(issueBookIdField);

        issuePanel.add(new JLabel("Member ID:"));
        issuePanel.add(issueMemberIdField);

        issuePanel.add(new JLabel("Loan Days:"));
        issuePanel.add(issueDaysField);

        JButton issueButton = new JButton("Issue Book");
        issueButton.addActionListener(e -> issueBook());
        issuePanel.add(issueButton);
        issuePanel.add(new JLabel());

        JPanel returnPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        returnPanel.setBorder(BorderFactory.createTitledBorder("Return / View Loans"));

        returnLoanIdField = new JTextField();

        returnPanel.add(new JLabel("Loan ID:"));
        returnPanel.add(returnLoanIdField);

        JButton returnButton = new JButton("Return Book");
        JButton viewLoansButton = new JButton("View All Loans");
        JButton overdueLoansButton = new JButton("View Overdue Loans");
        JButton clearLoanFieldsButton = new JButton("Clear Fields");

        returnButton.addActionListener(e -> returnBook());
        viewLoansButton.addActionListener(e -> viewAllLoans());
        overdueLoansButton.addActionListener(e -> viewOverdueLoans());
        clearLoanFieldsButton.addActionListener(e -> clearLoanFields());

        returnPanel.add(returnButton);
        returnPanel.add(viewLoansButton);
        returnPanel.add(overdueLoansButton);
        returnPanel.add(clearLoanFieldsButton);

        panel.add(issuePanel);
        panel.add(returnPanel);

        return panel;
    }

    private void addBook() {
        try {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String genre = genreField.getText().trim();
            String isbn = isbnField.getText().trim();
            int copies = Integer.parseInt(copiesField.getText().trim());

            Book book = new Book(title, author, genre, isbn, copies, copies);
            libraryService.addBook(book);

            outputArea.setText("Book added successfully.\n");
            clearBookFields();
            viewAllBooks();
        } catch (NumberFormatException e) {
            showError("Please enter a valid number for copies.");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void viewAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        outputArea.setText("=== ALL BOOKS ===\n");

        if (books.isEmpty()) {
            outputArea.append("No books found.\n");
            return;
        }

        for (Book book : books) {
            outputArea.append(book + "\n");
        }
    }

    private void searchBooks() {
        String keyword = searchField.getText().trim();
        List<Book> books = libraryService.searchBooks(keyword);

        outputArea.setText("=== SEARCH RESULTS ===\n");

        if (books.isEmpty()) {
            outputArea.append("No matching books found.\n");
            return;
        }

        for (Book book : books) {
            outputArea.append(book + "\n");
        }
    }

    private void deleteBook() {
        String input = JOptionPane.showInputDialog(this, "Enter Book ID to delete:");
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        try {
            int bookId = Integer.parseInt(input.trim());
            libraryService.deleteBook(bookId);
            outputArea.setText("Delete book request completed.\n");
            viewAllBooks();
        } catch (NumberFormatException e) {
            showError("Please enter a valid Book ID.");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void addMember() {
        try {
            String name = memberNameField.getText().trim();
            String email = memberEmailField.getText().trim();
            String phone = memberPhoneField.getText().trim();

            Member member = new Member(name, email, phone);
            libraryService.addMember(member);

            outputArea.setText("Member added successfully.\n");
            clearMemberFields();
            viewAllMembers();
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void viewAllMembers() {
        List<Member> members = libraryService.getAllMembers();
        outputArea.setText("=== ALL MEMBERS ===\n");

        if (members.isEmpty()) {
            outputArea.append("No members found.\n");
            return;
        }

        for (Member member : members) {
            outputArea.append(member + "\n");
        }
    }

    private void issueBook() {
        try {
            int bookId = Integer.parseInt(issueBookIdField.getText().trim());
            int memberId = Integer.parseInt(issueMemberIdField.getText().trim());
            int loanDays = Integer.parseInt(issueDaysField.getText().trim());

            libraryService.issueBook(bookId, memberId, loanDays);
            outputArea.setText("Issue book request completed.\n");
            viewAllLoans();
        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values for Book ID, Member ID, and Loan Days.");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void returnBook() {
        try {
            int loanId = Integer.parseInt(returnLoanIdField.getText().trim());
            libraryService.returnBook(loanId);
            outputArea.setText("Return book request completed.\n");
            viewAllLoans();
        } catch (NumberFormatException e) {
            showError("Please enter a valid Loan ID.");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void viewAllLoans() {
        outputArea.setText("=== ALL LOANS ===\n");
        var loans = libraryService.getAllLoans();

        if (loans.isEmpty()) {
            outputArea.append("No loans found.\n");
            return;
        }

        for (var loan : loans) {
            outputArea.append(loan + "\n");
        }
    }

    private void viewOverdueLoans() {
        outputArea.setText("=== OVERDUE LOANS ===\n");
        var loans = libraryService.getOverdueLoans();

        if (loans.isEmpty()) {
            outputArea.append("No overdue loans found.\n");
            return;
        }

        for (var loan : loans) {
            outputArea.append(loan + "\n");
        }
    }

    private void clearBookFields() {
        titleField.setText("");
        authorField.setText("");
        genreField.setText("");
        isbnField.setText("");
        copiesField.setText("");
        searchField.setText("");
    }

    private void clearMemberFields() {
        memberNameField.setText("");
        memberEmailField.setText("");
        memberPhoneField.setText("");
    }

    private void clearLoanFields() {
        issueBookIdField.setText("");
        issueMemberIdField.setText("");
        issueDaysField.setText("");
        returnLoanIdField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryUI ui = new LibraryUI();
            ui.setVisible(true);
        });
    }
}