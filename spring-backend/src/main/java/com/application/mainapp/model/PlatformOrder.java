package com.application.mainapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;



@Entity
@Data
@NoArgsConstructor
public class PlatformOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long platformOrderID;

    @CreationTimestamp
    private Timestamp createTimestamp;

    @OneToMany(mappedBy = "platformOrder",cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;

    @ManyToOne
    @JoinColumn(name="platformUserID")
    private IndividualUser individualUser;


    public PlatformOrder(Timestamp createTimestamp, IndividualUser individualUser) {
        this.createTimestamp = createTimestamp;
        this.orderDetails = new ArrayList<>();
        this.individualUser = individualUser;
    }

}
