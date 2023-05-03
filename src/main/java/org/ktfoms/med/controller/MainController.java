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

@Controller
public class MainController {

    @Autowired
    private LpuService lpuService;
    @Autowired
    private FapService fapService;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {
        System.out.println("!!!!!!!!!!!!!!!сервер отвечает!!!!!!!!!!!!!!!!");
        model.addAttribute("today", Calendar.getInstance());
        return "index";
    }
    @RequestMapping(value = { "/funding_fap" }, method = RequestMethod.GET)
    public String fundingFap(Model model) {
        return "funding_fap";
    }

    @RequestMapping(value = { "/fill_next_month" }, method = RequestMethod.GET)
    public String fillNextMonth(Model model) {
        MonthForm monthForm = new MonthForm();
        model.addAttribute("monthForm", monthForm);
        return "fill_next_month";
    }
    //todo: добавить обработку ошибки
    @RequestMapping(value = {"/fill_next_month"}, method = RequestMethod.POST)
    public String execFillNextMonth (Model model,
                                    @ModelAttribute("monthForm") MonthForm monthForm) throws NoSuchFieldException {

        Integer month = monthForm.getMonth();
        Integer year = monthForm.getYear();
        fapService.fillNextMonth(month, year);
        return "fill_next_month_done";
    }

    @RequestMapping(value = { "/funding_calc" }, method = RequestMethod.GET)
    public String fundingСalc(Model model) {
        MonthForm monthForm = new MonthForm();
        model.addAttribute("monthForm", monthForm);
        return "funding_calc";
    }

    @RequestMapping(value = {"/funding_calc"}, method = RequestMethod.POST)
    public String execFundingСalc (Model model,
                                     @ModelAttribute("monthForm") MonthForm monthForm) {

        Integer month = monthForm.getMonth();
        Integer year = monthForm.getYear();
        fapService.fundingCalc(month, year);
        return "funding_calc_done";
    }

    @RequestMapping(value = "/funding_norma", method = RequestMethod.GET)
    public String showFundingNormaPage(Model model) {
        List<FundingNorma> list = lpuService.getFundingNormaInfos();
        model.addAttribute("fundingNormaInfos", list);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.MM.uuuu");
        model.addAttribute("dateTimeFormatter", dateTimeFormatter);
        return "funding_norma_page";
    }

    @RequestMapping(value = { "/edit_funding_norma/{id}" }, method = RequestMethod.GET)
    public String editFundingNorma(Model model, @PathVariable("id") int id) {
        EditFundingNormaForm editFundingNormaForm = new EditFundingNormaForm();
        FundingNorma entity = lpuService.getFundingNormaById(id);
        editFundingNormaForm.setQuantityInAstr(entity.getQuantityInAstr().toString());
        editFundingNormaForm.setQuantityInKap(entity.getQuantityInKap().toString());
        editFundingNormaForm.setNorma(String.valueOf(entity.getNorma()));
        model.addAttribute("editFundingNormaForm", editFundingNormaForm);
        model.addAttribute("id", id);
        model.addAttribute("lpu", entity);
        return "edit_funding_norma";
    }

    @RequestMapping(value = { "/edit_funding_norma/{id}" }, method = RequestMethod.POST)
    public String saveFundingNorma(@PathVariable("id") int id,
                                   EditFundingNormaForm editFundingNormaForm) {

        lpuService.saveFundingNorma(id, editFundingNormaForm);
        return "redirect:/funding_norma";
    }

//Вернет краказябры, даже если найдет шаблон в виндовой кодировке
//    @RequestMapping(value = { "/sp" }, method = RequestMethod.GET, produces = "application/xml;charset=windows-1251")
//    public String sp() throws IOException {
//        fapService.getFileSpfinfap();
//        return "sp_fin_fap.xml";
//    }

}
