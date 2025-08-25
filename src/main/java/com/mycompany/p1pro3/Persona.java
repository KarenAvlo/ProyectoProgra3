package com.mycompany.p1pro3;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Persona {
    private String cedula;
    private String nombre;
    //los pacientes no tienen clase
}
