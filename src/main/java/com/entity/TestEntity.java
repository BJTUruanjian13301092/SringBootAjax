package com.entity;

import javax.persistence.*;

import lombok.Data;
/**
 * Created by User on 2017/3/30.
 */

@Entity
@Data
@Table(name="tonghuashun")
public class TestEntity {

    @Id
    @GeneratedValue
    private long Id;

    @Column(name="tradedate")
    private String tradeDate;

    @Column(name="code")
    private String code;

    @Column(name="shortname")
    private String shortName;

    @Column(name="newprice")
    private String newPrice;

    @Column(name="dealprice")
    private String dealPrice;

    @Column(name="volume")
    private String volume;

    @Column(name="buyer")
    private String buyer;

    @Column(name="seller")
    private String seller;


}
