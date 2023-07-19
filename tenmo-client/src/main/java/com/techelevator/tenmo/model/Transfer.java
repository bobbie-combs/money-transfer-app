/**
The Transfer class represents a transfer of funds in the Tenmo application.
It contains information about the transfer, including its ID, type, status, accounts involved, and amount.
*/
package com.techelevator.tenmo.model;
import java.math.BigDecimal;

public class Transfer {
    private int transferId; // The ID of the transfer
    private int transferTypeId; // The ID representing the type of transfer
    private int transferStatusId; // The ID representing the status of the transfer
    private int accountFrom; // The account number from which funds are transferred
    private int accountTo; // The account number to which funds are transferred
    private BigDecimal amount; // The amount of funds being transferred

    /**
     * Constructs a Transfer object with the provided parameters.
     *
     * @param transferTypeId   The ID representing the type of transfer
     * @param transferStatusId The ID representing the status of the transfer
     * @param accountFrom      The account number from which funds are transferred
     * @param accountTo        The account number to which funds are transferred
     * @param amount           The amount of funds being transferred
     */
    public Transfer(int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal amount) {
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    /**
     * Empty constructor for the Transfer class.
     */
    public Transfer() {

    }

    /**
     * Retrieves the ID of the transfer.
     *
     * @return The transfer ID
     */
    public int getTransferId() {
        return transferId;
    }

    /**
     * Sets the ID of the transfer.
     *
     * @param transferId The transfer ID to set
     */
    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    /**
     * Retrieves the ID representing the type of transfer.
     *
     * @return The transfer type ID
     */
    public int getTransferTypeId() {
        return transferTypeId;
    }

    /**
     * Sets the ID representing the type of transfer.
     *
     * @param transferTypeId The transfer type ID to set
     */
    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    /**
     * Retrieves the ID representing the status of the transfer.
     *
     * @return The transfer status ID
     */
    public int getTransferStatusId() {
        return transferStatusId;
    }

    /**
     * Sets the ID representing the status of the transfer.
     *
     * @param transferStatusId The transfer status ID to set
     */
    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    /**
     * Retrieves the account number from which funds are transferred.
     *
     * @return The account number from
     */
    public int getAccountFrom() {
        return accountFrom;
    }

    /**
     * Sets the account number from which funds are transferred.
     *
     * @param accountFrom The account number from to set
     */
    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    /**
     * Retrieves the account number to which funds are transferred.
     *
     * @return The account number to
     */
    public int getAccountTo() {
        return accountTo;
    }

/**
 * Sets the account number to which funds are transferred.
 * @param accountTo The account number
 */
    /**
     * Sets the account number to which funds are transferred.
     *
     * @param accountTo The account number to set as the destination of the transfer
     */
    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    /**
     * Retrieves the amount of funds being transferred.
     *
     * @return The transfer amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the amount of funds being transferred.
     *
     * @param amount The transfer amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * *Returns a string representation of the Transfer object.
     *
     * @return A string containing the transfer details
     */
    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", transferTypeId=" + transferTypeId +
                ", transferStatusId=" + transferStatusId +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", amount=" + amount +
                '}';
    }
}