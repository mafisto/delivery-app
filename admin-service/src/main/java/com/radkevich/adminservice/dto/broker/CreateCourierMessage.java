package com.radkevich.adminservice.dto.broker;

import com.radkevich.adminservice.dto.CreateCourierDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCourierMessage implements Serializable {
    CreateCourierDto data;
}
