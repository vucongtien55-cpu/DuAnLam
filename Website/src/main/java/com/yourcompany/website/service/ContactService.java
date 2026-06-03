package com.yourcompany.website.service;

import com.yourcompany.website.entity.Contact;
import com.yourcompany.website.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public Contact createContact(String fullName, String email, String phone, String company, String subject, String message) {
        Contact contact = Contact.builder()
                .fullName(fullName)
                .email(email)
                .phone(phone)
                .company(company)
                .subject(subject)
                .message(message)
                .isRead(false)
                .build();
        return contactRepository.save(contact);
    }
}

