package com.projectapi.Project_NIC.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "document")

public class ClientDocument {

    @Id
    private UUID document_id;
    private Date created_on;
    private Application application;
    private Module module;
    private Workflow workflow;
    private FileInformation file_information;
    private CreatedBy created_by;
    private CreatedFor created_for;
    private DocumentContent document;
    private AdditionalInfo additional_info_1;
    private AdditionalInfo additional_info_2;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Application {
        private String application_id;
        private String application_name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Module {
        private String module_id;
        private String module_name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Workflow {
        private String workflow_id;
        private String workflow_name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileInformation {
        private long application_transaction_id;
        private String file_type;
        private String file_name;
        private String creation_date;
        private String creation_time;
        private String system_ip;
        private String system_mac;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatedBy {
        private String employee_code;
        private String employee_name;
        private String designation;
        private String organization;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatedFor {
        private int person_id;
        private String name;
        private String gender;
        private int age_yr;
        private long mobile_number;
        private long additional_id_1;
        private String additional_id_1_description;
        private String additional_id_2;
        private String additional_id_2_description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DocumentContent {
        private String actual_document_base_64;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdditionalInfo {
        private Info info_1;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {
        private String greeting;
    }


}

