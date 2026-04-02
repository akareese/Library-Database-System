package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.LoanDAO;
import com.library.dao.MemberDAO;
import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.Member;

import java.time.LocalDate;
import java.util.List;

public class LibraryService {
    private final BookDAO bookDAO;
    private final MemberDAO memberDAO;
    private final LoanDAO loanDAO;

    public LibraryService() {
        this.bookDAO = new BookDAO();
        this.memberDAO = new MemberDAO();
        this.loanDAO = new LoanDAO();
    }

    public void addBook(Book book) {
        bookDAO.addBook(book);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public List<Book> searchBooks(String keyword) {
        return bookDAO.searchBooks(keyword);
    }

    public void updateBook(Book book) {
        Book existingBook = bookDAO.getBookById(book.getBookId());
        if (existingBook == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.getAvailableCopies() > book.getTotalCopies()) {
            System.out.println("Available copies cannot be greater than total copies.");
            return;
        }

        bookDAO.updateBook(book);
    }

    public void deleteBook(int bookId) {
        Book existingBook = bookDAO.getBookById(bookId);
        if (existingBook == null) {
            System.out.println("Book not found.");
            return;
        }

        bookDAO.deleteBook(bookId);
    }

    public void addMember(Member member) {
        memberDAO.addMember(member);
    }

    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }

    public void issueBook(int bookId, int memberId, int loanDays) {
        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        Member member = memberDAO.getMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        if (book.getAvailableCopies() <= 0) {
            System.out.println("No available copies for this book.");
            return;
        }

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(loanDays);

        Loan loan = new Loan(bookId, memberId, issueDate, dueDate);
        loanDAO.issueLoan(loan);

        boolean updated = bookDAO.updateAvailableCopies(bookId, book.getAvailableCopies() - 1);
        if (!updated) {
            System.out.println("Warning: loan created, but book availability was not updated.");
        }
    }

    public void returnBook(int loanId) {
        Loan loan = loanDAO.getActiveLoanById(loanId);
        if (loan == null) {
            System.out.println("Active loan not found.");
            return;
        }

        Book book = bookDAO.getBookById(loan.getBookId());
        if (book == null) {
            System.out.println("Related book not found.");
            return;
        }

        boolean returned = loanDAO.returnLoan(loanId, LocalDate.now());
        if (!returned) {
            System.out.println("Could not return book.");
            return;
        }

        boolean updated = bookDAO.updateAvailableCopies(book.getBookId(), book.getAvailableCopies() + 1);
        if (!updated) {
            System.out.println("Warning: loan returned, but book availability was not updated.");
            return;
        }

        System.out.println("Book returned successfully.");
    }

    public List<Loan> getAllLoans() {
        return loanDAO.getAllLoans();
    }

    public List<Loan> getOverdueLoans() {
        return loanDAO.getOverdueLoans();
    }
}