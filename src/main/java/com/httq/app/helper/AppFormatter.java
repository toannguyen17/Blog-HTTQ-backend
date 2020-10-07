package com.httq.app.helper;

import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class AppFormatter {
    public String dateFormat(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm dd-MM-YYYY"));
    }

    public String currencyFormat(Long amount){
        return NumberFormat.getCurrencyInstance(new Locale("vi","VN")).format(amount);
    }
}
