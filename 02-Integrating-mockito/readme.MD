## Unit test without Mockito ##
Let us consider we do have class as given below:

```java
package raj.mockito.revise;

import java.io.IOException;
import java.time.LocalDate;

public class AccountService {

    private static final String UNACCEPTABLE_RISK_PROFILE = "HIGH";
    private BackgroundCheckService backgroundCheckService;
    private ReferenceIdsManager referenceIdsManager;
    private AccountRepository accountRepository;


    public AccountService(BackgroundCheckService backgroundCheckService,
                          ReferenceIdsManager referenceIdsManager,
                          AccountRepository accountRepository) {
        this.backgroundCheckService = backgroundCheckService;
        this.referenceIdsManager = referenceIdsManager;
        this.accountRepository = accountRepository;
    }


    public AccountOpeningStatus openAccount(String firstName, String lastName, String taxId, LocalDate dob)
            throws IOException {

        final BackgroundCheckResults backgroundCheckResults = backgroundCheckService.confirm(firstName,
                lastName,
                taxId,
                dob);

        if (backgroundCheckResults == null || backgroundCheckResults.getRiskProfile().equals(UNACCEPTABLE_RISK_PROFILE)) {
            return AccountOpeningStatus.DECLINED;
        } else {
            final String id = referenceIdsManager.obtainId(firstName, lastName, taxId, dob);
            if (id != null) {
                accountRepository.save(id, firstName, lastName, taxId, dob, backgroundCheckResults);
                return AccountOpeningStatus.OPENED;
            } else {
                return AccountOpeningStatus.DECLINED;
            }
        }
    }
}
```
In our last tutorial, we provided null value for all the collaborator for AccountService. This time, let's mock all the collaborator using mockito

```java
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
```

### Result ###
<src img="error2.png" />

This time our test is still failing but we are not getting nullpointer exception anymore.