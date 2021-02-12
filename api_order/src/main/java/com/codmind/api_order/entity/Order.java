package com.codmind.api_order.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reg_date", nullable = false, updatable = false)
    private LocalDateTime regDate;
    // FetchType.EAGER cargar la lista junto con el resto de los campos
    // por defecto esta en carga peresosa
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = OrderLine.class)
    private List<OrderLine> lines;

    @Column(name = "total", nullable = false)
    private Double total;

    @ManyToOne
    //CUANDO SE HAGA UN UPDATE ESTE CAMPO NO SE ENVIE
    @JoinColumn(name = "fk_user", updatable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
