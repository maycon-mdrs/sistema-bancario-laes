package ufrn.imd.sistema_bancario.services;

import java.util.HashMap;
import java.util.Map;

import ufrn.imd.sistema_bancario.models.Conta;
import ufrn.imd.sistema_bancario.services.exceptions.ContaJaExisteException;
import ufrn.imd.sistema_bancario.services.exceptions.ContaNaoEncontradaException;
import ufrn.imd.sistema_bancario.services.exceptions.SaldoInsuficienteException;
import ufrn.imd.sistema_bancario.services.exceptions.ValorInvalidoException;

public class ContaService {

    /*@ spec_public non_null @*/
    private final Map<String, Conta> contas = new HashMap<>();

    /*@ public behavior
      @   requires numeroConta != null && numeroConta.length() > 0 && contas != null;
      @   requires !contas.containsKey(numeroConta);
      @   assignable contas.*;
      @   ensures contas.containsKey(numeroConta);
      @   ensures \result != null;
      @   ensures \result.getNumero().equals(numeroConta);
      @   signals_only RuntimeException; // <--- Allows Map's unchecked exceptions to escape
      @ also
      @ public exceptional_behavior
      @   requires numeroConta != null
      @         && numeroConta.length() > 0
      @         && contas.containsKey(numeroConta);
      @   assignable \nothing;
      @   signals_only ContaJaExisteException, RuntimeException;
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
      @   requires numeroConta != null && numeroConta.length() > 0;
      @   requires contas.get(numeroConta) != null;
      @   assignable \nothing;
      @   ensures \result != null;
      @   ensures \result == contas.get(numeroConta);
      @ also
      @ public exceptional_behavior
      @   requires numeroConta == null || numeroConta.length() == 0 || contas.get(numeroConta) == null;
      @   assignable \nothing;
      @   signals_only ContaNaoEncontradaException;
      @*/
    // @ pure
    public Conta buscarConta(String numeroConta) {
        if (numeroConta == null || numeroConta.isEmpty()) {
            throw new ContaNaoEncontradaException(numeroConta);
        }

        /*@ nullable @*/ Conta conta = contas.get(numeroConta);
        if (conta == null) {
            throw new ContaNaoEncontradaException(numeroConta);
        }
        return conta;
    }

    /*@ public normal_behavior
      @   requires numeroConta != null && numeroConta.length() > 0;
      @   requires contas.get(numeroConta) != null;
      @   assignable \nothing;
      @   ensures \result >= 0;
      @ also
      @ public exceptional_behavior
      @   requires numeroConta == null || numeroConta.length() == 0 || !contas.containsKey(numeroConta);
      @   assignable \nothing;
      @   signals_only ContaNaoEncontradaException;
      @*/
    public /*@ pure @*/ double consultarSaldo(String numeroConta) {
        Conta conta = buscarConta(numeroConta);
        return conta.getSaldo();
    }

    /*@ public normal_behavior
      @   requires numeroConta != null && numeroConta.length() > 0;
      @   requires contas.get(numeroConta) != null;
      @   requires valor > 0;
      @   assignable \everything;
      @   ensures \result != null;
      @ also
      @ public exceptional_behavior
      @   requires valor <= 0;
      @   assignable \nothing;
      @   signals_only ValorInvalidoException;
      @ also
      @ public exceptional_behavior
      @   requires valor > 0;
      @   requires numeroConta == null || numeroConta.length() == 0 || !contas.containsKey(numeroConta);
      @   assignable \nothing;
      @   signals_only ContaNaoEncontradaException;
      @*/
    public Conta creditar(String numeroConta, double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
        Conta conta = buscarConta(numeroConta);
        conta.creditar(valor);
        return conta;
    }

    /*@ public exceptional_behavior
  @   requires valor <= 0;
  @   assignable \nothing;
  @   signals_only ValorInvalidoException;
  @ also
  @ public exceptional_behavior
  @   requires valor > 0;
  @   requires numeroConta == null
  @         || numeroConta.length() == 0
  @         || !contas.containsKey(numeroConta);
  @   assignable \nothing;
  @   signals_only ContaNaoEncontradaException;
  @ also
  @ public exceptional_behavior
  @   requires valor > 0;
  @   requires numeroConta != null;
  @   requires numeroConta.length() > 0;
  @   requires contas.containsKey(numeroConta);
  @   requires contas.get(numeroConta).getSaldo() < valor;
  @   assignable \nothing;
  @   signals_only SaldoInsuficienteException;
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
      @   requires contas.containsKey(numeroContaOrigem) && contas.containsKey(numeroContaDestino);
      @   requires !numeroContaOrigem.equals(numeroContaDestino);
      @   requires valor > 0;
      @   requires contas.get(numeroContaOrigem).getSaldo() >= valor;
      @   assignable \everything;
      @   ensures \result != null;
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
      @   assignable \nothing;
      @   signals_only SaldoInsuficienteException;
      @*/
    private /*@ pure @*/ void verificarSaldoSuficiente(Conta conta, double valor) {
        if (conta.getSaldo() < valor) {
            throw new SaldoInsuficienteException(conta.getNumero());
        }
    }
}