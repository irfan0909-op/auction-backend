package com.bidhive.auction.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name="auctions")
public class Auction {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false) private String title;
  @Column(nullable=false, length=3000) private String description;
  @Column(nullable=false) private String category = "General";
  private String imageUrl;
  @Column(nullable=false, precision=12, scale=2) private BigDecimal startingPrice;
  @Column(nullable=false, precision=12, scale=2) private BigDecimal currentPrice;
  @Column(nullable=false, precision=12, scale=2) private BigDecimal minBidIncrement = BigDecimal.ONE;
  @Column(nullable=false) private LocalDateTime endTime;
  @Column(nullable=false) private String sellerName;
  @Column(nullable=false) private String status = "ACTIVE";
  protected Auction() {}
  public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
  public String getCategory(){return category;} public void setCategory(String v){category=v;}
  public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
  public BigDecimal getStartingPrice(){return startingPrice;} public void setStartingPrice(BigDecimal v){startingPrice=v;}
  public BigDecimal getCurrentPrice(){return currentPrice;} public void setCurrentPrice(BigDecimal v){currentPrice=v;}
  public BigDecimal getMinBidIncrement(){return minBidIncrement;} public void setMinBidIncrement(BigDecimal v){minBidIncrement=v;}
  public LocalDateTime getEndTime(){return endTime;} public void setEndTime(LocalDateTime v){endTime=v;}
  public String getSellerName(){return sellerName;} public void setSellerName(String v){sellerName=v;}
  public String getStatus(){return status;} public void setStatus(String v){status=v;}
}
