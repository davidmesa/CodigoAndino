/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fsfb.servicios;

import com.fsfb.bos.Medico;
import com.fsfb.bos.Paciente;
import com.fsfb.bos.ReporteIMC;
import com.fsfb.bos.ReportePresionArterial;
import java.util.Date;
import java.util.HashMap;
import javax.ejb.Singleton;
import javax.security.auth.message.AuthException;

/**
 * Maneja los medicos y pacientes del hospital.
 * @author Cristian
 */
@Singleton
public class ServicioFSFB implements ServicioFSFBLocal {

    /**
     * Constante para la creación simulada de pacientes
     */
    public final static int NUMERO_PACIENTES=10;
    
    /**
     * Constante para la creación simulada de medicos
     */
    public final static int NUMERO_MEDICOS=5;
    
    /**
     * HashMap que almacena los medicos.
     */
    private HashMap<String, Medico> medicos;
    
    /**
     * HashMap que almacena los pacientes.
     */
    private HashMap<String, Paciente> pacientes;
    
    /**
     * Constructor
     */
    public ServicioFSFB()
    {
        medicos = new HashMap<String, Medico>();
        for(int i=0; i<NUMERO_MEDICOS; i++)
        {
            medicos.put("Medico"+(i+1), new Medico("Medico"+(i+1),"Medico"+(i+1)));
        }
        medicos.put("davidmesa", new Medico("davidmesa", "algo"));
        
        pacientes = new HashMap<String, Paciente>();
        for(int i=0; i<NUMERO_PACIENTES; i++)
        {
            pacientes.put("Paciente"+(i+1), new Paciente("Paciente"+(i+1),"Paciente"+(i+1),i+100,new Date()));
        }
        pacientes.put("cristiansierra", new Paciente("cristiansierra", "algo",160,new Date("22/11/1995")));
    }
    
    /**
     * Método que retorna null si la contraseña no es correcta.
     * @param usuario, El nombre del usuario
     * @param contrasena, Contraseña del Medico
     * @return Medico, dado el caso, que sea correcto el Usuario y Contraseña
     * @throws AuthException si no existe el Usuario
     */
    @Override
    public Medico darMedicoPorDatos(String usuario, String contrasena) throws AuthException
    {
        Medico buscado=medicos.get(usuario);
        if(buscado!=null)
        {
            if(!buscado.getContrasena().equals(contrasena))
            {
                throw new AuthException("La contraseña no es correcta");
            }
        }
        else
        {
            throw new AuthException("El usuario no existe");
        }
        return buscado;
    }
    
    /**
     * Registra el ingreso de un paciente al sistema.
     * @param token Token del paciente que quiere ingresar al sistema.
     * @return paciente Retorna el objeto que contiene la información del paciente que ingreso al sistema.
     * @exception AuthException en caso de que el paciente no exista.
     */
    @Override
    public Paciente darPacientePorToken(String token) throws AuthException
    {
        String[] fracciones=token.split("-");
        String paramUsuario=fracciones[1];
        String paramContrasena=fracciones[4];
        
        Paciente buscado=pacientes.get(paramUsuario);
        if(buscado!=null)
        {
            if(!buscado.getContrasena().equals(paramContrasena))
            {
                buscado=null;
            }
        }
        else
        {
            throw new AuthException();
        }
        return buscado;
    }
    
    /**
     * Crea un token para ser utilizado por el paciente registrado en la aplicacion.
     * @param usuario Nombre de usuario del paciente.
     * @param contrasena Contrasena del paciente. 
     * @return Token del usuario en caso de estar registrado.
     * @exception AuthException en caso de que el paciente no exista.
     */
    @Override
    public String darTokenDelPaciente(String usuario, String contrasena) throws AuthException
    {
        Paciente buscado=pacientes.get(usuario);
        if(buscado!=null)
        {
            if(!buscado.getContrasena().equals(contrasena))
            {
                throw new AuthException();
            }
            else
            {
                return "-"+usuario+"--"+contrasena+"-";
            }
        }
        else
        {
            throw new AuthException();
        }
    }

