public import java.util.Date;

public class BookReservation {
	private Date creationDate;
	private ReservationStatus status;
	private String bookItemBarcode;
	private String memberId;

	public static BookReservation fetchReservationDetails(String barcode) {
		return null;
	}
}

class BookLending { //removed public
	private Date creationDate;
	private Date dueDate;
	private Date returnDate;
	private String bookItemBarcode;
	private String memberId;

	public static boolean lendBook(String barcode, String memberId) {
		return false;
	}
	public static BookLending fetchLendingDetails(String barcode) {
		return null;
	}
}

class Fine { //removed public
	private Date creationDate;
	private double bookItemBarcode;
	private String memberId;

	public static void collectFine(String memberId, long days) {}
}
 
