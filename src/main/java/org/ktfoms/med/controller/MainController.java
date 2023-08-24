package org.ktfoms.med.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.ktfoms.med.MedApplication;
import org.ktfoms.med.dto.FapFinDto;
import org.ktfoms.med.dto.FundingNormaSmpInfo;
import org.ktfoms.med.entity.Fap;
import org.ktfoms.med.entity.FundingNorma;
import org.ktfoms.med.entity.FundingNormaSmp;
import org.ktfoms.med.form.EditFapForm;
import org.ktfoms.med.form.EditFundingFapForm;
import org.ktfoms.med.form.EditFundingNormaForm;
import org.ktfoms.med.form.MonthForm;
import org.ktfoms.med.service.FapService;
import org.ktfoms.med.service.LpuService;
import org.ktfoms.med.enums.Month;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private LpuService lpuService;
    @Autowired
    private FapService fapService;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {
        logger.info("Вход в приложение с адреса: " + request.getRemoteAddr() + "| Логин: " + SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("today", Calendar.getInstance());
        return "index";
    }

    @RequestMapping(value = { "/publish" }, method = RequestMethod.GET)
    public String publish(Model model) {
        return "publish";
    }

    //Страница (основная) для справочника ЛПУ
    @RequestMapping(value = { "/lpu" }, method = RequestMethod.GET)
    public String lpu(Model model) {
        model.addAttribute("lpuList", lpuService.getLpuEntityList());
        return "lpu";
    }

    //Страница (вложенная) для справочника ЛПУ
    @RequestMapping(value = { "/lpu/{mcod}" }, method = RequestMethod.GET)
    public String lpuInfo(Model model, @PathVariable("mcod") Integer mcod) {
        model.addAttribute("lpu", lpuService.getLpuByMcod(mcod));
        return "lpu_info";
    }

    //Страница (основная) для справочника ФАП
    @RequestMapping(value = { "/fap" }, method = RequestMethod.GET)
    public String fap(Model model) {
        model.addAttribute("lpuList", fapService.getLpuFapCountDtoList());
        model.addAttribute("fapList", fapService.getFapEntityList());
        return "fap";
    }

    //Страница по ЛПУ (вложенная) для справочника ФАП
    @RequestMapping(value = { "/fap_by_lpu/{lpu}" }, method = RequestMethod.GET)
    public String fapByLpu(Model model, @PathVariable("lpu") String lpu) {
        model.addAttribute("lpu", lpuService.getLpuByOid(lpu));
        model.addAttribute("fapList", fapService.getFapEntityListByLpu(lpu));
        return "fap_by_lpu";
    }

    //Страница редактирования ФАП
    @RequestMapping(value = { "/edit_fap/{id}" }, method = RequestMethod.GET)
    public String editFap(Model model, @PathVariable("id") int id) {

        Fap entity = fapService.getFapById(id);
        EditFapForm editFapForm = new EditFapForm(entity);
        
        model.addAttribute("editFapForm", editFapForm);
        model.addAttribute("id", id);
        model.addAttribute("fap", entity);
        return "edit_fap";
    }

    //Сохранение записи ФАП
    @RequestMapping(value = { "/edit_fap/{id}" }, method = RequestMethod.POST)
    public String saveFap(Model model,
                          @PathVariable("id") int id,
                          EditFapForm editFapForm) {
        try {
            fapService.saveFap(id, editFapForm);
            return "redirect:/fap";
        }
        catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        return "error_catch";
        }
    }


    //Страница (основная) для справочника финансового обеспечения ФАП (СФОФ)
    @RequestMapping(value = { "/funding_fap" }, method = RequestMethod.GET)
    public String fundingFap(Model model) {
        model.addAttribute("lpuList", fapService.getLpuFapCountDtoList());
        model.addAttribute("year", LocalDate.now().getYear());
        model.addAttribute("isDecember", LocalDate.now().getMonthValue() == 12);

        return "funding_fap";
    }

    //Страница (вложенная) для справочника финансового обеспечения ФАП (СФОФ)
    @RequestMapping(value = { "/funding_fap/{lpu}/{year}" }, method = RequestMethod.GET)
    public String fundingFapByLpu(Model model,
                                  @PathVariable("lpu") String lpu,
                                  @PathVariable("year") String year) {
        model.addAttribute("lpu", lpuService.getLpuByOid(lpu));
        model.addAttribute("finFapDtos", fapService.getFapFinDtoListByLpu(Integer.parseInt(year),lpu));
        model.addAttribute("year", year);
        return "funding_fap_by_lpu";
    }


    //Страница с формой редактирования для СФОФ
    @RequestMapping(value = { "/edit_funding_fap/{podr}/{year}" }, method = RequestMethod.GET)
    public String editFundingFap(Model model,
                                   @PathVariable("podr") String podr,
                                 @PathVariable("year") String year) {

        FapFinDto dto = fapService.getFapFinDtoByPodrYear(podr, year);
        EditFundingFapForm editFundingFapForm = new EditFundingFapForm(dto);
        model.addAttribute("editFundingFapForm", editFundingFapForm);
        model.addAttribute("dto", dto);
        model.addAttribute("podr", podr);
        model.addAttribute("year", year);
        return "edit_funding_fap";
    }

    //Сохранение записи справочника СФОФ
    @RequestMapping(value = { "/edit_funding_fap/{podr}/{year}" }, method = RequestMethod.POST)
    public String saveFundingFap(@PathVariable("podr") String podr,
                                 @PathVariable("year") String year,
                                 EditFundingFapForm editFundingFapForm) {

        fapService.saveFapFin(podr, Integer.parseInt(year), editFundingFapForm);
        return "redirect:/funding_fap/" + podr.substring(0, 30) + "/" + year;
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
                                     @ModelAttribute("monthForm") MonthForm monthForm) {
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
            return "error_catch";
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
            return "error_catch";
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
            return "error_catch";
        }

    }

    //Справочник нормативов подушевого финансирования (СНПФ)
    @RequestMapping(value = "/funding_norma", method = RequestMethod.GET)
    public String showFundingNormaPage(Model model,
                                       @RequestParam(value = "message", required = false) String message) {
        List<FundingNorma> list = lpuService.getFundingNormaInfos();
        model.addAttribute("fundingNormaInfos", list);
        model.addAttribute("message", message);
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

    //Страница выбора месяца для процедуры заполнения справочника ПФ АПП по предыдущему месяцу
    @RequestMapping(value = { "/fill_next_month_norm_pd" }, method = RequestMethod.GET)
    public String fillNextMonthNormPd(Model model,
                                      @RequestParam (value = "warned", required = false, defaultValue = "false") Boolean warned,
                                      @RequestParam (value = "year", required = false) Integer year,
                                      @RequestParam (value = "month", required = false) Integer month,
                                      @RequestParam (value = "message", required = false) String message) {
        MonthForm monthForm = new MonthForm();
        monthForm.setYear(LocalDate.now().getYear());
        logger.info("Собираемся в спр. ПФ АПП заполнть какой-то месяц в данными из предыдущего месяца.");
        if (warned) {
            monthForm.setMonthByNum(month);
            monthForm.setYear(year);
            logger.info("Собираемся в спр. ПФ АПП заполнть месяц " + Month.of(month) + " " + year + "г данными из предыдущего месяца." +
                    " Предостережение о потере данных получено." +
                    " Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        }
        model.addAttribute("monthForm", monthForm);
        model.addAttribute("warned", warned);
        model.addAttribute("message", message);
        return "fill_next_month_norm_pd";
    }

    //Заполнить указанный месяц в справочнике ПФ АПП данными из предыдущего месяца
    // (если данные в базе уже есть: на первый раз предупреждает, на второй - заполняет)
    @RequestMapping(value = {"/fill_next_month_norm_pd"}, method = RequestMethod.POST)
    public String execFillNextMonthNormPdWarning (Model model,
                                                  @RequestParam (value = "warned", required = false) boolean warned,
                                                  @ModelAttribute("monthForm") MonthForm monthForm) {
        Integer month = monthForm.getMonth();
        Integer year = monthForm.getYear();
        LocalDate date = LocalDate.of(year, month, 01);
        logger.info("Хотим добавить записи с датой начала " + date);
        // Если за запрошенный период нет записей, или значимые поля в них пусты,
        // или пользователь предупрежден о последствиях процедуры, заполняем месяц по данным предыдущего.
        if (warned || !lpuService.isExistFundingNormaByDate(date) || lpuService.isEmptyFundingNormaByDate(date)){
            logger.info("Вызвана процедура заполнения спр. ПФ АПП за месяц " + Month.of(month) + " " + year + " данными из предыдущего месяца. " +
                    "Данный период не был заполнен ранее, или пользователь запустил операцию, будучи предупрежден.");
            //Добавляем записи
            try {
                logger.info("Пытаемся заполнить записи за требуемый месяц. " + Month.of(month));
                String message = lpuService.fillNextMonthNormPd(month, year);
                return "redirect:/funding_norma?message="
                        + URLEncoder.encode(message, StandardCharsets.UTF_8);
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error_catch";
            }
        } else {
            //если же за этот месяц есть заполненые поля в базе - предупредим пользователя о том,
            // что они будут заменены другими данными,
            // с этого момента пользователь предупрежден и может подтвердить свои намеренья
            return "redirect:/fill_next_month_norm_pd?warned=true&year=" + year
                    + "&month=" + month
                    + "&message=" + URLEncoder.encode("За запрошенный период в базе уже есть данные, " +
                    "продолжение этой операции приведет к их замене на данные скопированные с предыдущего периода. " +
                    "Будте внимательны, чтобы не потерять информацию!", StandardCharsets.UTF_8);
        }
    }

// Страница с выбором месяца для формирования справочника по подушевому финансированию АПП
    @RequestMapping(value = {"/get_norm_pd"}, method = RequestMethod.GET)
    public String getNormPd (Model model, @Param("message") String message) {
        MonthForm monthForm = new MonthForm();
        monthForm.setYear(LocalDate.now().getYear());
        model.addAttribute("monthForm", monthForm);
        model.addAttribute("message", message);
        return "get_norm_pd";
    }

    //Скачать справочник ПФ АПП для выбранного периода
    @RequestMapping(value = {"/get_norm_pd"}, method = RequestMethod.POST)
    public String redirectNormPdByMonth (Model model,
                                            @ModelAttribute("monthForm") MonthForm monthForm) {
        Integer month = monthForm.getMonth();
        Integer year = monthForm.getYear();
        LocalDate date = LocalDate.of(year, month, 01);
        if (lpuService.isExistFundingNormaByDate(date)) {
            return "redirect:/api/excel/get_norm_pd_excel/" + year.toString() + "/" + month.toString();
        } else {
            return "redirect:/get_norm_pd?message=No data";
        }
    }


    @RequestMapping(value = { "/upload_funding_norma_smp_xlsx" }, method = RequestMethod.POST)
    public String uploadFundingNormaSmp(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = lpuService.parseFundingNormaSmpXlsx(in);
        } catch (UnexpectedRollbackException e) {
            e.printStackTrace();
            message = "Данные за этот период были загружены ранее";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удается загрузить файл: " + file.getOriginalFilename() + " Error: " + e.getMessage();
        }
        return "redirect:/funding_norma_smp" + "?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    @RequestMapping(value = { "/funding_norma_smp" },  method = RequestMethod.GET)
    public String fundingNormaSmp(Model model, @Param("message") String message) {
        List<FundingNormaSmpInfo> list = lpuService.getFundingNormaSmpInfos();
        model.addAttribute("fundingNormaInfos", list);
        model.addAttribute("message", message);
        return "funding_norma_smp";
    }

    @PostMapping("/upload_funding_norma_smp_xml")
    public String uploadFundingNormaSmpXml(Model model, @RequestParam("file") MultipartFile file) {
        String message = "";

        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            message = lpuService.parseSpFundingNormaSmp(file);
            model.addAttribute("message", message);
        } catch (ConstraintViolationException e) {
            message = "Данные за этот период были загружены ранее";
            model.addAttribute("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Не удается загрузить файл: " + file.getOriginalFilename() + "Неверный формат. Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "message";
    }

    //Страница выбора месяца для процедуры заполнения справочника ПФ СМП по предыдущему месяцу
    @RequestMapping(value = { "/fill_next_month_norma_smp" }, method = RequestMethod.GET)
    public String fillNextMonthNormaSmp(Model model,
                                      @RequestParam (value = "warned", required = false, defaultValue = "false") Boolean warned,
                                      @RequestParam (value = "year", required = false) Integer year,
                                      @RequestParam (value = "month", required = false) Integer month,
                                      @RequestParam (value = "message", required = false) String message) {
        MonthForm monthForm = new MonthForm();
        monthForm.setYear(LocalDate.now().getYear());
        logger.info("Собираемся в спр. ПФ СМП заполнть какой-то месяц в данными из предыдущего месяца.");
        if (warned) {
            monthForm.setMonthByNum(month);
            monthForm.setYear(year);
            logger.info("Собираемся в спр. ПФ СМП заполнть месяц " + Month.of(month) + " " + year + "г данными из предыдущего месяца." +
                    " Предостережение о потере данных получено." +
                    " Имя пользователья: " + SecurityContextHolder.getContext().getAuthentication().getName());
        }
        model.addAttribute("monthForm", monthForm);
        model.addAttribute("warned", warned);
        model.addAttribute("message", message);
        return "fill_next_month_norma_smp";
    }

    //Заполнить указанный месяц в справочнике ПФ СМП данными из предыдущего месяца
    // (если данные в базе уже есть: на первый раз предупреждает, на второй - заполняет)
    @RequestMapping(value = {"/fill_next_month_norma_smp"}, method = RequestMethod.POST)
    public String execFillNextMonthNormaSmpWarning (Model model,
                                                  @RequestParam (value = "warned", required = false) boolean warned,
                                                  @ModelAttribute("monthForm") MonthForm monthForm) {
        Integer month = monthForm.getMonth();
        Integer year = monthForm.getYear();
        LocalDate date = LocalDate.of(year, month, 01);
        logger.info("Хотим добавить записи с датой начала " + date);
        // Если за запрошенный период нет записей, или значимые поля в них пусты,
        // или пользователь предупрежден о последствиях процедуры, заполняем месяц по данным предыдущего.
        if (warned || !lpuService.isExistFundingNormaSmpByDate(date)){
            logger.info("Вызвана процедура заполнения спр. ПФ АПП за месяц " + Month.of(month) + " " + year + " данными из предыдущего месяца. " +
                    "Данный период не был заполнен ранее, или пользователь запустил операцию, будучи предупрежден.");
            //Добавляем записи
            try {
                logger.info("Пытаемся заполнить записи за требуемый месяц. " + Month.of(month));
                String message = lpuService.fillNextMonthNormaSmp(month, year);
                return "redirect:/funding_norma_smp?message="
                        + URLEncoder.encode(message, StandardCharsets.UTF_8);
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "error_catch";
            }
        } else {
            //если же за этот месяц есть заполненые поля в базе - предупредим пользователя о том,
            // что они будут заменены другими данными,
            // с этого момента пользователь предупрежден и может подтвердить свои намеренья
            return "redirect:/fill_next_month_norma_smp?warned=true&year=" + year
                    + "&month=" + month
                    + "&message=" + URLEncoder.encode("За запрошенный период в базе уже есть данные, " +
                    "продолжение этой операции приведет к их замене на данные скопированные с предыдущего периода. " +
                    "Будте внимательны, чтобы не потерять информацию!", StandardCharsets.UTF_8);
        }
    }

    //Страница редактирования записи справочника ПФ СМП
    @RequestMapping(value = { "/edit_funding_norma_smp/{mcod}/{datebeg}/{dateend}" }, method = RequestMethod.GET)
    public String editFundingNormaSmp(Model model,
                                      @PathVariable("mcod") int mcod,
                                      @PathVariable("datebeg") LocalDate datebeg,
                                      @PathVariable("dateend") LocalDate dateend) {
        model.addAttribute("editFundingNormaForm", lpuService.getFundingNormaForm(mcod, datebeg, dateend));
        model.addAttribute("mcod", mcod);
            model.addAttribute("datebeg", datebeg);
            model.addAttribute("dateend", dateend);
            model.addAttribute("lpuName", lpuService.getLpuByMcod(mcod).getMNameS());
        return "edit_funding_norma_smp";
    }

    //Сохранение записи справочника  ПФ СМП
    @RequestMapping(value = { "/edit_funding_norma_smp/{mcod}/{datebeg}/{dateend}" }, method = RequestMethod.POST)
    public String saveFundingNormaSmp(@PathVariable("mcod") int mcod,
                                      @PathVariable("datebeg") LocalDate datebeg,
                                      @PathVariable("dateend") LocalDate dateend,
                                      EditFundingNormaForm editFundingNormaForm) {

        String message = lpuService.saveFundingNormaSmp(mcod, datebeg, dateend, editFundingNormaForm);
        return "redirect:/funding_norma_smp?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/403")
    public String accessDenied(Model model) {
        return "message";
    }
}
