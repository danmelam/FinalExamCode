package app.controller;

import java.time.LocalDate;

public class Data {

	private Integer PaymentAmn;
	private Double Payment, AddPayment, Interest, Principle, Balance;
	private LocalDate DueDate;

	public Data(Integer paymentAmn, Double payment, Double addPayment, Double interest, Double principle,
			Double balance, LocalDate dueDate) {
		PaymentAmn = new Integer(paymentAmn);
		Payment = new Double(payment);
		AddPayment = new Double(addPayment);
		Interest = new Double(interest);
		Principle = new Double(principle);
		Balance = new Double(balance);
		DueDate = dueDate;
	}

	public Integer getPaymentAmn() {
		return PaymentAmn;
	}

	public void setPaymentAmn(Integer paymentAmn) {
		PaymentAmn = paymentAmn;
	}

	public Double getPayment() {
		return Payment;
	}

	public void setPayment(Double payment) {
		Payment = payment;
	}

	public Double getAddPayment() {
		return AddPayment;
	}

	public void setAddPayment(Double addPayment) {
		AddPayment = addPayment;
	}

	public Double getInterest() {
		return Interest;
	}

	public void setInterest(Double interest) {
		Interest = interest;
	}

	public Double getPrinciple() {
		return Principle;
	}

	public void setPrinciple(Double principle) {
		Principle = principle;
	}

	public Double getBalance() {
		return Balance;
	}

	public void setBalance(Double balance) {
		Balance = balance;
	}

	public LocalDate getDueDate() {
		return DueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		DueDate = dueDate;
	}

}