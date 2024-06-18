package com.projectapi.Project_NIC.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WatermarkRequest {

    private long application_transaction_id;
    private String watermark;
}
