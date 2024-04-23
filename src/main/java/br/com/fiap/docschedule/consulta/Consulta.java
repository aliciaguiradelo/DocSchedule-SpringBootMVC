package br.com.fiap.docschedule.consulta;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import br.com.fiap.docschedule.user.User;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consulta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "{consulta.especialidade.notblank}")
    private String especialidade;
    @Size(min=10, max=255, message = "{consulta.motivo.size}")
    private String motivo;
    @NotNull(message = "{consulta.dataConsulta.notnull}")
    private LocalDate dataConsulta;
    @ManyToOne
    private User user;
}
