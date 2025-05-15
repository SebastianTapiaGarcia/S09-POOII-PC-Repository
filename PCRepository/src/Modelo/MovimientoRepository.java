
package Modelo;

import java.util.List;


public interface MovimientoRepository {
    void guardar(Movimiento movimiento);
    List<Movimiento> buscarPorCuenta(int cuentaId);
}
