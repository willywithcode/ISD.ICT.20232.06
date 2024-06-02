package org.example.common.exception;

public class TransactionFailedException extends PaymentException {

    public TransactionFailedException() {
        super("ERROR: Giao dịch thất bại!");
    }

}
