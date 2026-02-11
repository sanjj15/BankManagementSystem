package com.wipro.bank.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.util.DButil;

public class BankDAO {
    public int generateSequenceNumber() {
    	String query = "SELECT transactionId_seq.NEXTVAL FROM dual";

        try (Connection connection = DButil.getDBConnection()) {

            if (connection == null) return 0;

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public boolean validateAccount(String accountNumber) {
        String query = "SELECT 1 FROM ACCOUNT_TBL WHERE ACCOUNT_NUMBER=?";
        try (Connection connection = DButil.getDBConnection()) {
            if (connection == null) return false;
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public float findBalance(String accountNumber) {
        if (!validateAccount(accountNumber)) return -1;
        String query = "SELECT BALANCE FROM ACCOUNT_TBL WHERE ACCOUNT_NUMBER=?";
        try (Connection connection = DButil.getDBConnection()) {
            if (connection == null) return -1;
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public boolean updateBalance(String accountNumber, float newBalance) {
        String query = "UPDATE ACCOUNT_TBL SET BALANCE=? WHERE ACCOUNT_NUMBER=?";
        try (Connection connection = DButil.getDBConnection()) {
            if (connection == null) return false;
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setFloat(1, newBalance);
            ps.setString(2, accountNumber);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean transferMoney(TransferBean transferBean) {
        transferBean.setTransactionID(generateSequenceNumber());
        String query = "INSERT INTO TRANSFER_TBL VALUES (?,?,?,?,?)";
        try (Connection connection = DButil.getDBConnection()) {
            if (connection == null) return false;
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, transferBean.getTransactionID());
            ps.setString(2, transferBean.getFromAccountNumber());
            ps.setString(3, transferBean.getToAccountNumber());
            ps.setDate(4, new Date(transferBean.getDateOfTransaction().getTime()));
            ps.setFloat(5, transferBean.getAmount());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
