package ru.itis.dis403;

import ru.itis.dis403.config.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.itis.dis403.model.Phone;
import ru.itis.dis403.repository.PhoneRepository;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);

        Phone phone = new  Phone();
        phone.setNumber("1234567890");
        PhoneRepository phoneRepository = (PhoneRepository) context.getBean(PhoneRepository.class);
        phoneRepository.save(phone);

    }
}