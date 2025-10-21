package cencosud.tests.generacionVtex;

import java.io.IOException;

import org.junit.Test;

import cencosud.business.apiNegocio;
import cencosud.exception.PatternNotFoundException;

public class CrearOrdenVtex extends apiNegocio{

    @Test()
    public void generaOrdenJumboMayor () throws IOException, PatternNotFoundException{
        crearOrdenJumbo("Jumbo", "jumbo_dad_prime_mayor");
    }

    @Test()
    public void generaOrdenJumboMenor () throws IOException, PatternNotFoundException{
        crearOrdenJumbo("Jumbo-Sisa", "jumbo_dad_prime_menor");
    }


    // Pendiente picking Usuario no Prime menos 13000
    @Test()
    public void generaOrdenNoPrime () throws IOException, PatternNotFoundException{
        crearOrdenJumbo("SISA_dad", "spid_dad_noprime_menor");
    }

    @Test()
    public void generaOrdenPrueba () throws IOException, PatternNotFoundException{
        crearOrdenJumbo("Jumbo-Sisa", "test_ordenes");
    }

    // Pendiente picking Usuario Prime menos 13000
    @Test()
    public void generaOrdenPruebaSpid () throws IOException, PatternNotFoundException{
        crearOrdenJumbo("Jumbo-Sisa", "spid_dad_prime_menor");
    }

    // Pendiente noPrime Pesable
    @Test()
    public void generaOrdenNoPrimePesable () throws IOException, PatternNotFoundException{
        crearOrdenJumbo("Jumbo-Sisa", "spid_dad_noprime_pesable_menor");
    }

    // Pendiente noPrime Pesable
    @Test()
    public void generaOrdenPrimeJumbo () throws IOException, PatternNotFoundException{
        crearOrdenJumbo("Jumbo-Sisa", "jumbo_dad_prime_normal_menor");
    }
    
}
