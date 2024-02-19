package ru.otus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Utils {

    public static List<Banknote> generateBanknote(Denomination denomination, int size) {
        return Stream.generate(() -> new Banknote(denomination))
                .limit(size)
                .toList();
    }

    public static List<Banknote> unionList(List<Banknote>... listBanknotes) {
        List<Banknote> banknotes = new ArrayList<>();

        for (List<Banknote> list: listBanknotes) {
            banknotes = Stream.concat(banknotes.stream(), list.stream()).toList();
        }

        return banknotes;
    }

}
