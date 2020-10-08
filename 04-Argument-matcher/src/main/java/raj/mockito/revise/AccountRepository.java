package raj.mockito.revise;

import raj.mockito.revise.model.Account;

import java.time.LocalDate;

public interface AccountRepository {
    boolean save(String id, String firstName, String lastName, String taxId, LocalDate dob, BackgroundCheckResults backgroundCheckResults);

    boolean isExpired(Account account);
}
