package com.codmind.api_order.repository;

import com.codmind.api_order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//Crea un instancia de este objecto y lo mantiene en memoria, lo que spring llama bean
public interface ProductRepository extends JpaRepository<Product, Long> {
}
