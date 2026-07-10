package ufrn.imd.sistema_bancario.services;

import java.util.HashMap;
import java.util.Map;

import ufrn.imd.sistema_bancario.models.Conta;
import ufrn.imd.sistema_bancario.services.exceptions.ContaJaExisteException;
import ufrn.imd.sistema_bancario.services.exceptions.ContaNaoEncontradaException;
import ufrn.imd.sistema_bancario.services.exceptions.SaldoInsuficienteException;
import ufrn.imd.sistema_bancario.services.exceptions.ValorInvalidoException;

public class ContaService {

    /*@ public model \map<String, Conta> mapContas;
      @ in contas;
      @*/
    private final Map<String, Conta> contas = new HashMap<>();

    /*@ public normal_behavior
      @   requires numeroConta != null && numeroConta.length() > 0;
      @   requires !mapContas.has(numeroConta);
      @   assignable mapContas;
      @   ensures mapContas.has(numeroConta);
      @   ensures \result != null;
      @*/
    public Conta criarConta(String numeroConta, Double saldoInicial) {
        if (contas.containsKey(numeroConta)) {
            throw new ContaJaExisteException(numeroConta);
        }
        Conta novaConta = new Conta(numeroConta);
        if (saldoInicial != null && saldoInicial > 0) {
            novaConta.setSaldo(saldoInicial);
        }
        contas.put(numeroConta, novaConta);
        return novaConta;
    }

    /*@ public normal_behavior
      @   requires numeroConta != null && !numeroConta.isEmpty();
      @   requires mapContas.has(numeroConta);
      @   assignable \nothing;
      @   ensures \result != null;
      @*/
    public Conta buscarConta(String numeroConta) {
        Conta conta = contas.get(numeroConta);
        if (conta == null) {
            throw new ContaNaoEncontradaException(numeroConta);
        }
        return conta;
    }

    /*@ requires numeroConta != null && numeroConta.length() > 0;
      @ requires mapContas.has(numeroConta);
      @ assignable \nothing;
      @ ensures \result >= 0;
      @*/
    public double consultarSaldo(String numeroConta) {
        Conta conta = buscarConta(numeroConta);
        return conta.getSaldo();
    }

    /*@ public normal_behavior
      @   requires numeroConta != null && !numeroConta.isEmpty();
      @   requires mapContas.has(numeroConta);
      @   requires valor > 0;
      @   assignable \nothing;
      @   ensures \result != null;
      @*/
    public Conta creditar(String numeroConta, double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
        Conta conta = buscarConta(numeroConta);
        conta.creditar(valor);
        return conta;
    }

    /*@ public normal_behavior
      @   requires numeroConta != null && numeroConta.length() > 0;
      @   requires mapContas.has(numeroConta);
      @   requires valor > 0;
      @   assignable \nothing;
      @   ensures \result != null;
      @ also
      @ public exceptional_behavior
      @   requires valor <= 0;
      @   signals_only ValorInvalidoException;
      @*/
    public Conta debitar(String numeroConta, double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
        Conta conta = buscarConta(numeroConta);
        verificarSaldoSuficiente(conta, valor);
        conta.debitar(valor);
        return conta;
    }

    /*@ public normal_behavior
      @   requires numeroContaOrigem != null && numeroContaDestino != null;
      @   requires mapContas.has(numeroContaOrigem) && mapContas.has(numeroContaDestino);
      @   requires !numeroContaOrigem.equals(numeroContaDestino);
      @   assignable \nothing;
      @*/
    public Conta transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        this.debitar(numeroContaOrigem, valor);
        this.creditar(numeroContaDestino, valor);
        return buscarConta(numeroContaOrigem);
    }

    /*@ private normal_behavior
      @   requires conta != null && conta.getSaldo() >= valor;
      @   assignable \nothing;
      @ also
      @ private exceptional_behavior
      @   requires conta != null && conta.getSaldo() < valor;
      @   signals_only SaldoInsuficienteException;
      @*/
    private void verificarSaldoSuficiente(Conta conta, double valor) {
        if (conta.getSaldo() < valor) {
            throw new SaldoInsuficienteException(conta.getNumero());
        }
    }
}