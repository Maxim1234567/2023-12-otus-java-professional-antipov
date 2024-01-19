package ru.otus;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> map;

    public CustomerService(Comparator<Customer> comparator) {
        map = new TreeMap<>(comparator);
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
