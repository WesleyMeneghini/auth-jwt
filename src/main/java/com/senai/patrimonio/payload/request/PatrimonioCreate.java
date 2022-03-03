package com.senai.patrimonio.payload.request;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatrimonioCreate {
    private String descricao;
    @NotBlank
    private Long funcionarioId;
    @NotBlank
    private Long localId;
}
