package br.com.code7.financasbackend.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "financas", name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome", nullable = false)
    private String nome;
	
	@Version
	private Long version;

	@CreationTimestamp
	@Column(name = "data_criacao")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime dataCriacao;
	
	@UpdateTimestamp
	@Column(name = "data_atualizacao")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime dataAtualizacao;

}
