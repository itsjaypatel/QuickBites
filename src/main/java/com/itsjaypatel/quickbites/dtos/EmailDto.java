package com.itsjaypatel.quickbites.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class EmailDto {

    private String subject;

    private String content;

    private List<String> to;

    @Builder.Default
    private List<String> cc = new ArrayList<>();

    @Builder.Default
    private List<String> bcc = new ArrayList<>();

//    @Builder.Default
//    private Map<String,MultipartFile> attachments = new HashMap<>();

    @Builder.Default
    private EmailPriority priority = EmailPriority.NORMAL;


    @Getter
    @AllArgsConstructor
    public enum EmailPriority {
        LOW(5), NORMAL(3), HIGH(1);
        private final int code;
    }
}
