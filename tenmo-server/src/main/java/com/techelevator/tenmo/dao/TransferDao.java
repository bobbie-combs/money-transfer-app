package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import java.util.List;

public interface TransferDao {

        /**
         * Creates a new transfer.
         * @param transfer the transfer object to create
         * @return the created transfer object
         */
        Transfer createTransfer(Transfer transfer);

        /**
         * Requests a transfer.
         * @param transfer the transfer object to request
         * @return the requested transfer object
         */
        Transfer requestTransfer(Transfer transfer);

        /**
         * Changes the status of a transfer.
         * @param transfer the transfer object to update
         * @return the updated transfer object
         */
        Transfer changeTransferStatus(Transfer transfer);

        /**
         * Retrieves a transfer by transfer ID.
         * @param transferId the ID of the transfer to retrieve
         * @return the retrieved transfer object
         */
        Transfer getTransfer(int transferId);

        /**
         * Retrieves all transfers associated with an account.
         * @param accountId the ID of the account
         * @return a list of transfers associated with the account
         */
        List<Transfer> getAllTransfers(int accountId);
}
