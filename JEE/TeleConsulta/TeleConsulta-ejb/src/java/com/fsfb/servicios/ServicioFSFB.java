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
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.security.auth.message.AuthException;

/**
 * Maneja los medicos y pacientes del hospital.
 * @author Cristian
 */
@Singleton
public class ServicioFSFB implements ServicioFSFBLocal {

    private static ServicioFSFB instancia;
    
    /**
     * Constante para la creación simulada de pacientes
     */
    public final static int NUMERO_PACIENTES=20;
    
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
     * Arreglo que indica las personas que tienen emergencia
     */
    private ArrayList<Paciente> alertados;
    
    /**
     * Arreglo que indica las personas que tienen una consulta
     */
    private ArrayList<Paciente> consultados;
    
    /**
     * Arreglo de cantidad de datos por Semana
     */
    private Integer[] registrosSemanales;
    
    private static final String PUBLIC_KEY = "30819f300d06092a864886f70d0101010"
            + "50003818d0030818902818100a668b516692d4bb0b4ead35570af418dc7a28e4"
            + "5ed6cf451e3c28943edf3a651693272fe1e1eca84411bb75fca63d09b7f78208"
            + "8fefd9977053bd67d075aae22c09f7faabfdcd51f9f7c1938a130988f0162ce6"
            + "329990725be35c61102a4ea708a9f1b428706de72b5c3ec4e890dec2248c272a"
            + "91efd863dd55ad1cb3c5ba6030203010001";
    
    private static final String PRIVATE_KEY = "30820276020100300d06092a864886f70"
            + "d0101010500048202603082025c02010002818100a668b516692d4bb0b4ead355"
            + "70af418dc7a28e45ed6cf451e3c28943edf3a651693272fe1e1eca84411bb75fc"
            + "a63d09b7f782088fefd9977053bd67d075aae22c09f7faabfdcd51f9f7c1938a1"
            + "30988f0162ce6329990725be35c61102a4ea708a9f1b428706de72b5c3ec4e890"
            + "dec2248c272a91efd863dd55ad1cb3c5ba603020301000102818030ddbc97a929"
            + "18f9fa169f1a8eed981577533fee3eeb68cf874f8019878dae006820dd6dcc108"
            + "4add3a4bcf38f2e427af732a2733855e633f240811ad40707ed48d3e0d4a23cf7"
            + "d4135f954702b1257aebdcfa1644f078c7c714fbb60762e5023204f622305fe89"
            + "ccbf3df32a1029ac301e9e3d149ba0321844cb8d3c9bcb6d9024100e35612f50c"
            + "8c8d23a6e3dad60dd425036f37682453ab28d8b1df0483eb9869eb8f153adb1b9"
            + "33cf27db933d95560c4ba9b3b7fbac7610f31898e1e0c2c196595024100bb6408"
            + "93df44eb963f2e341b9f515415aec540b77fc9b0b2a6e2f342d97293d8a71b92b"
            + "43f08d3f920c47bafd92a57f320f021f70fab4c802dc29aa6d530c73702406da2"
            + "b725c2d58dc3a1dac550f1fe5b935a718821eccfe0b510a3135463ac6f7890da9"
            + "635d108a31df70ff83759fb7f24d7744c57518c377d966f19829949ee39024100"
            + "b1c42ceaf2a434055d3c5c8c53afd85f956364886f3e8b547f42ced87ce5d7e17"
            + "06d94d74ef0f5fde11ae3e726d1a78b6a94c2f3d8367da51f43fc6805d8773302"
            + "402c3c5b6448475beeec91e0a61c6565d47ddbb7ea686fe5327548d878d0be126"
            + "247885dda7efef5e6eb5a1e6afb69605587693a9157fce2814ba74d83d70c9d64";
    
    private static final String SYMMETRIC_KEY = "da6acd66fe5ee39b29a3399feafb9a2"
            + "52baf57f2827f80e85ea459d5817782c1";
    
