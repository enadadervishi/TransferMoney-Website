package it.polimi.tiw.dao;

import it.polimi.tiw.models.AddressBook;
import it.polimi.tiw.models.Table;
import it.polimi.tiw.operation.transfer.TermField;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AddressBookDao extends ModelDao<AddressBook> {

    public AddressBookDao(Connection connection) {
        super(Table.ADDRESS_BOOK, connection, AddressBook::new);
    }

    public List<AddressBook> selectByUserId(long userId) throws SQLException {
        return executor.selectAll("userId=?", "",
                new Object[] {userId}, AddressBook::new);
    }

    public boolean containsRecipient(long userId, long recipientUserId, long recipientAccountId) throws SQLException {
        return executor.selectFirst("userId=? AND recipientUserId=? AND recipientAccountId=?", "",
                new Object[] {userId, recipientUserId, recipientAccountId}, AddressBook::new)
                .isPresent();
    }

    public List<AddressBook> selectByUserIdAndTerm(long userId, TermField field, String term) throws SQLException {
        return executor.selectAll(String.format("userId=? AND %s LIKE '%s%%'", field.getTableName(), term),
                "", new Object[]{userId},
                AddressBook::new);
    }
}
