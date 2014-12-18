package com.cspinformatique.wevan.branch.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cspinformatique.wevan.branch.entity.Branch;
import com.cspinformatique.wevan.branch.repository.BranchRepository;
import com.cspinformatique.wevan.branch.service.BranchService;
import com.cspinformatique.wevan.contract.service.ContractService;
import com.cspinformatique.wevan.security.entity.Role;
import com.cspinformatique.wevan.security.entity.User;
import com.cspinformatique.wevan.security.service.RoleService;
import com.cspinformatique.wevan.security.service.UserService;

@Service
public class BranchServiceImpl implements BranchService {
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private ContractService contractService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;

	@Override
	public List<Branch> findActiveBranches() {
		return this.branchRepository.findActiveBranches();
	}

	@Override
	public List<Branch> findAll() {
		return this.branchRepository.findAll();
	}

	@Override
	public Branch findOne(int id) {
		return this.branchRepository.findOne(id);
	}

	@Override
	public Branch findOne(String name) {
		return this.branchRepository.findByName(name);
	}

	@Override
	public Branch saveBranch(Branch branch) {
		if (branch.getRegistrationDate() == null) {
			branch.setRegistrationDate(new Date());
		}

		branch = this.branchRepository.save(branch);

		this.userService.save(new User(branch, branch.getName().toLowerCase(),
				branch.getName().toLowerCase(), Arrays
						.asList(new Role[] { this.roleService
								.findByName(Role.Type.BRANCH_OWNER.toString()) })));

		new Thread(new Runnable(){
			@Override
			public void run() {
				contractService.fetchContractsOnWaiting();
			}
		}).start();

		return branch;
	}
}
