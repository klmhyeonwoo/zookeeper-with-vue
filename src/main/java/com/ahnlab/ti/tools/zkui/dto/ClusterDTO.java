package com.ahnlab.ti.tools.zkui.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClusterDTO {
    @NotBlank
    private String host;
    @NotBlank
    private String name;
}
