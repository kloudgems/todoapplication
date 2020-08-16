package com.application.todoapplication.dto;

import com.application.todoapplication.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Validated
@Setter
public class ItemPatchDto {
    private String name;

    private Status status;
}
