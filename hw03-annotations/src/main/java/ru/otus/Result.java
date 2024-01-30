package ru.otus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private int successfully;
    private int failed;
    private int total;

    void incrementSuccessfully() {
        successfully++;
    }

    void incrementFailed() {
        failed++;
    }

    void incrementTotal() {
        total++;
    }
}
