package ru.otus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
