package com.motorliberacaocredito.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.util.List;

/**
 * Entidade JPA que mapeia a tabela CLIENTES no banco H2.
 *
 * Separada do modelo de domínio (Cliente) para garantir
 * que as anotações JPA não "contaminem" o domínio de negócio.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CLIENTES")
public class ClienteEntity {


    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "SALDO", nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo;


    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TransacaoEntity> transacoes;


}