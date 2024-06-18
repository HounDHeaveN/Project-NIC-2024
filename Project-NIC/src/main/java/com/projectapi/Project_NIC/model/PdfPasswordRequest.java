package com.projectapi.Project_NIC.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PdfPasswordRequest {

    private long application_transaction_id;
    private String password;

}
