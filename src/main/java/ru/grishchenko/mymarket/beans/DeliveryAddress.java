package ru.grishchenko.mymarket.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryAddress {
    // начальный вариант, сделано классом для дальнейшего разбития на составляющие (индекс, город, улица.....)
    private String address;

}
