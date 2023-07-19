package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {
    private final JdbcTemplate jdbcTemplate;
    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Creates a new transfer
     * @param transfer the transfer to create
     * @return the created transfer
     */
    @Override
    public Transfer createTransfer(Transfer transfer) {
        int zero = 0;
        // Update account
        String sql;
        Integer newTransferId = 0;
        Integer check = 0;

        try {
            sql = "UPDATE account " +
                    "SET balance = balance - ? " +
                    "WHERE account_id = ? AND balance - ? >= ?";
            check = jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountFrom(), transfer.getAmount(),
                    zero);
            if (check > 0) {
                sql = "UPDATE account " +
                        "SET balance = balance + ? " +
                        "WHERE account_id = ?";
                jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountTo());
            } else {
                transfer.setTransferStatusId(3);
            }
            sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                    "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id";
            newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                    transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        } catch (DataAccessException e) {
            return null;
        }
        return getTransfer(newTransferId);
    }

    /**
     * Requests a new transfer.
     * @param transfer the transfer to request
     * @return the requested transfer
     */
    public Transfer requestTransfer(Transfer transfer) {
        String sql;
        Integer newTransferId = 0;

        try {
            sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                    "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id";
            newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                    transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
        } catch (DataAccessException e) {
            return null;
        }
        return getTransfer(newTransferId);
    }

    /**
     * Changes the status of a transfer.
     * @param transfer the transfer to update
     * @return the updated transfer
     */
    @Override
    public Transfer changeTransferStatus(Transfer transfer) {
        if (transfer.getTransferStatusId() == 3) {
            try {
                String sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?";
                jdbcTemplate.update(sql, transfer.getTransferStatusId(), transfer.getTransferId());
            } catch (DataAccessException e) {
                return null;
            }
        } else if (transfer.getTransferStatusId() == 2) {
            int zero = 0;
            String sql;
            Integer check = 0;
            try {
                sql = "UPDATE account " +
                        "SET balance = balance - ? " +
                        "WHERE account_id = ? AND balance - ? >= ?";
                check = jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountFrom(), transfer.getAmount(),
                        zero);
                if (check > 0) {
                    sql = "UPDATE account " +
                            "SET balance = balance + ? " +
                            "WHERE account_id = ?";
                    jdbcTemplate.update(sql, transfer.getAmount(), transfer.getAccountTo());
                } else {
                    transfer.setTransferStatusId(3);
                }
                sql = "UPDATE transfer SET transfer_status_id = ? WHERE transfer_id = ?";
                jdbcTemplate.update(sql, transfer.getTransferStatusId(), transfer.getTransferId());
            } catch (DataAccessException e) {
                return null;
            }
        }
        return getTransfer(transfer.getTransferId());
    }

    /**
     * Retrieves a transfer by its ID.
     * @param transferId the ID of the transfer to retrieve
     * @return the retrieved transfer
     */
    @Override
    public Transfer getTransfer(int transferId) {
        String sql = "SELECT * FROM transfer WHERE " +
                "transfer_id = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, transferId);
        if (rs.next()) {
            return mapRowToTransfer(rs);
        } else {
            return null;
        }
    }

    /**
     * Retrieves all transfers for a given account ID.
     * @param accountId the ID of the account
     * @return a list of transfers
     */
    @Override
    public List<Transfer> getAllTransfers(int accountId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE " +
                "account_from = ? OR account_to = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
        while (rs.next()) {
            transfers.add(mapRowToTransfer(rs));
        }
        return transfers;
    }

    /**
     * Maps a row from the result set to a Transfer object.
     * @param rs the result set
     * @return the mapped Transfer object
     */
    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
}
