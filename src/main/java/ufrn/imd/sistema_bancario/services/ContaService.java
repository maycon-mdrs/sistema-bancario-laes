package ufrn.imd.sistema_bancario.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import ufrn.imd.sistema_bancario.models.Conta;
import ufrn.imd.sistema_bancario.services.exceptions.ContaJaExisteException;
import ufrn.imd.sistema_bancario.services.exceptions.ContaNaoEncontradaException;
import ufrn.imd.sistema_bancario.services.exceptions.SaldoInsuficienteException;
import ufrn.imd.sistema_bancario.services.exceptions.ValorInvalidoException;

@Service
public class ContaService {

    private final Map<String, Conta> contas = new HashMap<>();

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

    public Conta buscarConta(String numeroConta) {
        Conta conta = contas.get(numeroConta);
        if (conta == null) {
            throw new ContaNaoEncontradaException(numeroConta);
        }
        return conta;
    }

    public double consultarSaldo(String numeroConta) {
        Conta conta = buscarConta(numeroConta);
        return conta.getSaldo();
    }

    public Conta creditar(String numeroConta, double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
        Conta conta = buscarConta(numeroConta);
        conta.creditar(valor);
        return conta;
    }

    public Conta debitar(String numeroConta, double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException();
        }
        Conta conta = buscarConta(numeroConta);
        verificarSaldoSuficiente(conta, valor);
        conta.debitar(valor);
        return conta;
    }

    public Conta transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        this.debitar(numeroContaOrigem, valor);
        this.creditar(numeroContaDestino, valor);
        return buscarConta(numeroContaOrigem);
    }

    private void verificarSaldoSuficiente(Conta conta, double valor) {
        if (conta.getSaldo() < valor) {
            throw new SaldoInsuficienteException(conta.getNumero());
        }
    }
}
