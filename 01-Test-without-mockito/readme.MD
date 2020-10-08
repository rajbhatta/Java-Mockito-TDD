## Junit test without Mockito ##
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

```java
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
```

### Problems ###
<src img="error1.png"/>
According to above error, we are getting null from 

```java
 final BackgroundCheckResults backgroundCheckResults = backgroundCheckService.confirm(firstName,
                lastName,
                taxId,
                dob);
```