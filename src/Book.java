import java.io.Serializable;
import java.util.Date;
import java.util.List;

import status.BookStatus;

public abstract class Book implements Serializable {
	private String isbn;
	private String title;
	private String author;
	private String subject;
	private String publisher;
	private String language;
	private int numPages;
	private double price;
	private BookStatus status;

	public Book() {
		isbn = "0";
		title = null;
		author = null;
		subject = null;
		publisher = null;
		language = null;
		numPages = 0;
		price = 0;
		status = BookStatus.AVAILABLE;
	}

	public Book(String isbn, String titl, String auth, String subj, String publ, String lang, int numP, double price, BookStatus stat) {
		this.isbn = isbn;
		this.title = titl;
		this.author = auth;
		this.subject = subj;
		this.publisher = publ;
		this.language = lang;
		this.numPages = numP;
		this.price = price;
		this.status = stat;
	}

	@Override
	public String toString() {
		return "\nTitle: " + title
			+ "\nAuthor: " + author
			+ "\nISBN: " + isbn
			+ "\nSubject: " + subject
			+ "\nPublisher: " + publisher
			+ "\nLanguage: " + language
			+ "\nNumber of Pages: " + numPages
			+ "\nPrice: " + price 
			+ "\nStatus: " + status + "\n";
	}

	// GETTERS AND SETTERS

	public void updateBookItemStatus(BookStatus stat) {
		this.status = stat;
	}


	// METHODS
	public boolean checkout(String isbn) {
		if (isbn.compareTo(this.isbn) == 0) {
			System.out.println("You have successfully checked out " + this.title + ".\nPlease return the book in two weeks.");
			this.updateBookItemStatus(BookStatus.LOANED);
			return true;
		}
		System.out.println("Cannot complete checkout. Please try again.");
		return false;
	}
}

