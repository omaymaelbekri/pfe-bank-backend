package org.sid.ebankingbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sid.ebankingbackend.dtos.CustomerDTO;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.mappers.BankAccountMapperImpl;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.sid.ebankingbackend.services.BankAccountServiceImpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BankAccountMapperImpl dtoMapper;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCustomer() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("John Doe");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        when(dtoMapper.fromCustomerDTO(any(CustomerDTO.class))).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(dtoMapper.fromCustomer(any(Customer.class))).thenReturn(customerDTO);

        // Act
        CustomerDTO savedCustomerDTO = bankAccountService.saveCustomer(customerDTO);

        // Assert
        assertNotNull(savedCustomerDTO);
        assertEquals(customerDTO.getId(), savedCustomerDTO.getId());
        assertEquals(customerDTO.getName(), savedCustomerDTO.getName());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    // Additional tests for other methods can be added here
}
