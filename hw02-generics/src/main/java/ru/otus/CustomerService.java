package ru.otus;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> map;

    public CustomerService() {
        map = new TreeMap<>();
    }

    public Map.Entry<Customer, String> getSmallest() {
        return cloneEntry(
                map.firstEntry()
        );
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return cloneEntry(
                map.higherEntry(customer)
        );
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> cloneEntry(Map.Entry<Customer, String> entry) {
        if (Objects.isNull(entry))
            return null;

        return Map.entry(entry.getKey().clone(), entry.getValue());
    }
}
