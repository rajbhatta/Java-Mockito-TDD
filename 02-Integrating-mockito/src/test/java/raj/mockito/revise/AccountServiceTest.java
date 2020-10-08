package raj.mockito.revise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class AccountServiceTest {

    //Create instance of AccountService
    private AccountService underTest;

    private BackgroundCheckService backgroundCheckService=mock(BackgroundCheckService.class);
    private ReferenceIdsManager referenceIdsManager=mock(ReferenceIdsManager.class);
    private AccountRepository accountRepository=mock(AccountRepository.class);

    @BeforeEach
    public void setup(){
        //We are injecting mock this time
        underTest=new AccountService(backgroundCheckService,referenceIdsManager,accountRepository);
    }

    @Test
    void openAccountTest() throws IOException {
        final AccountOpeningStatus accountOpeningStatus=underTest.openAccount("Raj","Bhatta","1234", LocalDate.of(2020,10,2));
        assertEquals(AccountOpeningStatus.OPENED,accountOpeningStatus);
    }
}