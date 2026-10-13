package com.bidhive.auction.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name="bids")
public class Bid {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false) private Long auctionId;
  @Column(nullable=false) private String bidderName;
  @Column(nullable=false, precision=12, scale=2) private BigDecimal amount;
  @Column(nullable=false) private LocalDateTime createdAt = LocalDateTime.now();
  protected Bid() {}
  public Bid(Long auctionId,String bidderName,BigDecimal amount){this.auctionId=auctionId;this.bidderName=bidderName;this.amount=amount;}
  public Long getId(){return id;} public Long getAuctionId(){return auctionId;} public String getBidderName(){return bidderName;}
  public BigDecimal getAmount(){return amount;} public LocalDateTime getCreatedAt(){return createdAt;}
}
