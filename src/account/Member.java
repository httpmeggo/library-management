package account;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import status.*;

public class Member extends Account { // removed public
    private static ArrayList<Member> members = new ArrayList<Member>();
    private static long idCounter = 0;

    public String name;
    public String email;
    public String phone;

    private Date joinDate;
    private int totalBooksCheckedOut;

    // trackable human-readable ID
    public static synchronized String createID() {
        return String.valueOf(++idCounter);
    }

    public static ArrayList<Member> getMemberList(){
        return members;
    }

    public static Member getByEmail(String email) {
        for (int i = 0; i < members.size(); i++) {
            Member m = members.get(i);
            if (m.getEmail().equalsIgnoreCase(email)) {
                return m;
            }
        }
        return null;
    }

    // Member constructors
    public Member(Date jd, String name, String email, String phone, String id, String password) {
        this.joinDate = jd;
        this.totalBooksCheckedOut = 0;
        this.name = name;
        this.email = email;
        this.phone = phone;
        super.initAccountInfo(id, password, AccountStatus.ACTIVE, false);
    }

    // not used? will only cause problems
    // public Member() {
    //     this.joinDate = null;
    //     this.totalBooksCheckedOut = 0;
    //     this.name = null;
    //     this.email = null;
    //     this.phone = null;
    //     this.id = null;
    //     super.setPassword(null);
    //     this.status = null;
    // }

    // adds new member to members ArrayList
    public static void registerMember(String name, String email, String phone, String password) {
        Date registerDate = new Date();
        String newID = createID();

        Member newMember = new Member(registerDate, name, email, phone, newID, password);
        members.add(newMember);
    }

    public int getTotalBooksCheckedOut() {
        return this.totalBooksCheckedOut;
    }
    public String getEmail(){
        return this.email;
    }


    private void incrementTotalBooksCheckedOut() {
        totalBooksCheckedOut++;
    }

    public boolean checkoutBook(Book book) {
        if (this.totalBooksCheckedOut >= Constants.MAX_BOOKS_ISSUED_TO_A_USER) {
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

        this.incrementTotalBooksCheckedOut();
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
            member.decrementTotalBooksCheckedOut();
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

