package com.itsjaypatel.zomatoapp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderEntity {

    @Id
    private Long id;

    private Double amount;

    @OneToMany(mappedBy = "order")
    private Set<OrderItem> orderItems;

    @ManyToOne
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    private DeliveryPartner deliveryPartner;

    @ManyToOne
    private Restaurant restaurant;

    @CreationTimestamp
    private LocalDateTime createdAt;


    private String customerOtp;

    private String restaurantPartnerOtp;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
