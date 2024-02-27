package com.ahnlab.ti.tools.zkui.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ClusterDTO {
    @NotBlank
    private String host;
    @NotBlank
    private String name;
}
