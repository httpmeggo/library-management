import java.util.Date;

public abstract class FineTransaction {

	private Date creationDate;
	private double amount;
	
	public boolean initiateTransaction() {
		return true;
	}
	
}
