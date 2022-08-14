import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import status.AccountStatus;
import status.BookStatus;

// For simplicity, we are not defining getter and setter functions. The reader can
// assume that all class attributes are private and accessed through their respective
// public getter methods and modified only through their public methods function.

public abstract class Account {
    private String id;
    private String password;
    private AccountStatus status;

    public boolean resetPassword() {
        return false;
    }

    public boolean checkPassword(String p) {
        return this.password.equals(p);
    }
}

class Librarian extends Account { // removed public
    String id;
    String password;
    AccountStatus status;

    public Librarian(String id, String pw, AccountStatus status) {
        this.id = id;
        this.password = pw;
        this.status = status;
    }
    
    public boolean addBook(Book book) {
        return false;
    }

    public boolean blockMember(Member member) {
        return false;
    }

    public boolean unBlockMember(Member member) {
        return false;
    }
}

class Member extends Account { // removed public
    private Date dateOfMembership;
    private int totalBooksCheckedout;
    String name;
    String email;
    String phone;
    private String id;
    String password;
    AccountStatus status;
    private static ArrayList<Member> members = new ArrayList<Member>();
    private static long idCounter = 0;

    // trackable human-readable ID
    public static synchronized String createID() {
        return String.valueOf(idCounter++);
    }

    // Member constructors
    public Member(Date dom, String name, String email, String phone, String id, String password) {
        this.dateOfMembership = dom;
        this.totalBooksCheckedout = 0;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.id = id;
        this.password = password;
        this.status = AccountStatus.ACTIVE;
    }

    public Member(){
        this.dateOfMembership = null;
        this.totalBooksCheckedout = 0;
        this.name = null;
        this.email = null;
        this.phone = null;
        this.id = null;
        this.password = null;
        this.status = null;
    }

    // adds new member to members ArrayList
    public static void registerMember(String name, String email, String phone, String password) {
        Date registerDate = new Date();
        String newid = createID();

        Member newMember = new Member(registerDate, name, email, phone, newid, password);
        members.add(newMember);
    }

    public int getTotalBooksCheckedout(Member a) {
        return a.totalBooksCheckedout;
    }
    public static ArrayList getMemberList(){
        return members;
    }
    public static String getEmail(Member a){
        return a.email;
    }



    private void incrementTotalBooksCheckedout() {
    }

    public boolean checkoutBook(Book book) {
        if (this.getTotalBooksCheckedOut() >= Constants.MAX_BOOKS_ISSUED_TO_A_USER) {
            ShowError("The user has already checked-out maximum number of books");
            return false;
        }

        BookReservation bookReservation = BookReservation.fetchReservationDetails(book.getBarcode());
        if (bookReservation != null && bookReservation.getMemberId() != this.getId()) {
            // book item has a pending reservation from another user
            ShowError("This book is reserved by another member");
            return false;
        } else if (bookReservation != null) {
            // book item has a pending reservation from the give member, update it
            bookReservation.updateStatus(ReservationStatus.COMPLETED);
        }

        if (!book.checkout(this.getId())) {
            return false;
        }

        this.incrementTotalBooksCheckedout();
        return true;
    }

    public void returnBook(Book book) {
        this.checkForFine(book.getBarcode());
        BookReservation bookReservation = BookReservation.fetchReservationDetails(book.getBarcode());
        if (bookReservation != null) {
            // book item has a pending reservation
            book.updateBookStatus(BookStatus.RESERVED);
            bookReservation.sendBookAvailableNotification();
        }
        book.updateBookStatus(BookStatus.AVAILABLE);
    }

    public boolean renewBook(Book book) {
        this.checkForFine(book.getBarcode());
        BookReservation bookReservation = BookReservation.fetchReservationDetails(book.getBarcode());
        // check if this book item has a pending reservation from another member
        if (bookReservation != null && bookReservation.getMemberId() != this.getMemberId()) {
            ShowError("This book is reserved by another member");
            member.decrementTotalBooksCheckedout();
            book.updateBookState(BookStatus.RESERVED);
            bookReservation.sendBookAvailableNotification();
            return false;
        } else if (bookReservation != null) {
            // book item has a pending reservation from this member
            bookReservation.updateStatus(ReservationStatus.COMPLETED);
        }
        BookLending.lendBook(book.getBarCode(), this.getMemberId());
        book.updateDueDate(LocalDate.now().plusDays(Constants.MAX_LENDING_DAYS));
        return true;
    }
}
