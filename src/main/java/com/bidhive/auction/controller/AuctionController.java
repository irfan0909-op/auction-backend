package com.bidhive.auction.controller;
import com.bidhive.auction.model.Auction;
import com.bidhive.auction.model.Bid;
import com.bidhive.auction.repository.AuctionRepository;
import com.bidhive.auction.repository.BidRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"http://localhost:5173","http://127.0.0.1:5173"})
public class AuctionController {
  private final AuctionRepository auctions; private final BidRepository bids;
  public AuctionController(AuctionRepository auctions, BidRepository bids){this.auctions=auctions;this.bids=bids;}

  @GetMapping("/health") public Map<String,String> health(){return Map.of("status","ok","service","BidHive Auction API");}
  @GetMapping("/auctions") public List<Auction> all(@RequestParam(required=false) String status){
    return status==null ? auctions.findAll() : auctions.findByStatusOrderByEndTimeAsc(status.toUpperCase());
  }
  @GetMapping("/auctions/{id}") public Auction get(@PathVariable Long id){return auctions.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Auction not found"));}

  public record AuctionRequest(@NotBlank String title,@NotBlank String description,
    @NotBlank String category,String imageUrl,@NotNull @DecimalMin("0.01") BigDecimal startingPrice,
    @NotNull @Future LocalDateTime endTime,@NotBlank String sellerName) {}
  @PostMapping("/auctions") @ResponseStatus(HttpStatus.CREATED)
  public Auction create(@Valid @RequestBody AuctionRequest r){
    Auction a=new Auction(); a.setTitle(r.title()); a.setDescription(r.description()); a.setCategory(r.category());
    a.setImageUrl(r.imageUrl()); a.setStartingPrice(r.startingPrice()); a.setCurrentPrice(r.startingPrice());
    a.setEndTime(r.endTime()); a.setSellerName(r.sellerName()); return auctions.save(a);
  }

  public record BidRequest(@NotBlank String bidderName,@NotNull @DecimalMin("0.01") BigDecimal amount) {}
  @PostMapping("/auctions/{id}/bids") @Transactional
  public Bid placeBid(@PathVariable Long id,@Valid @RequestBody BidRequest r){
    Auction a=auctions.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Auction not found"));
    if(!"ACTIVE".equals(a.getStatus()) || !a.getEndTime().isAfter(LocalDateTime.now()))
      throw new ResponseStatusException(HttpStatus.CONFLICT,"Auction has ended");
    if(r.amount().compareTo(a.getCurrentPrice().add(a.getMinBidIncrement()))<0)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bid must be at least "+a.getCurrentPrice().add(a.getMinBidIncrement()));
    a.setCurrentPrice(r.amount()); auctions.save(a); return bids.save(new Bid(id,r.bidderName(),r.amount()));
  }
  @GetMapping("/auctions/{id}/bids") public List<Bid> getBids(@PathVariable Long id){
    if(!auctions.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Auction not found");
    return bids.findByAuctionIdOrderByAmountDesc(id);
  }
}
