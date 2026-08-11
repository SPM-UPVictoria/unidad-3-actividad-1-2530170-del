package com.astrea.core;

public class NaveCarga extends NaveEspacial {
    private double cargaActual;
    private double cargaMaxima;

    public NaveCarga(String matricula, String modelo, double combustibleInicial, double capacidadCombustible, double cargaActual, double cargaMaxima) throws AstreaException {
        super(matricula, modelo, combustibleInicial, capacidadCombustible);
        if (cargaMaxima <= 0 || cargaActual < 0) {
            throw new AstreaException("La carga debe ser un valor positivo y la capacidad máxima mayor a cero.");
        }
        if (cargaActual > cargaMaxima) {
            throw new AstreaException("La carga actual no puede superar la capacidad máxima de carga.");
        }
        this.cargaActual = cargaActual;
        this.cargaMaxima = cargaMaxima;
    }

    public double getCargaActual() {
        return cargaActual;
    }

    public double getCargaMaxima() {
        return cargaMaxima;
    }

    @Override
    public void viajar(double distanciaAniosLuz) throws CombustibleInsuficienteException, AstreaException {
        if (distanciaAniosLuz <= 0) {
            throw new AstreaException("La distancia recorrida debe ser mayor a cero.");
        }

        // Consumo estándar: 1.5. Si la carga supera el 50%, se duplica a 3.0 unidades por año luz
        double consumoPorAnioLuz = (cargaActual > (cargaMaxima / 2.0)) ? 3.0 : 1.5;
        double combustibleNecesario = distanciaAniosLuz * consumoPorAnioLuz;

        if (combustibleNecesario > this.combustible) {
            // Atomicidad: el estado de 'combustible' no se altera
            throw new CombustibleInsuficienteException("Combustible insuficiente para completar la distancia con la carga actual.");
        }

        this.combustible -= combustibleNecesario;
    }
}