    /**
     * Constructor
     */
    public ServicioFSFB()
    {   
        alertados = new ArrayList<Paciente>();
        
        consultados = new ArrayList<Paciente>();
        
        registrosSemanales=new Integer[7];
        for(int i=0; i<7; i++)
        {
            registrosSemanales[i]=0;
        }
        
        medicos = new HashMap<String, Medico>();
        for(int i=0; i<NUMERO_MEDICOS; i++)
        {
            medicos.put("Medico"+(i+1), new Medico("Medico"+(i+1),"Medico"+(i+1)));
        }
        Medico david=new Medico("davidmesa", "algo");
        medicos.put(david.getUsuario(), david);
        
        pacientes = new HashMap<String, Paciente>();
        for(int i=0; i<NUMERO_PACIENTES; i++)
        {
            Date fn=new Date(1910+(int) Math.random()*100,(int)Math.random()*12, (int) Math.random()*28);
            Paciente nuevo=new Paciente("Paciente"+(i+1),"Paciente"+(i+1),(i*4)+100, fn);
            pacientes.put(nuevo.getUsuario(), nuevo);
            if(i%2==0 && i<NUMERO_PACIENTES/3)
            {
                david.agregarPaciente(nuevo);
            }
        }
        Paciente cristian=new Paciente("cristiansierra", "algo", 160, new Date("22/11/1995"));
        pacientes.put(cristian.getUsuario(), cristian);
        david.agregarPaciente(cristian);
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
        String[] fracciones=token.split("--");
        String paramUsuario=fracciones[0];
        String paramContrasena=fracciones[1];
        
        Paciente buscado=pacientes.get(paramUsuario);
        if(buscado!=null)
        {
            if(!buscado.getContrasena().equals(paramContrasena))
            {
                throw new AuthException("La contraseña no es correcta.");
            }
        }
        else
        {
            throw new AuthException("No se ha encontrado la cuenta.");
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
                throw new AuthException("La contraseña no es correcta.");
            }
            else
            {
                return usuario+"--"+contrasena;
            }
        }
        else
        {
            throw new AuthException("No se ha encontrado una cuenta con los datos ingresados.");
        }
    }

    @Override
    public String registarIMC(Paciente paciente, double peso, int altura) {
        System.out.println("peso:"+peso+"-altura:"+altura);
        if(altura!=0)
        {
            System.out.println("Entro");
            paciente.setEstatura(altura);
        }
        ReporteIMC retornado=paciente.registarIMC(peso, altura);
        
        Date fechaReporte=retornado.getFechaReporte();
        int numeroDia=fechaReporte.getDay();
        registrarReporte(numeroDia);
        
        double imc=retornado.getIMC();
        int edad=paciente.calcularEdad();
        String cadena=null;
        String status="";
        //  CONDICIONES DE ALERTA
        if(edad>60)
        {
            if(imc<25 || imc>27)
            {
                status= "alert";
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
                status="alert";
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
                status="alert";
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
            // TODO refinar esto
            if(imc<18.5)
            {
                status="alert";
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
            } else if(imc>25) {
                status="alert";
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
            } else {
                status = "ok";
                cadena = "Presenta un aceptado IMC";
            }
        }
        if(status.equals("alert"))
        {
            agregarAConsultados(paciente);
        }
        String cadena2 = "{\"status\":\"" + status + "\", \"mensaje\":\"" + cadena + "\"}";
        return cadena2;
    }
    
    @Lock(LockType.WRITE)
    public void agregarAConsultados( Paciente paciente )
    {
        consultados.add(paciente);
        System.out.print("Agrega a la tabla!!"+consultados.size());
    }

    @Override
    public String registrarPresionArterial(Paciente paciente, int diastole, int siastole, int pulsaciones) {
        ReportePresionArterial reporte=paciente.registarPresionArterial(diastole, siastole, pulsaciones);
        int edad=paciente.calcularEdad();
        
        Date fechaReporte=reporte.getFechaReporte();
        int numeroDia=fechaReporte.getDay();
        registrarReporte(numeroDia);
        
        //  CONDICIONES DE ALERTA
        String cadena=null;
        String status = "";
        if(edad>=18)
        {
            if(siastole>=180 || diastole>=110)
            {
                status="alert";
                cadena="[ATENCIÓN] Caso de HIPERTENSIÓN ETAPA 3"
                        + "\nConserve la calma"
                        + "\nLe recomendamos ir lo más pronto a un centro de servicio"
                        + "\nNosotros en la FUNDACIÓN SANTAFE DE BOGOTÁ, estaremos atentos a su llegada";
            }
            else if(siastole>=160 || diastole>=100)
            {
                status="alert";
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
                status="alert";
                cadena="Caso de HIPERTENSIÓN ETAPA 1"
                        + "\nLe recomendamos hacer un chequeo al médico por lo menos una vez en dos meses"
                        + "\n\nPara controlar la Hipertensión, usted puede:"
                        + "\n1. Evitar los alimentos salados"
                        + "\n2. Llevar una dieta moderada (Sin grasas)"
                        + "\n3. Consuma vitámina B de alimentos frescos"
                        + "\n4. Consuma alimentos ricos en Omega-3 y Potasio";
            }
            else
            {
                // TODO
                //  Puede que tenga Normal Alta o Normal o Óptima
                //  No afecta la salud del usuario
                status = "ok";
                cadena = "Presenta un aceptada Presión Arterial";
            }
        }
        else
        {
            if(siastole>=180 || diastole>=110)
            {
                status="alert";
                cadena="[ATENCIÓN] Caso de HIPERTENSIÓN ETAPA 3"
                        + "\nConserve la calma"
                        + "\nLe recomendamos ir lo más pronto a un centro de servicio"
                        + "\nNosotros en la FUNDACIÓN SANTAFE DE BOGOTÁ, estaremos atentos a su llegada";
            }
            else if(siastole>=160 || diastole>=100)
            {
                status="alert";
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
                status="alert";
                cadena="Caso de HIPERTENSIÓN ETAPA 1"
                        + "\nLe recomendamos hacer un chequeo al médico por lo menos una vez en dos meses"
                        + "\n\nPara controlar la Hipertensión, usted puede:"
                        + "\n1. Evitar los alimentos salados"
                        + "\n2. Llevar una dieta moderada (Sin grasas)"
                        + "\n3. Consuma vitámina B de alimentos frescos"
                        + "\n4. Consuma alimentos ricos en Omega-3 y Potasio";
            }
            else
            {
                // TODO
                //  Puede que tenga Normal Alta o Normal o Óptima
                //  No afecta la salud del usuario
                status = "ok";
                cadena = "Presenta un aceptada Presión Arterial";
            }
        }
        if(status.equals("alert"))
        {
            agregarAAlertados(paciente);
        }
        String cadena2 = "{\"status\":\"" + status + "\", \"mensaje\":\"" + cadena + "\"}";
        return cadena2;
    }
    
    @Lock(LockType.WRITE)
    private void agregarAAlertados(Paciente paciente)
    {
        alertados.add(paciente);
    }
    
    @Lock(LockType.WRITE)
    private void registrarReporte( int i )
    {
        registrosSemanales[i]=registrosSemanales[i]+1;
    }

    @Override
    public ArrayList<Paciente> darPacientesConEmergencia() {
        return alertados;
    }
    
    @Override
    public ArrayList<Paciente> darPacientesConConsulta()
    {
        System.out.print("Pide la tabla" + consultados.size());
        return consultados;
    }

    @Override
    public Integer[] darRegistrosSemanales() {
        return registrosSemanales;
    }
    
    public static ServicioFSFB darInstancia()
    {
        if(instancia == null)
        {
            System.out.print("Crea instancia");
            instancia = new ServicioFSFB();
        }
        return instancia;
    }
    
    public String getPublicKey() {
        return PUBLIC_KEY;
    }
    
    public String getSymmetricKey() throws Exception {
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePrivate(
                new PKCS8EncodedKeySpec(destransformar(PRIVATE_KEY))));
        return transformar(c.doFinal(destransformar(SYMMETRIC_KEY)));
    }
    
    private byte[] destransformar(String ss) {
        byte[] ret = new byte[ss.length()/2];
        for (int i = 0 ; i < ret.length ; i++) {
            ret[i] = (byte) Integer.parseInt(ss.substring(i*2,(i+1)*2), 16);
        }
        return ret;
    }
    
    private String transformar(byte[] b) {
        String ret = "";
        for (int i = 0 ; i < b.length ; i++) {
            String g = Integer.toHexString(((char)b[i])&0x00ff);
            ret += (g.length()==1?"0":"") + g;
        }
        return ret;
    }
    
    public String darCifrado(String s) throws Exception {
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("AES/ECB/PKCS5Padding").generatePrivate(
                new PKCS8EncodedKeySpec(destransformar(SYMMETRIC_KEY))));
        return transformar(c.doFinal(destransformar(s)));
    }
}