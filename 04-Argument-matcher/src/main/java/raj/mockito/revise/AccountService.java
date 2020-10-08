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