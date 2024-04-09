package org.sid.ebankingbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.services.BankService;

import java.util.Optional;

import static org.mockito.Mockito.*;

class BankServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankService bankService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consulterShouldCallBankAccountRepository() {
        // Arrange
        String accountId = "0b36be78-8d5d-446b-9f20-37eadc9d3c3b";
        BankAccount mockBankAccount = mock(BankAccount.class);
        Customer mockCustomer = mock(Customer.class); // Mocking Customer to avoid null pointer when getCustomer() is called

        when(mockBankAccount.getCustomer()).thenReturn(mockCustomer); // Ensure getCustomer() returns the mocked Customer
        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.of(mockBankAccount));

        // Act
        bankService.consulter();

        // Assert
        verify(bankAccountRepository, times(1)).findById(accountId);
        // You can also verify interactions with the mockCustomer if needed
    }
}
