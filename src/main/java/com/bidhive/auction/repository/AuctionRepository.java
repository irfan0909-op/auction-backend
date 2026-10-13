package com.bidhive.auction.repository;
import com.bidhive.auction.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AuctionRepository extends JpaRepository<Auction,Long> {
  List<Auction> findByStatusOrderByEndTimeAsc(String status);
}
