package com.yourcompany.website.controller;

import com.yourcompany.website.dto.response.ApiResponse;
import com.yourcompany.website.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/contact")
    public ResponseEntity<ApiResponse<String>> createContact(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String subject,
            @RequestParam String message) {
        try {
            contactService.createContact(fullName, email, phone, company, subject, message);
            return ResponseEntity.ok(ApiResponse.success("Liên hệ thành công. Chúng tôi sẽ phản hồi trong 24 giờ.", "OK"));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Lỗi khi lưu liên hệ: " + e.getMessage()));
        }
    }
}

