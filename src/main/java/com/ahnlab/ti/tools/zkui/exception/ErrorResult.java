package com.ahnlab.ti.tools.zkui.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    private int status;
    private String message;
}
