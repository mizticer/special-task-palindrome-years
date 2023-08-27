package pl.sylwek;

import pl.sylwek.exceptions.WrongDataException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

public class Main {
    public static void main(String[] args) {
        printBonusDatesBetween(2010, 2015);
    }

    public static void printBonusDatesBetween(int fromYear, int toYear) {
        //Validate - checking if the entered years are correct
        check(fromYear, toYear);
        //Extract all days between these dates
        List<String> datesBetween = getDatesBetween(fromYear, toYear);
        //Searching for dates that can be read backwards
        List<String> getSearchedData = getPalindromes(datesBetween);
        //Convert String to LocalDate
        List<LocalDate> convertedPalindromes = convertStringToLocalDate(getSearchedData);
        //Print the result of the method
        if (!convertedPalindromes.isEmpty()) {
            for (LocalDate days : convertedPalindromes) {
                System.out.println(days);
            }
        } else {
            System.out.println("Not found");
        }
    }

    public static void check(int fromYear, int toYear) {
        try {
            if (fromYear > toYear) {
                throw new WrongDataException("Wrong input data, the first input should be smaller");
            }
        } catch (WrongDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<LocalDate> convertStringToLocalDate(List<String> getSearchedData) {
        return getSearchedData.stream()
                .map(str -> LocalDate.parse(str, java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")))
                .collect(Collectors.toList());
    }

    public static List<String> getPalindromes(List<String> datesBetween) {
        return datesBetween.stream()
                .map(str -> str.replace("-", ""))
                .filter(str -> str.equals(new StringBuilder(str).reverse().toString()))
                .collect(Collectors.toList());
    }

    public static List<String> getDatesBetween(int fromYear, int toYear) {
        LocalDate dateFrom = LocalDate.of(fromYear, 1, 1);
        LocalDate dateEnd;
        if (fromYear == toYear) {
            dateEnd = LocalDate.of(toYear, 12, 31);
        } else {
            dateEnd = LocalDate.of(toYear, 1, 1);
        }
        return Stream.iterate(dateFrom, date -> date.plusDays(1))
                .limit(DAYS.between(dateFrom, dateEnd.plusDays(1)))
                .map(LocalDate::toString)
                .collect(Collectors.toList());
    }
}
