package ru.grishchenko.mymarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.grishchenko.mymarket.models.Order;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select * from orders o where o.user_id=:uid", nativeQuery = true)
    List<Order> findOrdersByUserId(@Param("uid") Long uid);
}
