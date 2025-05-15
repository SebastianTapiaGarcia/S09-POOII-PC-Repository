package Modelo;

import java.util.Date;
import java.util.List;

public class BancoFacade {
    private final CuentaRepository    cuentaRepo;
    private final MovimientoRepository movRepo;

    public BancoFacade() {
        // Instancias tus repositorios
        this.cuentaRepo = new CuentaRepositoryArchivo();
        this.movRepo    = new MovimientoRepositoryArchivo();
    }

    public List<Cuenta> listarCuentas() {
        return cuentaRepo.obtenerTodas();
    }

    public Cuenta crearCuenta(int id, String tipo, double saldo, double limite) {
        Cuenta c = new CuentaBuilder()
                       .conId(id)
                       .conTipo(tipo)
                       .conSaldo(saldo)
                       .conLimite(limite)
                       .build();
        cuentaRepo.guardar(c);          // persiste en archivo
        return c;
    }

    public boolean transferir(int origenId, int destinoId, double monto) {
        Cuenta o = cuentaRepo.buscarPorId(origenId);
        Cuenta d = cuentaRepo.buscarPorId(destinoId);
        if (o == null || d == null || monto <= 0 || o.getSaldo() + o.getLimite() < monto) {
            return false;
        }
        // Ajusta saldos y persiste automáticamente
        o.setSaldo(o.getSaldo() - monto);
        d.setSaldo(d.getSaldo() + monto);
        cuentaRepo.guardar(o);  // podrías añadir un método actualizar si no quieres duplicar
        cuentaRepo.guardar(d);

        // Crear movimientos y persistirlos
        Date ahora = new Date();
        Movimiento m1 = new Movimiento(ahora, "Transferencia a " + destinoId, -monto, origenId);
        Movimiento m2 = new Movimiento(ahora, "Transferencia de " + origenId, monto, destinoId);
        movRepo.guardar(m1);
        movRepo.guardar(m2);
        return true;
    }

    public List<Movimiento> verMovimientos(int cuentaId) {
        return movRepo.buscarPorCuenta(cuentaId);
    }
}
