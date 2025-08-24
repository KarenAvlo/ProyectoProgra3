
package com.mycompany.p1pro3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Indicaciones {
    private Medicamento medicamento;
    private int cantidad;
    private String indicaciones;
    private int duracion;
}
