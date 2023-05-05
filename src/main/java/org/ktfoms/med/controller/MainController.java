package org.ktfoms.med.controller;

import org.ktfoms.med.dto.Test;
import org.ktfoms.med.entity.FundingNorma;
import org.ktfoms.med.entity.Lpu;
import org.ktfoms.med.form.EditFundingNormaForm;
import org.ktfoms.med.form.MonthForm;
import org.ktfoms.med.service.FapService;
import org.ktfoms.med.service.LpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Controller
public class MainController {

    @Autowired
    private LpuService lpuService;
    @Autowired
    private FapService fapService;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
//        System.out.println("!!!!!!!!!!!!!!!сервер отвечает!!!!!!!!!!!!!!!!");
        model.addAttribute("today", Calendar.getInstance());
        return "index";
    }

    //Страница (основная) для справочника финансового обеспечения ФАП (СФОФ)
    @RequestMapping(value = { "/funding_fap" }, method = RequestMethod.GET)
    public String fundingFap(Model model) {
        return "funding_fap";
    }

    //Здесь можно заполнить следующий период в СФОФ
    @RequestMapping(value = { "/fill_next_month" }, method = RequestMethod.GET)
    public String fillNextMonth(Model model) {
        MonthForm monthForm = new MonthForm();
        monthForm.setYear(LocalDate.now().getYear());
        model.addAttribute("monthForm", monthForm);
        return "fill_next_month";
    }

    ////POST заполнить следующий период в СФОФ, но выведет предупреждение если данные за этот период уже есть.
    @RequestMapping(value = {"/fill_next_month_warning"}, method = RequestMethod.POST)
    public String execFillNextMonthWarning (Model model,
                                     @ModelAttribute("monthForm") MonthForm monthForm) throws NoSuchFieldException {
        Integer month = monthForm.getMonth();
        Integer year = monthForm.getYear();
        try {
            if (fapService.isFieldsAreFilled(month, year)){
                model.addAttribute("monthForm", monthForm);
                return "fill_next_month_warning";
            }else {
                fapService.fillNextMonth(month, year);
                return "fill_next_month_done";
            }
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }


    //POST заполнить следующий период в СФОФ
    //todo: Реализовать перенос данных прошлого года на январь следующего
    @RequestMapping(value = {"/fill_next_month/{year}/{month}"}, method = RequestMethod.POST)
    public String execFillNextMonth (Model model,
                                     @PathVariable("year") int year,
                                     @PathVariable("month") int month) {
        try {
            fapService.fillNextMonth(month, year);
        }catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        return "fill_next_month_done";
    }

    //Страница запуска рассчета финансирования ФАП за указанный месяц
    @RequestMapping(value = { "/funding_calc" }, method = RequestMethod.GET)
    public String fundingСalc(Model model) {
        MonthForm monthForm = new MonthForm();
        monthForm.setYear(LocalDate.now().getYear());
        model.addAttribute("monthForm", monthForm);
        return "funding_calc";
    }

    //Процедура рассчета  финансирования ФАП за указанный месяц
    @RequestMapping(value = {"/funding_calc"}, method = RequestMethod.POST)
    public String execFundingСalc (Model model,
                                     @ModelAttribute("monthForm") MonthForm monthForm) {
        try {
            Integer month = monthForm.getMonth();
            Integer year = monthForm.getYear();
            fapService.fundingCalc(month, year);
            return "funding_calc_done";
        }catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }

    }

    //Справочник нормативов подушевого финансирования (СНПФ)
    @RequestMapping(value = "/funding_norma", method = RequestMethod.GET)
    public String showFundingNormaPage(Model model) {
        List<FundingNorma> list = lpuService.getFundingNormaInfos();
        model.addAttribute("fundingNormaInfos", list);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.MM.uuuu");
        model.addAttribute("dateTimeFormatter", dateTimeFormatter);
        return "funding_norma_page";
    }

    //Страница редактирования записи справочника СНПФ
    @RequestMapping(value = { "/edit_funding_norma/{id}" }, method = RequestMethod.GET)
    public String editFundingNorma(Model model, @PathVariable("id") int id) {
        EditFundingNormaForm editFundingNormaForm = new EditFundingNormaForm();
        FundingNorma entity = lpuService.getFundingNormaById(id);
        if (!Objects.isNull(entity.getQuantityInAstr())){
        editFundingNormaForm.setQuantityInAstr(entity.getQuantityInAstr().toString());}
        if (!Objects.isNull(entity.getQuantityInKap())){
        editFundingNormaForm.setQuantityInKap(entity.getQuantityInKap().toString());}
        if (!Objects.isNull(entity.getNorma())){
        editFundingNormaForm.setNorma(String.valueOf(entity.getNorma()));
        }
        model.addAttribute("editFundingNormaForm", editFundingNormaForm);
        model.addAttribute("id", id);
        model.addAttribute("lpu", entity);
        return "edit_funding_norma";
    }

    //Сохранение записи справочника СНПФ
    @RequestMapping(value = { "/edit_funding_norma/{id}" }, method = RequestMethod.POST)
    public String saveFundingNorma(@PathVariable("id") int id,
                                   EditFundingNormaForm editFundingNormaForm) {

        lpuService.saveFundingNorma(id, editFundingNormaForm);
        return "redirect:/funding_norma";
    }

    //Страница формы для добавления записей справочника СНПФ для нового периода
    @RequestMapping(value = {"/add_period_funding_norma"}, method = RequestMethod.GET)
    public String addPeriodFundingNorma (Model model) {
        MonthForm monthForm = new MonthForm();
        monthForm.setYear(LocalDate.now().getYear());
        model.addAttribute("monthForm", monthForm);
        return "add_period_funding_norma";
    }

    //Добавит записи справочника СНПФ для нового периода
    @RequestMapping(value = {"/add_period_funding_norma"}, method = RequestMethod.POST)
    public String createPeriodFundingNorma (Model model,
                                            @ModelAttribute("monthForm") MonthForm monthForm) {
        Integer month = monthForm.getMonth();
        Integer year = monthForm.getYear();
        lpuService.addPeriodFundingNorma(month, year);
        return "redirect:/funding_norma";
    }


}
