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



public class Medico extends Persona{
  private String clave;
    public void prescribirReceta(String id, String codMedicamento,int cant,String Indicaciones,
            int duración){
        
    }
    private String especialidad;
    
}
