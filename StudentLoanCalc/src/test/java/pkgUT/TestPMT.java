package pkgUT;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.apache.poi.ss.formula.functions.*;
import org.junit.Test;
import app.controller.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TestPMT {

	@Test
	public void test() {
		double PMT;
		double r = 0.07 / 12;
		double n = 20 * 12;
		double p = 150000;
		double f = 0;
		boolean t = false;
		PMT = Math.abs(FinanceLib.pmt(r, n, p, f, t));

		double PMTExpected = 1162.95;

		assertEquals(PMTExpected, PMT, 0.01);

	}

	public static double[] buttonAction(double dLoanAmount, double dInterestRate, int dAmnOfYears, LocalDate localDate,
			double dAddPayment) {
		ObservableList<Data> Data = FXCollections.observableArrayList();

		boolean off = false;
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
			if (off) {
				dAmnOfYears = row / 12;
				break;
			}
		}

		TotalPayments = TotalInterest + dLoanAmount;

		double[] answer = { TotalPayments, TotalInterest, dAmnOfYears };
		return answer;
	}

	@Test
	public void buttonActionTest() {
		double[] answerLst = buttonAction(200000, 0.07, 15, LocalDate.of(2016, 1, 1), 100);
		assertEquals(answerLst[0], 311244.53, 0.01);
		assertEquals(answerLst[1], 111244.53, 0.01);
		assertEquals(answerLst[2], 13, 0.01);

	}

}