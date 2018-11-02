package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="TINTERVENCIONES",uniqueConstraints = { @UniqueConstraint(columnNames = { "AVERIA_ID", "mEeCaNico_id" }) })
public class Intervencion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Averia averia;
	@ManyToOne
	private Mecanico mecanico;
	private int minutos;

	@OneToMany(mappedBy = "intervencion")
	private Set<Sustitucion> sustituciones;

	Intervencion() {

	}

	public Intervencion(Mecanico mecanico, Averia averia) {
		super();
		Association.Intervenir.link(averia, this, mecanico);
	}

	public Averia getAveria() {
		return averia;
	}

	public Mecanico getMecanico() {
		return mecanico;
	}

	public int getMinutos() {
		return minutos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((averia == null) ? 0 : averia.hashCode());
		result = prime * result + ((mecanico == null) ? 0 : mecanico.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intervencion other = (Intervencion) obj;
		if (averia == null) {
			if (other.averia != null)
				return false;
		} else if (!averia.equals(other.averia))
			return false;
		if (mecanico == null) {
			if (other.mecanico != null)
				return false;
		} else if (!mecanico.equals(other.mecanico))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Intervencion [averia=" + averia + ", mecanico=" + mecanico + ", minutos=" + minutos + "]";
	}

	void _setAveria(Averia averia2) {
		this.averia = averia2;

	}

	void _setMecanico(Mecanico mecanico2) {
		this.mecanico = mecanico2;

	}

	public void setMinutos(int i) {
		this.minutos = minutos;

	}

	public Set<Sustitucion> getSustituciones() {
		return new HashSet<Sustitucion>(sustituciones);
	}

	Set<Sustitucion> _getSustituciones() {
		return sustituciones;
	}

	public double getImporte() {
		return (minutos * (averia.getVehiculo().getTipo().getPrecioHora() / 60))
				+ (sustituciones.parallelStream().map(Sustitucion::getImporte).reduce(0.0, ((a, b) -> a + b)));
	}

	public Long getId() {
		return id;
	}
}
