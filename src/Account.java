import java.util.*;

//Basic Main Class

public class Account {
Scanner MyScanner;
String holder;
String Response;
boolean complete;
protected int exit = 1;

public Account() {
	Transactions A = new Transactions();
	while (exit != 0) {
System.out.println("Please enter in a command (Deposit, Withdraw, Balance, Exit) : ");
MyScanner = new Scanner(System.in);
holder = new String(MyScanner.next());
holder = holder.toLowerCase();
A.compute(holder); 
complete = A.getSuccess();
Response = A.getResult();
if (Response.equals("exit")) {
	exit = 0;
}

	
}


}
	public static void main(String[] args) {
		Account A = new Account();

	}

}
