package br.com.fiap.docschedule.consulta;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consulta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "{task.especialidade.notblank}")
    private String especialidade;
    @Size(min=10, max=255, message = "{task.motivo.size}")
    private String motivo;
    @NotBlank(message = "{task.dataConsulta.notblank}")
    private LocalDate dataConsulta;
}
