import java.io.Serializable;
import java.util.Date;
import java.util.List;

public abstract class Book implements Serializable {
	private String ISBN;
	private String title;
	private String author;
	private String subject;
	private String publisher;
	private String language;
	private int numberOfPages;
	private double price;
	private BookStatus status;

	public Book() {
		ISBN = "0";
		title = null;
		author = null;
		subject = null;
		publisher = null;
		language = null;
		numberOfPages = 0;
		price = 0;
		status = BookStatus.AVAILABLE;
	}

	public Book(String newISBN, String newTitle, 
			String newAuthor, String newSubject, 
			String newPublisher, String newLanguage,
			int newNumOfPages, double newPrice, BookStatus newStatus) {
		this.ISBN = newISBN;
		this.title = newTitle;
		this.author = newAuthor;
		this.subject = newSubject;
		this.publisher = newPublisher;
		this.language = newLanguage;
		this.numberOfPages = newNumOfPages;
		this.price = newPrice;
		this.status = newStatus;
	}

	@Override
	public String toString() {
		return "\nTitle: " + title + "\nAuthor: " + author + "\nISBN: " 
				+ ISBN + "\nSubject: " + subject + "\nPublisher: " + 
				publisher + "\nLanguage: " + language + 
				"\nNumber of Pages: " + numberOfPages + "\nPrice: " + price 
				+ "\nStatus: " + status + "\n";
	}

	//GETTERS AND SETTERS

	public void updateBookItemStatus(BookStatus stat) {
		this.status = stat;
	}


	//METHODS
	public boolean checkout(String isbn) {
		if(isbn.compareTo(this.ISBN) == 0) {
			System.out.println("You have successfully checked out " + this.title
					+ "\nPlease return the book in two weeks.");
			this.updateBookItemStatus(BookStatus.LOANED);
			return true;
		}
		System.out.println("Cannot complete checkout. Please try again.");
		return false;
	}
}

