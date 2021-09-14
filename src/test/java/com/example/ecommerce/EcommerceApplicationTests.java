package com.example.ecommerce;

import com.example.ecommerce.models.Address;
import com.example.ecommerce.models.Customer;
import com.example.ecommerce.repository.AddressRepo;
import com.example.ecommerce.repository.CustomerRepo;
import com.example.ecommerce.services.ShipmentService;
import com.example.ecommerce.services.ShipmentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


class EcommerceApplicationTests {

	@Test
	void testGetAddress() throws SQLException {
		List<Address> addresses = shipmentService.getAddress(1);
		List<Address> addresses1=shipmentService.getAddress(1);
//		Assertions.assertEquals("test city", addresses.get(0).getCity());
		Assertions.assertEquals(addresses1,addresses);
	}


	@BeforeEach
	public void init()
	{
		MockitoAnnotations.initMocks(this);
		shipmentService = new ShipmentServiceImpl(addressRepo, customerRepo);
		Optional<Customer> customer = Optional.of(new Customer());
		Mockito.doReturn(customer).when(customerRepo).findById(any());
		Address address = new Address();
		address.setCity("test city");
		Mockito.doReturn(Arrays.asList(address)).when(addressRepo).findAllByCustomer(any(Customer.class));
	}

	private ShipmentService shipmentService;

	@Mock
	private AddressRepo addressRepo;
	@Mock
	private CustomerRepo customerRepo;



}
