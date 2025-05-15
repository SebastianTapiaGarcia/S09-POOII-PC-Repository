package controlador;

import Modelo.BancoFacade;
import Modelo.Movimiento;
import Modelo.Cuenta;

import java.util.List;

public class BancoControlador {
    private final BancoFacade facade;

    public BancoControlador() {
       this.facade = new BancoFacade();
    }

   public Cuenta crearCuenta(int id, String tipo, double saldo, double limite) {
        return facade.crearCuenta(id, tipo, saldo, limite);
    }
   
    public boolean transferir(int origenId, int destinIdo, double monto) {
        return facade.transferir(origenId, destinIdo, monto);
    }
    
    public List<Movimiento> verMovimientos(int cuentaId) {
       return facade.verMovimientos(cuentaId);
    }
    
    public List<Cuenta> listarCuentas() {
        return facade.listarCuentas();
    }
}
