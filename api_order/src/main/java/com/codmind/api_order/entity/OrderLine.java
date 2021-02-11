package com.codmind.api_order.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "order_lines")
public class OrderLine {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "fk_order", nullable = false, foreignKey = @ForeignKey(name = "fk_order"))
    private Order order;
    @ManyToOne
    @JoinColumn(name = "fk_product", nullable = false, foreignKey = @ForeignKey(name = "fk_product"))
    private Product product;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "quantity", nullable = false)
    private Double quantity;
    @Column(name = "total", nullable = false)
    private Double total;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return id.equals(orderLine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
