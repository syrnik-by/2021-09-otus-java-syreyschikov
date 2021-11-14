package hw;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    final Map<Customer, String> customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        TreeMap<Customer, String> customersCopy = copy();
        return customersCopy.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        TreeMap<Customer, String> customersCopy = copy();

        return customersCopy.higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }

    private TreeMap<Customer, String> copy() {
        TreeMap<Customer, String> customerCopy = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
        for (Customer key : customers.keySet()) {
            customerCopy.put(new Customer(key.getId(), key.getName(), key.getScores()), customers.get(key));
        }
        return customerCopy;
    }
}
