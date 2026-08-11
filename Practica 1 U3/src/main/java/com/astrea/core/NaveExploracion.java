package com.astrea.core;

public class NaveExploracion extends NaveEspacial implements Propulsable, Defendible {
    private double integridadEscudo;
    private boolean hiperviajeListo;

    public NaveExploracion(String matricula, String modelo, double combustibleInicial, double capacidadCombustible) throws AstreaException {
        super(matricula, modelo, combustibleInicial, capacidadCombustible);
        this.integridadEscudo = 100.0;
        this.hiperviajeListo = true;
    }

    public double getIntegridadEscudo() {
        return integridadEscudo;
    }

    public boolean isHiperviajeListo() {
        return hiperviajeListo;
    }

    @Override
    public void viajar(double distanciaAniosLuz) throws CombustibleInsuficienteException, AstreaException {
        if (distanciaAniosLuz <= 0) {
            throw new AstreaException("La distancia recorrida debe ser mayor a cero.");
        }

        double consumoPorAnioLuz = 0.8;
        double combustibleNecesario = distanciaAniosLuz * consumoPorAnioLuz;

        if (combustibleNecesario > this.combustible) {
            throw new CombustibleInsuficienteException("Combustible insuficiente para la travesía de exploración.");
        }

        this.combustible -= combustibleNecesario;
    }

    @Override
    public void activarHiperviaje(double factorWarp) throws FallaSistemasException, CombustibleInsuficienteException {
        if (this.combustible < 50.0) {
            // Atomicidad: el estado no se altera si no alcanza el combustible
            throw new CombustibleInsuficienteException("Se requieren al menos 50.0 unidades de combustible para el salto hiperespacial.");
        }

        if (factorWarp > 9.0) {
            // Simulación probabilística: 30% de probabilidad de fallo en Warp extremo
            if (Math.random() < 0.30) {
                this.hiperviajeListo = false;
                throw new FallaSistemasException("Fallo en el núcleo de salto FTL por factor Warp extremo.");
            }
        }

        this.combustible -= 50.0;
    }

    @Override
    public void recibirImpacto(double potenciaDano) throws EscudoCriticoException {
        if (this.integridadEscudo <= 0.0) {
            throw new EscudoCriticoException("Intento de operación con escudos inhabilitados.");
        }

        this.integridadEscudo -= potenciaDano;

        if (this.integridadEscudo <= 0.0) {
            this.integridadEscudo = 0.0;
            throw new EscudoCriticoException("Impacto destructivo: integridad del escudo reducida a nivel crítico.");
        }
    }
}