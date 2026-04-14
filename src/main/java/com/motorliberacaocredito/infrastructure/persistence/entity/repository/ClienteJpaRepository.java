package com.motorliberacaocredito.infrastructure.persistence.entity.repository;

import com.motorliberacaocredito.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, String> {



}
