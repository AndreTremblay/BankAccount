import java.util.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;

//This class handles logic checking

public class Transactions {
	
//variables used throughout  
	
	String result;
	double Balance, amount;
	DecimalFormat df= new DecimalFormat("#.00");
	Scanner myScanner;
	boolean success = false;
	BufferedWriter output;
	BufferedReader br;
	FileInputStream fstream;
	
	//constructor initializes balance for Deposit and Withdrawal computations
public Transactions() {
//Track account Balance
		Balance = 0;
		try {
			br = new BufferedReader(new FileReader("log.html"));
			String nextLine;
			double next;
			while ((nextLine = br.readLine()) != null) {
			//find the transactions table
				if (nextLine.contains(("transactions")) == true) {
				//find the beginning of the number values
					while ((nextLine = br.readLine()).contains("</tbody>") == false ) {
						if (nextLine.contains("<tr><td>") == true) {
							nextLine = nextLine.replaceAll("<tr><td>", "");
							nextLine = nextLine.replaceAll("</td></tr>", "");
						//add the number values together 
							next = Double.parseDouble(nextLine);
							Balance = Balance + next;
						}
					}
				}
			}
			//close the file
			br.close();
		}
		
		//throw an error upon not finding the file
		catch (Exception e) {
			result = "Error finding file.";
			System.out.println(result);
		}
}

//major computations

public void compute(String hold) {	
	success = false;
	
	//DEPOSIT BLOCK
	if (hold.equals("deposit") == true) {
		System.out.println("Please enter an amount to deposit: ");
		myScanner = new Scanner(System.in);
	while (success == false) {
		if (myScanner.hasNextDouble() == true) {
			amount = myScanner.nextDouble();
			
			//check for negative deposit
			if (amount < 0) {
				result = ("Invalid amount entered.\n");
				System.out.println(result + "\n");
				System.out.println("Please enter an amount to deposit: ");
				myScanner = new Scanner(System.in);
			}
			
			//correct deposit amount
			else {
			Balance = Balance + amount;
			result = new String("Deposit Successful.");
			System.out.println(result + "\n");
			success = true;
			
			//update the log file by writing to a new log and copying over
			try {
				PrintWriter writer = new PrintWriter("newlog.html", "UTF-8");
				String lineSource;
				String holdLine;
				String lastLine = "";
				BufferedReader brSource = new BufferedReader(new FileReader("log.html"));
				while ((lineSource = brSource.readLine()) != null) {
					writer.println(lineSource);
					if (lineSource.contains(("transactions")) == true) {
						while ((lineSource = brSource.readLine()).contains("</tbody>") == false ) {
							writer.println(lineSource);
							lastLine = lineSource;
						}
						break;
					}
					
				}
				
				//add the new transaction
				holdLine = lastLine.replaceFirst("(-*)(\\d+).(\\d)*", "" + amount);
				writer.println(holdLine);
				writer.println(lineSource);
				while ((lineSource = brSource.readLine()) != null) {
					writer.println(lineSource);
				}
				
				//close the open files
				brSource.close();
				writer.close();
				
				//replace the old log with the new log
				File p1 = new File("log.html");
				File p2 = new File("newlog.html");
				p2.renameTo(p1);
				p1.delete();
				p2.renameTo(p1);
			}
			
			//catch the file path error
			catch (IOException e) {
				System.out.println("File Path Error.");
			}
			
			break;
			}
		}
		//incorrect input 
		else {
			result = ("Invalid amount entered.");
			System.out.println(result + "\n");
			System.out.println("Please enter an amount to deposit: ");
			myScanner = new Scanner(System.in);
		}
	}
	}
	
	//WITHDRAW BLOCK
	
	else if (hold.equals("withdraw") == true) {
		System.out.println("Please enter an amount to withdraw: ");
		myScanner = new Scanner(System.in);
		while (success == false) {
			if (myScanner.hasNextDouble() == true) {
				amount = myScanner.nextDouble();
				
				//check for negative withdrawal amount
				if (amount < 0) {
					result = ("Invalid amount entered.\n");
					System.out.println(result + "\n");
					System.out.println("Please enter an amount to withdraw: ");
					myScanner = new Scanner(System.in);
				}
				
				//check for insufficient funds
				else if (amount > Balance) {
					result = new String("Not enough funds.");
					System.out.println(result + "\n");
					System.out.println("Please enter an amount to withdraw: ");
					myScanner = new Scanner(System.in);
				
				}
				
				//correct input
				else {
				Balance = Balance - amount;
				result = new String("Withdraw Successful.");
				System.out.println(result + "\n");
				success = true;
				
				//write the withdrawal to the log file
				try {
					PrintWriter writer = new PrintWriter("newlog.html", "UTF-8");
					String lineSource;
					String holdLine;
					String lastLine = "";
					
					//read in the file
					//write the new file
					BufferedReader brSource = new BufferedReader(new FileReader("log.html"));
					while ((lineSource = brSource.readLine()) != null) {
						writer.println(lineSource);
						if (lineSource.contains(("transactions")) == true) {
							while ((lineSource = brSource.readLine()).contains("</tbody>") == false ) {
								writer.println(lineSource);
								lastLine = lineSource;
							}
							break;
						}
						
					}
					
					//add the new transaction
					holdLine = lastLine.replaceFirst("(-*)(\\d+).(\\d)*", "-" + amount);
					writer.println(holdLine);
					writer.println(lineSource);
					while ((lineSource = brSource.readLine()) != null) {
						writer.println(lineSource);
					}
					
					//close the open files
					brSource.close();
					writer.close();
					
					//delete old log, rename new log
					File p1 = new File("log.html");
					File p2 = new File("newlog.html");
					p2.renameTo(p1);
					p1.delete();
					p2.renameTo(p1);
				}
				
				//catch a missed file exception
				catch (IOException e) {
					System.out.println("File Path Error.");
				}
				break;
				}
			}
			
			//invalid input
			else {
				result = ("Invalid amount entered.\n");
				System.out.println(result + "\n");
				System.out.println("Please enter an amount to withdraw: ");
				myScanner = new Scanner(System.in);
			}
		}
		
	}
	
	// BALANCE BLOCK
	else if (hold.equals("balance") == true){
		Balance = 0;
		try {
			
			//read the balance in the current log file
			br = new BufferedReader(new FileReader("log.html"));
			String nextLine;
			double next;
			
			//parse out the tags and find the correct value, then add them together to find the balance
			while ((nextLine = br.readLine()) != null) {
				if (nextLine.contains(("transactions")) == true) {
					while ((nextLine = br.readLine()).contains("</tbody>") == false ) {
						if (nextLine.contains("<tr><td>") == true) {
							nextLine = nextLine.replaceAll("<tr><td>", "");
							nextLine = nextLine.replaceAll("</td></tr>", "");
							next = Double.parseDouble(nextLine);
							Balance = Balance + next;
						}
					}
				}			
			}
			
			//close to open log
			br.close();
			
			//return the balance
			result = ("The current balance is: $" + Balance);
			success = true;
			System.out.println(result);
			
		}
		
		//catch error
		catch (Exception e) {
			result = "Error finding file.";
			System.out.println(result);
		}
	}
	
	//EXIT BLOCK
	else if (hold.equals("exit") == true) {
		result = "exit";
		success = true;
	}
	
	//INCORRECT INPUT BLOCK
	else {
		result = new String("Invalid input.");
		System.out.println(result);
	}
	}

//GETTERS AND SETTERS
public String getResult() {
	return result;
}

public boolean getSuccess() {
	return success;
}

}
