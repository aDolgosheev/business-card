package ru.dolgosheev.card.contact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactMessageRepository repository;

    public ContactService(ContactMessageRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ContactMessage save(ContactRequest request) {
        ContactMessage saved = repository.save(new ContactMessage(
                request.name().strip(),
                request.email().strip(),
                request.message().strip()));
        log.info("Новое сообщение #{} от {} <{}>", saved.getId(), saved.getName(), saved.getEmail());
        return saved;
    }
}
