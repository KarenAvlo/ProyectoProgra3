package com.mycompany.p1pro3;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Medico extends Persona {

    private String clave;

    public Receta prescribirReceta(String id, String codMedicamento, int cant, String Indicaciones,
            int duración) {

        Receta re = new Receta();
        return re;
    }

    public Indicaciones CrearIndicacion(Medicamento medi, int cant, String indicaciones, int duracion) {
        Indicaciones i = new Indicaciones(medi, cant, indicaciones, duracion);
        return i;

    }
    private String especialidad;

}