    @Override
    public String registarIMC(Paciente paciente, double peso, double altura) {
        if(altura!=-1)
        {
            paciente.setEstatura(altura);
        }
        //ReporteIMC retornado=paciente.registarIMC(peso, altura);
        double imc=paciente.registarIMC(peso, altura).getIMC();
        int edad=paciente.calcularEdad();
        String cadena=null;
        //  CONDICIONES DE ALERTA
        if(edad>60)
        {
            if(imc<25 || imc>27)
            {
                cadena="Te recomendamos que apliques la siguiente dieta, tienes un IMC un poco distante"
                        + " del ideal\n"
                        + "DIETA PARA EL ANCIANO SANO"
                        + "\nDesayuno: Leche Semidescremada o descremada, pan integral, "
                        + "cereales integrales, Confituras o Queso, Media Fruta"
                        + "\nAlmuerzo: Pasta o arroz o legumbres. Carne con poca grasa "
                        + "o pescado. Vegetales o Frutas (Fresca o Cocida)"
                        + "\nOnces: Yogurt poco azucarado o con miel. Galletas o tostadas"
                        + "\nCena: Sopa o pasta o verduras y papa. Pescado o queso o huevo. "
                        + "Fruta fresca o zumo (sin Azúcar)";
            }
        }
        else if(edad>20)
        {
            if(imc<18.5)
            {
                cadena="Tienes un estado de DELGADEZ ";
                cadena=cadena+((imc<16)?"SEVERA":(imc<17)?"MODERADA":"NO MUY PRONUNCIADA");
                cadena=cadena+"\n\n"
                        + "Te damos 9 consejos para subir de peso de manera saludable"
                        + "\n1. Come cada tres horas y trata de incluir una variedad de nutrientes cada"
                        + " vez que lo hagas, aún cuando se trate de bocadillos entre comidas. Recuerda"
                        + " siempre incluir proteínas, carbohidratos y grasas. Por ejemplo: yogurt con"
                        + " frutas y galletas con mantequilla de maní."
                        + "\n2. Consume calorías de fuentes apropiadas. Las grasas son de las que más calorías"
                        + " aportan, pero debes tener cuidado de que no sean grasas saturadas, para evitar que"
                        + " se eleve tu nivel de colesterol. Elige grasas de origen vegetal, como nueces y almendras,"
                        + "  aceite de oliva, aguacates (palta), aceitunas, pescados y otros alimentos ricos en"
                        + " calorías, vitaminas y minerales, en vez de productos con mucha grasa o azúcar (como los"
                        + " que come Julián, con sus papas fritas y la soda)."
                        + "\n3. Incluye muchas proteínas en tu dieta. En este caso, también deben ser de fuentes"
                        + " adecuadas. Elige productos bajos en grasa animal: selecciona las carnes magras, pero"
                        + " las nueces y las semillas son saludables."
                        + "\n4. Bebe mucho líquido. Los licuados de fruta son ideales porque aportan vitaminas, "
                        + "minerales y calorías. Puedes agregarles leche o yogurt bajos en grasa para aumentar "
                        + "las calorías que consumas."
                        + "\n5. Aumenta las calorías de tus comidas. Otra clave para lograrlo es agregar huevos, por "
                        + "ejemplo, al puré de papas, a la sopa y a los batidos."
                        + "\n6. Si te sientes satisfecho(a) muy pronto, prueba dejar de beber durante media hora antes "
                        + "y después de cada comida o tomar un pequeño trago de alcohol para que te abra el apetito "
                        + "(pero ten cuidado, recuerda que tomar alcohol en exceso puede ser perjudicial para la salud)."
                        + "\n7. Toma una cena tardía o come algo nutritivo antes de ir a dormir. Así el cuerpo no necesita "
                        + "tomar las calorías que necesita de las que consumió durante el día, ya que en ese momento, las "
                        + "células se están regenerando y reparando."
                        + "\n8. Duerme suficiente para que los músculos puedan reconstruirse adecuadamente. Distintas "
                        + "investigaciones han comprobado las ventajas para la salud de dormir bien y de dormir el número "
                        + "de horas necesarias."
                        + "\n9. Mantén una rutina de ejercicios y de entrenamiento físico. Si bien los ejercicios de "
                        + "resistencia son los que te permiten desarrollar masa muscular, no te olvides de los aeróbicos, "
                        + "que también son saludables.";
            }
            else if(imc>25)
            {
                cadena="En términos medicos, estás en un estado de OBESIDAD TIPO ";
                cadena=cadena+((imc<35)?"I":(imc<40)?"II":"III");
                cadena=cadena+"\n\nTe recomendamos seguir los siguientes consejos: "
                        + "\n1. Bebe dos vasos de agua antes de desayunar"
                        + "\n2. Empieza comiendo fruta desde muy temprano"
                        + "\n3. Realiza dos tentempiés sanos"
                        + "\n4. Come despacio"
                        + "\n5. Inclínate por productos orgánicos"
                        + "\n6. Intenta consumir altas dosis de fibra combinando alimentos"
                        + "\n7. Cena muy ligero"
                        + "\n8. Antes de acostarte, tómate una bebida relajante";
            }
        }
        else
        {
            //  Análisis de Menores de 20.
            //  No se hace concretamente...
            //  Ellos tienen mucha vida...
        }
        return cadena;
    }

    @Override
    public String registrarPresionArterial(Paciente paciente, int diastole, int siastole, int pulsaciones) {
        ReportePresionArterial reporte=paciente.registarPresionArterial(diastole, siastole, pulsaciones);
        int edad=paciente.calcularEdad();
        
        //  CONDICIONES DE ALERTA
        String cadena=null;
        if(edad>=18)
        {
            if(siastole>=180 || diastole>=110)
            {
                cadena="[ATENCIÓN] Caso de HIPERTENSIÓN ETAPA 3"
                        + "\nConserve la calma"
                        + "\nLe recomendamos ir lo más pronto a un centro de servicio"
                        + "\nNosotros en la FUNDACIÓN SANTAFE DE BOGOTÁ, estaremos atentos a su llegada";
            }
            else if(siastole>=160 || diastole>=100)
            {
                cadena="Caso de HIPERTENSIÓN ETAPA 2"
                        + "\nLe recomendamos hace un chequeo al médico en este mes."
                        + "\n\nPara bajar la Hipertensión:"
                        + "Le recomendamos llevar una dieta saludable, si tiene sintomas "
                        + "tome jugos naturales (En especial, de Lulo). Además considere "
                        + "seleccionar comida saludable."
                        + "\n Si el sintoma persiste venga a la central medica";
            }
            else if(siastole>=140 || diastole>=90)
            {
                cadena="Caso de HIPERTENSIÓN ETAPA 3"
                        + "\nLe recomendamos hacer un chequeo al médico por lo menos una vez en dos meses"
                        + "\n\nPara controlar la Hipertensión, usted puede:"
                        + "\n1. Evitar los alimentos salados"
                        + "\n2. Llevar una dieta moderada (Sin grasas)"
                        + "\n3. Consuma vitámina B de alimentos frescos"
                        + "\n4. Consuma alimentos ricos en Omega-3 y Potasio";
            }
            else
            {
                //  Puede que tenga Normal Alta o Normal o Óptima
                //  No afecta la salud del usuario
            }
        }
        else
        {
            //  No se revisa el caso de un niño...
        }
        return cadena;
    }
}
