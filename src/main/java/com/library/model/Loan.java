package com.library.model;

import java.time.LocalDate;

public class Loan {
    private int loanId;
    private int bookId;
    private int memberId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Loan() {
    }

    public Loan(int bookId, int memberId, LocalDate issueDate, LocalDate dueDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public Loan(int loanId, int bookId, int memberId, LocalDate issueDate, LocalDate dueDate, LocalDate returnDate) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return String.format(
                "Loan ID: %d | Book ID: %d | Member ID: %d | Issued: %s | Due: %s | Returned: %s",
                loanId, bookId, memberId, issueDate, dueDate,
                returnDate == null ? "Not returned" : returnDate.toString()
        );
    }
}