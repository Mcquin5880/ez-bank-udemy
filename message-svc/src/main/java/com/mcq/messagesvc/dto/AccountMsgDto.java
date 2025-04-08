package com.mcq.messagesvc.dto;

public record AccountMsgDto(String accountNumber,
                            String firstName,
                            String lastName,
                            String email,
                            String mobileNumber) {
}
