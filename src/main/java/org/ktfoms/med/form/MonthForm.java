package org.ktfoms.med.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class MonthForm {
    private Integer month;

    @Setter
    private Integer year;
    private final List<String> monthnames = List.of("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");


    public void setMonth(String month) {
        this.month = monthnames.indexOf(month) + 1;
    }

    public String getMonthAsString() {
        if (this.month != null){return this.monthnames.get(this.month - 1);}
        else {return null;}
    }
    public void setMonthByNum(Integer month) {
        this.month = month;
    }
    public void setMonthAsString(String month) {
        this.month = monthnames.indexOf(month) + 1;
    }
}
