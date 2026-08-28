    package com.example.orderflowapi.factory;

    import com.example.orderflowapi.enums.FormaPagamento;
    import com.example.orderflowapi.strategy.EstrategiaPagamento;
    import com.example.orderflowapi.strategy.PagamentoBoleto;
    import com.example.orderflowapi.strategy.PagamentoCartao;
    import com.example.orderflowapi.strategy.PagamentoPix;
    import org.springframework.stereotype.Component;

    @Component
    public class PagamentoFactory {

        public EstrategiaPagamento criar(FormaPagamento formaPagamento){
            EstrategiaPagamento pagamento;
            switch(formaPagamento)
            {
                case PIX:
                    pagamento = new PagamentoPix();
                    break;

                case CARTAO:
                    pagamento = new PagamentoCartao();
                    break;

                case BOLETO:
                    pagamento = new PagamentoBoleto();
                    break;

                case null, default:
                    throw new IllegalArgumentException("A forma de pagamento não pode ser suportada: " + formaPagamento);
            }
            return pagamento;
        }





    }
