package hw;

import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    final Map <Customer, String> customers = new HashMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        Map<Customer, String> customersCopy = copy();
        return customersCopy.entrySet().stream().min((entry1, entry2) -> entry1.getKey().getScores() > entry2.getKey().getScores() ? 1 : -1).get();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        Map<Customer, String> customersCopy = copy();
        Customer returnCustomer = null;
        Map.Entry<Customer, String> e = null;

        for (Map.Entry<Customer, String> entry : customersCopy.entrySet()) {

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

    private Map<Customer, String> copy() {
        Map<Customer, String> customerCopy = new HashMap<Customer, String>();
        for(Customer key : customers.keySet()) {
            customerCopy.put(new Customer(key.getId(), key.getName(), key.getScores()), customers.get(key));
        }
        return customerCopy;
    }
}
