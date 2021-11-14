package hw;

import java.util.Deque;
import java.util.LinkedList;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    Deque<Customer> customers = new LinkedList<>();

    public void add(Customer customer) {
        customers.addLast(customer);
    }

    public Customer take() {
        return customers.pollLast();
    }
}
