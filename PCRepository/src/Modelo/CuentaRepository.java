package Modelo;

import java.util.List;

public interface CuentaRepository {
    void guardar(Cuenta cuenta);
    List<Cuenta> obtenerTodas();
    Cuenta buscarPorId(int id);
}

