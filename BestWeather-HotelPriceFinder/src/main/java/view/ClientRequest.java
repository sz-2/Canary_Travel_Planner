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
		datamartManagerBuilder.readFromDatamart(this.getQueryParameters());
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
				LocalDate fourDaysAhead = today.plusDays(4);

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
		List<String> destinosDisponibles = List.of("Tenerife", "Gran Canaria", "Lanzarote", "Fuerteventura",
				"La Palma", "La Gomera", "El Hierro", "La Graciosa");
		String destino;
		do {
			System.out.print("Enter the Canary Island name: ");
			destino = scanner.nextLine();
			if (!destinosDisponibles.contains(destino)) {
				System.out.println("Invalid destination. Please choose a Canary Island");
			}
		} while (!destinosDisponibles.contains(destino));

		return destino;
	}
}
