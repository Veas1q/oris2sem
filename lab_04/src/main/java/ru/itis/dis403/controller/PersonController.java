package ru.itis.dis403.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.dis403.model.Person;
import ru.itis.dis403.model.Phone;
import ru.itis.dis403.service.PersonService;
import ru.itis.dis403.service.PhoneService;
import java.util.List;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;
    private final PhoneService phoneService;

    public PersonController(PersonService personService, PhoneService phoneService) {
        this.personService = personService;
        this.phoneService = phoneService;
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute  ("person", new Person());
        model.addAttribute("allPhones", phoneService.findAll());
        return "form";
    }

    @PostMapping("/save")
    public String savePerson(@ModelAttribute Person person,
                             @RequestParam(required = false) List<Long> phoneIds,
                             @RequestParam(required = false) List<String> newPhones) {

        if (phoneIds != null) {
            for (Long phoneId : phoneIds) {
                phoneService.findById(phoneId).ifPresent(phone ->
                        person.getPhones().add(phone)
                );
            }
        }

        if (newPhones != null) {
            for (String newPhone : newPhones) {
                if (newPhone != null && !newPhone.trim().isEmpty()) {
                    Phone phone = new Phone();
                    phone.setNumber(newPhone.trim());
                    person.getPhones().add(phone);
                }
            }
        }

        personService.save(person);
        return "redirect:/persons/all";
    }

    @GetMapping("/all")
    public String listAll(Model model) {
        model.addAttribute("persons", personService.findAll());
        return "list";
    }
}