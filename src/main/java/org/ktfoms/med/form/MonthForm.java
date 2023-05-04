package org.ktfoms.med.form;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter

public class MonthForm {
    private Integer month;

    @Setter
    private Integer year;
    private final List<String> monthnames = List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");


    public void setMonth(String month) {
        this.month = monthnames.indexOf(month) + 1;
    }

}
