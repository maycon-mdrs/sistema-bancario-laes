package ufrn.imd.sistema_bancario.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ufrn.imd.sistema_bancario.models.Conta;
import ufrn.imd.sistema_bancario.services.exceptions.ContaJaExisteException;
import ufrn.imd.sistema_bancario.services.exceptions.ContaNaoEncontradaException;
import ufrn.imd.sistema_bancario.services.exceptions.SaldoInsuficienteException;
import ufrn.imd.sistema_bancario.services.exceptions.ValorInvalidoException;

class ContaServiceTest {

    ContaService contaService;

    @BeforeEach
    void setUp() {
        contaService = new ContaService();
    }

    @Nested
    class CadastrarConta {
        @Test
        void deveCadastrarContaComSaldoInicial() {
            Conta conta = contaService.criarConta("001", 100.0);
            assertNotNull(conta);
            assertEquals("001", conta.getNumero());
            assertEquals(100.0, conta.getSaldo());
        }

        @Test
        void deveCadastrarContaSemSaldoInicial() {
            Conta conta = contaService.criarConta("002", null);
            assertNotNull(conta);
            assertEquals(0.0, conta.getSaldo());
        }

        @Test
        void deveCadastrarContaComSaldoZero() {
            Conta conta = contaService.criarConta("003", 0.0);
            assertNotNull(conta);
            assertEquals(0.0, conta.getSaldo());
        }

        @Test
        void deveLancarExcecaoQuandoContaJaExiste() {
            contaService.criarConta("004", 100.0);
            assertThrows(ContaJaExisteException.class, () -> {
                contaService.criarConta("004", 50.0);
            });
        }
    }

    @Nested
    class ConsultarSaldo {
        @Test
        void deveConsultarSaldoQuandoContaExiste() {
            contaService.criarConta("004", 80.0);
            double saldo = contaService.consultarSaldo("004");
            assertEquals(80.0, saldo);
        }

        @Test
        void deveLancarExcecaoQuandoContaNaoEncontrada() {
            assertThrows(ContaNaoEncontradaException.class, () -> {
                contaService.consultarSaldo("999");
            });
        }
    }

    @Nested
    class Creditar {
        @Test
        void deveCreditarValorQuandoValorPositivo() {
            contaService.criarConta("005", 10.0);
            Conta conta = contaService.creditar("005", 40.0);
            assertEquals(50.0, conta.getSaldo());
        }

        @Test
        void deveLancarExcecaoQuandoCreditarValorNegativo() {
            contaService.criarConta("006", 10.0);
            assertThrows(ValorInvalidoException.class, () -> {
                contaService.creditar("006", -5.0);
            });
        }

        @Test
        void deveLancarExcecaoQuandoCreditarValorZero() {
            contaService.criarConta("007", 10.0);
            assertThrows(ValorInvalidoException.class, () -> {
                contaService.creditar("007", 0.0);
            });
        }

        @Test
        void deveLancarExcecaoQuandoContaNaoEncontrada() {
            assertThrows(ContaNaoEncontradaException.class, () -> {
                contaService.creditar("999", 50.0);
            });
        }
    }

    @Nested
    class Debitar {
        @Test
        void deveDebitarValorQuandoSaldoSuficiente() {
            contaService.criarConta("008", 100.0);
            Conta conta = contaService.debitar("008", 30.0);
            assertEquals(70.0, conta.getSaldo());
        }

        @Test
        void deveLancarExcecaoQuandoDebitarValorNegativo() {
            contaService.criarConta("009", 100.0);
            assertThrows(ValorInvalidoException.class, () -> {
                contaService.debitar("009", -10.0);
            });
        }

        @Test
        void deveLancarExcecaoQuandoDebitarValorZero() {
            contaService.criarConta("010", 100.0);
            assertThrows(ValorInvalidoException.class, () -> {
                contaService.debitar("010", 0.0);
            });
        }

        @Test
        void deveLancarExcecaoQuandoSaldoInsuficiente() {
            contaService.criarConta("011", 10.0);
            assertThrows(SaldoInsuficienteException.class, () -> {
                contaService.debitar("011", 200.0);
            });
        }

        @Test
        void deveLancarExcecaoQuandoContaNaoEncontrada() {
            assertThrows(ContaNaoEncontradaException.class, () -> {
                contaService.debitar("999", 50.0);
            });
        }
    }

    @Nested
    class Transferir {
        @Test
        void deveTransferirEntreContasQuandoSaldoSuficiente() {
            Conta origem = contaService.criarConta("012", 200.0);
            Conta destino = contaService.criarConta("013", 100.0);
            contaService.transferir("012", "013", 50.0);
            assertEquals(150.0, origem.getSaldo());
            assertEquals(150.0, destino.getSaldo());
        }

        @Test
        void deveLancarExcecaoQuandoTransferirValorNegativo() {
            contaService.criarConta("014", 100.0);
            contaService.criarConta("015", 100.0);
            assertThrows(ValorInvalidoException.class, () -> {
                contaService.transferir("014", "015", -50.0);
            });
        }

        @Test
        void deveLancarExcecaoQuandoTransferirValorZero() {
            contaService.criarConta("016", 100.0);
            contaService.criarConta("017", 100.0);
            assertThrows(ValorInvalidoException.class, () -> {
                contaService.transferir("016", "017", 0.0);
            });
        }

        @Test
        void deveLancarExcecaoQuandoSaldoInsuficienteParaTransferencia() {
            contaService.criarConta("018", 10.0);
            contaService.criarConta("019", 100.0);
            assertThrows(SaldoInsuficienteException.class, () -> {
                contaService.transferir("018", "019", 500.0);
            });
        }

        @Test
        void deveLancarExcecaoQuandoContaOrigemNaoEncontrada() {
            contaService.criarConta("020", 100.0);
            assertThrows(ContaNaoEncontradaException.class, () -> {
                contaService.transferir("999", "020", 50.0);
            });
        }

        @Test
        void deveLancarExcecaoQuandoContaDestinoNaoEncontrada() {
            contaService.criarConta("021", 100.0);
            assertThrows(ContaNaoEncontradaException.class, () -> {
                contaService.transferir("021", "999", 50.0);
            });
        }
    }
}
