package com.chandu.OneToOne.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandu.OneToOne.Entity.Address;

public interface AddressRepo extends JpaRepository<Address, Integer>{

}
