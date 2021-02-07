package ru.grishchenko.mymarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.grishchenko.mymarket.models.Order;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserUsername(String userName);
}
