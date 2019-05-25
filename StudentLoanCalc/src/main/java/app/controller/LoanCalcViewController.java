package app.controller;

import app.StudentCalc;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import org.apache.poi.ss.formula.functions.*;

public class LoanCalcViewController implements Initializable {
	private StudentCalc SC = null;
	@FXML
	private TableView<Data> table;
	@FXML
	private TextField LoanAmount;
	@FXML
	private TextField InterestRate;
	@FXML
	private TextField AmnOfYears;
	@FXML
	private TextField AdditionalPayment;
	@FXML
	private Label lblTotalPayemnts;
	@FXML
	private Label lblTotalInterest;
	@FXML
	private DatePicker PaymentStartDate;
	@FXML
	private TableColumn<Data, Integer> PaymentAmnCol;
	@FXML
	private TableColumn<Data, LocalDate> DueDateCol;
	@FXML
	private TableColumn<Data, Double> PaymentCol;
	@FXML
	private TableColumn<Data, Double> AddPaymentCol;
	@FXML
	private TableColumn<Data, Double> InterestCol;
	@FXML
	private TableColumn<Data, Double> PrincipleCol;
	@FXML
	private TableColumn<Data, Double> BalanceCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PaymentAmnCol.setCellValueFactory(new PropertyValueFactory<Data, Integer>("Payment #"));
		DueDateCol.setCellValueFactory(new PropertyValueFactory<Data, LocalDate>("Due Date"));
		PaymentCol.setCellValueFactory(new PropertyValueFactory<Data, Double>("Payment"));
		AddPaymentCol.setCellValueFactory(new PropertyValueFactory<Data, Double>("Additonal Payment"));
		InterestCol.setCellValueFactory(new PropertyValueFactory<Data, Double>("Interest"));
		PrincipleCol.setCellValueFactory(new PropertyValueFactory<Data, Double>("Principle"));
		BalanceCol.setCellValueFactory(new PropertyValueFactory<Data, Double>("Balance"));
	}

	public void setMainApp(StudentCalc sc) {
		this.SC = sc;
	}

	/**
	 * btnCalcLoan - Fire this event when the button clicks
	 * 
	 * @version 1.0
	 * @param event
	 */
	@FXML
	private void btnCalcLoan(ActionEvent event) {
		System.out.println("Payment #, Due Date, Payment, Additional Payment, Interest, Principle, Balance");
		ObservableList<Data> Data = FXCollections.observableArrayList();

		boolean off = false;
		double dLoanAmount = Double.parseDouble(LoanAmount.getText());
		double dInterestRate = Double.parseDouble(InterestRate.getText()) / 100;
		double dAmnOfYears = Double.parseDouble(AmnOfYears.getText());
		LocalDate localDate = PaymentStartDate.getValue().minusMonths(1);
		double dAddPayment = Double.parseDouble(AdditionalPayment.getText());

		double PMT = Math.abs(FinanceLib.pmt(dInterestRate / 12, dAmnOfYears * 12, dLoanAmount, 0, false));
		double TotalPayments = 0;
		double TotalInterest = 0;
		double Payment = PMT;
		double interest;
		double principle;
		double balance = dLoanAmount;

		for (int row = 1; row <= (dAmnOfYears * 12); row++) {
			interest = dInterestRate / 12 * balance;
			principle = PMT - interest + dAddPayment;
			if (balance - principle < 0) {
				principle = balance;
				off = true;
			}
			balance -= principle;
			localDate = localDate.plusMonths(1);
			TotalInterest += interest;

			Data.add(new Data(row, Math.round(Payment * 100.0) / 100.0, dAddPayment,
					Math.round(interest * 100.0) / 100.0, Math.round(principle * 100.0) / 100.0,
					Math.round(balance * 100.0) / 100.0, localDate));
			System.out.println(row + ",\t" + localDate + ", " + Math.round(Payment * 100.0) / 100.0 + ", " + dAddPayment
					+ ", " + Math.round(interest * 100.0) / 100.0 + ", " + Math.round(principle * 100.0) / 100.0 + ", "
					+ Math.round(balance * 100.0) / 100.0);
			if (off) {
				dAmnOfYears = row / 12;
				break;
			}
		}
		table.setItems(Data);

		TotalPayments = TotalInterest + dLoanAmount;
		lblTotalPayemnts.setText(String.format("%.02f", TotalPayments));
		lblTotalInterest.setText(String.format("%.02f", TotalInterest));

		System.out.println("Loan Amount: " + dLoanAmount);
		System.out.println("Interest Rate: " + dInterestRate);
		System.out.println("Term: " + dAmnOfYears);
		System.out.println("Additional Payment: " + dAddPayment);
		System.out.println("Total Payments: " + TotalPayments);
		System.out.println("Total Interest: " + TotalInterest);
		System.out.println("Final Payment Date: " + localDate);
	}
}