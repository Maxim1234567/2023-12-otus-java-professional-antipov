package ru.otus;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    private final Deque<Customer> queue = new ArrayDeque<>();

    public void add(Customer customer) {
        queue.push(customer);
    }

    public Customer take() {
        return queue.pop();
    }
}
