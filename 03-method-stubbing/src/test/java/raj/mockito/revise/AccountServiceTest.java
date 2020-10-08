package raj.mockito.revise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    //Create instance of AccountService
    private AccountService underTest;

    private BackgroundCheckService backgroundCheckService = mock(BackgroundCheckService.class);
    private ReferenceIdsManager referenceIdsManager = mock(ReferenceIdsManager.class);
    private AccountRepository accountRepository = mock(AccountRepository.class);

    @BeforeEach
    public void setup() {
        //We are injecting mock this time
        underTest = new AccountService(backgroundCheckService, referenceIdsManager, accountRepository);
    }

    @Test
    public void shouldOpenAccount() throws IOException {

        //This return will direct us to the else condition
        when(backgroundCheckService.confirm("Raj", "Bhatta", "1234", LocalDate.of(2020, 10, 2))).thenReturn(new BackgroundCheckResults("something_not_unacceptable", 100));
        when(referenceIdsManager.obtainId("Raj", "Bhatta", "1234", LocalDate.of(2020, 10, 2))).thenReturn("some_id");

        final AccountOpeningStatus accountOpeningStatus = underTest.openAccount("Raj", "Bhatta", "123", LocalDate.of(2020, 10, 10));
        assertEquals(AccountOpeningStatus.OPENED, accountOpeningStatus);
    }

    @Test
    public void shouldDeclineAccount() throws IOException {

        final String UNACCEPTABLE_RISK_PROFILE = "HIGH";

        //Let's add method subbing
        when(backgroundCheckService.confirm("Raj", "Bhatta", "1234", LocalDate.of(2020, 10, 2))).thenReturn(new BackgroundCheckResults(UNACCEPTABLE_RISK_PROFILE, 0));

        final AccountOpeningStatus accountOpeningStatus = underTest.openAccount("Raj", "Bhatta", "1234", LocalDate.of(2020, 10, 2));
        assertEquals(AccountOpeningStatus.DECLINED, accountOpeningStatus);
    }
}