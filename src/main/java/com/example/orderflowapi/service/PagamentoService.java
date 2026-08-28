package com.example.orderflowapi.service;

import com.example.orderflowapi.dto.request.PagamentoRequest;
import com.example.orderflowapi.enums.FormaPagamento;
import com.example.orderflowapi.enums.StatusPagamento;
import com.example.orderflowapi.model.Pagamento;
import com.example.orderflowapi.model.Pedido;
import org.springframework.stereotype.Service;
import com.example.orderflowapi.repository.PagamentoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    public PagamentoService (PagamentoRepository pagamentoRepository){
        this.pagamentoRepository = pagamentoRepository;
    }

   public List<Pagamento> listarTodos(){
        return pagamentoRepository.findAll();
   }

   public Optional<Pagamento> buscarPorId(Integer id){
        return pagamentoRepository.findById(id);
   }

   public Pagamento montarPagamento(PagamentoRequest request){
          FormaPagamento formaPagamento = request.getFormaPagamento();
          Pagamento pagamento = Pagamento.builder()
                  .statusPagamento(StatusPagamento.PENDENTE)
                  .formaPagamento(formaPagamento)
                  .build();

          return pagamento;
   }
   public Pagamento cadastrar(Pagamento pagamento){
        return pagamentoRepository.save(pagamento);
   }

   public void excluir(Integer id){
        pagamentoRepository.deleteById(id);
   }
   public Pagamento atualizar(Integer id, Pagamento pagamento){

        pagamento.setPagamentoId(id);

       return pagamentoRepository.save(pagamento);
       }
}
