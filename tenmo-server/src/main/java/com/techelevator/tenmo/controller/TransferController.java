package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.*;


import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    @Autowired
    private AccountRepository accountRepository;
    private final TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/transfers")
    public Transfer saveTransfer(@Valid @RequestBody Transfer transfer) {
        Transfer newTransfer = null;
        if (transfer.getTransferTypeId() == 2) {
            newTransfer = transferDao.createTransfer(transfer);
        }
        return  newTransfer;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/transfers/requests")
    public Transfer requestTransfer(@Valid @RequestBody Transfer transfer) {
        Transfer newTransfer = null;

        newTransfer = transferDao.requestTransfer(transfer);

        return  newTransfer;
    }
    @PutMapping("/transfers/updates")
    public Transfer updateTransfer(@Valid @RequestBody Transfer transfer) {
        Transfer newTransfer = null;

        newTransfer = transferDao.changeTransferStatus(transfer);
        return  newTransfer;
    }

    @GetMapping("/transfers/accounts/{accountId}")
    public List<Transfer> findAllTransfers(@PathVariable int accountId) {
        return transferDao.getAllTransfers(accountId);
    }
    @GetMapping("/transfers/{transferId}")
    public Transfer findTransfer(@PathVariable int transferId) {

        return transferDao.getTransfer(transferId);
    }
}
