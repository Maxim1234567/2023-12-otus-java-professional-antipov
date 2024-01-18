package ru.otus;

import java.util.List;
import java.util.Stack;

public class CustomerReverseOrder {

    private final List<Customer> stack = new Stack<>();

    public void add(Customer customer) {
        stack.add(customer);
    }

    public Customer take() {
        return stack.removeLast();
    }
}
