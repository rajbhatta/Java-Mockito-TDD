package raj.mockito.revise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountServiceTest {

    //Create instance of AccountService
    private AccountService underTest;

    @BeforeEach
    public void setup(){
        //We are injecting null without doing mock
        underTest=new AccountService(null,null,null);
    }

    @Test
    void openAccountTest() throws IOException {
        final AccountOpeningStatus accountOpeningStatus=underTest.openAccount("Raj","Bhatta","1234", LocalDate.of(2020,10,2));
        assertEquals(AccountOpeningStatus.OPENED,accountOpeningStatus);
    }
}