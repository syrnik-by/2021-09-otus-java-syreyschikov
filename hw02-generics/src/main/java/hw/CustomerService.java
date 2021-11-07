package hw;

import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    Map <Customer, String> customers = new HashMap<>();

    public Map.Entry<Customer, String> getSmallest() {

        return customers.entrySet().stream().min((entry1, entry2) -> entry1.getKey().getScores() > entry2.getKey().getScores() ? 1 : -1).get();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        Customer returnCustomer = null;
        Map.Entry<Customer, String> e = null;

        for (Map.Entry<Customer, String> entry : customers.entrySet()) {

            if (!entry.getKey().equals(customer) && entry.getKey().getScores()>customer.getScores()) {
                if (returnCustomer==null) {
                    returnCustomer = entry.getKey();
                    e = entry;
                }
                else if (entry.getKey().getScores()<returnCustomer.getScores()) {
                    returnCustomer = entry.getKey();
                    e = entry;
                }
            }
        }

        return e;
    }

    public void add(Customer customer, String data) {
        customers.put(customer,data);
    }
}
