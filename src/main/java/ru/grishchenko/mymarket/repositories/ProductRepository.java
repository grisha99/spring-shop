package ru.grishchenko.mymarket.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.grishchenko.mymarket.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByPriceBetween(int min, int max, Pageable pageable);

}
