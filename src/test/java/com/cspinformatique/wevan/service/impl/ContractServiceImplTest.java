//package com.cspinformatique.wevan.service.impl;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import com.cspinformatique.wevan.repository.ContractRepository;
//import com.cspinformatique.wevan.service.impl.ContractServiceImpl;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ContractServiceImplTest {
//	@Mock private ContractRepository contractRepository;
//	
//	@InjectMocks private ContractServiceImpl contractServiceImpl;
//	
//	@Test
//	public void fetchContractsTest(){
//		Mockito.when(contractRepository.findLastContractModified()).thenReturn(null);
//		
//		contractServiceImpl.fetchContracts();
//	}
//}
