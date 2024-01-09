package view;

import control.DatamartManagerBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientRequest {
	private final Scanner scanner;
	private final DatamartManagerBuilder datamartManagerBuilder;

	public ClientRequest(DatamartManagerBuilder datamartManagerBuilder) {
		this.scanner = new Scanner(System.in);
		this.datamartManagerBuilder = datamartManagerBuilder;
	}

	public void execute(){
		do{
			datamartManagerBuilder.readFromDatamart(this.getQueryParameters());
		}while (askUserToContinue(scanner));
		scanner.close();
		System.exit(0);
	}

	private List<String> getQueryParameters(){
		List<String> clientRequest = new ArrayList<>();
		System.out.println("Welcome to Canary Islands Travel Planner");
		clientRequest.add(captureAndValidateDestiny());
		clientRequest.add(captureAndValidateDate("check-in", clientRequest));
		clientRequest.add(captureAndValidateDate("check-out", clientRequest));
		return clientRequest;
	}

	private String captureAndValidateDate(String dateType, List<String> clientRequest) {
		while (true) {
			try {
				System.out.printf("Enter the %s date (format YYYY-MM-DD): ", dateType);
				String inputDate = scanner.nextLine();
				LocalDate parsedDate = LocalDate.parse(inputDate);
				LocalDate today = LocalDate.now();
				LocalDate fourDaysAhead = today.plusDays(5);

				if (!(parsedDate.isBefore(today) || parsedDate.isAfter(fourDaysAhead) || (dateType.equals("check-out") && parsedDate.isBefore(LocalDate.parse(clientRequest.get(1)))))) {
					return inputDate;
				} else {
					System.out.printf("Please enter a %s date between %s and %s.%n", dateType, today, fourDaysAhead);
				}
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use YYYY-MM-DD.");
			}
		}
	}

	private String captureAndValidateDestiny() {
		List<String> destinosDisponibles = List.of("tenerife", "gran canaria", "lanzarote", "fuerteventura",
				"la palma", "la gomera", "el hierro", "la graciosa");
		String destino;
		do {
			System.out.print("Enter the Canary Island name: ");
			destino = scanner.nextLine().toLowerCase();
			if (!destinosDisponibles.contains(destino)) {
				System.out.println("Invalid destination. Please choose a Canary Island");
			}
		} while (!destinosDisponibles.contains(destino));
		return destino;
	}

	private static boolean askUserToContinue(Scanner scanner) {
		System.out.print("Do you want to continue? (yes/no): ");
		String userInput = scanner.nextLine().toLowerCase();
		if (userInput.equals("no")) {
			System.out.println("Exiting the application.");
			return false;
		}
		System.out.println("------------------------------------------------------------------------");
		return true;
	}
}
