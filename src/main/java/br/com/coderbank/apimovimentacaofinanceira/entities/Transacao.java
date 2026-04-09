package br.com.coderbank.apimovimentacaofinanceira.entities;

import br.com.coderbank.apimovimentacaofinanceira.enums.StatusTransacao;
import br.com.coderbank.apimovimentacaofinanceira.enums.TipoTransacao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column
    private TipoTransacao tipoTransacao;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valor;

    @Column
    private String descricao;

    @Column
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column
    private StatusTransacao statusTransacao;

    @Column
    private UUID contaOrigemId;

    @Column
    private UUID contaDestinoId;
}
