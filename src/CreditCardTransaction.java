
public class CreditCardTransaction extends FineTransaction {

	private String nameOnCard;
}

class CheckTransaction extends FineTransaction {
	private String bankName;
	private String checkNumber;
}

class CashTransaction extends FineTransaction {
	private double cashTendered;
}